package com.cardsagainsthumanity.Entities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CreateGame extends Activity
{

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.creategame);
		Button returns = (Button) findViewById(R.id.btnReturn);
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
