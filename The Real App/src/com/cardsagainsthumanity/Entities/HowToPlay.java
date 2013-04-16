package com.cardsagainsthumanity.Entities;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cardsagainsthumanity.Entities.R;

public class HowToPlay extends Activity
{
	int imageNumber = 1;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		

       // getActionBar().setDisplayShowTitleEnabled(false);
		setContentView(R.layout.howtoplay);
		
		TextView howPlay = (TextView) findViewById(R.id.rulesLabel);
		howPlay.setMovementMethod(new ScrollingMovementMethod());
		
		final String testContent = "<html><body><p>To start a game, select Create Game from the Main Menu</p>" + "<img src=\"ss_menu.png\"/>" +
				"<p>Next, select the Number of Rounds</p>"+	"<p>Once the desired number of rounds has been chosen, select Create Game. Now, in " +
				"the Player Lobby, press the Invite Friend icon</p>"+ "<img src=\"add_friend.jpg\"/>"+ "<p>There are three terms that " +
				"players should be familiar with. They are Black Cards, White Cards, and Card Czar. The objective of the game is to use the terms on " +
				"the White Cards to fill in the blanks on the Black Cards, in order to win points from the Card Czar, who will decide the best/funniest " +
				"card combination. When the game begins, the game host will be assigned the role of Card Czar. All other players will get five White " +
				"Cards.</p>"+"<img src=\"ss_whitecard\"/>"+"<p>Next, they will select one of their White Cards that they feel will best complete the " +
				"Black Card, and will submit it for judging.</p>" +"<img src=\"ss_blackcards\"/>"+ "<p>At the end of every round, a new Card Czar will be " +
				"assigned. In the end, who ever ends up with the most points will win.</p></body></html>";
		
	    howPlay.setText(Html.fromHtml(testContent, imgGetter, null));
		
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
	
	private ImageGetter imgGetter = new ImageGetter() {

	    public Drawable getDrawable(String source) {
	            Drawable drawable = null;
	            if(imageNumber == 1) {
		            drawable = getResources().getDrawable(R.drawable.ss_menu);
		            ++imageNumber;
	            }
	            else if(imageNumber == 2){
	            	drawable = getResources().getDrawable(R.drawable.add_friend);
	            	++imageNumber;
	            }
	            else if(imageNumber == 3){
	            	drawable = getResources().getDrawable(R.drawable.ss_whitecard);
	            	++imageNumber;
	            }
	            else if(imageNumber == 4){
	            	drawable = getResources().getDrawable(R.drawable.ss_blackcard);
	            	++imageNumber;
	            }
	            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable .getIntrinsicHeight());

	            return drawable;
	    }
	 };
}
