package Testing;
import java.io.*;
import java.util.List;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(urlPatterns={"/Test"})
public class Test extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -504282938198562128L;
	private String Tested =  "Not set";
	private Factory UsersFact = new Factory();
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		PrintWriter out = response.getWriter();
			out.println("Hello ");
		
	
	}


	public String getTested() {
		return Tested;
	}


	public void setTested(String tested) {
		Tested = tested;
	}
}