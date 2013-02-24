package com.example.cardsagainsthumanity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import com.cardsagainsthumanity.Entities.R;

public class MainMenu extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mainmenu);
	}
}
