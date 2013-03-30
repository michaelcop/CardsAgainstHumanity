package Actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Statement;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
//? User=UserId&GameID=
//Returns GameLobby;#ofPeople;NameOfEachPlayer..;...;


@WebServlet(urlPatterns={"/UserGameLobby"})
public class UserGameLobby extends HttpServlet implements DataSource {
	/**
	 * 
	 */
	private static final long serialVersionUID = -972529346689447377L;
	private String User =  null;
	private int UserID;
	Connection connection = null;

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		if(request.getParameter("User") != null){ 
			this.setUser((String) request.getParameter("User").toString());
			UserID =  Integer.parseInt(User);
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
		if(connection != null){
			//Check if user exists in database
			if(User!= null && User.length()!=0){
				Statement stmt;
				ResultSet rs;
				//int rs2;
				try {
					stmt = connection.createStatement();
					
					//QUERY HERE NEEDS TO CHANGE TO RETURN PLAYERS IN GAME
					rs = stmt.executeQuery("SELECT tblFriends.FriendUserID, tblUsers_1.Username, tblFriends.FriendStatus FROM tblUsers AS tblUsers_1 INNER JOIN (tblUsers INNER JOIN tblFriends ON tblUsers.UserID = tblFriends.UserID) ON tblUsers_1.UserID = tblFriends.FriendUserID WHERE tblUsers.Username = '"+ User +"';");
					if(!rs.isBeforeFirst()){
						//this situation shouldn't be possible
						out.println("GameLobby;1000;None;-1");
					}
					else{
						out.print("GameLobby");
						//for size check this - http://stackoverflow.com/questions/192078/how-do-i-get-the-size-of-a-java-sql-resultset
						while(rs.next()){
							out.print(";" + /* Number of Players + */ rs.getInt(1));
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