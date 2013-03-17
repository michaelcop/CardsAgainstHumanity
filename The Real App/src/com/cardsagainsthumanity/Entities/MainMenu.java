package com.cardsagainsthumanity.Entities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainMenu extends Activity 
{
	final Context context = this;

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
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
		
		
		//Create listener----------------------------------------------
		ImageButton create = (ImageButton) findViewById(R.id.createButton);
		
		create.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				Intent cGame = new Intent(v.getContext(), CreateGame.class);
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
				Intent myIntent = new Intent(v.getContext(), FriendsList.class);
                startActivity(myIntent);
				
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
	}
}
