package com.cardsagainsthumanity.Entities;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.app.Activity;
import android.content.Intent;
import com.cardsagainsthumanity.Entities.R;

public class StartPage extends Activity {
	
	EditText inputUsername;
	EditText inputPassword;
	String userName;
	String password;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);
		
		Button v = (Button) findViewById(R.id.button1);
		
		inputUsername = (EditText) findViewById(R.id.editText1);
		inputPassword = (EditText) findViewById(R.id.editText2);
		
		v.setOnClickListener(new OnClickListener() {
			
	        public void onClick(View v) 
	        {
	        	
	        	User user = new User(inputUsername.getText().toString());
	        	user.setPassword("Test");
	        	
	        	if((inputPassword.getText().toString()).equals(user.getPassword()))
	        	{   	
		        	Intent myIntent = new Intent(v.getContext(), MainMenu.class);
	                startActivityForResult(myIntent, 0);
	        	}
	        	else
	        	{
	        		inputUsername.setText("NONE");
	        		System.out.println("Incorrect username or password. Please try again.");	        		
	        	}
        	}
			

        }); 
		
		Button ca = (Button) findViewById(R.id.button2);
	
		ca.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) 
			{
				Intent myIntent = new Intent(v.getContext(), CreateAccount.class);
                startActivity(myIntent);
				
			}
			
		});
	}	
}
