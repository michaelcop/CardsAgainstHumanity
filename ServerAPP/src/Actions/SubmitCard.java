package src.Actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
//? User=UserId&GameID=
//


@WebServlet(urlPatterns={"/SubmitCard"})
public class SubmitCard extends HttpServlet implements DataSource {
	/**
	 * 
	 */
	private static final long serialVersionUID = -972529346689447377L;
	private String User =  null;
	private int UserID = 0;
	private String Card = null;
	private int CardID = 0;
	private String Game = null;
	private int GameID = 0;
	
	private int CurrentRound = 0;
	private int MaxRounds = 0;
	private int NewCzarID = 0;
	
	private String WinningCardText = null;
	private int WinningCardID;
	
	private int GameWinningPlayerID = 0;
	private String GameWinningPlayer = null;
	
	private String LosingPlayerIDs = null;
	
	
	private int RoundWinningPlayerID = 0;
	private int CurrentScore = 0;
	
	private String blackCards = "";
	private String blackCard = "";
	
	
	
	Connection connection = null;

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		if(request.getParameter("User") != null){ 
			this.setUser((String) request.getParameter("User").toString());
			UserID =  Integer.parseInt(User);
		}
		if(request.getParameter("Game") != null){ 
			this.setGame((String) request.getParameter("Game").toString());
			GameID =  Integer.parseInt(Game);
		}
		if(request.getParameter("Card") != null){ 
			this.setCard((String) request.getParameter("Card").toString());
			CardID =  Integer.parseInt(Card);
		}
		
		try {
		    System.out.println("Loading driver...");
		    Class.forName("com.mysql.jdbc.Driver");
		    System.out.println("Driver loaded!");
		} catch (ClassNotFoundException e) {
		    throw new RuntimeException("Cannot find the driver in the classpath!", e);
		}
		
