package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Classe per la gestione Web della visualizzazione delle news di un topic
 */
@WebServlet("/ServletMoreNews")
public class ServletMoreNews extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/** costruttore **/
	public ServletMoreNews() {
		super();
		// TODO Auto-generated constructor stub
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");  

		String topic = (String) request.getParameter("topic");
		String email = (String) request.getParameter("email");

		System.out.println("il topic è " + topic);
		request.setAttribute("topic", topic);
		request.setAttribute("email", email);
		request.getRequestDispatcher("/moreNews.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
