package com.cardsagainsthumanity.Entities;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class FriendsList extends Activity
{
	ArrayList<String> testStrings;
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.friendslist);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			testStrings = extras.getStringArrayList("data");
		}
		
		Button returns = (Button) findViewById(R.id.ReturnToMenu);
		returns.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				finish();
			}
			
		});
		
		// Get the TableLayout
        TableLayout tl = (TableLayout) findViewById(R.id.friendsTable);

        // Go through each item in the array
        for (int current = 0; current <  testStrings.size(); current++)
        {
            // Create a TableRow and give it an ID
            TableRow tr = new TableRow(this);
            tr.setId(100+current);
            tr.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));   

            // Create a TextView to house the name of the province
            TextView labelTV = new TextView(this);
            labelTV.setId(200+current);
            labelTV.setText(testStrings.get(current));
            labelTV.setTextColor(Color.WHITE);
            //labelTV.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
            tr.addView(labelTV);

            // Create a TextView to house the value of the after-tax income
            TextView valueTV = new TextView(this);
            valueTV.setId(current);
            valueTV.setText("$0");
            valueTV.setTextColor(Color.WHITE);
            //valueTV.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
            tr.addView(valueTV);

            // Add the TableRow to the TableLayout
            tl.addView(tr, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
        }
	} 
}