		SubmitCard v = new SubmitCard();
        try {
			connection = v.getConnection();
			System.out.println("connection made");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PrintWriter out = response.getWriter();
		if(connection != null && User != null && Card != null){
				Statement stmt;
				ResultSet rs2, rs3, rs5, rs6, rs19, rs15, rs16;
				int rs, rs4, rs7, rs8, rs9, rs20, rs21, rs22;
				try {
					stmt = connection.createStatement();
					
					//Check to see if User is Card Czar
					rs2 = stmt.executeQuery("SELECT GameJudge, GameRounds, GameCurRound FROM tblGames WHERE GameID = "+ GameID +";");
					
					//Get Card CzarID
					if(rs2.next())
					{
						
						//Get game round information
						MaxRounds = rs2.getInt(2);
						CurrentRound = rs2.getInt(3);
						
						//User is the Card Czar
						if(rs2.getInt(1) == UserID)
						{
							//Look up submitted cards to see if they equal the card the Czar is submitting
							rs3 = stmt.executeQuery("SELECT PlayerUserID, PlayerScore FROM tblPlayers WHERE SubmittedCard = "+ CardID +" AND PlayerGameID = "+ GameID +";");
							
							//Get player that won the round
							if(rs3.next())
							{
								RoundWinningPlayerID = rs3.getInt(1);
								
								//Get current score
								CurrentScore = rs3.getInt(2);
									
								//Increment score and update player score
								CurrentScore = CurrentScore+1;
									
								rs4 = stmt.executeUpdate("UPDATE tblPlayers SET tblPlayers.PlayerScore = "+CurrentScore+" WHERE PlayerGameID = "+ GameID +" AND PlayerUserID = "+ RoundWinningPlayerID +";");
								
								if(rs4!=0)
								{
									//Score Updated
									//out.print("Score Updated");
								}
							}
						
							//Get players to update new czar
							rs5 = stmt.executeQuery("SELECT PlayerUserID FROM tblPlayers WHERE PlayerGameID = "+ GameID +" AND PlayerStatus = 1 ORDER BY PlayerUserID;");
							
							if(rs5.isBeforeFirst())
							{
								//Pick next Czar
								while(rs5.next()){
									//Get next user in list, after the current czar
									if(rs5.getInt(1) == UserID)
									{
										break;
									}
								}
								
								if(rs5.next())
								{
									//Set card czar to next user in list
									NewCzarID = rs5.getInt(1);
								}
								else
								{
									//Pick card czar from the head of the list
									rs5.first();
									NewCzarID = rs5.getInt(1);
									//out.print("first() :" + rs5.getInt(1));
								}
								
								//Update new Card Czar
								//Done in rs7
								//out.print("NewCzar: "+ NewCzarID);
							}
							
							//out.print("NewCzar: "+ NewCzarID);
							//====================== GAME ROUND ===================================
								
							//Increment round number for game
							CurrentRound = CurrentRound + 1;
							
							//Check if current round equals max round
							if(CurrentRound <= MaxRounds )
							{
								
								rs7 = stmt.executeUpdate("UPDATE tblGames SET GameCurRound = "+ CurrentRound +", GameJudge = "+ NewCzarID +" WHERE GameID = "+ GameID +";");
								
								//Check if update round was successful
								if(rs7==0)
								{
									//Error
									out.print("Error: Unable to update round.");
								}
								else
								{
									//Successful
									//Update black card
									
									//Remove all submitted Cards for game
									rs8 = stmt.executeUpdate("UPDATE tblPlayers SET SubmittedCard = 0 WHERE PlayerGameID = "+GameID+";");
									
									if(rs8!=0)
									{
										//Update successful
										out.print("Submitted cards updated");
									}
									
									//Update the new black card
									//Update Last winning white and black cards
									 rs19 = stmt.executeQuery("SELECT GameBlackCards FROM tblGames WHERE GameID = "+GameID+";");
                                     if(rs19.next())
                                     {
                                         blackCards = rs19.getString(1);
                                         String[] blackCardListArray = blackCards.split(";");
                                         blackCard = blackCardListArray[0];
                                         List<String> blackCardList = new ArrayList<String>(Arrays.asList(blackCardListArray));
                                         blackCardList.remove(0);
                                         blackCards = null;
                                         for(String x: blackCardList)
                                         {
                                             if(blackCards==null)
                                             {
                                            	 blackCards=x;
                                             }
                                             else
                                             {
                                                 blackCards=blackCards+ ";" + x;
                                             }                                        
                                         }

                                         rs20 =  stmt.executeUpdate("UPDATE tblGames SET LastBlackCard="+blackCard+", LastWhiteCard = "+CardID+",GameBlackCards='"+blackCards+"', LastWinningPlayer = "+RoundWinningPlayerID+" WHERE GameID = "+GameID+";");
                                     }
								}
								
							}
							else
							{
								//============================ GAME OVER ============================
								
								//Get player with the highest score in game - Winner of Game
								rs15 = stmt.executeQuery("SELECT PlayerUserID FROM tblPlayers WHERE PlayerGameID = "+ GameID +" AND PlayerScore = (SELECT Max(PlayerScore) FROM tblPlayers GROUP BY PlayerGameID HAVING PlayerGameID = "+GameID+") ORDER BY PlayerID LIMIT 1;");
								
								if(rs15.next())
								{
									//Game Winner
									GameWinningPlayerID = rs15.getInt(1);
									
									//out.print("Game Winner: "+GameWinningPlayerID+";");
									
									//Update winner stats in Users table
									rs21 = stmt.executeUpdate("UPDATE tblUsers SET UserWins = UserWins+1 WHERE UserID = "+GameWinningPlayerID+";");
									
									if(rs21==0)
									{
										//Error
										//out.print("Error: unable to update game wins.");
									}
									
									
									//Get losing player IDs
									rs16 = stmt.executeQuery("SELECT PlayerUserID FROM tblPlayers WHERE PlayerGameID = "+GameID+" AND PlayerStatus = 1 AND PlayerUserID NOT In("+GameWinningPlayerID+");");
									
									//Build SQL In string of losing players
									if(rs16.isBeforeFirst())
									{
										LosingPlayerIDs = "";
										while(rs16.next())
										{
											LosingPlayerIDs = LosingPlayerIDs + rs16.getString(1) + ",";
										}
										//trim comma off string
										LosingPlayerIDs = LosingPlayerIDs.substring(0, LosingPlayerIDs.lastIndexOf(","));
										
										//out.print("Losing Player IDs: "+LosingPlayerIDs+";");
										
										//Update losses for other players
										rs22 = stmt.executeUpdate("UPDATE tblUsers SET UserLosses = UserLosses+1 WHERE UserID In ("+LosingPlayerIDs+");");
										
										//Check if successful
										if(rs22==0)
										{
											//Error
											//out.print("Error: unable to udate game losses.");
										}
										
										
									}
									
								}
								else
								{
									//Error
									//out.print("Error: Unable to determine game winner");
								}
								
								//Game over
								out.print("GameOver;");
								
								//Remove players and game records
								
								//============================ END GAME OVER ========================
								
							}
							//========================== END ========================================
							
						}
						//Not the Card Czar
						else
						{
							//Set submitted card in players table
							rs = stmt.executeUpdate("UPDATE tblPlayers SET SubmittedCard = "+ CardID +" WHERE PlayerGameID = "+ GameID +" AND PlayerUserID = "+UserID+";");
							
							//Check if update was successful
							if(rs!= 0)
							{
								//Update successful
								out.print("Success");
								
								 //Remove card from hand and add a new one
                                rs2 = stmt.executeQuery("SELECT PlayerHand FROM tblPlayers WHERE PlayerUserID="+ UserID + ";");
                                if(rs2.next()){
                                        String Hand = rs2.getString(1);
                                        String[] Cards = Hand.split(";");
                                        List<String> cardsList = new ArrayList<String>(Arrays.asList(Cards));
                                        if(cardsList.contains(Integer.toString(CardID))){
                                                cardsList.remove(Integer.toString(CardID));
                                        }
                                        else{
                                                out.print("Card Not in Hand");
                                        }
                                        
                                        rs3 = stmt.executeQuery("SELECT GameDeck FROM tblGames WHERE GameID="+GameID+";");
                                        if(rs3.next()){
                                                String Deck = rs3.getString(1);
                                                String[] deckCards = Deck.split(";");
                                                List<String> deckList = new ArrayList<String>(Arrays.asList(deckCards));
                                                String newCard = deckList.get(0);
                                                deckList.remove(0);
                                                
                                                cardsList.add(newCard);
                                                //put deck back together
                                                Deck = null;
                                                for(String x: deckList)
                                                {
                                                    if(Deck==null){
                                                            Deck = x;
                                                    }
                                                    else{
                                                            Deck = Deck + ";" + x;
                                                    }
                                                }
                                                
                                       
                                             rs4 = stmt.executeUpdate("UPDATE tblGames SET GameDeck='"+Deck+"' WHERE GameID="+GameID +";");
                                        }
                                        else{
                                                //out.print("No Deck");
                                        }
                                        //put hand back together
                                        Hand = null;
                                        for(String x: cardsList){
                                                if(Hand==null){
                                                        Hand = x;
                                                }
                                                else{
                                                        Hand = Hand + ";" + x;
                                                }
                                        }
                                        rs7 = stmt.executeUpdate("UPDATE tblPlayers SET PlayerHand='"+Hand+"' WHERE PlayerUserID="+UserID+" AND PlayerGameID="+ GameID +";");
                                }
                                else
                                {
                                	//No Hand
                                }
								
								
							}
							else
							{
								
								//Error
								//out.print("Error: Unable to submit card");
							}
						}
					}
					
					
					

					
					
					stmt.close();
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			else
			{
				out.print("");
			}
		
		}
		



	private void setGame(String strGame) {
		// TODO Auto-generated method stub
		Game = strGame;
	}




	private void setCard(String strCard) {
		// TODO Auto-generated method stub
		Card = strCard;
	}




	@Override
	public PrintWriter getLogWriter() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public int getLoginTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public Connection getConnection() throws SQLException {
        if (connection != null) {
            System.out.println("Cant craete a Connection");
    } else {
            connection = DriverManager.getConnection(
                            "jdbc:mysql://ourdbinstance.cbvrc3frdaal.us-east-1.rds.amazonaws.com:3306/dbAppData", "AWSCards", "Cards9876");
    }
    return connection;
	}

	@Override
	public Connection getConnection(String username, String password)
			throws SQLException {
		// TODO Auto-generated method stub
        if (connection != null) {
                System.out.println("Cant create a Connection");
        } else {
                connection = DriverManager.getConnection(
                                "jdbc:mysql://ourdbinstance.cbvrc3frdaal.us-east-1.rds.amazonaws.com:3306/dbAppData", username, password);
        }
        return connection;
	}


	public String getUser() {
		return User;
	}


	public void setUser(String user) {
		User = user;
	}

	/*
	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}
	*/
	
	
	
	
	
}


