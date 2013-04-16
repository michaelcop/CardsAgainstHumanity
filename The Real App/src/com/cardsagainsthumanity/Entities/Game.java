package com.cardsagainsthumanity.Entities;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Game extends Activity
{
	private final int maxUser = 6;
	private int numGameRounds;
	private String gameName;
	private Deck deck;
	private List<User> users;
	private int cardCzarIndex;
	
	final Context context = this;
	public static final String SPREF_USER = "othPrefs";
	
	String check;
	
	public User currentUser;//this is the meat of the data the where it is stored see User.java
	//for more info
	
	int gameId;
	String userId;
	
	String submissionID;
	String newCardText;
	String newCardID;
	
	private TextView error;
	
	int currentBlankCard = 0;
	private String userName;
	
	TextView chatBox;
	TextView card1;
	TextView card2;
	TextView card3;
	TextView card4;
	TextView card5;
	TextView card6;
	TextView card7;
	TextView blackCard;
	TextView playerList;
	TextView currentRound;
	
	Button sendMessage;
	Button submit;
	

	EditText message;
	
	int currentCardSelected = 0;
	
	protected void onCreate(Bundle savedInstanceState)
	{ 
		this.currentUser = new User();
		super.onCreate(savedInstanceState);
		//this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.ingame);

        getActionBar().setDisplayShowTitleEnabled(false);
		
		//Please don't move these :<
		chatBox = (TextView) findViewById(R.id.chatBox);
		card1 = (TextView) findViewById(R.id.c1);
		card2 = (TextView) findViewById(R.id.c2);
		card3 = (TextView) findViewById(R.id.c3);
		card4 = (TextView) findViewById(R.id.c4);
		card5 = (TextView) findViewById(R.id.c5);
		blackCard = (TextView) findViewById(R.id.blackcard);
		playerList = (TextView) findViewById(R.id.playerList);
		currentRound = (TextView) findViewById(R.id.playerRound);
		
		//sendMessage = (Button) findViewById(R.id.sendMessage);
		submit = (Button) findViewById(R.id.submit);
		

		 message = (EditText)findViewById(R.id.messageInput);
		chatBox.setMovementMethod(new ScrollingMovementMethod());
		card1.setMovementMethod(new ScrollingMovementMethod());
		card2.setMovementMethod(new ScrollingMovementMethod());
		card3.setMovementMethod(new ScrollingMovementMethod());
		card4.setMovementMethod(new ScrollingMovementMethod());
		card5.setMovementMethod(new ScrollingMovementMethod());
		blackCard.setMovementMethod(new ScrollingMovementMethod());
		//sendMessage.setEnabled(false);
		
		//card1.setHint(hint);
		
		//Drawable selected = res.getDrawable(R.drawable.selectedCard);
		
		//android.view.Display display = ((android.view.WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		//Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		Display display = getWindowManager().getDefaultDisplay();
		
		@SuppressWarnings("deprecation")
		int width = display.getWidth();
		@SuppressWarnings("deprecation")
		int height = display.getHeight();
		
		card1.setWidth(width/5);
		card2.setWidth(width/5);
		card3.setWidth(width/5);
		card4.setWidth(width/5);
		card5.setWidth(width/5);
		
		card1.setHeight(height/4);
		card2.setHeight(height/4);
		card3.setHeight(height/4);
		card4.setHeight(height/4);
		card5.setHeight(height/4);
		
		//New stuff is here!
		card1.setOnTouchListener(new OnTouchListener()
		{
			
			@Override
			public boolean onTouch(View v, MotionEvent event) 
			{
				if(event.getAction() == MotionEvent.ACTION_DOWN)
		    	{

		    		 card1.bringToFront();
		    		 /*
		    		 card2.invalidate();
		    		 card3.invalidate();
		    		 card4.invalidate();
		    		 card5.invalidate();
		    		 */
		    	}
				 if (event.getAction() == MotionEvent.ACTION_UP)
				 {
					 if(currentUser.submitted)
					 {
						 	//submit.setEnabled(false);
							return true;
					 }
						//submissionID =  (String) card1.getHint();
						/*
						 * Card czar selects a card which corresponds to this button
						 * then you get the text of this card/button
						 * send to the server the text on the card and if you can the which user had this card
						 * because the user who has this card gets a point
						 * 
						 * for play white card if this person is not the card czar then they can play a white card
						 * so you have to check that i.e. if they are not card czar and the pressed the card then
						 * they have played this white card
						 * playWhiteCard(get button text)
						 */
						currentCardSelected = 1;
						
						card1.setBackgroundResource(R.drawable.selectedcard);
						
						card2.setBackgroundResource(R.drawable.white);
						card3.setBackgroundResource(R.drawable.white);
						card4.setBackgroundResource(R.drawable.white);
						card5.setBackgroundResource(R.drawable.white);
						
						currentBlankCard = 1;
				 }
				 if (event.getAction() == MotionEvent.ACTION_MOVE) 
		        {
		            // Offsets are for centering the TextView on the touch location
		            v.setX(event.getRawX() - v.getWidth() / 2.0f);
		            //v.setY(event.getRawY() - v.getHeight() / 2.0f);
		        }
				return false;
			}
		});

		card2.setOnTouchListener(new OnTouchListener()
		{

			@Override
			public boolean onTouch(View v, MotionEvent event) 
			{
				if(event.getAction() == MotionEvent.ACTION_DOWN)
		    	{

		    		 //card1.invalidate();
		    		 card2.bringToFront();
		    		 /*
		    		 card3.invalidate();
		    		 card4.invalidate();
		    		 card5.invalidate();
		    		 */
		    	}
				 if (event.getAction() == MotionEvent.ACTION_UP)
				 {
					 if(currentUser.submitted)
					 {
						 	//submit.setEnabled(false);
							return true;
					 }
						currentCardSelected = 2;
						
						//submissionID = (String) card2.getHint();
						card2.setBackgroundResource(R.drawable.selectedcard);
						
						card1.setBackgroundResource(R.drawable.white);
						card3.setBackgroundResource(R.drawable.white);
						card4.setBackgroundResource(R.drawable.white);
						card5.setBackgroundResource(R.drawable.white);
						
						currentBlankCard = 2;
				 }
				 if (event.getAction() == MotionEvent.ACTION_MOVE) 
			        {
			            // Offsets are for centering the TextView on the touch location
			            v.setX(event.getRawX() - v.getWidth() / 2.0f);
			            //v.setY(event.getRawY() - v.getHeight() / 2.0f);
			        }
				return false;
				
			}
		});
		
		card3.setOnTouchListener(new OnTouchListener()
		{

			@Override
			public boolean onTouch(View v, MotionEvent event) 
			{
				if(event.getAction() == MotionEvent.ACTION_DOWN)
		    	{

		    		// card1.invalidate();
		    		// card2.invalidate();
		    		 card3.bringToFront();
		    		// card4.invalidate();
		    		// card5.invalidate();
		    	}
				 if (event.getAction() == MotionEvent.ACTION_UP)
				 {
					 if(currentUser.submitted)
					 {
						 	//submit.setEnabled(false);
							return true;
					 }
					currentCardSelected = 3;

					//submissionID = (String) card3.getHint();
					card3.setBackgroundResource(R.drawable.selectedcard);
					
					card2.setBackgroundResource(R.drawable.white);
					card1.setBackgroundResource(R.drawable.white);
					card4.setBackgroundResource(R.drawable.white);
					card5.setBackgroundResource(R.drawable.white);
					
					currentBlankCard = 3;
				 }
				 if (event.getAction() == MotionEvent.ACTION_MOVE) 
			        {
			            // Offsets are for centering the TextView on the touch location
			            v.setX(event.getRawX() - v.getWidth() / 2.0f);
			            //v.setY(event.getRawY() - v.getHeight() / 2.0f);
			        }
				return false;
				
			}
		});
		card4.setOnTouchListener(new OnTouchListener()
		{
			
			@Override
			public boolean onTouch(View v, MotionEvent event) 
			{
				if(event.getAction() == MotionEvent.ACTION_DOWN)
		    	{
					/*
		    		 card1.invalidate();
		    		 card2.invalidate();
		    		 card3.invalidate();
		    		 */
		    		 card4.bringToFront();
		    		// card5.invalidate();
		    	}
				 if (event.getAction() == MotionEvent.ACTION_UP)
				 {
					 if(currentUser.submitted)
					 {
						 	//submit.setEnabled(false);
							return true;
					 }
						currentCardSelected = 4;
						
						//submissionID = (String) card4.getHint();
						card4.setBackgroundResource(R.drawable.selectedcard);
						
						card2.setBackgroundResource(R.drawable.white);
						card3.setBackgroundResource(R.drawable.white);
						card1.setBackgroundResource(R.drawable.white);
						card5.setBackgroundResource(R.drawable.white);
						
						currentBlankCard = 4;
				 }
				 if (event.getAction() == MotionEvent.ACTION_MOVE) 
			        {
			            // Offsets are for centering the TextView on the touch location
			            v.setX(event.getRawX() - v.getWidth() / 2.0f);
			            //v.setY(event.getRawY() - v.getHeight() / 2.0f);
			        }
				return false;
				
			}
		});
		card5.setOnTouchListener(new OnTouchListener()
		{

			@Override
			public boolean onTouch(View v, MotionEvent event) 
			{
				if(event.getAction() == MotionEvent.ACTION_DOWN)
		    	{
					/*
		    		 card1.invalidate();
		    		 card2.invalidate();
		    		 card3.invalidate();
		    		 card4.invalidate();
		    		 */
		    		 card5.bringToFront();
		    	}
				 if (event.getAction() == MotionEvent.ACTION_UP)
				 {
					 if(currentUser.submitted)
					 {
						 	//submit.setEnabled(false);
							return false;
					 }
						currentCardSelected = 5;

						//submissionID = (String) card5.getHint();
						card5.setBackgroundResource(R.drawable.selectedcard);
						
						card2.setBackgroundResource(R.drawable.white);
						card3.setBackgroundResource(R.drawable.white);
						card4.setBackgroundResource(R.drawable.white);
						card1.setBackgroundResource(R.drawable.white);
						
						currentBlankCard = 5;
				 }
				 if (event.getAction() == MotionEvent.ACTION_MOVE) 
			        {
			            // Offsets are for centering the TextView on the touch location
			            v.setX(event.getRawX() - v.getWidth() / 2.0f);
			            //v.setY(event.getRawY() - v.getHeight() / 2.0f);
			        }
				return false;
			}
		});
		
		/*sendMessage.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0) 
			{
				String value = message.getText().toString();
				//chatBox.append("\n" + value);
			}
			
		}); */
		
		submit.setOnClickListener(new OnClickListener()
		{
			public void onClick(View arg0)
			{
				//Will send submissionID to something
				if(currentCardSelected == 1)
				{
					if(userName.equalsIgnoreCase(currentUser.currentCzar))
					{
						//get text off card and post to server and post which user the card belonged to 
						String cardText = card1.getText().toString();
						int tempIndex = currentUser.whiteCardsList.indexOf(cardText);
						cardText = currentUser.whiteCardsID.get(tempIndex) + "";
						czarSelectCard(cardText);
						
					}
					else
					{
						//this is a regular user playing a white card
						//set variable to which card they clicked then
						//when the submit button is pressed register them playing that card
						String cardText = card1.getText().toString();
						int tempIndex = currentUser.whiteCardsList.indexOf(cardText);
						cardText = currentUser.whiteCardsID.get(tempIndex) + "";
						playWhiteCard(cardText);
					}
					card1.setBackgroundResource(R.drawable.white);
					card2.setBackgroundResource(R.drawable.white);
					card3.setBackgroundResource(R.drawable.white);
					card4.setBackgroundResource(R.drawable.white);
					card5.setBackgroundResource(R.drawable.white);
				}
				else if(currentCardSelected == 2)
				{
					if(userName.equalsIgnoreCase(currentUser.currentCzar))
					{
						//get text off card and post to server and post which user the card belonged to 
						String cardText = card2.getText().toString();
						int tempIndex = currentUser.whiteCardsList.indexOf(cardText);
						cardText = currentUser.whiteCardsID.get(tempIndex) + "";
						czarSelectCard(cardText);
					}
					else
					{
						//this is a regular user playing a white card
						//set variable to which card they clicked then
						//when the submit button is pressed register them playing that card
						String cardText = card2.getText().toString();
						int tempIndex = currentUser.whiteCardsList.indexOf(cardText);
						cardText = currentUser.whiteCardsID.get(tempIndex) + "";
						playWhiteCard(cardText);
					}
					card1.setBackgroundResource(R.drawable.white);
					card2.setBackgroundResource(R.drawable.white);
					card3.setBackgroundResource(R.drawable.white);
					card4.setBackgroundResource(R.drawable.white);
					card5.setBackgroundResource(R.drawable.white);
				}
				else if(currentCardSelected == 3)
				{
					if(userName.equalsIgnoreCase(currentUser.currentCzar))
					{
						//get text off card and post to server and post which user the card belonged to 
						String cardText = card3.getText().toString();
						int tempIndex = currentUser.whiteCardsList.indexOf(cardText);
						cardText = currentUser.whiteCardsID.get(tempIndex) + "";
						czarSelectCard(cardText);
					}
					else
					{
						//this is a regular user playing a white card
						//set variable to which card they clicked then
						//when the submit button is pressed register them playing that card
						String cardText = card3.getText().toString();
						int tempIndex = currentUser.whiteCardsList.indexOf(cardText);
						cardText = currentUser.whiteCardsID.get(tempIndex) + "";
						playWhiteCard(cardText);
					}
					card1.setBackgroundResource(R.drawable.white);
					card2.setBackgroundResource(R.drawable.white);
					card3.setBackgroundResource(R.drawable.white);
					card4.setBackgroundResource(R.drawable.white);
					card5.setBackgroundResource(R.drawable.white);
				}
				else if(currentCardSelected == 4)
				{
					if(userName.equalsIgnoreCase(currentUser.currentCzar))
					{
						//get text off card and post to server and post which user the card belonged to 
						String cardText = card4.getText().toString();
						int tempIndex = currentUser.whiteCardsList.indexOf(cardText);
						cardText = currentUser.whiteCardsID.get(tempIndex) + "";
						czarSelectCard(cardText);
					}
					else
					{
						//this is a regular user playing a white card
						//set variable to which card they clicked then
						//when the submit button is pressed register them playing that card
						String cardText = card4.getText().toString();
						int tempIndex = currentUser.whiteCardsList.indexOf(cardText);
						cardText = currentUser.whiteCardsID.get(tempIndex) + "";
						playWhiteCard(cardText);
					}
					card1.setBackgroundResource(R.drawable.white);
					card2.setBackgroundResource(R.drawable.white);
					card3.setBackgroundResource(R.drawable.white);
					card4.setBackgroundResource(R.drawable.white);
					card5.setBackgroundResource(R.drawable.white);
				}
				else if(currentCardSelected == 5)
				{
					if(userName.equalsIgnoreCase(currentUser.currentCzar))
					{
						//get text off card and post to server and post which user the card belonged to 
						String cardText = card5.getText().toString();
						int tempIndex = currentUser.whiteCardsList.indexOf(cardText);
						cardText = currentUser.whiteCardsID.get(tempIndex) + "";
						czarSelectCard(cardText);
					}
					else
					{
						//this is a regular user playing a white card
						//set variable to which card they clicked then
						//when the submit button is pressed register them playing that card
						String cardText = card5.getText().toString();
						int tempIndex = currentUser.whiteCardsList.indexOf(cardText);
						cardText = currentUser.whiteCardsID.get(tempIndex) + "";
						playWhiteCard(cardText);
					}
					card1.setBackgroundResource(R.drawable.white);
					card2.setBackgroundResource(R.drawable.white);
					card3.setBackgroundResource(R.drawable.white);
					card4.setBackgroundResource(R.drawable.white);
					card5.setBackgroundResource(R.drawable.white);
				}
				
			}
		});
		
		//UI buttons end here-------------------------------------------------------------------------
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
        	String g = extras.getString("gameId");
        	if(g!=null){
        	gameId = Integer.parseInt(g);}
        	userId = extras.getString("userId");
        	userName = extras.getString("userName");
        	Toast.makeText(Game.this, "GameID = " + gameId + " , UserID = " + userId + " , UserName = " + userName, Toast.LENGTH_LONG).show();
		}

		TextView vd = (TextView) findViewById(R.id.textView3);
		if(vd!=null){
			vd.setText(""+gameId);
		}
		
		refreshUser();//refresh after creating everything
	}
	
	public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        
        MenuInflater menuInflater2 = getMenuInflater();
        menuInflater2.inflate(R.menu.invite, menu);
        
        MenuInflater menuInflater3 = getMenuInflater();
        menuInflater3.inflate(R.menu.refresh, menu);
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item)
    {
 
        switch (item.getItemId())
        {

        case R.id.menu_preferences:
           // Toast.makeText(MainMenu.this, "Settings is Selected", Toast.LENGTH_SHORT).show();
            Intent set = new Intent(Game.this, Settings.class);
            startActivityForResult(set, 0);
            return true;
            
        case R.id.invite_friend:
            // Toast.makeText(MainMenu.this, "Invite Friend is Selected", Toast.LENGTH_SHORT).show();
            Intent set2 = new Intent(Game.this, InviteFriends.class);
            set2.putExtra("GameId", String.valueOf(gameId));
            set2.putExtra("UserId", userId);
            set2.putExtra("UserName", userName);

            startActivityForResult(set2, 0);
            return true;
            
        case R.id.refresh_button:
        	refreshUser();
        	//Toast.makeText(Game.this, "afterRefreshUser", Toast.LENGTH_SHORT).show();
        	//inviteList();
        	//Toast.makeText(Game.this, "afterInviteList", Toast.LENGTH_SHORT).show();
        	return true;
 
        default:
            return super.onOptionsItemSelected(item);
        }
    }  
	
	
	public String getGameName() {
		return gameName;
	}
	
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	
	public int getNumGameRounds() {
		return numGameRounds;
	}

	public void setNumGameRounds(int numGameRounds) {
		this.numGameRounds = numGameRounds;
	}

	public Deck getDeck() {
		return deck;
	}

	public void setDeck(Deck deck) {
		this.deck = deck;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public int getCardCzarIndex() {
		return cardCzarIndex;
	}

	public void setCardCzarIndex(int cardCzarIndex) {
		this.cardCzarIndex = cardCzarIndex;
	}

	public int getGameID() {
		return gameId;
	}

	 void setGameID(int gameID) {
		this.gameId = gameID;
	}

	 public int getMaxUser() {
		return maxUser;
	}


	
	public void createGame()
	{
		deck = new Deck();
		//GUI would call addusers to game after we created the game
		//how are we going to identify the games?
		//assign ID
		
		//Need to instantiate users
		
		int currentPlayers = getMaxUser();
		
		if(currentPlayers == 3)
		{
			User player1 = new User();
			User player2 = new User();
			User player3 = new User();
		}
		else if(currentPlayers == 4)
		{
			User player1 = new User();
			User player2 = new User();
			User player3 = new User();
			User player4 = new User();
		}
		else if(currentPlayers == 5)
		{
			User player1 = new User();
			User player2 = new User();
			User player3 = new User();
			User player4 = new User();
			User player5 = new User();
		}
		else if(currentPlayers == 6)
		{
			User player1 = new User();
			User player2 = new User();
			User player3 = new User();
			User player4 = new User();
			User player5 = new User();
			User player6 = new User();
		}
		
	}
	
	public void playGame()
	{
		for(int i=0; i<numGameRounds; i++)
		{
			//Select Czar for round i
			//Czar pulls black card
			//Polls for white cards from users
			//Present white cards to Czar
			//Czar selects card
			//Add point to winner
			
			
			
		}
	}
	
	public boolean addUserToGame(User user)
	{
		if(users.size() < this.maxUser)
		{
			users.add(user);
			return true;
		}
		else
		{
			System.out.println("Error: Too many users");
			return false;
		}
	}
	
	public void playWhiteCard(String text)
	{
		//URL contains the userID and gameID
		//passing in cardID as text see the buttons
		String stringUrl = "http://54.225.225.185:8080/ServerAPP/SubmitCard?User=" + userId + "&Game="+gameId + "&Card="+text;
    	check = "PlayWhiteCard";
    	ConnectivityManager connMgr = (ConnectivityManager) 
		getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        //error.setText("creating");
        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadWebpageText().execute(stringUrl);
        } else {
            //error.setText("No network connection available.");
        }
        refreshUser();
	}
	
	public void czarSelectCard(String text)
	{
		//URL contains the userID and gameID
		//passing in cardID as text see the buttons
		String stringUrl = "http://54.225.225.185:8080/ServerAPP/SubmitCard?User=" + userId + "&Game="+gameId + "&Card="+text;
    	check = "CzarSelectCard";
    	ConnectivityManager connMgr = (ConnectivityManager) 
		getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        //error.setText("creating");
        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadWebpageText().execute(stringUrl);
        } else {
            //error.setText("No network connection available.");
        }
        refreshUser();
	}
	
	public void refreshUser()
	{
		//URL contains the userID and gameID
		//Toast.makeText(Game.this, "Refresh User = " + userId + " , GameID = " + gameId, Toast.LENGTH_LONG).show();
		String stringUrl = "http://54.225.225.185:8080/ServerAPP/RefreshGame?User=" + userId + "&Game="+gameId;
    	check = "RefreshGame";
    	ConnectivityManager connMgr = (ConnectivityManager) 
		getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        //error.setText("creating");
        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadWebpageText().execute(stringUrl);
        } else {
            //error.setText("No network connection available.");
        }
	}
	
	public void inviteList()
	{
		//URL contains the userID and gameID
		String stringUrl = "http://54.225.225.185:8080/ServerAPP/CurrentFriends?User=" + userName;
    	check = "Friends";
    	ConnectivityManager connMgr = (ConnectivityManager) 
		getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        //error.setText("creating");
        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadWebpageText().execute(stringUrl);
        } else {
           // error.setText("No network connection available.");
        }
	}
	
