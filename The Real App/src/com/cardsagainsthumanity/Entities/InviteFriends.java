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
	private TextView error;
	int UserId;
	String UserName;
	
	ArrayList<String> testStrings;
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.invitefriend);
		error = (TextView) findViewById(R.id.error);
		TableLayout t = (TableLayout) findViewById(R.id.friendsTable);
		Bundle extras = getIntent().getExtras();
		
		if (extras != null) {
			UserName = extras.getString("UserName");
			testStrings = extras.getStringArrayList("data");
		}
		// Get the TableLayout
	    t = (TableLayout) findViewById(R.id.friendsTable);
	
	    // Go through each item in the array
	    for ( int current = 0; current < testStrings.size(); current++)
	    {
	        // Create a TableRow and give it an ID
	        TableRow tr = new TableRow(this);
	        
			tr.setId(UserId);
	        tr.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT ,LayoutParams.WRAP_CONTENT));   
	
	        // Create a TextView to house the name of the province
	        TextView labelTV = new TextView(this);
	        labelTV.setId(current);
	        labelTV.setText(testStrings.get(++current));
	        labelTV.setTextColor(Color.WHITE);
	        //labelTV.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
	        tr.addView(labelTV);
	
	        // Create a TextView to house the value of the after-tax income
	        String temp = testStrings.get(++current);
	        UserId = Integer.parseInt(temp);
	        Button b = new Button(this);
	        b.setId(500+UserId);
	        b.setTextColor(InviteFriends.this.getResources().getColor(R.color.White));
	        if(testStrings.get(current).equals("1")){
	        	b.setText("Invite to Game");
	        	b.setOnClickListener(new OnClickListener()
	    		{
	
	    			@Override
	    			public void onClick(View v) 
	    			{
	    				//Delete
	    				String stringUrl = "http://54.225.225.185:8080/ServerAPP/DeleteFriend?User="+UserName+"&UserID="+UserId;
			        	error.setText("USER ID FOR DELETION IS: " +UserId);
	    				//callUrl(stringUrl);
	    			}
	    			
	    		});
	        	tr.addView(b);
	        }
	        else if(testStrings.get(current).equals("2")){
	        	b.setText("Cancel Request");
	        	b.setOnClickListener(new OnClickListener()
	    		{
	
	    			@Override
	    			public void onClick(View v) 
	    			{
	    				//Cancel 
	    				
	    			}
	    			
	    		});
	        	tr.addView(b);
	        }
	        else if(testStrings.get(current).equals("3")){
	        	b.setText("Accept Request");
	        	b.setOnClickListener(new OnClickListener()
	    		{
	
	    			@Override
	    			public void onClick(View v) 
	    			{
	    				//Accept
	    				
	    			}
	    			
	    		});
	        	tr.addView(b);
	        	}
	    	else if(testStrings.get(current).equals("-1")){
	        	TextView v = new TextView(InviteFriends.this);
	        	tr.addView(v);
	    	}
	        //valueTV.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
	        
	        // Add the TableRow to the TableLayout
	        t.addView(tr, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
	    }
	} 
}
