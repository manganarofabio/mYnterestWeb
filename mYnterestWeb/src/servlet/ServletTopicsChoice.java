package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.UserManagement;

/**
 * Servlet implementation class ServletTopicsChoice
 */
@WebServlet("/ServletTopicsChoice")
public class ServletTopicsChoice extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public ServletTopicsChoice() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");  
	    PrintWriter out = response.getWriter();  
	    
	        //valore choicebox  
	    
	    String e = (String) request.getParameter("email");  //name dell html
	    
	    String topics = "";
	    
	    System.out.println(e);
	    
	    String sport = request.getParameter("sport");//name dell html  RITORNA la stringa on se è checked
	    
	    System.out.println(sport);
	    
	    if(sport != null)	{
	    	System.out.println(sport);
	    	topics = topics.concat("sport,");
	    }
	    
	    String cronaca = request.getParameter("cronaca"); 
	    if(cronaca != null)	{
	    	topics = topics.concat("cronaca,");
	    }
	    String politica = request.getParameter("politica"); 
	    if(politica != null)	{
	    	topics = topics.concat("politica,");
	    }
	    String tecnologia = request.getParameter("tecnologia"); 
	    if(tecnologia != null)	{
	    	topics = topics.concat("tecnologia,");
	    }
	    String scienze  = request.getParameter("scienze"); 
	    if(scienze != null)	{
	    	topics = topics.concat("scienze,");
	    }
	    String economia = request.getParameter("economia"); 
	    if(economia != null)	{
	    	topics = topics.concat("economia,");
	    }
	    String esteri = request.getParameter("esteri"); 
	    if(esteri != null)	{
	    	topics = topics.concat("esteri,");
	    }
	    System.out.println(topics);
	    
	    try {
			if(UserManagement.addTopics(e, topics))
				System.out.println("buon fine"); //andiamo alla pagina delle notizie
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    
	  
		  	
	}

}
