package src.Actions;
//HERE'S THE CLASS BUT WE THE QUERY AREA IS NOT RIGHT AT ALL
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

@WebServlet(urlPatterns={"/FriendsList"})
public class FriendsList extends HttpServlet implements DataSource {
	private String User =  null;
	//private int UserID;
	Connection connection = null;
	//private String password =  null;

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		if(request.getParameter("User") != null){ 
			this.setUser((String) request.getParameter("User").toString());
		}
		/*
		if(request.getParameter("password") != null){
			this.setPassword((String) request.getParameter("password").toString());
		}
		*/
		
		try {
		    System.out.println("Loading driver...");
		    Class.forName("com.mysql.jdbc.Driver");
		    System.out.println("Driver loaded!");
		} catch (ClassNotFoundException e) {
		    throw new RuntimeException("Cannot find the driver in the classpath!", e);
		}
		
		FriendsList ds = new FriendsList();
        try {
			connection = ds.getConnection();
			System.out.println("connection made");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PrintWriter out = response.getWriter();
		if(connection != null){
			//System.out.println("not null");
			//out.println(User  + "   " + password);
			
			//Check if user exists in database
			if(User!= null && User.length()!=0){
				//System.out.println("user not null");
				Statement stmt;
				ResultSet rs;
				//int rs2;
				try {
					stmt = connection.createStatement();
					
					//Get user table ID
					//rs = stmt.executeQuery("SELECT UserID FROM tblUsers WHERE Username = " + User + ";");
					
					
					//Get user's friends
					//System.out.println("before query");
					//rs = stmt.executeQuery("SELECT tblUsers.Username, tblFriends.FriendUserID, tblFriends.FriendStatus FROM tblUsers INNER JOIN tblFriends ON tblUsers.UserID = tblFriends.UserID WHERE tblUsers.Username = '" + User + "' AND tblFriends.FriendStatus = 1;");
					rs = stmt.executeQuery("SELECT tblUsers_1.Username, tblFriends.FriendStatus FROM tblUsers AS tblUsers_1 INNER JOIN (tblUsers INNER JOIN tblFriends ON tblUsers.UserID = tblFriends.UserID) ON tblUsers_1.UserID = tblFriends.FriendUserID WHERE tblUsers.Username = '"+ User +"';");
					//System.out.println("after query");
					//If user has no friends
					if(!rs.isBeforeFirst()){
						out.println(User + " has not added any friends.");
					}
					else{
						//Display users friends
						out.print("Friends");
						while(rs.next()){
							//System.out.println("in while");
							//System.out.println("Friend Username: " + rs.getString(1) + " Friend Status: " + rs.getInt(2) );
							out.print(";" + rs.getString(1) + ";" + rs.getInt(2));
						}
					}
					
					
					/*
					stmt2 = connection.createStatement();
					rs = stmt.executeQuery("SELECT * FROM tblUsers WHERE Username = '" + User + "';");
					System.out.println("query exe");
					//If Username doesn't exist, then add user to the database
					if(!rs.next()){
						System.out.println("user does not exist");
						rs2 = stmt2.executeUpdate("INSERT INTO tblUsers (Username, UserPassword) VALUES ('" + User + "', '" + password + "');");
						if(rs2==0){
							out.println("Account failed to be created. Server error code: I hate jews");
						}
						else{
							out.println("User: " + User + " successfully created!");
						}
						//out.println("Username: " + User + " was not found in Users table.");
					}
					else{
						//Username already exists, display warning message
						if(rs.getString(2).equals(User)){
							out.println("Unable to create username. User: " + User + " already exists!");
						}

					}
					*/
					
					//Close recordset and connection
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
