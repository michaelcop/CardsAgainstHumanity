package com.cardsagainsthumanity.Entities;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.*;

import android.app.Activity;
import android.content.*;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.*;

public class Settings extends Activity
{
	private User user;
	private Game games;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.settings);
		
		//Save Button listener----------------------------------------------
		Button saveButton = (Button) findViewById(R.id.SaveButton);
		
		saveButton.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) 
			{
				ToggleButton notificationsToggleButton = (ToggleButton) findViewById(R.id.toggleNotificationsButton);
				if(notificationsToggleButton.isChecked())
				{
					Toast.makeText(Settings.this, "Toggle Button is on", Toast.LENGTH_SHORT).show();
				}
			}
			
		});
		
		Button returns = (Button) findViewById(R.id.ReturnToMenu);
		returns.setOnClickListener(new OnClickListener()
		{
	
			@Override
			public void onClick(View w) 
			{
				finish();
			}
			
		});
		
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Game getGames() {
		return games;
	}

	public void setGames(Game games) {
		this.games = games;
	}

	void logOut()
	{
		
	}
	
	void changeNumRounds()
	{
		
	}
	
	void editUserName()
	{
		
	}
	
	void editPassWord()
	{
		
	}
	
	void writeSettings()
	{
		/*
		 * Tutorial located at 
		 * http://stackoverflow.com/questions/1239026/how-to-create-a-file-in-android
		 */
		try
		{
			//write testString to file
			String testString = "test";
			FileOutputStream fOut = openFileOutput("samplefile.txt", Context.MODE_PRIVATE);
			OutputStreamWriter osw = new OutputStreamWriter(fOut);
			
			osw.write(testString);
			
			osw.flush();
			osw.close();
			
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
	
	void readSettings()
	{
		try
		{
			FileInputStream fIn = openFileInput("samplefile.txt");
	        InputStreamReader isr = new InputStreamReader(fIn);
	        
	        char[] inputBuffer = new char[100];
	        isr.read(inputBuffer);
	        String readString = new String(inputBuffer);
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
}
