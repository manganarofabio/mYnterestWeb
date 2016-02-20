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
 * Classe per la gestione Web dell'autenticazione dell'Utente
 *
 */
@WebServlet("/ServletLogin")
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/** costruttore **/
	public ServletLogin() {
		super();
		// TODO Auto-generated constructor stub
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		response.setContentType("text/html");  
		PrintWriter out = response.getWriter();  

		String e = request.getParameter("email");  

		//System.out.println(e);

		String p = request.getParameter("password");

		//System.out.println(p);

		if (request.getParameter("login") != null) {
			try {
				if(UserManagement.logInUser(e, p)){  

					request.setAttribute("email", e);

					RequestDispatcher rd=request.getRequestDispatcher("/news.jsp"); 
					rd.forward(request,response);  
				}

				else{  
					out.print("Email o password errati");  
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
		else{
			System.out.println("delete");
			try {
				if(UserManagement.deleteUser(e, p)){  

					out.print("Utente eliminato");  
					RequestDispatcher rd=request.getRequestDispatcher("index.html");  
					rd.include(request,response);  
				}
				else{

					out.print("Errore utente non esistente");  
					RequestDispatcher rd=request.getRequestDispatcher("index.html");  
					rd.include(request,response);  

				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

}
