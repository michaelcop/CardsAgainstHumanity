package src.Actions;
import java.io.*;
import java.sql.*;
import java.util.logging.Logger;


import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;

@WebServlet(urlPatterns={"/InviteToGame"})
//Invited

// Input for this class
// /InviteToGame?UserInvited=''&Game=''&UserInviting=''
// NOTE: UserInvited, Game, and UserInviting are all integer inputs
public class InviteToGame extends HttpServlet implements DataSource {

	//UserID of user that is being invited
	private String UserInvited = null;
	private int InvitedUserID = 0;
	
	//UserID of user that is inviting a friend
	private String UserInviting = null;
	private int InvitingUserID = 0;
	
	//GameID that user is being invited to
	private String Game = null;
	private int GameID = 0;
	
	Connection connection = null;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("UserInvited") != null){ 
			this.setUserInvited((String) request.getParameter("UserInvited").toString());
			InvitedUserID =  Integer.parseInt(UserInvited);
		}
		if(request.getParameter("Game") != null){ 
			this.setGame((String) request.getParameter("Game").toString());
			GameID =  Integer.parseInt(Game);
		}
		if(request.getParameter("UserInviting") != null){ 
			this.setUserInviting((String) request.getParameter("UserInviting").toString());
			InvitingUserID =  Integer.parseInt(UserInviting);
		}
		
		try {
		    System.out.println("Loading driver...");
		    Class.forName("com.mysql.jdbc.Driver");
		    System.out.println("Driver loaded!");
		} catch (ClassNotFoundException e) {
		    throw new RuntimeException("Cannot find the driver in the classpath!", e);
		}
		
		InviteToGame ds = new InviteToGame();
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
			Statement stmt, stmt2;
			ResultSet rs2;
			int rs;
			try {				
				stmt = connection.createStatement();
				stmt2 = connection.createStatement();
				//Check if player has already been invited to the game
				rs2 = stmt2.executeQuery("SELECT PlayerGameID, PlayerUserID, PlayerStatus FROM tblPlayers WHERE PlayerGameID = "+ GameID +" AND PlayerUserID = "+InvitedUserID+";");
				
				//Move to position 0 in the record set
				//rs2.beforeFirst();
				//Check if record set is empty
				if(rs2.next()){
					//out.println("I SHOULD be here.");
					//Check if User is in game or has already been invited
					if(rs2.getInt(3) == 0)
						out.println("Already invited");
					else if(rs2.getInt(3) == 1)
						out.println("In Game");
				}
				else if(!rs2.next()){
					//out.println("I shouldn't be here." + "playerGame" + rs2.getInt(1) + "playerUser" + rs2.getInt(2));
					//User has not been invited to game - Proceed with invitation
					
					//Add invited friend to the Players table and set player status to 1 - indicates that a player
					//has been invited to a game, but they have not joined yet
					rs = stmt.executeUpdate("INSERT INTO tblPlayers (PlayerGameID, PlayerUserID, PlayerStatus, InvitedByUserID) VALUES ("+GameID+","+ InvitedUserID+",0,"+InvitingUserID+");");
					
					if(rs!=0){
						out.println("Invited");
					}
					else{
						out.println("Error: Unable to invite to game");
					}
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
 	
	private void setUserInviting(String strInviting) {
		// TODO Auto-generated method stub
		UserInviting = strInviting;
		
	}

	private void setUserInvited(String strInvited) {
		// TODO Auto-generated method stub
		UserInvited = strInvited;
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
