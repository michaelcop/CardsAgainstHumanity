package com.example.cardsagainsthumanity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.app.Activity;

public class StartPage extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		Button v = (Button) findViewById(R.id.button1);
		v.setOnClickListener(new OnClickListener() {

	        public void onClick(View v) {
	        	setContentView(R.layout.mainmenu); 
	        	}
			
			
	        });
	        
	}
	
	
}
