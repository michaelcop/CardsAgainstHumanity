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

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class PlayerStats extends Activity
{
	String check;
	int gameID;
	String userID;
	String userName;
	Context context;
	TextView playerNameStat;
	TextView gamesWonTextView;
	TextView gamesLostTextView;
	TextView averagePointsPerGameTextView;
	TextView maxPointsInRoundTextView;
	
	private ProgressBar mProgress;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.playerstats);
		context = this;
		playerNameStat = (TextView) findViewById(R.id.PlayerNameStat);
		gamesWonTextView = (TextView) findViewById(R.id.GamesWonStat);
		gamesLostTextView = (TextView) findViewById(R.id.GamesLostStat);

		//refreshPlayerStats();
        //getActionBar().setDisplayShowTitleEnabled(false);
		Button returns = (Button) findViewById(R.id.ReturnToMenu);
		returns.setOnClickListener(new OnClickListener()
		{
	
			@Override
			public void onClick(View v) 
			{
				finish();
			}
			
		});
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
        	String g = extras.getString("GameID");
        	//gameID = Integer.parseInt(g);
        	userID = extras.getString("userId");
        	userName = extras.getString("userName");
		}
		refreshPlayerStats();
	} 
	
	
	public void refreshPlayerStats()
	{
		//URL contains the userID
		String stringUrl = "http://54.225.225.185:8080/ServerAPP/PlayerStats?User=" + userName;
		mProgress = (ProgressBar) findViewById(R.id.progressBar1);
    	check = "UserStats";
    	ConnectivityManager connMgr = (ConnectivityManager) 
		getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        //error.setText("creating");
        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadWebpageText().execute(stringUrl);
        } else {
           
        }
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
        protected void onPostExecute(Object result) {
        
        mProgress.setVisibility(ProgressBar.INVISIBLE);	
        	
        if(result!=null){
        	String results = (String) result.toString();
        	if(results!=null){
	        	results = results.trim();
	        	
	            //check the result for the what's needed to move on
	            if(results!=null){
					//error.setText("");
	            	String[] resultArray = results.split(";");
	            	if(resultArray!=null && resultArray[0].equals("Stats")){
		            	ArrayList<String> data;
						data = new ArrayList<String>(Arrays.asList(resultArray));
						data.remove(0);//we are removing the check data field
						playerNameStat.setText(userName.toUpperCase());
						gamesWonTextView.setText("Games won: " + data.get(0));
						gamesLostTextView.setText("Games lost: " + data.get(0));
					}
					else {
		        		Toast.makeText(PlayerStats.this, results, Toast.LENGTH_SHORT).show();
					}
	
	            }
	            else{
	            	
	            }
        	}
        }
        else{
        	
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
