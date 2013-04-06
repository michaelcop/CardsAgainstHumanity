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
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class GameLobby extends Activity
{
	int minNumberPlayers;
	TextView gameSizeTextView;
	
	String gameID;
	String userID;
	String check;
	
	int numPlayersInGame = 0;
	private String userName;
	
	private int m_interval = 45000; // 45 seconds by default, can be changed later
	private Handler m_handler;
	
	final Context context = this;
	public static final String SPREF_USER = "othPrefs";
	
	boolean isInFront = true;
	
	private ProgressBar mProgress;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.lobby);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
        	String g = extras.getString("GameID");
        	gameID = g;
        	userID = extras.getString("UserID");
        	userName = extras.getString("UserName");
		}
		ActionBar actionBar = GameLobby.this.getActionBar();
		if(actionBar!=null){actionBar.show();}
        getActionBar().setDisplayShowTitleEnabled(false);
		
		gameSizeTextView = (TextView) findViewById(R.id.playerListLobby);
		

		//Toast.makeText(GameLobby.this, userName, Toast.LENGTH_LONG).show();
		
		m_handler = new Handler();
		//refreshGameLobby();
		//startRepeatingTask();
	}
	/*
	Runnable m_statusChecker = new Runnable()
	{
	     @Override 
	     public void run() {
	          //updateStatus(); //this function can change value of m_interval.
	    	 //if activity in foreground refresh else do nothing
	    	 if(isInFront)
	    		 refreshGameLobby();
	         m_handler.postDelayed(m_statusChecker, m_interval);
	     }
	};

	public void onPause() {
		super.onPause();
	    isInFront = false;
	}
	
	
	void startRepeatingTask()
	{
	    m_statusChecker.run(); 
	}
	
	void stopRepeatingTask()
	{
	    m_handler.removeCallbacks(m_statusChecker);
	}
	*/
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.gamelobbymenu, menu);
        //Toast.makeText(GameLobby.this, "In menue at create game", Toast.LENGTH_SHORT).show();
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
        	 setFriend.putExtra("UserId", userID);
        	 setFriend.putExtra("GameId", gameID);
        	 startActivityForResult(setFriend, 0);
             return true;
             
        case R.id.refresh_button:
        	refreshGameLobby();
        	return true;
        	
        case R.id.exit_button:
        	GameLobby.this.finish();
 
        default:
            return super.onOptionsItemSelected(item);
        }
    }

	public void refreshGameLobby()
	{
		//URL contains the userID and gameID
		//Toast.makeText(GameLobby.this, "GameID = " + gameID, Toast.LENGTH_SHORT).show();
		String stringUrl = "http://54.225.225.185:8080/ServerAPP/UserGameLobby?Game="+gameID;
		mProgress = (ProgressBar) findViewById(R.id.progressBar1);
    	check = "GameLobby";
    	ConnectivityManager connMgr = (ConnectivityManager) 
		getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        //Toast.makeText(GameLobby.this, "At refresh game lobby", Toast.LENGTH_SHORT).show();
        //error.setText("creating");
        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadWebpageText().execute(stringUrl);
        } else {
            //error.setText("No network connection available.");
        }
	}
	
	//http://54.225.225.185:8080/ServerAPP/LeaveGame?Game=3&User=1
	
	public void exitURLHandler()
	{
		//URL contains the userID and gameID
		//Toast.makeText(GameLobby.this, "GameID = " + gameID, Toast.LENGTH_SHORT).show();
		String stringUrl = "http://54.225.225.185:8080/ServerAPP/LeaveGame?Game="+gameID+"&User="+userID;
		mProgress = (ProgressBar) findViewById(R.id.progressBar1);
    	check = "LeaveGame";
    	ConnectivityManager connMgr = (ConnectivityManager) 
		getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        //Toast.makeText(GameLobby.this, "At refresh game lobby", Toast.LENGTH_SHORT).show();
        //error.setText("creating");
        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadWebpageText().execute(stringUrl);
        } else {
            //error.setText("No network connection available.");
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
	        	//Toast.makeText(GameLobby.this, "In post execute", Toast.LENGTH_SHORT).show();
	            if(results!=null){
					//error.setText("");
	            	String[] resultArray = results.split(";");
	            	if(resultArray!=null && resultArray[0].equals("GameLobby")){
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
						//changing numPlayersInGame >= 0 to test Game.java and get in the Game easier
						if(numPlayersInGame >= 0)
						{
							//stopRepeatingTask();
							//go to start game activity and kill the game lobby activity
							Intent myIntent = new Intent(GameLobby.this, Game.class);
			            	myIntent.putExtra("UserName", userName);
			            	myIntent.putExtra("UserID", userID);
			            	startActivity(myIntent);
			            	GameLobby.this.finish();
						}
					}
	            	
	            	else if(resultArray!=null && resultArray[0].equals("PlayerDeleted"))
	            	{
	                	SharedPreferences othSettings = getSharedPreferences(SPREF_USER, 0);
	                	SharedPreferences.Editor spEditor = othSettings.edit();
	                	spEditor.remove("CurGameID").commit();
	                	spEditor.putBoolean("inGame", false).commit();
	                	//End erasing
	                	
	                	//Inform user of logout status on game close
	                	if(!othSettings.contains("CurGameID") && !othSettings.getBoolean("inGame",true))
	                		Toast.makeText(context, "Quitting Game", Toast.LENGTH_SHORT).show();
	                	else
	                		Toast.makeText(context, "Failed to quit game", Toast.LENGTH_SHORT).show();
	                	//End logout message  --------------JK
	                	
						//close activity
						GameLobby.this.finish();
						Intent intent = new Intent(getApplicationContext(), MainMenu.class);
				    	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				    	intent.putExtra("UserName", userName);
				    	intent.putExtra("UserId", userID);
				    	intent.putExtra("EXIT", true);
				    	startActivity(intent);
	            	}
	            	
					else if(resultArray[0]=="none") {
						//Toast.makeText(GameLobby.this, "Error1", Toast.LENGTH_SHORT).show();
					}
	
	            }
	            else{
	            	//Toast.makeText(GameLobby.this, "Error2", Toast.LENGTH_SHORT).show();
	            }
        	}
        }
        else{
        	//Toast.makeText(GameLobby.this, "Error3", Toast.LENGTH_SHORT).show();
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
	
	//Back button functionality------------------------------------
		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) 
		{
			
		    if (keyCode == KeyEvent.KEYCODE_BACK) 
		    {
		    	
		    	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		 
					// set title
					alertDialogBuilder.setTitle("Leaving \"Oh the Humanity!\"");
		 
					// set dialog message
					alertDialogBuilder
						.setMessage("Do you want to close application or quit game?")
						.setCancelable(true)
						.setPositiveButton("Close",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								// close current activity
								GameLobby.this.finish();
						    	//stopRepeatingTask();
							}
						  })
						  .setNeutralButton("Quit Game",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								exitURLHandler();//deal with exiting on post execute
								SharedPreferences othSettings = getSharedPreferences(SPREF_USER, 0);
			                	SharedPreferences.Editor spEditor = othSettings.edit();
			                	spEditor.remove("inGame").commit();
			                	spEditor.remove("CurGameID").commit();
								GameLobby.this.finish();
							} 
						
						}) 
						.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								// cancel back button op
								dialog.cancel();
							}
						
						});
		 
						// create alert dialog
						AlertDialog alertDialog = alertDialogBuilder.create();
		 
						// show it
						alertDialog.show();
		    	
		        //moveTaskToBack(true);
		        return true;
		    }
		    return super.onKeyDown(keyCode, event);
		}  //End back button functionality---------------------------------
	
}
