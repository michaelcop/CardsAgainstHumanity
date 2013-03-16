package com.cardsagainsthumanity.Entities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

import com.cardsagainsthumanity.Entities.R;

public class MainMenu extends Activity 
{
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
        case R.id.menu_bookmark:
            // Single menu item is selected do something
            // Ex: launching new activity/screen or show alert message
            Toast.makeText(MainMenu.this, "Bookmark is Selected", Toast.LENGTH_SHORT).show();
            return true;
 
        case R.id.menu_save:
            Toast.makeText(MainMenu.this, "Save is Selected", Toast.LENGTH_SHORT).show();
            return true;
 
        case R.id.menu_search:
            Toast.makeText(MainMenu.this, "Search is Selected", Toast.LENGTH_SHORT).show();
            return true;
 
        case R.id.menu_share:
            Toast.makeText(MainMenu.this, "Share is Selected", Toast.LENGTH_SHORT).show();
            return true;
 
        case R.id.menu_delete:
            Toast.makeText(MainMenu.this, "Delete is Selected", Toast.LENGTH_SHORT).show();
            return true;
 
        case R.id.menu_preferences:
            Toast.makeText(MainMenu.this, "Preferences is Selected", Toast.LENGTH_SHORT).show();
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
}
