package Actions;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;


@WebServlet(urlPatterns={"/JoinGame"})

// Input for this class
// /JoinGame?User=''&Game=''
// NOTE: User, and Game are integer inputs
public class JoinGame extends HttpServlet implements DataSource {

	//UserID of user that is joining game
	private String User = null;
	private int UserID = 0;
	
	//GameID that user is being invited to
	private String Game = null;
	private int GameID = 0;
	
	//private String PlayerHand = null;
	private String userCards = null;
	private String Cards = null;
	
	Connection connection = null;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("User") != null){ 
			this.setUser((String) request.getParameter("User").toString());
			UserID =  Integer.parseInt(User);
		}
		if(request.getParameter("Game") != null){ 
			this.setGame((String) request.getParameter("Game").toString());
			GameID =  Integer.parseInt(Game);
		}
		
		try {
		    System.out.println("Loading driver...");
		    Class.forName("com.mysql.jdbc.Driver");
		    System.out.println("Driver loaded!");
		} catch (ClassNotFoundException e) {
		    throw new RuntimeException("Cannot find the driver in the classpath!", e);
		}
		
		JoinGame ds = new JoinGame();
        try {
			connection = ds.getConnection();
			System.out.println("connection made");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PrintWriter out = response.getWriter();
		//Check connection has been made
		if(connection != null){
			Statement stmt;
			ResultSet rs2;
			int rs, rs3;
			try {				
				stmt = connection.createStatement();
				
				//Query deck that is being used in for game
				rs2 = stmt.executeQuery("SELECT GameDeck FROM tblGames WHERE GameID = "+ GameID +";");
				
				if(rs2.next())
				{
				   //pull five cards off the top of the deck
				   Cards = rs2.getString(1);
				   //out.println("Old Cards: " + Cards);
	               String[] cards;
	               cards = Cards.split(";");
	               List<String> cardsList = new ArrayList(Arrays.asList(cards));
	               List<String> userCardsList = new ArrayList();
	               for(int i=0; i<5; i++){
	                       userCardsList.add(cardsList.get(0));
	                       cardsList.remove(0);
	               }
	               Cards = null;
	               for(String x: cardsList){
	                       if(Cards==null){
	                               Cards = x;
	                       }
	                       else{
	                               Cards = Cards+ ";" + x;
	                       }
	               }
	               userCards = null;
	               for(String x: userCardsList){
	                       if(userCards==null){
	                               userCards = x;
	                       }
	                       else{
	                               userCards = userCards+ ";" + x;
	                       }
	               }
		               
		           //out.println("New Cards : " + Cards);
		           //out.println("User Hand: " + userCards);
				}
				
				
				//Query to Update the joining user's PlayerStatus to 'In Game' - 1
				rs = stmt.executeUpdate("UPDATE tblPlayers SET PlayerStatus = 1, PlayerHand = '"+ userCards +"' WHERE PlayerGameID = "+ GameID +" AND PlayerUserID = "+ UserID +";");
				
				//Check if update was successful
				if(rs != 0){
					//User's PlayerStatus is 1 - they are 'In Game'
					out.println("Joining");
				}
				else{
					//Unable to join game
					out.println("Error: Unable to join game");
				}
				
				//Place deck back into game table
				rs3 = stmt.executeUpdate("UPDATE tblGames SET GameDeck = '"+ Cards +"' WHERE GameID = "+ GameID +";");
				
				//Check if update was successful
				if(rs3 != 0){
					//Cards updated in games table
					//out.println("Joining");
				}
				else{
					//Unable to join game
					out.println("Error: Unable to update cards in games table");
				}
				
				//Close record sets and connection
				rs2.close();
				stmt.close();
				connection.close();	
			}
			catch(SQLException e){
				e.printStackTrace();	
			}
		}
	}
 	
	private void setUser(String strUser) {
		// TODO Auto-generated method stub
		User = strUser;
		
	}

	private void setGame(String strGame) {
	// TODO Auto-generated method stub
		Game = strGame;
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
            System.out.println("Cant create a Connection");
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
		
}
