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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class CreateAccount extends Activity
{
	private EditText inputUsername;
	private EditText inputPassword;
	private EditText confirmPassword;
	private String userName;
	private String password;
	private String confirm;
	private String hash;
	Button v;
	String check;
    private TextView error;
	private ProgressBar mProgress;

	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.createaccount);
		inputUsername = (EditText) findViewById(R.id.username);
		inputPassword = (EditText) findViewById(R.id.password);
		confirmPassword = (EditText) findViewById(R.id.confirmpassword);
		error = (TextView) findViewById(R.id.error);

        //getActionBar().setDisplayShowTitleEnabled(false);
		
		Button returns = (Button) findViewById(R.id.Cancel);
		returns.setOnClickListener(new OnClickListener()
		{	
			@Override
			public void onClick(View w) 
			{
				finish();
			}
			
		});
		Button create = (Button) findViewById(R.id.Create);
		create.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) 
			{
				//create --------------------------------------------------------------------------------------------------
	        	userName = inputUsername.getText().toString();
	        	password = inputPassword.getText().toString();
	        	confirm = confirmPassword.getText().toString();
	        	
	        	userName = userName.trim();
	        	
	        	if(userName.length()==0 || password.length()==0){
	        		error.setText("Username and password must not be blank");
	        		
	        	}
	        	error.setText("Clicked");
	        	if(!password.equals(confirm)){
	                error.setText("Password and Confirmed password are different");
	                return;
	        	}
	        	if(userName.length() < 5 || password.length() < 5)
	        	{
	        		Toast.makeText(CreateAccount.this, "Error user name and password must be atleast 5 characters.  Try Again!", Toast.LENGTH_SHORT).show();
	        		inputUsername.setText("");
	        		inputPassword.setText("");
	        		confirmPassword.setText("");
	        		return;
	        	} //End create ------------------------------------------------------------------------------------------
	        	
	        	//Encryption -----------------------------------------------------------------------------
	        	String stringThatNeedsToBeEncrpyted = password; // Value to encrypt
                MessageDigest Enc = null;
                try {
                    Enc = MessageDigest.getInstance("SHA-1");
                } catch (NoSuchAlgorithmException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } // Encryption algorithm
                Enc.update(stringThatNeedsToBeEncrpyted.getBytes(), 0, stringThatNeedsToBeEncrpyted.length());
                hash = new BigInteger(1, Enc.digest()).toString(16); //Make the Encrypted string
                //--------------------------------------------------------------------------------------------
	        	
	        	String stringUrl = "http://54.225.225.185:8080/ServerAPP/CreateAccount?User="+userName+"&password="+hash;
	        	mProgress = (ProgressBar) findViewById(R.id.progressBar1);
	        	check = "User: "+userName+" successfully created!";
	        	ConnectivityManager connMgr = (ConnectivityManager) 
        		getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                error.setText("creating");
                if (networkInfo != null && networkInfo.isConnected()) {
                    new DownloadWebpageText().execute(stringUrl);
                } else {
                    error.setText("No network connection available.");
                }
                
			}
			
		});
	}

	private class DownloadWebpageText extends AsyncTask {
		
		@Override
        protected void onPreExecute()
		{
			mProgress.setVisibility(ProgressBar.VISIBLE);
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
        
        mProgress.setVisibility(ProgressBar.INVISIBLE);
        
        if(result!=null)
        {
        	String results = (String) result.toString();
        	if(results!=null)
        	{
	        	results = results.trim();
	            //check the result for the what's needed to move on
	            if(results!=null && results.equalsIgnoreCase(check))
	            {
	            	error.setText("Confirmed creation");
	            	finish();
	            }
	            else
	            {
	        		Toast.makeText(CreateAccount.this, "User already exists", Toast.LENGTH_SHORT).show();
	            }
        	}
        }
        else
        {
        	error.setText("Result was null");
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
