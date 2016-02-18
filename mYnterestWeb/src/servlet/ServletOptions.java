package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Classe per la gestione Web delle impostazioni di un Utente
 */
@WebServlet("/ServletOptions")
public class ServletOptions extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/** costruttore **/
	public ServletOptions() {
		super();
		// TODO Auto-generated constructor stub
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");  

		String e = (String) request.getParameter("email");

		request.setAttribute("email", e);
		request.getRequestDispatcher("/topicsChoice.jsp").forward(request, response);
	}

}
