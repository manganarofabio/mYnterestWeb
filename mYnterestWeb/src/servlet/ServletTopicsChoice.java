package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
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

		boolean flagEmail = false;
		boolean flag = false;
		
		ArrayList<String> topicsList = new ArrayList<String>();
		


		response.setContentType("text/html");  
		PrintWriter out = response.getWriter();  

		//valore choicebox  

		String e = (String) request.getParameter("email");  //name dell html
		
		String p = (String) request.getParameter("password");

		String n = (String) request.getParameter("notifica");
		
		System.out.println(n);

		if(n != null)
			flagEmail = true;

		//CREAZIONE UTENTE
		
		try {
			UserManagement.createtUser(e, p, flagEmail);
		} catch (Throwable e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		



		System.out.println(e);

		String sport = request.getParameter("sport");//name dell html  RITORNA la stringa on se è checked
		if(sport != null)	{
			flag = true;
			System.out.println(sport);
			topicsList.add("Sport");
					
		}

		String cronaca = request.getParameter("cronaca"); 
		if(cronaca != null)	{
			flag = true;
			topicsList.add("Cronaca");
		}
		String politica = request.getParameter("politica"); 
		if(politica != null)	{
			flag = true;
			topicsList.add("Politica");
		}
		
		String scienze  = request.getParameter("scienze"); 
		if(scienze != null)	{
			flag = true;
			topicsList.add("Scienze");
		}
		String economia = request.getParameter("economia"); 
		if(economia != null)	{
			flag = true;
			topicsList.add("Economia");
		}
		String esteri = request.getParameter("esteri"); 
		if(esteri != null)	{
			flag = true;
			topicsList.add("Esteri");
		}





	
		try {
			if(flag && UserManagement.addTopics(e, topicsList)){
			
					System.out.println("buon fine"); //andiamo alla pagina delle notizie
					request.setAttribute("email", e);
					request.getRequestDispatcher("/news.jsp").forward(request, response);

				}
			
			
			else{
				 
			    
				out.print("Non hai scelto nessun topic");
				request.setAttribute("email", e);
				RequestDispatcher rd=request.getRequestDispatcher("/topicsChoice.jsp");  

				rd.include(request,response);
				}
			
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}






	}

}