private class DownloadWebpageText extends AsyncTask {
    	@Override
        protected Object doInBackground(Object... urls) {
            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl((String) urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
    	
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Object result) {
        if(result!=null){
        	String results = (String) result.toString();
        	if(results!=null){
        		card1.setVisibility(card1.INVISIBLE);
				card2.setVisibility(card2.INVISIBLE);
				card3.setVisibility(card3.INVISIBLE);
				card4.setVisibility(card4.INVISIBLE);
				card5.setVisibility(card5.INVISIBLE);
	        	results = results.trim();
	        	//Toast.makeText(Game.this, "results = " + results, Toast.LENGTH_SHORT).show();
	        	//results = "RefreshGame;0;4;a;0;b;1;c;2;d;4;a;3;387;Making a pouty face.;399;Parting the Red Sea.;521;The economy.;What am I giving up for Lent?";
	        	//results = "RefreshGame;5;3;bob;35;jeff;5;jane;6;jeff;9;jane;czar;2;card1ID;card1 descrpiton;card2ID,card2 description;black card description";
	            //check the result for the what's needed to move on
	        	//add Submitted or NotSubmitted
	        	//results = "RefreshGame;0;3;a;0;David2;0;justin;0;a;5;4;An honest cop with nothing left to lose.;64;Bingeing and purging.;287;The true meaning of Christmas.;524;The harsh light of day.;526;The shambling corpse of Larry King.;While the United States raced the Soviet Union to the moon, the Mexican government funneled millions of pesos into research on __________.;false;;;";
	        	//results = "RefreshGame;0;3;a;0;David2;0;justin;0;a;0;;While the United States raced the Soviet Union to the moon, the Mexican government funneled millions of pesos into research on ________.;false;;;";
	            if(results!=null){
	            	
	            	String[] resultArray = results.split(";");
	            	if(resultArray!=null && resultArray[0].equals("RefreshGame")){
	            		//lets delete the contents of the the arraylist other users and whiteCardsList
	            		currentUser.whiteCardsList.clear();
	            		currentUser.whiteCardsID.clear();
	            		currentUser.otherUsers.clear();
	            		currentUser.otherUsersScore.clear();
		            	ArrayList<String> data;
						data = new ArrayList<String>(Arrays.asList(resultArray));
						data.remove(0);//we are removing the check data field
						//now lets take that data an update it with the local fields in Game.java
						currentUser.gameRound = Integer.parseInt(data.get(0));
						int numOtherUsers = Integer.parseInt(data.get(1));
						int userNameStartIndex = 2;
						int userNameEndIndex = userNameStartIndex + (numOtherUsers *2);
						
						for(int i=userNameStartIndex; i<userNameEndIndex; i+=2)
						{
							currentUser.otherUsers.add(data.get(i));
							currentUser.otherUsersScore.add(Integer.parseInt(data.get(i+1)));
						}
						currentUser.currentCzar = data.get(userNameEndIndex);
						//Toast.makeText(Game.this, "CurrentCzar = " + currentUser.currentCzar, Toast.LENGTH_SHORT).show();
						int numWhiteCards = Integer.parseInt(data.get(userNameEndIndex + 1));
						//Toast.makeText(Game.this, "numWhiteCards = " + numWhiteCards, Toast.LENGTH_SHORT).show();
						int whiteCardsStartIndex = userNameEndIndex + 2;
						int whiteCardsEndIndex = (numWhiteCards*2) + whiteCardsStartIndex;
						for(int i=whiteCardsStartIndex; i<whiteCardsEndIndex; i+=2)
						{
							currentUser.whiteCardsID.add(Integer.parseInt(data.get(i)));
							currentUser.whiteCardsList.add(data.get(i+1));
						}
						if(whiteCardsStartIndex == whiteCardsEndIndex)
						{
							whiteCardsEndIndex++;
						}
						//Toast.makeText(Game.this, whiteCardsStartIndex + " : " + whiteCardsEndIndex, Toast.LENGTH_SHORT).show();
						//Toast.makeText(Game.this, "getblack = " + data.get(whiteCardsEndIndex+1), Toast.LENGTH_SHORT).show();
						currentUser.blackCard = data.get(whiteCardsEndIndex);
						String tempSubmittedString = data.get(whiteCardsEndIndex+1);
						//Toast.makeText(Game.this, "tempSubmittedString = " + tempSubmittedString, Toast.LENGTH_SHORT).show();
						if(tempSubmittedString.equals("true"))
							currentUser.submitted = true;
						else
							currentUser.submitted = false;
						
						if(currentUser.submitted)
						{
							submit.setEnabled(false);
							chatBox.append("Waiting for others to submit cards");
						}
						else
							submit.setEnabled(true);
						
						//Toast.makeText(Game.this, "Black Card = " + currentUser.blackCard, Toast.LENGTH_SHORT).show();
						//Toast.makeText(Game.this, "data size = " + data.size(), Toast.LENGTH_LONG).show();
						//Toast.makeText(Game.this, "white cards end index = " + whiteCardsEndIndex, Toast.LENGTH_LONG).show();
						
						//if(1==1)
							//return;
						
						if(currentUser.currentCzar.equalsIgnoreCase(userName))
						{
							/*
							 * Shuffling the czars cards
							 */
							Map<String, Integer> tempCardShuffle = new HashMap<String, Integer>();
							for(int i=0; i<currentUser.whiteCardsList.size(); i++)
							{
								tempCardShuffle.put(currentUser.whiteCardsList.get(i), currentUser.whiteCardsID.get(i));
							}
							Collections.shuffle(currentUser.whiteCardsList);
							currentUser.whiteCardsID.clear();
							for(int i=0; i<currentUser.whiteCardsList.size(); i++)
							{
								currentUser.whiteCardsID.add(tempCardShuffle.get(currentUser.whiteCardsList.get(i)));
							}
						}
						
						String lastWinningWhite ="";
						String lastBlackCard = "";
						String lastWinningUser = "";
						if(data.size() > whiteCardsEndIndex + 3)
						{
							lastWinningWhite = data.get(whiteCardsEndIndex+2);
							lastBlackCard = data.get(whiteCardsEndIndex+3);
							lastWinningUser = data.get(whiteCardsEndIndex+4);
						}
						
						if(lastWinningWhite != null && lastBlackCard != null && lastWinningUser != null
							&& !lastWinningWhite.equals("") && !lastBlackCard.equals("") && !lastWinningUser.equals(""))
						{
							chatBox.setText("Last winning user = " + lastWinningUser + "\nLast winning white card = " + 
						lastWinningWhite + "\nLast black card = " + lastBlackCard);
							currentRound.setText("Game Over!");
						}
						
						/*
						if(currentUser.whiteCardsList.size() <1)
						{
							card1.setVisibility(card1.INVISIBLE);
							card2.setVisibility(card2.INVISIBLE);
							card3.setVisibility(card3.INVISIBLE);
							card4.setVisibility(card4.INVISIBLE);
							card5.setVisibility(card5.INVISIBLE);
						}
						else if(currentUser.whiteCardsList.size() <2)
						{
							card2.setVisibility(card2.INVISIBLE);
							card3.setVisibility(card3.INVISIBLE);
							card4.setVisibility(card4.INVISIBLE);
							card5.setVisibility(card5.INVISIBLE);
						}
						else if(currentUser.whiteCardsList.size() <3)
						{
							card3.setVisibility(card3.INVISIBLE);
							card4.setVisibility(card4.INVISIBLE);
							card5.setVisibility(card5.INVISIBLE);
						}
						else if(currentUser.whiteCardsList.size() <4)
						{
							card4.setVisibility(card4.INVISIBLE);
							card5.setVisibility(card5.INVISIBLE);
						}
						else if(currentUser.whiteCardsList.size() <5)
						{
							card5.setVisibility(card5.INVISIBLE);
						}
						
						*/
						//clear text to make sure we dont leave old stuff and
						//keep the new items only showing
						card1.setText("");
						card2.setText("");
						card3.setText("");
						card4.setText("");
						card5.setText("");
						
						//Toast.makeText(Game.this, "WhiteCardsListSize = " + currentUser.whiteCardsList.size() + " , otherUsersSize = " + currentUser.otherUsers.size(), Toast.LENGTH_SHORT).show();
						//Toast.makeText(Game.this, "1" + (currentUser.currentCzar.equals(userName) && currentUser.whiteCardsList.size() == (currentUser.otherUsers.size() - 1)), Toast.LENGTH_SHORT).show();
						//Toast.makeText(Game.this, "2" + (!currentUser.currentCzar.equals(userName) || (currentUser.currentCzar.equals(userName) && currentUser.whiteCardsList.size() == (currentUser.otherUsers.size() - 1))), Toast.LENGTH_SHORT).show();
						//Toast.makeText(Game.this, "3" + (!currentUser.currentCzar.equals(userName)), Toast.LENGTH_SHORT).show();
						//Toast.makeText(Game.this, "currentCzar = " + currentUser.currentCzar + " , username = " + userName, Toast.LENGTH_SHORT).show();
						
						if(!currentUser.currentCzar.equalsIgnoreCase(userName) || (currentUser.currentCzar.equalsIgnoreCase(userName) && currentUser.whiteCardsList.size() == (currentUser.otherUsers.size() - 1)))
						{
							
							if(currentUser.whiteCardsList.size() > 0)
							{
								card1.setVisibility(card1.VISIBLE);
								card1.setText(currentUser.whiteCardsList.get(0));
							}
							if(currentUser.whiteCardsList.size() > 1)
							{
								card2.setVisibility(card2.VISIBLE);
								card2.setText(currentUser.whiteCardsList.get(1));
							}
							if(currentUser.whiteCardsList.size() > 2)
							{
								card3.setVisibility(card3.VISIBLE);
								card3.setText(currentUser.whiteCardsList.get(2));
							}
							if(currentUser.whiteCardsList.size() > 3)
							{
								card4.setVisibility(card4.VISIBLE);
								card4.setText(currentUser.whiteCardsList.get(3));
							}
							if(currentUser.whiteCardsList.size() > 4)
							{
								card5.setVisibility(card5.VISIBLE);
								card5.setText(currentUser.whiteCardsList.get(4));
							}
							
						}
						blackCard.setText(currentUser.blackCard);
						
						String playerListString = "";
						for(int i=0; i<currentUser.otherUsers.size(); i++)
						{
							playerListString += currentUser.otherUsers.get(i) + " : " + currentUser.otherUsersScore.get(i) + "\n";
						}
						playerList.setText(playerListString);
						
						//put game round to gui
						currentRound.setText("Currend Round = " + currentUser.gameRound);
						
						if(currentUser.currentCzar.equalsIgnoreCase(userName))
						{
							//this is the card czar deal with his cards
							if(currentUser.whiteCardsList.size() < currentUser.otherUsers.size() - 1)
							{
								int numCardsLeft = currentUser.otherUsers.size()-1 - currentUser.whiteCardsList.size();
								chatBox.setText("You are the Card Czar!\nWe are waiting on " + numCardsLeft + " cards" + "\n" + 
												currentUser.whiteCardsList.size() + "/" + (currentUser.otherUsers.size()-1) );
								
							}
							else
							{
								chatBox.setText("You are the Card Czar!\nSelect the winner!"  + "\n" + 
										currentUser.whiteCardsList.size() + ":" + currentUser.otherUsers.size());
							}
						}
						
						//game round, list of users in game, current czar, your list of white cards,
						//the black card from the czar,
						//Note we are using ; as the delim you should do a String.split(";")
						/*
						 * Example data
						 * 5;//game round
						 * 3; bob ;35; jeff; 5; jane; 6;//the numbers after the name is the other users score
						 * jeff;//jeff is czar
						 * 2; card 1 description; card 2 description;
						 * black card description
						 * submitted
						 */
						//function fgggggggor I am card czar and find out what people played
					}
	            	
	            	
	            	else if(resultArray!=null && resultArray[0].equals("PlayerDeleted"))
	            	{
	            		Toast.makeText(Game.this, "Leaving Game", Toast.LENGTH_SHORT).show();
	            		//handles the leave game/quit game code only when in post execute
						SharedPreferences othSettings = getSharedPreferences(SPREF_USER, 0);
	                	SharedPreferences.Editor spEditor = othSettings.edit();
	                	spEditor.putBoolean("inGame", false).commit();
	                	spEditor.remove("CurGameID").commit();
	                	Intent intent = new Intent(getApplicationContext(), MainMenu.class);
				    	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				    	intent.putExtra("userName", userName);
				    	intent.putExtra("userId", userId);
				    	startActivity(intent);
						Game.this.finish();
	            	}
	            	
	            	else if(resultArray!=null && resultArray[0].equals("GameOver"))
	            	{
	            		String winner = resultArray[1];
	            		chatBox.setText("The winner is " + winner);
	            		card1.setVisibility(card1.INVISIBLE);
						card2.setVisibility(card2.INVISIBLE);
						card3.setVisibility(card3.INVISIBLE);
						card4.setVisibility(card4.INVISIBLE);
						card5.setVisibility(card5.INVISIBLE);
						blackCard.setVisibility(blackCard.INVISIBLE);
						submit.setEnabled(false);
	            	}
	            	
	            	else if(resultArray!=null && resultArray[0].equals("Success"))
	            	{
	            		//if(resultArray[1] == "Success")
	            	//	{
	            			Toast.makeText(Game.this, "Successfully submitted the card", Toast.LENGTH_SHORT).show();
	            		//}
	            		//else
	            		//{
	            		//	Toast.makeText(Game.this, "Error submitting card", Toast.LENGTH_SHORT).show();
	            		//}
	            	}
	            	
					else if(resultArray[0]=="none") {
						Toast.makeText(Game.this, "Error in RefreshUser", Toast.LENGTH_SHORT).show();
					}
	
	            }
	            else{
	            	Toast.makeText(Game.this, "Error in RefreshUser", Toast.LENGTH_SHORT).show();
	            }
        	}
        }
        else{
        	//error.setText("Result was null");
        }
       }

     }

