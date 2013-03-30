package com.cardsagainsthumanity.Entities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class JoinGame extends Activity
{
	Context context = this;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.joingame);
		Bundle extras = getIntent().getExtras();
		
		Toast.makeText(context, "CallingURL", Toast.LENGTH_LONG).show();
		
		Button join = (Button) findViewById(R.id.joinButton);
		join.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				
			}
		});
		
		Button returns = (Button) findViewById(R.id.ReturnToMenu);
		returns.setOnClickListener(new OnClickListener()
		{
	
			@Override
			public void onClick(View v) 
			{
				finish();
			}
			
		});
	} 
}
