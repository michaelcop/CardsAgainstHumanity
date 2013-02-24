package com.example.cardsagainsthumanity;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class StartPage extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_start_page, menu);
		return true;
	}
	
	public void onClick(){
		
		
		return;
	}
	
}
