package com.cardsagainsthumanity.Entities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class StartActivity extends Activity
{
	
	//public static final String SPREF_USER = "othPrefs";
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Intent myIntent = new Intent(this, StartPage.class);
		startActivity(myIntent);
		StartActivity.this.finish();
	}
	
}
