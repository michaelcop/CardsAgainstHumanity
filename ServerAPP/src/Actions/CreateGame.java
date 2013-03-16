package Actions;
import java.io.*;
import java.sql.*;
import java.util.logging.Logger;


import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;

@WebServlet(urlPatterns={"/CreateGame"})
public class CreateGame{

		/* extends HttpServlet implements DataSource 
		// need username - rounds -
		private String User =  null;
		Connection connection = null;
		private String password =  null;

		
		public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			if(request.getParameter("User") != null){ 
				this.setUser((String) request.getParameter("User").toString());
			}
			if(request.getParameter("password") != null){
				this.setPassword((String) request.getParameter("password").toString());
			}
			
			
			
			try {
			    System.out.println("Loading driver...");
			    Class.forName("com.mysql.jdbc.Driver");
			    System.out.println("Driver loaded!");
			} catch (ClassNotFoundException e) {
			    throw new RuntimeException("Cannot find the driver in the classpath!", e);
			}
			
			CreateGame ds = new CreateGame();
	        try {
				connection = ds.getConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			PrintWriter out = response.getWriter();
			if(connection != null){
				
				//out.println(User  + "   " + password);
				
				//Check if user exists in database
				if(User!= null){
					
					Statement stmt;
					ResultSet rs;
					try {
						stmt = connection.createStatement();
						rs = stmt.executeQuery("SELECT * FROM tblUsers WHERE Username = '" + User + "';");
						
						
						if(!rs.next()){
							out.println("Username: " + User + " was not found in Users table.");
						}
						else{
							//User was found now check if password is correct
							if(rs.getString(3).equals(password)){
								out.println("User: " + User + " login successful!");
							}
							else if(rs.getString(3).equals(password) == false){
								//password was incorrect
								out.println("Password incorrect!");
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
				*/
	
	
	
	
	
	
	
}
