package Actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
//? User=UserId&GameID=
//Returns RefreshGame;GameRound#;#OfPlayers;PlayersInGame;
//CardCzarUserName;NumberOfCardsInHand;CardStrings;blackcardString;


@WebServlet(urlPatterns={"/RefreshGame"})
public class Game extends HttpServlet implements DataSource {
	/**
	 * 
	 */
	private static final long serialVersionUID = -972529346689447377L;
	private String User =  null;
	private String Game = null;
	private int UserID;
	private int GameId;
	Connection connection = null;
	List<String> UsersInGame;
	
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		if(request.getParameter("UserID") != null){ 
			this.setUser((String) request.getParameter("UserID").toString());
			UserID =  Integer.parseInt(User);
		}
		if(request.getParameter("GameID") != null){ 
			this.setUser((String) request.getParameter("GameID").toString());
			GameId =  Integer.parseInt(User);
		}
		try {
		    System.out.println("Loading driver...");
		    Class.forName("com.mysql.jdbc.Driver");
		    System.out.println("Driver loaded!");
		} catch (ClassNotFoundException e) {
		    throw new RuntimeException("Cannot find the driver in the classpath!", e);
		}
		
		FriendsList v = new FriendsList();
        try {
			connection = v.getConnection();
			System.out.println("connection made");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PrintWriter out = response.getWriter();
		
		//Multiple Queries will need to be run here.
		if(connection != null){
				Statement stmt;
				ResultSet rs;
				try {
					stmt = connection.createStatement();
					//QUERY HERE NEEDS TO CHANGE TO RETURN PLAYERS IN GAME
					rs = stmt.executeQuery("QUERY");
					if(!rs.isBeforeFirst()){
						out.println("None in the List");
					}
					else{
						out.print("Successful");
						while(rs.next()){
							out.print("; List");
						}
					}
					rs.close();
					stmt.close();
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		
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


