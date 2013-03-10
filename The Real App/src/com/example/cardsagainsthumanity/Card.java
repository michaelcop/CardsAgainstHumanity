package com.example.cardsagainsthumanity;

public class Card 
{
	private String text;
	
	Card(String text)
	{
		this.text = text;
	}
	
	public String getText()
	{
		return this.text;
	}
	
	public void setText(String text)
	{
		this.text = text;
	}
}
