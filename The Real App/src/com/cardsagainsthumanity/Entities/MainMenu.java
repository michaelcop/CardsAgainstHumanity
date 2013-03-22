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
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainMenu extends Activity 
{
	final Context context = this;
	String UserName;
	String check;
    private TextView error;
	
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
		error = (TextView) findViewById(R.id.error);
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item)
    {
 
        switch (item.getItemId())
        {

        case R.id.menu_preferences:
           // Toast.makeText(MainMenu.this, "Settings is Selected", Toast.LENGTH_SHORT).show();
            Intent set = new Intent(MainMenu.this, Settings.class);
            startActivityForResult(set, 0);
            return true;
 
        default:
            return super.onOptionsItemSelected(item);
        }
    }    
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mainmenu);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    UserName = extras.getString("UserName");
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
				cGame.putExtra("UserName", UserName);
				startActivity(cGame);
			}
			
		});
		
		//Join listener ----------------------------------------------
		
		ImageButton join = (ImageButton) findViewById(R.id.joinButton);
		
		join.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) 
			{
				Intent myIntent = new Intent(v.getContext(), JoinGame.class);
                startActivity(myIntent);
				
			}
			
		});
		
		
		ImageButton friends = (ImageButton) findViewById(R.id.friendsButton);
		
		friends.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) 
			{
				String stringUrl = "http://54.225.225.185:8080/ServerAPP/FriendsList?User="+UserName;
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
		
		ImageButton stats = (ImageButton) findViewById(R.id.statsButton);
		
		stats.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) 
			{
				Intent myIntent = new Intent(v.getContext(), PlayerStats.class);
                startActivity(myIntent);
				
			}
			
		});
		
		//How to Play listener---------------------------------------------
		
		ImageButton how = (ImageButton) findViewById(R.id.howButton);
		
		how.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				Intent myIntent = new Intent(v.getContext(), HowToPlay.class);
				startActivity(myIntent);
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
							//close activity and logout user
							MainMenu.this.finish();
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
		        	Log.d("FUCK", "results is not null");
					//error.setText("");
					Intent myIntent = new Intent(MainMenu.this, FriendsList.class);
					Log.d("FUCK", "intent created");
	            	String[] resultArray = results.split(";");
	            	Log.d("FUCK", "split");
	            	if(resultArray!=null && resultArray[0].equals(check)){
	    	        	Log.d("FUCK", resultArray[0]);
		            	ArrayList<String> data;
						data = new ArrayList<String>(Arrays.asList(resultArray));
						data.remove(0);
						Log.d("FUCK", "removed it");
						myIntent.putStringArrayListExtra("data", data);
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
