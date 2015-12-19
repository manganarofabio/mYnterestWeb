package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.LogCreateDelUser;


@WebServlet("/ServletCreate")
public class ServletCreate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public ServletCreate() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		

		response.setContentType("text/html");  
	    PrintWriter out = response.getWriter();  
	          
	    String e = request.getParameter("email");  //name dell html
	    
	    System.out.println(e);
	    
	    String p = request.getParameter("password");
	    
	    System.out.println(p);
	          
	    try {
			if(LogCreateDelUser.createtUser(e, p)){
			    RequestDispatcher rd=request.getRequestDispatcher("index.html");  
			    rd.forward(request,response);  
			}  
			else{  
			    out.print("Sorry utente gi� esistente");  
			    RequestDispatcher rd=request.getRequestDispatcher("index.html");  
			    rd.include(request,response);  
			}
		} catch (ClassNotFoundException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		} catch (SQLException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}  
	          
	    out.close(); 
	}

}
