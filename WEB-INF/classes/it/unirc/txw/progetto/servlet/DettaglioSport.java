package it.unirc.txw.progetto.servlet;

import java.io.IOException;
import java.util.Vector;

import it.unirc.txw.progetto.beans.torneo.Torneo;
import it.unirc.txw.progetto.beans.torneo.TorneoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Calcio
 */
@WebServlet("/DettaglioSport")
public class DettaglioSport extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final TorneoDAO torneoDAO = new TorneoDAO();

	/**
	 * Default constructor.
	 */
	public DettaglioSport() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String idSport = request.getParameter("id");
		if (idSport == null || !idSport.matches("\\d+")) {
			response.sendError(400);
			return;
		}

		int Sportid = Integer.parseInt(idSport);

		Vector<Torneo> tornei = torneoDAO.getAll_bySportid(Sportid);

		// 4) Debug rapido su console
		System.out.println("[CalcioServlet] trovati " + tornei.size() + " tornei per sport id=" + Sportid);

		request.setAttribute("tornei", tornei);
		request.setAttribute("sportId", Sportid);

		request.getRequestDispatcher("WEB-INF/dettagliosport.jsp").forward(request, response);

	}

}
