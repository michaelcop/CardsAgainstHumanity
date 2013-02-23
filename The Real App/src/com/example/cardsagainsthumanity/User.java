package com.example.cardsagainsthumanity;

import java.util.*;

public class User 
{
	List<User> friends;
	
	String name;//Assume each person has a unique name
	private String password;
	
	int wins;
	int losses;
	
	int points;//points in the current game and we are assuming you are only playing one
	//game at a time
	
	User(String name)
	{
		this.name = name;
	}
	
	public void setPassWord(String passWord)
	{
		this.password = passWord;
	}
	
	public boolean hasFriend(User user)
	{
		for(int i=0; i<friends.size(); i++)
		{
			if(friends.get(i).name.equals(user.name))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public boolean addFriend(User user)
	{
		for(int i=0; i<friends.size(); i++)
		{
			if(friends.get(i).name.equals(user.name))
			{
				return false;
			}
		}
		
		friends.add(user);
		return true;
		
	}
}
