package com.example.cardsagainsthumanity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.app.Activity;
import android.content.Intent;

public class StartPage extends Activity {
	
	EditText inputUsername;
	EditText inputPassword;
	String userName;
	String password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		Button v = (Button) findViewById(R.id.button1);
		
		inputUsername = (EditText) findViewById(R.id.editText1);
		inputPassword = (EditText) findViewById(R.id.editText2);
		
		v.setOnClickListener(new OnClickListener() {
			
	        public void onClick(View v) {
	        	
	        	password = "test";
	        	if(inputPassword.getText().toString() == password)
	        	{
	        		
	        	
		        	Intent myIntent = new Intent(v.getContext(), MainMenu.class);
	                startActivityForResult(myIntent, 0);
	        	}
	        	}
			
			
	        });
	        
	}
	
	
}
