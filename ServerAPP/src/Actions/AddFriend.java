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

@WebServlet(urlPatterns={"/AddFriend"})
public class AddFriend extends HttpServlet implements DataSource{
	private String User = "";
	private String User2 = "";
	Connection connection = null;
	//private String password =  null;
	int UserID = 0;
	int UserFriendID = 0;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		if(request.getParameter("User") != null){ 
			this.setUser((String) request.getParameter("User").toString());
			UserID =  Integer.parseInt(User);
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
		
		AddFriend ds = new AddFriend();
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
			
			Statement stmt, stmt2, stmt3, stmt4;
			ResultSet rs, rs2 = null;
			int rs3, rs4;
			try {
				stmt = connection.createStatement();
				stmt2 = connection.createStatement();
				stmt3 = connection.createStatement();
				stmt4 = connection.createStatement();
				//Query to get User’s friend ID
				rs = stmt.executeQuery("SELECT UserID FROM tblUsers WHERE Username = '" + User2 + "';");
				if(!rs.isBeforeFirst()){
					//User not found
					out.println(User2 + " does not exist.");
				}
				else if(rs.next()){
					//User found, get User ID
					UserFriendID = rs.getInt(1);
					
					//Check if User is friends with User2 already
					rs2 = stmt2.executeQuery("SELECT tblFriends.UserID, tblFriends.FriendUserID FROM tblFriends WHERE UserID = " + UserID + " AND FriendUserID = " + UserFriendID + ";");
					if(!rs2.isBeforeFirst()){
						//User and User2 are not friends - Proceed to add User2 as a friend
						//Add Friends - User1 - UserID
						rs3 = stmt3.executeUpdate("INSERT INTO tblFriends (UserID, FriendUserID, FriendStatus) VALUES (" + UserID + "," + UserFriendID + ","  + 2 + ");");
						//Add Friends - User2 - UserFriendID
						rs4 = stmt4.executeUpdate("INSERT INTO tblFriends (UserID, FriendUserID, FriendStatus) VALUES (" + UserFriendID + "," + UserID + "," + 3 + ");");
						if(rs3 != 0 && rs4 != 0){
							//Friend Added Successfully
							out.println("Added"+";"+UserFriendID);
						}
						else{
							//Unable to add friend
							out.println("None");
						}					
					}
					else if(rs2.next()){
						//User is already friends with User2
						out.println("Already friends with '" + User2 + "'");
					}
					rs2.close();
				}
			
				//Close record set and connection
				rs.close();
				
				stmt.close();
				stmt2.close();
				stmt3.close();
				stmt4.close();
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
