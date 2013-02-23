package com.example.cardsagainsthumanity;
import java.util.*;

public class Game 
{
	final int maxUser = 6;
	int numGameRounds;
	Deck deck;
	List<User> users;
	int cardCzarIndex;
	int gameID;
	
	Game()
	{
		
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
			System.out.println("error too many users");
			return false;
		}
	}
}