 private String downloadUrl(String myurl) throws IOException {
      InputStream is = null;
      // Only display the first 500 characters of the retrieved
      // web page content.
      int len = 500;
          
      try {
          URL url = new URL(myurl);
          HttpURLConnection conn = (HttpURLConnection) url.openConnection();
          conn.setReadTimeout(10000 /* milliseconds */);
          conn.setConnectTimeout(15000 /* milliseconds */);
          conn.setRequestMethod("GET");
          conn.setDoInput(true);
          // Starts the query
          conn.connect();
          int response = conn.getResponseCode();
          is = conn.getInputStream();

          // Convert the InputStream into a string
          String contentAsString = readIt(is, len);
          return contentAsString;
          
      // Makes sure that the InputStream is closed after the app is
      // finished using it.
      } finally {
          if (is != null) {
              is.close();
          } 
      }
  }
	
  	//Reads an InputStream and converts it to a String.
	public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
	   Reader reader = null;
	   reader = new InputStreamReader(stream, "UTF-8");        
	   char[] buffer = new char[len];
	   reader.read(buffer);
	   return new String(buffer);
	} 
	
	//Back button functionality------------------------------------
			@Override
			public boolean onKeyDown(int keyCode, KeyEvent event) 
			{
				
			    if (keyCode == KeyEvent.KEYCODE_BACK) 
			    {
			    	
			    	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
			 
						// set title
						alertDialogBuilder.setTitle("Leaving \"Oh the Humanity!\"");
			 
						// set dialog message
						alertDialogBuilder
							.setMessage("Do you want to close application or quit game?")
							.setCancelable(true)
							.setPositiveButton("Close",new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,int id) {
									// close current activity
									Game.this.finish();
							    	//stopRepeatingTask();
								}
							  })
							  .setNeutralButton("Quit Game",new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,int id) {
									exitURLHandler();//deal with exiting on post execute
								} 
							
							}) 
							.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,int id) {
									// cancel back button op
									dialog.cancel();
								}
							
							});
			 
							// create alert dialog
							AlertDialog alertDialog = alertDialogBuilder.create();
			 
							// show it
							alertDialog.show();
			    	
			        //moveTaskToBack(true);
			        return true;
			    }
			    return super.onKeyDown(keyCode, event);
			}  //End back button functionality---------------------------------
			
			public void exitURLHandler()
			{
				//URL contains the userID and gameID
				//Toast.makeText(GameLobby.this, "GameID = " + gameID, Toast.LENGTH_SHORT).show();
				String stringUrl = "http://54.225.225.185:8080/ServerAPP/LeaveGame?Game="+gameId+"&User="+userId;
		    	check = "LeaveGame";
		    	ConnectivityManager connMgr = (ConnectivityManager) 
				getSystemService(Context.CONNECTIVITY_SERVICE);
		        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		        //Toast.makeText(GameLobby.this, "At refresh game lobby", Toast.LENGTH_SHORT).show();
		        //error.setText("creating");
		        if (networkInfo != null && networkInfo.isConnected()) {
		            new DownloadWebpageText().execute(stringUrl);
		        } else {
		            //error.setText("No network connection available.");
		        }
			}

}