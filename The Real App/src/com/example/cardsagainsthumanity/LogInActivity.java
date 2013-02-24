package com.example.cardsagainsthumanity;

import android.R;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.view.View.OnClickListener;


public class LogInActivity extends Activity
{
	
	EditText inputUsername;
	EditText inputPassword;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		inputUsername = (EditText) findViewById(R.id.editText1);
		inputPassword = (EditText) findViewById(R.id.editText2);
		
		Button login = (Button) findViewById(R.id.button1);
		
		login.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View arg0)
			{
				Intent nextScreen = new Intent(getApplicationContext(), MainMenuActivity.class);
				
				nextScreen.putExtra("Username", inputUsername.getText().toString());
				nextScreen.putExtra("Password", inputPassword.getText().toString());
				
				startActivity(nextScreen);
			}
		});
	}
	

}
