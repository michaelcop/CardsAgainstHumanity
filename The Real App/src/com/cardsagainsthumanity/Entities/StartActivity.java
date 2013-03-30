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
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class StartActivity extends Activity
{
	
	private String userName;
	private String sha;
    private EditText urlText;
    private String results;
    private String UserId;
	
	public static final String SPREF_USER = "othPrefs";
	final Context context = this;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		SharedPreferences othSettings = getSharedPreferences(SPREF_USER, 0);
		if(othSettings.contains("UserName") && othSettings.contains("digest") && othSettings.contains("ID"))
		{
			userName = othSettings.getString("UserName", "0");
        	sha = othSettings.getString("digest", "0");
			super.onCreate(savedInstanceState);
			String stringUrl = "http://54.225.225.185:8080/ServerAPP/Login?User="+userName+"&password="+sha;
        	ConnectivityManager connMgr = (ConnectivityManager) 
    		getSystemService(Context.CONNECTIVITY_SERVICE);
            
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                Toast.makeText(context, "Logging in", Toast.LENGTH_SHORT).show();
                new DownloadWebpageText().execute(stringUrl);
                
            } else 
            {
                Toast.makeText(context, "No network connection available.", Toast.LENGTH_SHORT);
            }
		}
		else
		{
			super.onCreate(savedInstanceState);
			Intent myIntent = new Intent(this, StartPage.class);
			startActivity(myIntent);
			StartActivity.this.finish();
		}
		

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
             	
            	SharedPreferences othSettings = getSharedPreferences(SPREF_USER, 0);            	
             	UserId = othSettings.getString("ID", "err");
             	
             	Intent myIntent = new Intent(context, MainMenu.class);
             	myIntent.putExtra("UserName", userName);
             	myIntent.putExtra("UserId", UserId);
             	startActivityForResult(myIntent, 0);
             	StartActivity.this.finish();	//Close StartActivity page when MainMenu starts
             	
             }
             else
             {
            	Toast.makeText(context, "err", Toast.LENGTH_SHORT);
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