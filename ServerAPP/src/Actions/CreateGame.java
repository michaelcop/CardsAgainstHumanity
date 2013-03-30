package Actions;
import java.io.*;
import java.sql.*;
import java.util.logging.Logger;


import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;

@WebServlet(urlPatterns={"/CreateGame"})

public class CreateGame extends HttpServlet implements DataSource {

 		private String User =  null;
		private int UserID, GameID;
		private int rounds = -1;
 		Connection connection = null;

 		
		public String getUser() {
			return User;
		}
		public void setUser(String user) {
			User = user;
		}

 		public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 			if(request.getParameter("User") != null){ 
 				this.setUser((String) request.getParameter("User").toString());
 				System.out.println(User);
 			}

			if(request.getParameter("rounds") != null){
				this.setRounds(Integer.parseInt((String) request.getParameter("rounds").toString()));
 			}
 			
 			
			PrintWriter out = response.getWriter();
 			try {
 			    System.out.println("Loading driver...");
 			    Class.forName("com.mysql.jdbc.Driver");
 				//Check if user exists in database
 			}
			catch(ClassNotFoundException e){
						e.printStackTrace();	
			}
 			
 			CreateGame ds = new CreateGame();
 		
 				try {
					connection = ds.getConnection();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
 			
 			    if(User!= null){
 				
					Statement stmt, stmt2, stmt3, stmt4;
 					ResultSet rs,rs4;
 					int rs2, rs3;
 					try {
 						stmt = connection.createStatement();
						stmt2 = connection.createStatement();
						stmt3 = connection.createStatement();
						stmt4 = connection.createStatement();

						//Get user ID
 						rs = stmt.executeQuery("SELECT * FROM tblUsers WHERE Username = '" + User + "';");
						
 						//Ensure record set has one record
						if(rs.next()){
							System.out.println("UserID: "+ rs.getInt(1));
							UserID = rs.getInt(1);
						}

						//Create game record
						rs2 = stmt2.executeUpdate("INSERT INTO tblGames (GameRounds, GameJudge, GameCurRound) VALUES (" + rounds + "," + UserID +"," + 0 + ");");                           
						System.out.println(rs2);
						if(rs2!=0){
							//out.println("Game Created!\n");
							}
						else{
							out.println("Unable to create game!");
							return;
 						}
						
						//Get game ID
 						rs4 = stmt4.executeQuery("SELECT * FROM tblGames ORDER BY GameID DESC LIMIT 1;");
						
 						//Ensure record set has record
						if(rs4.next()){
							//out.print("Success "+ rs4.getInt(1) +"\n");
							GameID = rs4.getInt(1);
						}
						
						
						//Add user that created game into players table -- 'tblPlayers'
						rs3 = stmt3.executeUpdate("INSERT INTO tblPlayers (PlayerGameID, PlayerUserID) VALUES (" + GameID + "," + UserID +");");                           
						System.out.println(rs3);
						if(rs3!=0){
							out.println("Game:"+ GameID);
						}
						else{
							out.println("Unable to add user to game!");
 						}										
						
						//Close record sets and connection
 						rs.close();
 						rs4.close();
 						stmt.close();
 						stmt2.close();
 						stmt3.close();
 						stmt4.close();
 						connection.close();	
 					}
 					catch(SQLException e){
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
		public int getRounds() {
			return rounds;
		}
		public void setRounds(int rounds) {
			this.rounds = rounds;
		}

		
}
