package com.example.cardsagainsthumanity;

import java.util.*;

public class User 
{
	String name;
	private String password;
	
	int wins;
	int losses;
	
	int points;
	
	User(String name)
	{
		this.name = name;
	}
	
	public void setPassWord(String passWord)
	{
		this.password = passWord;
	}
}
