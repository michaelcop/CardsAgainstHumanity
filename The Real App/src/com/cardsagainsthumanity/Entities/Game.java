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
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Game extends Activity
{
	private final int maxUser = 6;
	private int numGameRounds;
	private String gameName;
	private Deck deck;
	private List<User> users;
	private int cardCzarIndex;
	
	String check;
	
	public User currentUser;//this is the meat of the data the where it is stored see User.java
	//for more info
	
	int gameID;
	String userID;
	
	String submissionID;
	String newCardText;
	String newCardID;
	
	private TextView error;
	
	int currentBlankCard = 0;
	
	//final TextView chatBox = (TextView) findViewById(R.id.chatBox);
	final TextView card1 = (TextView) findViewById(R.id.c1);
	final TextView card2 = (TextView) findViewById(R.id.c2);
	final TextView card3 = (TextView) findViewById(R.id.c3);
	final TextView card4 = (TextView) findViewById(R.id.c4);
	final TextView card5 = (TextView) findViewById(R.id.c5);
	final TextView card6 = (TextView) findViewById(R.id.c6);
	final TextView card7 = (TextView) findViewById(R.id.c7);
	final TextView blackCard = (TextView) findViewById(R.id.blackcard);
	
	final Button sendMessage = (Button) findViewById(R.id.sendMessage);
	final Button submit = (Button) findViewById(R.id.submit);
	
	final EditText message = (EditText)findViewById(R.id.messageInput);
	
	protected void onCreate(Bundle savedInstanceState)
	{ 
		this.currentUser = new User();
		super.onCreate(savedInstanceState);
		//this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.ingame);
		
		//chatBox.setMovementMethod(new ScrollingMovementMethod());
		card1.setMovementMethod(new ScrollingMovementMethod());
		card2.setMovementMethod(new ScrollingMovementMethod());
		card3.setMovementMethod(new ScrollingMovementMethod());
		card4.setMovementMethod(new ScrollingMovementMethod());
		card5.setMovementMethod(new ScrollingMovementMethod());
		card6.setMovementMethod(new ScrollingMovementMethod());
		card7.setMovementMethod(new ScrollingMovementMethod());
		blackCard.setMovementMethod(new ScrollingMovementMethod());
		sendMessage.setEnabled(false);
		
		//card1.setHint(hint);
		
		//Drawable selected = res.getDrawable(R.drawable.selectedCard);
		
		
		card1.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0) 
			{
				submissionID =  (String) card1.getHint();
				card1.setBackgroundResource(R.drawable.selectedcard);
				
				card2.setBackgroundResource(R.drawable.white);
				card3.setBackgroundResource(R.drawable.white);
				card4.setBackgroundResource(R.drawable.white);
				card5.setBackgroundResource(R.drawable.white);
				card6.setBackgroundResource(R.drawable.white);
				card7.setBackgroundResource(R.drawable.white);
				
				currentBlankCard = 1;
			}
		});

		card2.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0) 
			{
				submissionID = (String) card2.getHint();
				card2.setBackgroundResource(R.drawable.selectedcard);
				
				card1.setBackgroundResource(R.drawable.white);
				card3.setBackgroundResource(R.drawable.white);
				card4.setBackgroundResource(R.drawable.white);
				card5.setBackgroundResource(R.drawable.white);
				card6.setBackgroundResource(R.drawable.white);
				card7.setBackgroundResource(R.drawable.white);
				
				currentBlankCard = 2;
			}
		});
		
		card3.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0) 
			{
				submissionID = (String) card3.getHint();
				card3.setBackgroundResource(R.drawable.selectedcard);
				
				card2.setBackgroundResource(R.drawable.white);
				card1.setBackgroundResource(R.drawable.white);
				card4.setBackgroundResource(R.drawable.white);
				card5.setBackgroundResource(R.drawable.white);
				card6.setBackgroundResource(R.drawable.white);
				card7.setBackgroundResource(R.drawable.white);
				
				currentBlankCard = 3;
			}
		});
		card4.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0) 
			{
				submissionID = (String) card4.getHint();
				card4.setBackgroundResource(R.drawable.selectedcard);
				
				card2.setBackgroundResource(R.drawable.white);
				card3.setBackgroundResource(R.drawable.white);
				card1.setBackgroundResource(R.drawable.white);
				card5.setBackgroundResource(R.drawable.white);
				card6.setBackgroundResource(R.drawable.white);
				card7.setBackgroundResource(R.drawable.white);
				
				currentBlankCard = 4;
			}
		});
		card5.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0) 
			{
				submissionID = (String) card5.getHint();
				card5.setBackgroundResource(R.drawable.selectedcard);
				
				card2.setBackgroundResource(R.drawable.white);
				card3.setBackgroundResource(R.drawable.white);
				card4.setBackgroundResource(R.drawable.white);
				card1.setBackgroundResource(R.drawable.white);
				card6.setBackgroundResource(R.drawable.white);
				card7.setBackgroundResource(R.drawable.white);
				
				currentBlankCard = 5;
			}
		});
		card6.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0) 
			{
				submissionID = (String) card6.getHint();
				card6.setBackgroundResource(R.drawable.selectedcard);
				
				card2.setBackgroundResource(R.drawable.white);
				card3.setBackgroundResource(R.drawable.white);
				card4.setBackgroundResource(R.drawable.white);
				card5.setBackgroundResource(R.drawable.white);
				card1.setBackgroundResource(R.drawable.white);
				card7.setBackgroundResource(R.drawable.white);
				
				currentBlankCard = 6;
			}
		});
		
		card7.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0) 
			{
				submissionID = (String) card7.getHint();
				card7.setBackgroundResource(R.drawable.selectedcard);
				
				card2.setBackgroundResource(R.drawable.white);
				card3.setBackgroundResource(R.drawable.white);
				card4.setBackgroundResource(R.drawable.white);
				card5.setBackgroundResource(R.drawable.white);
				card6.setBackgroundResource(R.drawable.white);
				card1.setBackgroundResource(R.drawable.white);
				
				currentBlankCard = 7;
			}
		});
		
		sendMessage.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0) 
			{
				String value = message.getText().toString();
				//chatBox.append("\n" + value);
			}
			
		});
		
		submit.setOnClickListener(new OnClickListener()
		{
			public void onClick(View arg0)
			{
				//Will send submissionID to something
				
				
			}
		});
		
		//UI buttons end here-------------------------------------------------------------------------
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
        	Log.d("FUCK", "Jimmy");
        	String g = extras.getString("GameID");
        	Log.d("FUCK", "Jimmy2");
        	gameID = Integer.parseInt(g);
        	userID = extras.getString("UserID");
		}
    	Log.d("FUCK", "MIKE TOO");
		TextView vd = (TextView) findViewById(R.id.textView3);
		if(vd!=null){
			vd.setText(""+gameID);
		}
	}
	
	public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        
        MenuInflater menuInflater2 = getMenuInflater();
        menuInflater2.inflate(R.menu.invite, menu);
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
            startActivityForResult(set2, 0);
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
		return gameID;
	}

	 void setGameID(int gameID) {
		this.gameID = gameID;
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
	
	public void refreshUser()
	{
		//URL contains the userID and gameID
		String stringUrl = "http://54.225.225.185:8080/ServerAPP/UserGameState?User=" + userID + "&gameID="+gameID;
    	check = "UserGameState";
    	ConnectivityManager connMgr = (ConnectivityManager) 
		getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        //error.setText("creating");
        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadWebpageText().execute(stringUrl);
        } else {
            error.setText("No network connection available.");
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
	        	results = results.trim();
	        	
	            //check the result for the what's needed to move on
	            if(results!=null){
					//error.setText("");
	            	String[] resultArray = results.split(";");
	            	if(resultArray!=null && resultArray[0].equals(check)){
		            	ArrayList<String> data;
						data = new ArrayList<String>(Arrays.asList(resultArray));
						data.remove(0);//we are removing the check data field
						//now lets take that data an update it with the local fields in Game.java
						currentUser.name = data.get(0);//set user name
						currentUser.gameRound = Integer.parseInt(data.get(1));
						int numOtherUsers = Integer.parseInt(data.get(2));
						int userNameStartIndex = 3;
						int userNameEndIndex = userNameStartIndex + numOtherUsers;
						for(int i=userNameStartIndex; i<userNameEndIndex; userNameStartIndex++)
						{
							currentUser.otherUsers.add(data.get(userNameStartIndex));
						}
						currentUser.currentCzar = data.get(userNameEndIndex);
						int numWhiteCards = Integer.parseInt(data.get(userNameEndIndex + 1));
						int whiteCardsStartIndex = Integer.parseInt(data.get(userNameEndIndex + 2));
						int whiteCardsEndIndex = numWhiteCards + whiteCardsStartIndex;
						for(int i=whiteCardsStartIndex; i<whiteCardsEndIndex; whiteCardsStartIndex++)
						{
							currentUser.whiteCardsList.add(data.get(whiteCardsStartIndex));
						}
						currentUser.blackCard = data.get(whiteCardsEndIndex);
						
						//game round, list of users in game, current czar, your list of white cards,
						//the black card from the czar,
						//Note we are using ; as the delim you should do a String.split(";")
						/*
						 * Example data
						 * joe;
						 * 5;//game round
						 * 3; bob; jeff; jane;
						 * jeff;//jeff is czar
						 * 2; card 1 description; card 2 description;
						 * black card description
						 */
						//function for I am card czar and find out what people played
					}
					else if(resultArray[0]=="none") {
						error.setText("Result Array null");
					}
	
	            }
	            else{
	            	error.setText(results);
	            }
        	}
        }
        else{
        	error.setText("Result was null");
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
	
	
	

}