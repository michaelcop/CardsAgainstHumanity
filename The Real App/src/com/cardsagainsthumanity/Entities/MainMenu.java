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


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
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
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainMenu extends Activity 
{
	final Context context = this;
	String userName;
	String check;
    private TextView error;
	private String userId;
	private ProgressBar mProgress;
	
    public static final String SPREF_USER = "othPrefs";
	
	public boolean onCreateOptionsMenu(Menu menu)
    {
		//Returns user to their ongoing game --------------------------------------------
		SharedPreferences othSettings = getSharedPreferences(SPREF_USER, 0);
		if(othSettings.contains("CurGameID") && othSettings.getBoolean("inGame", true))
		{
			Intent rejoin = new Intent(MainMenu.this, GameLobby.class);
        	rejoin.putExtra("gameId", othSettings.getString("CurGameID", "wtf"));
        	rejoin.putExtra("userName", userName);
        	rejoin.putExtra("userId", userId);
        	startActivity(rejoin);
        	MainMenu.this.finish();
		} //------------------------------------------------------------------------------
		
		//Create menu --------------------------------------------------------------------
        MenuInflater menuInflater = getMenuInflater();
        getActionBar().setDisplayShowTitleEnabled(false);
        menuInflater.inflate(R.menu.menu, menu);
		error = (TextView) findViewById(R.id.error);
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item)
    {
 
        switch (item.getItemId())
        {

        case R.id.menu_preferences:
            Intent set = new Intent(MainMenu.this, Settings.class);
            startActivityForResult(set, 0);
            return true;
 
        default:
            return super.onOptionsItemSelected(item);
        }
    }    
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mainmenu);
		//this.requestFeature(Window.FEATURE_ACTION_BAR);
		
		ActionBar actionBar = MainMenu.this.getActionBar();
		if(actionBar!=null){actionBar.show();}
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    userName = extras.getString("userName");
		    userId = extras.getString("userId");
		}
		
		//Create listener----------------------------------------------
		ImageButton create = (ImageButton) findViewById(R.id.createButton);
		
		create.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				Intent cGame = new Intent(v.getContext(), CreateGame.class);
				cGame.putExtra("userName", userName);
				cGame.putExtra("userId", userId);

				startActivity(cGame);
				MainMenu.this.finish();
			}
			
		});
		
		//Join listener ----------------------------------------------
		
		ImageButton join = (ImageButton) findViewById(R.id.joinButton);
		
		join.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) 
			{
				Intent joinGameIntent = new Intent(v.getContext(), JoinGame.class);
				joinGameIntent.putExtra("userName", userName);
				joinGameIntent.putExtra("userId", userId);
				startActivity(joinGameIntent);
				
			}
			
		});
		
		//Friends listener ----------------------------------------------------------------------------
		ImageButton friends = (ImageButton) findViewById(R.id.friendsButton);
		
		friends.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) 
			{
				Toast.makeText(context, "Checking for friends", Toast.LENGTH_SHORT).show();
				String stringUrl = "http://54.225.225.185:8080/ServerAPP/FriendsList?User="+userName;
				mProgress = (ProgressBar) findViewById(R.id.progressBar1);
	        	check = "Friends";
	        	ConnectivityManager connMgr = (ConnectivityManager) 
        		getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                //error.setText("creating");
                if (networkInfo != null && networkInfo.isConnected()) {
                    new DownloadWebpageText().execute(stringUrl);
                } else {
                    error.setText("No network connection available.");
                }
				
			}
			
		});
		
		//Player stats listener ----------------------------------------------------------------------------
		ImageButton stats = (ImageButton) findViewById(R.id.statsButton);
		
		stats.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) 
			{
				Intent pStatsIntent = new Intent(v.getContext(), PlayerStats.class);
                pStatsIntent.putExtra("userName", userName);
                pStatsIntent.putExtra("userId", userId);
				startActivity(pStatsIntent);
				
			}
			
		});
		
		//How to Play listener---------------------------------------------
		
		ImageButton how = (ImageButton) findViewById(R.id.howButton);
		
		how.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				Intent htpIntent = new Intent(v.getContext(), HowToPlay.class);
				startActivity(htpIntent);
			}
			
		});
		
		
		
		
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
					.setMessage("Do you want to close the application or logout?")
					.setCancelable(true)
					.setPositiveButton("Close",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							// close current activity
							MainMenu.this.finish();
						}
					  })
					  .setNeutralButton("Logout",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							//logout user
							//Erase Username & pw in SharedPref
		                	SharedPreferences othSettings = getSharedPreferences(SPREF_USER, 0);
		                	SharedPreferences.Editor spEditor = othSettings.edit();
		                	spEditor.remove("UserName").commit();
		                	spEditor.remove("digest").commit();
		                	spEditor.remove("ID").commit();
		                	spEditor.remove("defGameRounds").commit();
		                	if(othSettings.contains("inGame"))
		                		spEditor.remove("inGame");
		                	if(othSettings.contains("CurGameID"))
		                		spEditor.remove("CurGameID");
		                	//End erasing username & pw
		                	
		                	//Inform user of logout status on game close
		                	if(!othSettings.contains("UserName") && !othSettings.contains("digest"))
		                		Toast.makeText(context, "Logging Out", Toast.LENGTH_SHORT).show();
		                	else
		                		Toast.makeText(context, "Logout failed", Toast.LENGTH_SHORT).show();
		                	//End logout message  --------------JK
		                	
							//close activity
							MainMenu.this.finish();
							Intent intent = new Intent(getApplicationContext(), StartPage.class);
					    	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					    	intent.putExtra("EXIT", true);
					    	startActivity(intent);
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
					Intent myIntent = new Intent(MainMenu.this, FriendsList.class);
	            	String[] resultArray = results.split(";");
	            	if(resultArray!=null && resultArray[0].equals(check)){
		            	ArrayList<String> data;
						data = new ArrayList<String>(Arrays.asList(resultArray));
						data.remove(0);
						myIntent.putStringArrayListExtra("data", data);
						myIntent.putExtra("UserName", userName);
						myIntent.putExtra("UserId", userId);
		            	startActivity(myIntent);
					}
					else if(resultArray[0]=="none") {
						error.setText("Result Array null");
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
	
	
	
}
