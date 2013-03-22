package com.cardsagainsthumanity.Entities;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.cardsagainsthumanity.Entities.R;

public class HowToPlay extends Activity
{
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		
		setContentView(R.layout.howtoplay);
		
		TextView howPlay = (TextView) findViewById(R.id.rulesLabel);
		howPlay.setMovementMethod(new ScrollingMovementMethod());
		
		/*
		DrawableTest mCustomView;
		mCustomView = new DrawableTest(this);
		setContentView(mCustomView);
		*/
		Button returns = (Button) findViewById(R.id.GotIt);
		
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
