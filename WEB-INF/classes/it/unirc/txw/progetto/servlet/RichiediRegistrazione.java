package it.unirc.txw.progetto.servlet;

import java.io.IOException;
import java.util.Vector;

import it.unirc.txw.progetto.beans.sport.Sport;
import it.unirc.txw.progetto.beans.sport.SportDAO;
import it.unirc.txw.progetto.beans.torneo.Torneo;
import it.unirc.txw.progetto.beans.torneo.TorneoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RichiediLogin
 */
@WebServlet("/RichiediRegistrazione")
public class RichiediRegistrazione extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final TorneoDAO torneoDAO = new TorneoDAO();
	private final SportDAO sportDAO = new SportDAO();

	/**
	 * Default constructor.
	 */
	public RichiediRegistrazione() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		request.getAttribute("errori");

		Vector<Torneo> lista_tornei = torneoDAO.getAll();
		Vector<Sport> lista_sport = sportDAO.getAll();

		request.setAttribute("lista tornei", lista_tornei);
		request.setAttribute("lista sport", lista_sport);

		request.getRequestDispatcher("WEB-INF/registrazione.jsp").forward(request, response);
	}

}