import java.util.*;
import java.io.*;

public class Deck 
{
	List<WhiteCard> whiteCards;
	List<BlackCard> blackCards;
	
	Deck()
	{
		this.initDeck();
	}
	
	public void initDeck()
	{
		//read in cards and shuffle
		this.whiteCards = new ArrayList<WhiteCard>();
		this.blackCards = new ArrayList<BlackCard>();
	}
	
	private void readCards()
	{
		try
		{
			//read cards from file
			Scanner scBlack = new Scanner(new File("blackCards.txt"));
			Scanner scWhite = new Scanner(new File("whiteCards.txt"));
			String blackCardString ="", whiteCardString = "";
			while(scBlack.hasNext())
				blackCardString +=  scBlack.next();
			while(scWhite.hasNext())
				whiteCardString +=  scWhite.next();
			String[] blackCards = blackCardString.split("<>");
			String[] whiteCards = blackCardString.split("<>");
			
			//add cards to deck
			for(String blackCard : blackCards)
			{
				this.blackCards.add(new BlackCard(blackCard));
			}
			for(String whiteCard : whiteCards)
			{
				this.whiteCards.add(new WhiteCard(whiteCard));
			}
		}
		catch(Exception e)
		{
			
		}
	}
}
