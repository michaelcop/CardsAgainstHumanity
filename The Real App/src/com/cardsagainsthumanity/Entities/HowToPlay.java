package com.cardsagainsthumanity.Entities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.cardsagainsthumanity.Entities.R;

public class HowToPlay extends Activity
{
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		//Old
		//setContentView(R.layout.howtoplay);
	
		DrawableTest mCustomView;
		mCustomView = new DrawableTest(this);
		setContentView(mCustomView);
		
		Button returns = (Button) findViewById(R.id.button1);
		
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
