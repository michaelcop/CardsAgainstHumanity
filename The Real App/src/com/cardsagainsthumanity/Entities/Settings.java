package com.cardsagainsthumanity.Entities;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.*;

import android.app.Activity;
import android.content.*;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.AdapterView.OnItemSelectedListener;

import java.io.*;

public class Settings extends Activity implements OnItemSelectedListener 
{
	private User user;
	private Game games;
	public int defaultGameRounds;
	int rounds = 0;
	
	final Context context = this;
	public static final String SPREF_USER = "othPrefs";
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.settings);
		
		Spinner inputRounds = (Spinner) findViewById(R.id.settingRoundsSpinner);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.roundArray, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		inputRounds.setAdapter(adapter);
		//Set spinner to default
		SharedPreferences othSettings = getSharedPreferences(SPREF_USER, 0);
		if(othSettings.contains("defGameRounds"))
			inputRounds.setSelection(othSettings.getInt("defGameRounds", 2) - 1);
		inputRounds.setOnItemSelectedListener(this);
		
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
				
            	
				//Gets and stores default game rounds --------------------------------
            	SharedPreferences othSettings = getSharedPreferences(SPREF_USER, 0);
            	SharedPreferences.Editor spEditor = othSettings.edit();
            	spEditor.putInt("defGameRounds", rounds).commit();
            	//End def game rounds store ------------------------------------------
				
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
		
		Button Logout = (Button) findViewById(R.id.LogOutButton);
		Logout.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View x)
			{
				logOut();
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
		//Erase user preferences info.
		SharedPreferences othSettings = getSharedPreferences(SPREF_USER, 0);
    	SharedPreferences.Editor spEditor = othSettings.edit();
    	spEditor.remove("UserName").commit();
    	spEditor.remove("digest").commit();
    	spEditor.remove("ID").commit();
    	spEditor.remove("defGameRounds").commit();
    	//End erasing username & pw
    	
    	//Inform user of logout status on game close
    	if(!othSettings.contains("UserName") && !othSettings.contains("digest"))
    		Toast.makeText(context, "Logging Out", Toast.LENGTH_SHORT).show();
    	else
    		Toast.makeText(context, "Logout failed", Toast.LENGTH_LONG).show();
    	//End logout message  --------------JK
    	
    	//Goes back to login screen
    	Settings.this.finish();
    	Intent intent = new Intent(getApplicationContext(), StartPage.class);
    	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	intent.putExtra("EXIT", true);
    	startActivity(intent);
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
	        @SuppressWarnings("unused")
			String readString = new String(inputBuffer);
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}



	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) 
	{
		// TODO Auto-generated method stub
		((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
		//Toast.makeText(getApplicationContext(), (String)parent.getItemAtPosition(pos), Toast.LENGTH_SHORT).show();
		
		String numRounds = (String)parent.getItemAtPosition(pos);
		rounds = Integer.parseInt(numRounds);
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) 
	{
		// TODO Auto-generated method stub
	}
}
