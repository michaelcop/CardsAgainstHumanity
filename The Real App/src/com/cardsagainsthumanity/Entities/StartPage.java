package com.cardsagainsthumanity.Entities;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.cardsagainsthumanity.Entities.R;

public class StartPage extends Activity {
	
	private EditText inputUsername;
	private EditText inputPassword;
	private String userName;
	private String password;
    private EditText urlText;
    private TextView textView;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);
		
		Button v = (Button) findViewById(R.id.button1);
		
		inputUsername = (EditText) findViewById(R.id.editText1);
		inputPassword = (EditText) findViewById(R.id.editText2);
		
		v.setOnClickListener(new OnClickListener() {
			
	        public void onClick(View v) 
	        {
	        	//login
	        	String stringUrl = "http://http://54.225.225.185:8080/ServerAPP/Login?User="+inputUsername.getText().toString()+"&password="+inputPassword.getText().toString();
	        	
	        	ConnectivityManager connMgr = (ConnectivityManager) 
	        		getSystemService(Context.CONNECTIVITY_SERVICE);
	                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
	                if (networkInfo != null && networkInfo.isConnected()) {
	                    new DownloadWebpageText().execute(stringUrl);
	                } else {
	                    textView.setText("No network connection available.");
	                }
	                
		        	Intent myIntent = new Intent(v.getContext(), MainMenu.class);
	                startActivityForResult(myIntent, 0);

        	}
			

        }); 
		
		Button ca = (Button) findViewById(R.id.button2);
	
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
	            textView.setText((CharSequence) result);
	            //check the result for the what's needed to move on
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
