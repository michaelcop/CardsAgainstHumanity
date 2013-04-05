package com.cardsagainsthumanity.Entities;


import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

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
    private String UserId;
    private String sha;
    Button v;
    public static final String SPREF_USER = "othPrefs";
    
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
	        	/*
	        	if(userName.length() < 5 || password.length() < 5)
	        	{
	        		Toast.makeText(StartPage.this, "Error user name and password must be atleast 5 characters.  Try Again!", Toast.LENGTH_LONG).show();
	        		inputUsername.setText("");
	        		inputPassword.setText("");
	        		return;
	        	}
	        	*/
	        	
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
                //Digest made
                
	        	String stringUrl = "http://54.225.225.185:8080/ServerAPP/Login?User="+userName+"&password="+sha;
	        	ConnectivityManager connMgr = (ConnectivityManager) 
        		getSystemService(Context.CONNECTIVITY_SERVICE);
                
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    //Toast.makeText(v.getContext(), "Logging in", Toast.LENGTH_SHORT).show();
                	//Intent myIntent = new Intent(v.getContext(), LoadingLogIn.class);
    				//startActivity(myIntent);
                    new DownloadWebpageText().execute(stringUrl);
                    
                } else {
                    login.setText("No network connection available.");
                }
                
        	}
			

        }); 
		
		Button ca = (Button) findViewById(R.id.CreateAccount);
	
		ca.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) 
			{
				Intent myIntent = new Intent(v.getContext(), CreateAccount.class);
                startActivity(myIntent);
				
			}
			
		});
	}
	
	 private class DownloadWebpageText extends AsyncTask {
	        
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
	        protected void onPostExecute(Object result) {
	            results = (String) result.toString();
	            results = results.trim();
	            //check the result for the what's needed to move on
	            String[] resultArr = results.split(":"); 
                if(resultArr[0].equals("User")){
                	
                	//Store Username and ID in SharedPref
                	SharedPreferences othSettings = getSharedPreferences(SPREF_USER, 0);
                	SharedPreferences.Editor spEditor = othSettings.edit();
                	spEditor.putString("UserName", userName.toString()).commit();
                	UserId = resultArr[1].toString();
                	spEditor.putString("ID", UserId).commit();
                	//End storing username
                	
                	//Need to encrypt password and store
                	
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
                                	
                	//End store password
                	
                	Intent myIntent = new Intent(v.getContext(), MainMenu.class);
                	myIntent.putExtra("UserName", userName);
                	myIntent.putExtra("UserId", UserId);
                	login.setText("");
                	startActivityForResult(myIntent, 0);
                	StartPage.this.finish();	//Close login page when Main<enu starts
                	
                }
                else{
                	login.setText(results);
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
