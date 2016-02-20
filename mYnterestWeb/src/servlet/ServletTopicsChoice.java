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
 * Classe per la gestione Web della selezione 
 * dei topic preferiti di un utente
 */
@WebServlet("/ServletTopicsChoice")
public class ServletTopicsChoice extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/** costruttore **/
	public ServletTopicsChoice() {
		super();
		// TODO Auto-generated constructor stub
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		boolean flagEmail = false;
		boolean flag = false;
		boolean succes = false;

		ArrayList<String> topicsList = new ArrayList<String>();

		response.setContentType("text/html");  
		PrintWriter out = response.getWriter();  

		String e = (String) request.getParameter("email");  

		String p = (String) request.getParameter("password");

		String n = (String) request.getParameter("notifica");

		System.out.println("email: " + e);

		if(n != null)
			flagEmail = true;



		String sport = request.getParameter("sport");
		if(sport != null)	{
			flag = true;
			topicsList.add("sport");

		}

		String cronaca = request.getParameter("cronaca"); 
		if(cronaca != null)	{
			flag = true;
			topicsList.add("cronaca");
		}
		String politica = request.getParameter("politica"); 
		if(politica != null)	{
			flag = true;
			topicsList.add("politica");
		}

		String scienze  = request.getParameter("scienze"); 
		if(scienze != null)	{
			flag = true;
			topicsList.add("scienze");
		}
		String economia = request.getParameter("economia"); 
		if(economia != null)	{
			flag = true;
			topicsList.add("economia");
		}
		String esteri = request.getParameter("esteri"); 
		if(esteri != null)	{
			flag = true;
			topicsList.add("esteri");
		}
		
		System.out.println("il flag è: "+ flag);

		if(flag){

			try {
				succes = UserManagement.createtUser(e, p, flagEmail);
			} catch (Throwable e1) {

				e1.printStackTrace();
			}
		}

		try {
			if(succes && UserManagement.addTopics(e, topicsList)){

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
