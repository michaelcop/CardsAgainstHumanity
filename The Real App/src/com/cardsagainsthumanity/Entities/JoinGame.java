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
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class JoinGame extends Activity
{
	Context context = this;
	TableLayout gameList;
	public static final String SPREF_USER = "othPrefs";
	int current;
	int gameId;
	int deletingID;
	String currentUser;
	String User1Id;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.joingame);
		Bundle extras = getIntent().getExtras();
		//getActionBar().setDisplayShowTitleEnabled(false);
		
		if (extras != null) 
		{
			currentUser = extras.getString("userName");
			//testStrings = extras.getStringArrayList("data");
			User1Id = extras.getString("userId");
		}
		
		//Toast.makeText(context, "CallingURL", Toast.LENGTH_LONG).show();
		
		if(User1Id != null)
		{
			callUrl("http://54.225.225.185:8080/ServerAPP/InviteList?User="+User1Id);
		}
		else
		{
    		//Toast.makeText(context, "No User", Toast.LENGTH_LONG).show();
			ArrayList<String> pass = new ArrayList<String>();
			makeTable(pass);
		}
		
		
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
	
	public void makeTable(ArrayList<String> testStrings)
	{
		
		gameList = (TableLayout) findViewById(R.id.gameTable);
		
		 for (current = 0; current < testStrings.size(); current++)
		 {
			 	//Creates a table row and sets the ID of the table row to the current game ID
			 	String temp = testStrings.get(current);
		    	gameId = Integer.parseInt(temp);
		    	// Create a TableRow and give it an ID
		        TableRow tr = new TableRow(this);
		        tr.setId(gameId);
		        tr.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT ,LayoutParams.WRAP_CONTENT));
		        
		        //Set the actual TextView to the text in the list
		        TextView labelTV = new TextView(this);
	            labelTV.setText("Invited by "+ testStrings.get(++current));
	            labelTV.setTextColor(Color.WHITE);
	            //labelTV.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
	            tr.addView(labelTV);
	            
	            //Creating join buttons
	            if(gameId!=-1)
	            {
		            final Button b = new Button(this);
		            b.setId(-1*gameId);
		            b.setTextColor(JoinGame.this.getResources().getColor(R.color.White));
		            b.setText("Accept");
		            
		            
		            b.setHint("GameId:"+gameId);
		            b.setTextSize(12);
		            b.setHint("GameId:"+gameId);
		            tr.addView(b);
		            b.setOnClickListener(new OnClickListener()
	        		{
	        			@Override
	        			public void onClick(View v) 
	        			{
	        				String[] temp = b.getHint().toString().split(":");
	        				String ID = temp[1];
	        				String stringUrl = "http://54.225.225.185:8080/ServerAPP/JoinGame?User="+User1Id+"&Game="+ID;
	        				callUrl(stringUrl);

	        			}
	        			
	        		});
		            final Button c = new Button(this);
		            c.setId(-2*gameId);
		            c.setTextColor(JoinGame.this.getResources().getColor(R.color.White));
		            c.setText("Decline");
		            c.setTextSize(12);
		            c.setHint("GameId:"+gameId);
		            tr.addView(c);
		            c.setOnClickListener(new OnClickListener()
	        		{
	        			@Override
	        			public void onClick(View v) 
	        			{
	        				String[] temp = b.getHint().toString().split(":");
	        				String ID = temp[1];
	        				deletingID = Integer.parseInt(ID);
	        				String stringUrl = "http://54.225.225.185:8080/ServerAPP/LeaveGame?User="+User1Id+"&Game="+ID;
	        				callUrl(stringUrl);

	        			}
	        			
	        		});
	            }
	            
	            
	            
	            
	            //Add the new table row to the list
	            gameList.addView(tr, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		 }
		 
        
	}
	
	public void callUrl(String url)
	{
    	ConnectivityManager connMgr = (ConnectivityManager) 
		getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        //error.setText("creating");
        if (networkInfo != null && networkInfo.isConnected()) 
        {
            new DownloadWebpageText().execute(url);
        } 
        else 
        {
    		//Toast.makeText(context, "No network COnnection", Toast.LENGTH_LONG).show();

        }	
	}
	
	private class DownloadWebpageText extends AsyncTask
	{
    	@Override
        protected Object doInBackground(Object... urls) {
            // params comes from the execute() call: params[0] is the url.
    		//Toast.makeText(context, "BG", Toast.LENGTH_LONG).show();

    		try 
    		{
                return downloadUrl((String) urls[0]);
            } 
    		catch (IOException e)
    		{
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
    	
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Object result)
        {
    		Toast.makeText(context, "postExe", Toast.LENGTH_LONG).show();

        	if(result!=null)
        	{
        		String results = (String) result.toString();
	        	if(results!=null)
	        	{
		        	results = results.trim();
		     
		            //check the result for the what's needed to move on
		            if(results!=null)
		            {
						//error.setText("");
		            	String[] resultArray = results.split(";");
						if(resultArray!=null && resultArray[0].equals("Invites"))
						{
			            	ArrayList<String> data = new ArrayList<String>(Arrays.asList(resultArray));
							data.remove(0);
			            	makeTable(data);
	                		Toast.makeText(context, "Should be making table", Toast.LENGTH_LONG).show();
						}
						
						else if(resultArray!=null && resultArray[0].equals("Joining")){
							Intent myIntent = new Intent(JoinGame.this, GameLobby.class);
			            	myIntent.putExtra("gameId", gameId+"");
			            	SharedPreferences othSettings = getSharedPreferences(SPREF_USER, 0);
		                	SharedPreferences.Editor spEditor = othSettings.edit();
		                	spEditor.putString("CurGameID", gameId+"").commit(); //Stores current game ID
		                	spEditor.putBoolean("inGame", true).commit();   //Sets flag for user being in game
			            	myIntent.putExtra("userName", currentUser);
			            	myIntent.putExtra("userId", User1Id);
			            	startActivity(myIntent);
			            	JoinGame.this.finish();
						}
						else if(resultArray!=null && resultArray[0].equals("PlayerDeleted")){
							gameList = (TableLayout) findViewById(R.id.gameTable);
							gameList.removeView((TableRow)findViewById(deletingID));
							Toast.makeText(context, "Removed Game Invite", Toast.LENGTH_LONG).show();
						}
						else{
	                		Toast.makeText(context, "You broke it.", Toast.LENGTH_LONG).show();
						}
		            }
		            else
		            {
		            	//error.setText(results);
	            		Toast.makeText(context, results, Toast.LENGTH_LONG).show();
	
		            }
	        	}
        	}
        	else
        	{
        		//error.setText("Result was null");
        		Toast.makeText(context, "Result = null", Toast.LENGTH_LONG).show();
        	}
        }
    }

	private String downloadUrl(String myurl) throws IOException 
	{
	    InputStream is = null;
	    // Only display the first 500 characters of the retrieved
	    // web page content.
	    int len = 500;
			//Toast.makeText(context, "downloadURL", Toast.LENGTH_LONG).show();
	
	    try 
	    {
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
	    } 
	    finally 
	    {
	        if (is != null) 
	        {
	            is.close();
	        } 
	    }
	}

	public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException
	{
	   Reader reader = null;
	   reader = new InputStreamReader(stream, "UTF-8");        
	   char[] buffer = new char[len];
	   reader.read(buffer);
	   return new String(buffer);
	} 
}
