package com.cardsagainsthumanity.Entities;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

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
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CreateGame extends Activity
{
	private int rounds;
	private String UserName;
	Button v;
	String check;
    private TextView error;
    private String userId;
    
    final Context context = this;
    public static final String SPREF_USER = "othPrefs";
    
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.creategame);
		
		error = (TextView) findViewById(R.id.error);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    UserName = extras.getString("UserName");
		    userId = extras.getString("UserID");
		}
		Button returns = (Button) findViewById(R.id.btnReturn);
		
		
		returns.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				finish();
			}
			
		});
	
	
		Button create = (Button) findViewById(R.id.btnCreate);
		create.setOnClickListener(new OnClickListener()
		{
	
			@Override
			public void onClick(View v) 
			{
				String numRounds = ((EditText) findViewById(R.id.numRounds)).getText().toString();
				if(numRounds == null || numRounds.trim().equals(""))
				{
					Toast.makeText(CreateGame.this, "Number of rounds invalid defaulting to 10", Toast.LENGTH_LONG).show();
					numRounds = "10";
				}
				rounds = Integer.parseInt(numRounds);
				if(!(rounds>0) || !(rounds < 50) ){
					Toast.makeText(CreateGame.this, "Number of rounds invalid defaulting to 10", Toast.LENGTH_LONG).show();
					rounds = 10;
	        	}
				
				String stringUrl = "http://54.225.225.185:8080/ServerAPP/CreateGame?User="+UserName+"&rounds="+rounds;
	        	check = "Game";
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
	        	Log.d("FUCK", "First");
	        	String[] resultArr = results.split(":");
	        	Log.d("FUCK", resultArr[0]);
	            //check the result for the what's needed to move on
	            if(resultArr!=null && (resultArr[0]).equalsIgnoreCase(check)){
	            	Toast.makeText(CreateGame.this, "Created Game", Toast.LENGTH_SHORT).show();
					error.setText("");
					
					Intent myIntent = new Intent(CreateGame.this, GameLobby.class);
	            	myIntent.putExtra("GameID", resultArr[1]);
	            	SharedPreferences othSettings = getSharedPreferences(SPREF_USER, 0);
                	SharedPreferences.Editor spEditor = othSettings.edit();
                	spEditor.putString("CurGameID", resultArr[1]).commit(); //Stores current game ID
                	spEditor.putBoolean("inGame", true).commit();   //Sets flag for user being in game
	            	myIntent.putExtra("UserName", UserName);
	            	myIntent.putExtra("UserID", userId);
	            	startActivity(myIntent);
	            	CreateGame.this.finish();
					
					//mike changing it to go to GameLobby.java first instead of Game.java
					/*
	            	Intent myIntent = new Intent(CreateGame.this, Game.class);
	            	myIntent.putExtra("GameID", resultArr[1]);
	            	myIntent.putExtra("UserName", UserName);
	            	myIntent.putExtra("UserID", userId);
	            	startActivity(myIntent);
	            	*/	
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
	
	//Back button functionality------------------------------------
			@Override
			public boolean onKeyDown(int keyCode, KeyEvent event) 
			{
				
			    if (keyCode == KeyEvent.KEYCODE_BACK) 
			    {
			    	
			    	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
			 
						// set title
						alertDialogBuilder.setTitle("Leaving \"Create Game\"");
			 
						// set dialog message
						alertDialogBuilder
							.setMessage("Do you want to close application or go to Main Menu?")
							.setCancelable(true)
							.setPositiveButton("Close",new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,int id) {
									// close current activity
									CreateGame.this.finish();
							    	//stopRepeatingTask();
								}
							  })
							  .setNeutralButton("Main Menu",new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,int id) {
									Intent myIntent = new Intent(context, MainMenu.class);
					             	myIntent.putExtra("UserName", UserName);
					             	myIntent.putExtra("UserId", userId);
					             	startActivityForResult(myIntent, 0);
					             	CreateGame.this.finish();	//Close StartActivity page when MainMenu starts
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
