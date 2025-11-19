package it.unirc.txw.progetto.servlet;

import java.io.IOException;
import java.util.Vector;

import it.unirc.txw.progetto.beans.sport.Sport;
import it.unirc.txw.progetto.beans.sport.SportDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Index
 */
@WebServlet("/Index")
public class Index extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final SportDAO sportDAO = new SportDAO();

	/**
	 * Default constructor.
	 */
	public Index() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		Vector<Sport> listaSport = sportDAO.getAll();
		// 2) Li metto in request
		request.setAttribute("listaSport", listaSport);
		// 3) Forward alla JSP protetta in WEB-INF
		request.getRequestDispatcher("WEB-INF/index.jsp").forward(request, response);

	}

}
