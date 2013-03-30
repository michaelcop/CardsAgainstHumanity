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

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class GameLobby extends Activity
{
	int minNumberPlayers;
	TextView gameSizeTextView;
	
	int gameID;
	String userID;
	String check;
	
	int numPlayersInGame = 0;
	private String userName;
	
	private int m_interval = 5000; // 5 seconds by default, can be changed later
	private Handler m_handler;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.lobby);
		
		ActionBar actionBar = GameLobby.this.getActionBar();
		if(actionBar!=null){actionBar.show();}
		
		gameSizeTextView = (TextView) findViewById(R.id.playerListLobby);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
        	Log.d("FUCK", "Jimmy");
        	String g = extras.getString("GameID");
        	Log.d("FUCK", "Jimmy2");
        	gameID = Integer.parseInt(g);
        	userID = extras.getString("UserID");
        	userName = extras.getString("UserName");
		}
		Toast.makeText(GameLobby.this, userName, Toast.LENGTH_LONG).show();
		
		m_handler = new Handler();
		//refreshGameLobby();
		startRepeatingTask();
	}
	
	Runnable m_statusChecker = new Runnable()
	{
	     @Override 
	     public void run() {
	          //updateStatus(); //this function can change value of m_interval.
	    	 refreshGameLobby();
	         m_handler.postDelayed(m_statusChecker, m_interval);
	     }
	};
	
	void startRepeatingTask()
	{
	    m_statusChecker.run(); 
	}
	
	void stopRepeatingTask()
	{
	    m_handler.removeCallbacks(m_statusChecker);
	}
	
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.gamelobbymenu, menu);
        Toast.makeText(GameLobby.this, "In menue at create game", Toast.LENGTH_SHORT).show();
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item)
    {
 
        switch (item.getItemId())
        {

        case R.id.menu_preferences:
        	//Toast.makeText(MainMenu.this, "Settings is Selected", Toast.LENGTH_SHORT).show();
            Intent set = new Intent(GameLobby.this, Settings.class);
            startActivityForResult(set, 0);
            return true;
         
        case R.id.invite_friend:
        	 Intent setFriend = new Intent(GameLobby.this, InviteFriends.class);
        	 setFriend.putExtra("UserName", userName);
        	 startActivityForResult(setFriend, 0);
             
             return true;
 
        default:
            return super.onOptionsItemSelected(item);
        }
    }

	public void refreshGameLobby()
	{
		//URL contains the userID and gameID
		Toast.makeText(GameLobby.this, "GameID = " + gameID, Toast.LENGTH_SHORT).show();
		String stringUrl = "http://54.225.225.185:8080/ServerAPP/UserGameLobby?Game="+gameID;
    	check = "GameLobby";
    	ConnectivityManager connMgr = (ConnectivityManager) 
		getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        Toast.makeText(GameLobby.this, "At refresh game lobby", Toast.LENGTH_SHORT).show();
        //error.setText("creating");
        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadWebpageText().execute(stringUrl);
        } else {
            //error.setText("No network connection available.");
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
	        	Toast.makeText(GameLobby.this, "In post execute", Toast.LENGTH_SHORT).show();
	            if(results!=null){
					//error.setText("");
	            	String[] resultArray = results.split(";");
	            	if(resultArray!=null && resultArray[0].equals(check)){
		            	ArrayList<String> data;
						data = new ArrayList<String>(Arrays.asList(resultArray));
						data.remove(0);//we are removing the check data field
						GameLobby.this.numPlayersInGame = Integer.parseInt(data.get(0));
						String playersInGame = "";
						for(int i=1; i<numPlayersInGame+1; i++)
						{
							playersInGame += data.get(i) + "\n";
						}
						
						GameLobby.this.gameSizeTextView.setText(playersInGame);
						if(numPlayersInGame >= 3)
						{
							stopRepeatingTask();
							//go to start game activity and kill the game lobby activity
							Intent myIntent = new Intent(GameLobby.this, Game.class);;
			            	myIntent.putExtra("UserName", userName);
			            	myIntent.putExtra("UserID", userID);
			            	startActivity(myIntent);
						}
					}
					else if(resultArray[0]=="none") {
						Toast.makeText(GameLobby.this, "Error1", Toast.LENGTH_SHORT).show();
					}
	
	            }
	            else{
	            	Toast.makeText(GameLobby.this, "Error2", Toast.LENGTH_SHORT).show();
	            }
        	}
        }
        else{
        	Toast.makeText(GameLobby.this, "Error3", Toast.LENGTH_SHORT).show();
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
