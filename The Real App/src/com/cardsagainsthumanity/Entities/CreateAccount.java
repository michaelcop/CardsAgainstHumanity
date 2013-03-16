package com.cardsagainsthumanity.Entities;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

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
import android.widget.TextView;

public class CreateAccount extends Activity
{
	private EditText inputUsername;
	private EditText inputPassword;
	private EditText confirmPassword;
	private String userName;
	private String password;
	private String confirm;
	Button v;
	String check;
    private TextView error;

	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.createaccount);
		inputUsername = (EditText) findViewById(R.id.editText1);
		inputPassword = (EditText) findViewById(R.id.editText2);
		confirmPassword = (EditText) findViewById(R.id.editText3);
		error = (TextView) findViewById(R.id.error);

		
		
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
				//create
	        	userName = inputUsername.getText().toString();
	        	password = inputPassword.getText().toString();
	        	confirm = confirmPassword.getText().toString();
	        	if(userName.length()==0 || password.length()==0){
	        		error.setText("Username and password must not be blank");
	        		
	        	}
	        	error.setText("Clicked");
	        	if(!password.equals(confirm)){
	                error.setText("Password and Confirmed password are different");
	                return;
	        	}
	        	String stringUrl = "http://54.225.225.185:8080/ServerAPP/CreateAccount?User="+userName+"&password="+password;
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
        if(result!=null){
        	String results = (String) result.toString();
        	if(results!=null){
	        	results = results.trim();
	            //check the result for the what's needed to move on
	            if(results!=null && results.equalsIgnoreCase(check)){
	            	error.setText("Confirmed creation");
	            	finish();
	            }
	            else{
	            	error.setText(results);
	            }
        	}
        }
        else{
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
