package com.cardsagainsthumanity.Entities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

import com.cardsagainsthumanity.Entities.R;

public class MainMenu extends Activity 
{
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
				Intent cGame = new Intent(v.getContext(), Game.class);
				startActivityForResult(cGame, 0);
				
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
                startActivityForResult(myIntent, 0);
				
			}
			
		});
		
		
		ImageButton friends = (ImageButton) findViewById(R.id.friendsButton);
		
		friends.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) 
			{
				Intent myIntent = new Intent(v.getContext(), FriendsList.class);
                startActivityForResult(myIntent, 0);
				
			}
			
		});
		
		ImageButton stats = (ImageButton) findViewById(R.id.statsButton);
		
		stats.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) 
			{
				Intent myIntent = new Intent(v.getContext(), PlayerStats.class);
                startActivityForResult(myIntent, 0);
				
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
                startActivityForResult(myIntent, 0);
				
			}
			
		});
		
		
		
		
	}
}
