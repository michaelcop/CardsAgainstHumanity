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
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
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
	
	int gameID;
	String userID;
	
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
	
	protected void onCreate(Bundle savedInstanceState)
	{ 
		this.currentUser = new User();
		super.onCreate(savedInstanceState);
		//this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.ingame);

        //getActionBar().setDisplayShowTitleEnabled(false);
		
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
		//chatBox.setMovementMethod(new ScrollingMovementMethod());
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
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		
		card1.setWidth((int)(metrics.widthPixels * .33) );
		
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
				
				
			}
		});
		
		//UI buttons end here-------------------------------------------------------------------------
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
        	Log.d("FUCK", "Jimmy");
        	String g = extras.getString("GameID");
        	Log.d("FUCK", "Jimmy2");
        	if(g!=null){
        	gameID = Integer.parseInt(g);}
        	userID = extras.getString("UserID");
        	userName = extras.getString("UserName");
        	Toast.makeText(Game.this, "GameID = " + gameID + " , UserID = " + userID + " , UserName = " + userName, Toast.LENGTH_LONG).show();
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
            set2.putExtra("GameId", String.valueOf(gameID));
            set2.putExtra("UserId", userID);
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
	
	public void czarCardSelected()
	{
		/*
		 * In refresh call this and if the number of cards played == numotherusers.size - 1
		 * change gui and update white cards to the to the ones the others have played
		 */
		//URL contains the userID and gameID
		String stringUrl = "http://54.225.225.185:8080/ServerAPP/CzarCardSelected?UserID=" + userID + "&GameID="+gameID;
    	check = "CzarCardSelected";
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
	
	public void refreshUser()
	{
		//URL contains the userID and gameID
		String stringUrl = "http://54.225.225.185:8080/ServerAPP/RefreshGame?UserID=" + userID + "&GameID="+gameID;
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
	        	results = results.trim();
	        	results = "RefreshGame;5;3;bob;35;jeff;5;jane;6;jeff;2;card1 descrpiton;card2 description;black card description";
	            //check the result for the what's needed to move on
	            if(results!=null){
					//error.setText("");Toast.makeText(Game.this, "In results = " + results, Toast.LENGTH_SHORT).show();
	            	String[] resultArray = results.split(";");
	            	if(resultArray!=null && resultArray[0].equals("RefreshGame")){
	            		//lets delete the contents of the the arraylist other users and whiteCardsList
	            		currentUser.whiteCardsList.clear();
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
						int numWhiteCards = Integer.parseInt(data.get(userNameEndIndex + 1));
						int whiteCardsStartIndex = userNameEndIndex + 2;
						int whiteCardsEndIndex = numWhiteCards + whiteCardsStartIndex;
						for(int i=whiteCardsStartIndex; i<whiteCardsEndIndex; i++)
						{
							currentUser.whiteCardsList.add(data.get(i));
						}
						currentUser.blackCard = data.get(whiteCardsEndIndex);
						
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
						
						if(currentUser.whiteCardsList.size() > 0)
							card1.setText(currentUser.whiteCardsList.get(0));
						if(currentUser.whiteCardsList.size() > 1)
							card2.setText(currentUser.whiteCardsList.get(1));
						if(currentUser.whiteCardsList.size() > 2)
							card3.setText(currentUser.whiteCardsList.get(2));
						if(currentUser.whiteCardsList.size() > 3)
							card4.setText(currentUser.whiteCardsList.get(3));
						if(currentUser.whiteCardsList.size() > 4)
							card5.setText(currentUser.whiteCardsList.get(4));
						blackCard.setText(currentUser.blackCard);
						
						String playerListString = "";
						for(int i=0; i<currentUser.otherUsers.size(); i++)
						{
							playerListString += currentUser.otherUsers.get(i) + " : " + currentUser.otherUsersScore.get(i) + "\n";
						}
						playerList.setText(playerListString);
						
						//put game round to gui
						currentRound.setText("Currend Round = " + currentUser.gameRound);
						
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
						 */
						//function for I am card czar and find out what people played
					}
	            	
	            	else if(resultArray!=null && resultArray[0].equals("CzarCardSelected"))
	            	{
	            		/*
	            		 * Example data
	            		 * 4;card1 description; card2 description; card3 description; card4 description
	            		 */
	            		ArrayList<String> data;
						data = new ArrayList<String>(Arrays.asList(resultArray));
						data.remove(0);//we are removing the check data field
						int numCardsPlayed = Integer.parseInt(data.get(0));
						if(numCardsPlayed == currentUser.otherUsers.size() - 1 && userName.equals(currentUser.currentCzar))
						{
							//everyone has played their card and this is the card czar
							//change the gui and have the card czar select
							List<String> otherCards = new ArrayList<String>();
							for(int i=0; i<numCardsPlayed; i++)
							{
								otherCards.add(data.get(i+1));
							}
							if(otherCards.size() > 0)
								card1.setText(otherCards.get(0));
							if(otherCards.size() > 1)
								card2.setText(otherCards.get(1));
							if(otherCards.size() > 2)
								card3.setText(otherCards.get(2));
							if(otherCards.size() > 3)
								card4.setText(otherCards.get(3));
							if(otherCards.size() > 4)
								card5.setText(otherCards.get(4));
							//gui is updated now deal with pressing the buttons
						}
						else
						{
							//not everyone has played their card yet
						}
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
									SharedPreferences othSettings = getSharedPreferences(SPREF_USER, 0);
				                	SharedPreferences.Editor spEditor = othSettings.edit();
				                	spEditor.putBoolean("inGame", false).commit();
				                	spEditor.remove("CurGameID").commit();
				                	Intent intent = new Intent(getApplicationContext(), MainMenu.class);
							    	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							    	intent.putExtra("UserName", userName);
							    	intent.putExtra("UserId", userID);
							    	startActivity(intent);
									Game.this.finish();
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
				String stringUrl = "http://54.225.225.185:8080/ServerAPP/LeaveGame?Game="+gameID+"&User="+userID;
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