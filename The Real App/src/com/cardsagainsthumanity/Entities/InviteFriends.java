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
    //private TextView error;
	String UserName;
	String check;
	int current;
	String User2;
	TableLayout t;
	int  UserId;
	private String User1Id;
	private String GameId;	
	ArrayList<String> testStrings;
	String ID;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.invitefriend);
		Bundle extras = getIntent().getExtras();

        //getActionBar().setDisplayShowTitleEnabled(false);
		if (extras != null) {
			User1Id = extras.getString("UserId");
			GameId = extras.getString("GameId");
			UserName = extras.getString("UserName");
			//testStrings = extras.getStringArrayList("data");
		}
		//Toast.makeText(context,  GameId, Toast.LENGTH_LONG).show();
		if(UserName!=null){
			callUrl("http://54.225.225.185:8080/ServerAPP/CurrentFriends?User="+UserName);
		}
		else{
    		Toast.makeText(context, "No User", Toast.LENGTH_LONG).show();
		}
		//error = (TextView) findViewById(R.id.error);

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
            tr.setBackgroundResource(R.drawable.friendsrow);
            // Create a TextView to house the name of the province
            TextView labelTV = new TextView(this);
            labelTV.setText(testStrings.get(++current));
            labelTV.setId(500+UserId);
            labelTV.setTextColor(Color.WHITE);
            labelTV.setPadding(10, 0, 5, 0);
            //labelTV.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
            tr.addView(labelTV);
            
            // Create a TextView to house the value of the after-tax income
            if(UserId!=1000){
	            final Button b = new Button(this);
	            b.setId(5000+UserId);
	            b.setTextColor(InviteFriends.this.getResources().getColor(R.color.White));
	            b.setText("Invite Friend to Game");
	            b.setHint("UserId:"+UserId);
	            tr.addView(b);
	            b.setOnClickListener(new OnClickListener()
        		{

        			@Override
        			public void onClick(View v) 
        			{
        				//Delete
        				String[] temp = b.getHint().toString().split(":");
        				ID = temp[1];
        				String stringUrl = "http://54.225.225.185:8080/ServerAPP/InviteToGame?UserInvited="+ID+"&Game="+GameId+"&UserInviting="+User1Id;        
        	    		//Toast.makeText(context, stringUrl, Toast.LENGTH_LONG).show();

        				callUrl(stringUrl);
        			}
        			
        		});
	            
	            b.setWidth(LayoutParams.WRAP_CONTENT);
	            b.setTextSize(12);
            }
            //valueTV.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
           
            // Add the TableRow to the TableLayout
            t.addView(tr, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
        }
		
		
	}
	
	
	private class DownloadWebpageText extends AsyncTask {
        
    	@Override
        protected Object doInBackground(Object... urls) {
            // params comes from the execute() call: params[0] is the url.
    		//Toast.makeText(context, "BG", Toast.LENGTH_LONG).show();

    		try {
                return downloadUrl((String) urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
    	
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Object result) {
    		//Toast.makeText(context, "postExe", Toast.LENGTH_LONG).show();

        	if(result!=null){
        	String results = (String) result.toString();
        	if(results!=null){
	        	results = results.trim();
	     
	            //check the result for the what's needed to move on
	            if(results!=null){
					//error.setText("");
	            	String[] resultArray = results.split(";");
					if(resultArray!=null && resultArray[0].equals("Friends")){
		            	ArrayList<String> data = new ArrayList<String>(Arrays.asList(resultArray));
						data.remove(0);
		            	makeTable(data);
                		//Toast.makeText(context, "Should be making table", Toast.LENGTH_LONG).show();
					}
					else if(resultArray!=null && resultArray[0].equals("Invited")){
						Button C;
						if(resultArray.length==2){
							C = (Button) findViewById(5000+Integer.parseInt(resultArray[1]));

						}
						else{
							 C = (Button) findViewById((5000+Integer.parseInt(ID)));
						}
						C.setText("Invited");
						C.setEnabled(false);
						Toast.makeText(context,  "Invited!", Toast.LENGTH_LONG).show();
					}
					else{
                		//Toast.makeText(context, results, Toast.LENGTH_LONG).show();
						}
	            }
	            else{
	            	//error.setText(results);
            		//Toast.makeText(context, results, Toast.LENGTH_LONG).show();

	            }
        	}
        }
        else{
        	//error.setText("Result was null");
    		//Toast.makeText(context, "Result = null", Toast.LENGTH_LONG).show();

        }
       }

     }

	private String downloadUrl(String myurl) throws IOException {
      InputStream is = null;
      // Only display the first 500 characters of the retrieved
      // web page content.
      int len = 500;
		//Toast.makeText(context, "downloadURL", Toast.LENGTH_LONG).show();

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
            		//Toast.makeText(context, "No network COnnection", Toast.LENGTH_LONG).show();

                }	
	}
	
	
	
}
