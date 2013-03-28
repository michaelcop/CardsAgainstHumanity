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

@WebServlet(urlPatterns={"/AcceptFriend"})
public class AcceptFriend extends HttpServlet implements DataSource{
	private String User =  null;
	private String User2 =  null;
	Connection connection = null;
	private String password =  null;
	int UserID, UserFriendID;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		if(request.getParameter("User") != null){ 
			this.setUser((String) request.getParameter("User").toString());
		}
		if(request.getParameter("User2") != null){
			this.setUser2((String) request.getParameter("User2").toString());
		}
		
		try {
		    System.out.println("Loading driver...");
		    Class.forName("com.mysql.jdbc.Driver");
		    System.out.println("Driver loaded!");
		} catch (ClassNotFoundException e) {
		    throw new RuntimeException("Cannot find the driver in the classpath!", e);
		}
		
		CreateAccount ds = new CreateAccount();
        try {
			connection = ds.getConnection();
			System.out.println("connection made");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PrintWriter out = response.getWriter();
		if(connection != null){
			System.out.println("not null");
			//out.println(User  + "   " + password);
			
			//Check if user exists in database
			if(User!= null && User.length()!=0){
				System.out.println("user not null");
				Statement stmt, stmt2, stmt3;
				ResultSet rs;
				int rs2, rs3;
				try {
					stmt = connection.createStatement();
					stmt2 = connection.createStatement();
					stmt3 = connection.createStatement();
					//Query to get User1’s ID
					rs = stmt.executeQuery("SELECT UserID FROM tblUsers WHERE Username = '" + User + "';");
					//Get User1’s UserID
					if(!rs.next()){
						//User not found
						System.out.println("user does not exist");
					}
					else{
						//User found, get User ID
						UserID = rs.getInt(1);
					}
					//Query to get User’s friend ID
					rs = stmt.executeQuery("SELECT UserID FROM tblUsers WHERE Username = '" + User2 + "';");
					if(!rs.next()){
						//User not found
						System.out.println("user does not exist");
					}
					else{
						//User found, get User ID
						UserFriendID = rs.getInt(1);
					}
					//Add Friends - User1 - UserID
					rs2 = stmt2.executeUpdate("INSERT INTO tblFriends (UserID, FriendUserID, FriendStatus) VALUES (" + UserID + "," + UserFriendID + ","  + 2 + ");");
					//Add Friends - User2 - UserFriendID
					rs3 = stmt3.executeUpdate("INSERT INTO tblFriends (UserID, FriendUserID, FriendStatus) VALUES (" + UserFriendID + "," + UserID + "," + 3 + ");");
					if(rs2 != 0 && rs3 != 0){
						//Friend Added Successfully
					out.println("Added");
					}
					else{
						//Unable to add friend
						out.println("None");
					}

					
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
	
	public String getUser2() {
		return User2;
	}


	public void setUser2(String user) {
		User2 = user;
	}
}
