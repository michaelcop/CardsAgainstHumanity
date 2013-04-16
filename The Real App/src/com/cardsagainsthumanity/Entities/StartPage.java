package com.cardsagainsthumanity.Entities;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class StartPage extends Activity {
	
	private EditText inputUsername;
	private EditText inputPassword;
	private String userName;
	private String password;
    private EditText urlText;
    private TextView login;
    private String results;
    private String userId;
    private String sha;
    Button v;
    public static final String SPREF_USER = "othPrefs";
    private ProgressBar loginProgress;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);
		v = (Button) findViewById(R.id.LogIn);
		
		
		inputUsername = (EditText) findViewById(R.id.username);
		inputPassword = (EditText) findViewById(R.id.password);
		login = (TextView) findViewById(R.id.myText);
		
		v.setOnClickListener(new OnClickListener() {
			
	        public void onClick(View v) 
	        {
	        	//login
	        	userName = inputUsername.getText().toString();
	        	password = inputPassword.getText().toString();
	        	
	        	loginProgress = (ProgressBar) findViewById(R.id.loginProgressBar);
	        	
	        	
	        	/*
	        	if(userName.length() < 5 || password.length() < 5)
	        	{
	        		Toast.makeText(StartPage.this, "Error user name and password must be atleast 5 characters.  Try Again!", Toast.LENGTH_LONG).show();
	        		inputUsername.setText("");
	        		inputPassword.setText("");
	        		return;
	        	}
	        	*/
	        	
	        	//Encrypt pw ---------------------------------------------------------------------------------
	        	String stringThatNeedsToBeEncrpyted = password; // Value to encrypt
                MessageDigest Enc = null;
                
                try {
                    Enc = MessageDigest.getInstance("SHA-1");
                } catch (NoSuchAlgorithmException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } // Encryption algorithm
                Enc.update(stringThatNeedsToBeEncrpyted.getBytes(), 0, stringThatNeedsToBeEncrpyted.length());
                sha = new BigInteger(1, Enc.digest()).toString(16); //Make the Encrypted string
                //Digest made ---------------------------------------------------------------------------------
                
                //Send to db -----------------------------------------------------------------------------------
	        	String stringUrl = "http://54.225.225.185:8080/ServerAPP/Login?User="+userName+"&password="+sha;
	        	ConnectivityManager connMgr = (ConnectivityManager) 
        		getSystemService(Context.CONNECTIVITY_SERVICE);
                
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                	//Intent myIntent = new Intent(v.getContext(), LoadingLogIn.class);
    				//startActivity(myIntent);
                    new DownloadWebpageText().execute(stringUrl);
                    
                } else {
                    login.setText("No network connection available.");
                } // End send to db------------------------------------------------------------------------------
                
        	}
			

        }); 
		
		Button ca = (Button) findViewById(R.id.CreateAccount);
	
		ca.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) 
			{
				Intent createAccountIntent = new Intent(v.getContext(), CreateAccount.class);
                startActivity(createAccountIntent);
				
			}
			
		});
	}
	
	 private class DownloadWebpageText extends AsyncTask
	 {
		 	protected void onPreExecute()
			{
				loginProgress.setVisibility(ProgressBar.VISIBLE);
			}
	    	@Override
	        protected Object doInBackground(Object... urls) {
	            // params comes from the execute() call: params[0] is the url.
	            try {
	                return downloadUrl((String) urls[0]);
	            } catch (IOException e) {
	                return "Unable to retrieve web page. URL may be invalid.";
	            }
	        }
	    	
	        // onPostExecute displays the results of the AsyncTask.
	        @Override
	        protected void onPostExecute(Object result) 
	        {
	        	 loginProgress.setVisibility(ProgressBar.INVISIBLE);
	            results = (String) result.toString();
	            results = results.trim();
	            //check the result for the what's needed to move on
	            String[] resultArr = results.split(":"); 
                if(resultArr[0].equals("User")){
                	
                	//Store Username and ID in SharedPref ------------------------------
                	SharedPreferences othSettings = getSharedPreferences(SPREF_USER, 0);
                	SharedPreferences.Editor spEditor = othSettings.edit();
                	spEditor.putString("UserName", userName.toString()).commit();
                	userId = resultArr[1].toString();
                	spEditor.putString("ID", userId).commit();
                	//End storing username ---------------------------------------------
                	
                	//Need to encrypt password and store ----------------------------------------------------------
                	String stringThatNeedsToBeEncrpyted = password; // Value to encrypt
                    MessageDigest Enc = null;
                    try {
                        Enc = MessageDigest.getInstance("SHA-1");
                    } catch (NoSuchAlgorithmException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } // Encryption algorithm
                    Enc.update(stringThatNeedsToBeEncrpyted.getBytes(), 0, stringThatNeedsToBeEncrpyted.length());
                    String sha1 = new BigInteger(1, Enc.digest()).toString(16); //Make the Encrypted string
                    
                	//Store hash to file
                    spEditor.putString("digest", sha1).commit();			         	
                	//End store password -------------------------------------------------------------------------
                	
                    //Start MainMenu -----------------------------------------------------------------------------
                	Intent mainMenuIntent = new Intent(v.getContext(), MainMenu.class);
                	mainMenuIntent.putExtra("userName", userName);
                	mainMenuIntent.putExtra("userId", userId);
                	//login.setText("");
                	startActivityForResult(mainMenuIntent, 0);
                	StartPage.this.finish();	//Close login page when MainMenu starts ---------------------------
                	
                }
                else{
                	//login.setText(results);
                	Toast.makeText(StartPage.this, results,Toast.LENGTH_LONG).show();

                }
	            
	            
	       }

	     }
	
	 private String downloadUrl(String myurl) throws IOException {
	      InputStream is = null;
	      // Only display the first 500 characters of the retrieved
	      // web page content.
	      int len = 500;
	          
	      try {
	          URL url = new URL(myurl);
	          HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	          conn.setReadTimeout(10000 /* milliseconds */);
	          conn.setConnectTimeout(15000 /* milliseconds */);
	          conn.setRequestMethod("GET");
	          conn.setDoInput(true);
	          // Starts the query
	          conn.connect();
	          int response = conn.getResponseCode();
	          Log.d("FUCK", "The response is: " + response);
	          is = conn.getInputStream();

	          // Convert the InputStream into a string
	          String contentAsString = readIt(is, len);
	          return contentAsString;
	          
	      // Makes sure that the InputStream is closed after the app is
	      // finished using it.
	      } finally {
	          if (is != null) {
	              is.close();
	          } 
	      }
	  }
		
	  	//Reads an InputStream and converts it to a String.
		public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
		   Reader reader = null;
		   reader = new InputStreamReader(stream, "UTF-8");        
		   char[] buffer = new char[len];
		   reader.read(buffer);
		   return new String(buffer);
		}  
	
}
