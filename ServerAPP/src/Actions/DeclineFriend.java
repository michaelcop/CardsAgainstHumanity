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

@WebServlet(urlPatterns={"/DeclineFriend"})
public class DeclineFriend extends HttpServlet implements DataSource{
	private String User = "";
	private String User2 = "";
	Connection connection = null;
	int UserID = 0;
	int UserFriendID = 0;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		if(request.getParameter("User") != null){ 
			this.setUser((String) request.getParameter("User").toString());
			UserID = Integer.parseInt(User);
		}
		if(request.getParameter("User2") != null){
			this.setUser2((String) request.getParameter("User2").toString());
			UserFriendID = Integer.parseInt(User2);
		}
		
		try {
		    System.out.println("Loading driver...");
		    Class.forName("com.mysql.jdbc.Driver");
		    System.out.println("Driver loaded!");
		} catch (ClassNotFoundException e) {
		    throw new RuntimeException("Cannot find the driver in the classpath!", e);
		}
		
		DeclineFriend ds = new DeclineFriend();
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
			int rs;
			try {				
				stmt = connection.createStatement();
				//Query to delete/decline friendship/friend request
				rs = stmt.executeUpdate("DELETE FROM tblFriends WHERE (UserID ="+ UserID +" AND FriendUserID = "+ UserFriendID +") OR (UserID = "+ UserFriendID +" AND FriendUserID = "+ UserID +");");
				
				if(rs != 0){
					//Deleted/Declined Friend request
					out.println("Deleted;"+UserFriendID);
				}
				else{
					//Error occurred when deleting friend
					out.println("Error: unable to delete.");
				}
			
				//Close connection
				stmt.close();
				connection.close();
			} 
			catch (SQLException e) {
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
	
	public String getUser2() {
		return User2;
	}


	public void setUser2(String user) {
		User2 = user;
	}
}
