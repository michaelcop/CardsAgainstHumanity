package com.cardsagainsthumanity.Entities;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class InviteFriends extends Activity
{
	Context context = this;
    private TextView error;
	String UserName;
	String check;
	int current;
	String User2;
	TableLayout t;
	int  UserId;
		
	ArrayList<String> testStrings;
	protected void onCreate(Bundle savedInstanceState)
	{
		
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.invitefriend);
		error = (TextView) findViewById(R.id.error);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			UserName = extras.getString("UserName");
			//testStrings = extras.getStringArrayList("data");
		}
		callUrl("http://54.225.225.185:8080/ServerAPP/CurrentFriends?User="+UserName);
		Button returns = (Button) findViewById(R.id.ReturnToMenu);
		returns.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				finish();
			}
			
		});
		
		
	} 
	
	
	public void makeTable(List<String> testStrings){
		// Get the TableLayout
        t = (TableLayout) findViewById(R.id.friendsTable);

        // Go through each item in the array
        for ( current = 0; current < testStrings.size(); current++)
        {
        	String temp = testStrings.get(current);
        	UserId = Integer.parseInt(temp);
        	// Create a TableRow and give it an ID
            TableRow tr = new TableRow(this);
            tr.setId(UserId);
            tr.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT ,LayoutParams.WRAP_CONTENT));   

            // Create a TextView to house the name of the province
            TextView labelTV = new TextView(this);
            labelTV.setText(testStrings.get(++current));
            labelTV.setId(500+UserId);
            labelTV.setTextColor(Color.WHITE);
            //labelTV.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
            tr.addView(labelTV);

            // Create a TextView to house the value of the after-tax income
            
            final Button b = new Button(this);
            b.setId(5000+UserId);
            b.setTextColor(InviteFriends.this.getResources().getColor(R.color.White));
            b.setText("Invite Friend to Game");
            tr.addView(b);
            //valueTV.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
            
            // Add the TableRow to the TableLayout
            t.addView(tr, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
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
        if(result!=null){
        	String results = (String) result.toString();
        	if(results!=null){
	        	results = results.trim();
	     
	            //check the result for the what's needed to move on
	            if(results!=null){
					error.setText("");
	            	String[] resultArray = results.split(";");
					if(resultArray!=null && resultArray[0].equals("Friends") && resultArray.length==2){
		            	ArrayList<String> data = new ArrayList<String>(Arrays.asList(resultArray));
						makeTable(data);
					}
					else{
                		Toast.makeText(context, "You broke it.", Toast.LENGTH_LONG).show();
						}
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
	
	public void callUrl(String url){

	        	ConnectivityManager connMgr = (ConnectivityManager) 
        		getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                //error.setText("creating");
                if (networkInfo != null && networkInfo.isConnected()) {
                    new DownloadWebpageText().execute(url);
                } else {
                    error.setText("No network connection available.");
                }	
	}
	
	
	
}
