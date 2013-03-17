package com.cardsagainsthumanity.Entities;
import java.util.*;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

public class Game extends Activity
{
	private final int maxUser = 6;
	private int numGameRounds;
	private String gameName;
	private Deck deck;
	private List<User> users;
	private int cardCzarIndex;
	private int gameID;
	
	
	protected void onCreate(Bundle savedInstanceState){ 
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.gamelayout);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
        	Log.d("FUCK", "Jimmy");
        	String g = extras.getString("GameID");
        	Log.d("FUCK", "Jimmy2");
        	gameID = Integer.parseInt(g);
		}
    	Log.d("FUCK", "MIKE TOO");
		TextView vd = (TextView) findViewById(R.id.textView3);
		if(vd!=null){
			vd.setText(""+gameID);
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
}