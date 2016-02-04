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
		boolean flagS = false;


		response.setContentType("text/html");  
		PrintWriter out = response.getWriter();  

		//valore choicebox  

		String e = (String) request.getParameter("email");  //name dell html

		String n = (String) request.getParameter("notifica");

		System.out.println(n);

		if(n != null)
			flagEmail = true;


		String topics = "";

		String sources = "";

		System.out.println(e);

		String sport = request.getParameter("sport");//name dell html  RITORNA la stringa on se è checked
		if(sport != null)	{
			flag = true;
			System.out.println(sport);
			topics = topics.concat("sport,");
		}

		String cronaca = request.getParameter("cronaca"); 
		if(cronaca != null)	{
			flag = true;
			topics = topics.concat("cronaca,");
		}
		String politica = request.getParameter("politica"); 
		if(politica != null)	{
			flag = true;
			topics = topics.concat("politica,");
		}
		String tecnologia = request.getParameter("tecnologia"); 
		if(tecnologia != null)	{
			flag = true;
			topics = topics.concat("tecnologia,");
		}
		String scienze  = request.getParameter("scienze"); 
		if(scienze != null)	{
			flag = true;
			topics = topics.concat("scienze,");
		}
		String economia = request.getParameter("economia"); 
		if(economia != null)	{
			flag = true;
			topics = topics.concat("economia,");
		}
		String esteri = request.getParameter("esteri"); 
		if(esteri != null)	{
			flag = true;
			topics = topics.concat("esteri,");
		}



		System.out.println(topics);



		//SOURCES



		String laStampa= request.getParameter("LaStampa.it"); 
		if(laStampa != null)	{
			flagS = true;
			sources = sources.concat("LaStampa.it,");
		}

		String laRepubblica= request.getParameter("LaRepubblica.it"); 
		if(laRepubblica != null)	{
			
			flagS = true;
			sources = sources.concat("LaRepubblica.it,");
		}

		String corriere= request.getParameter("Corriere.it"); 
		if(corriere != null)	{
			flagS = true;
			sources = sources.concat("Corriere.it,");
		}


		System.out.println(sources);

	
		try {
			if(flagS && flag && UserManagement.addTopics(e, topics, flagEmail)){
				if(UserManagement.addSources(e, sources)){
					System.out.println("buon fine"); //andiamo alla pagina delle notizie
					request.setAttribute("email", e);
					request.getRequestDispatcher("/news.jsp").forward(request, response);

				}
			}
			
			else{
				 
			    
				out.print("Non hai scelto nessun topic o nessuna fonte, scegliere almeno uno di entrambi");
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
