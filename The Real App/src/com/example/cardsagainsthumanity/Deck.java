package com.example.cardsagainsthumanity;

import java.util.*;
import java.io.*;

public class Deck 
{
	List<Card> cards;
	
	Deck()
	{
		this.initDeck();
	}
	
	public void initDeck()
	{
		//read in cards and shuffle
		this.cards = new ArrayList<Card>();
	}

	public void getCards()
	{
		
	}
	
	private void setCards(List<Card> cards) {
		this.cards = cards;
	}

	public void shuffleDeck()
	{
		/*
		for(int i=0; i<whiteCards.size(); i++)
		{
			int a = (int) (Math.random() * whiteCards.size());
			int b = (int) (Math.random() * whiteCards.size());
			Card tempA = whiteCards.get(a);
			Card tempB = whiteCards.get(b);
			whiteCards.remove(a);
			whiteCards.remove(b);
			whiteCards.add(tempA);
			whiteCards.add(tempB);
		}
		
		for(int i=0; i<blackCards.size(); i++)
		{
			int a = (int) (Math.random() * blackCards.size());
			int b = (int) (Math.random() * blackCards.size());
			BlackCard tempA = blackCards.get(a);
			BlackCard tempB = blackCards.get(b);
			blackCards.remove(a);
			blackCards.remove(b);
			blackCards.add(tempA);
			blackCards.add(tempB);
		}
		*/
	}
}
