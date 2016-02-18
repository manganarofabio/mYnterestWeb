package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.UserManagement;



/**
 * Classe per la gestione Web della crezione dell'Utente
 *
 */
@WebServlet("/ServletCreate")
public class ServletCreate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/** costruttore **/
	public ServletCreate() {
		super();
		// TODO Auto-generated constructor stub
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

	}



	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");  
		PrintWriter out = response.getWriter();  

		String e = request.getParameter("email");  

		System.out.println(e);

		String p = request.getParameter("password");

		System.out.println(p);

		String rp = request.getParameter("repassword");

		System.out.println(rp);



		if( e.isEmpty() || p.isEmpty() || rp.isEmpty() ){
			out.print("Errore inserimento dati");
			RequestDispatcher rd=request.getRequestDispatcher("index.html");  
			rd.include(request,response);
		}

		else if( p.equals(rp)){

			try {
				if(! UserManagement.checkUser(e)){

					request.setAttribute("email", e);
					request.setAttribute("password", p);


					request.getRequestDispatcher("/topicsChoice.jsp").forward(request, response);
				}  
				else{  
					out.print("Errore utente già esistente");  
					RequestDispatcher rd=request.getRequestDispatcher("index.html");  
					rd.include(request,response);  
				}
			} catch (Throwable e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}  

			out.close(); 
		}
		else{
			out.print("Password non corrispondenti");
			RequestDispatcher rd=request.getRequestDispatcher("/index.html");  
			rd.include(request,response);  
		}
	}
}
