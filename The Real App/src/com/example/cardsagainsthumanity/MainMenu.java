package com.example.cardsagainsthumanity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.cardsagainsthumanity.Entities.R;

public class MainMenu extends Activity 
{
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mainmenu);
	
		//Create listener----------------------------------------------
		Button create = (Button) findViewById(R.id.createButton);
		
		create.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(v.getContext(), CreateGame.class);
                startActivityForResult(myIntent, 0);
				
			}
			
		});
		
		//Create listener end----------------------------------------------
		
		
		//How to Play listener---------------------------------------------
		
		Button how = (Button) findViewById(R.id.howButton);
		
		how.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) 
			{
				Intent myIntent = new Intent(v.getContext(), HowToPlay.class);
                startActivityForResult(myIntent, 0);
				
			}
			
		});
		
		//How to Play listener end---------------------------------------------
		
		//Join listener---------------------------------------------
		
		Button join = (Button) findViewById(R.id.joinButton);
		
		join.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) 
			{
				Intent myIntent = new Intent(v.getContext(), JoinGame.class);
                startActivityForResult(myIntent, 0);
				
			}
			
		});
		
		//Join listener end---------------------------------------------
		
		Button friends = (Button) findViewById(R.id.friendsButton);
		
		friends.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) 
			{
				Intent myIntent = new Intent(v.getContext(), FriendsList.class);
                startActivityForResult(myIntent, 0);
				
			}
			
		});
		
		
		Button stats = (Button) findViewById(R.id.statsButton);
		
		stats.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) 
			{
				Intent myIntent = new Intent(v.getContext(), PlayerStats.class);
                startActivityForResult(myIntent, 0);
				
			}
			
		});
	}
}
