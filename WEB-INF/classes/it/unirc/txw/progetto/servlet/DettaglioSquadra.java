package it.unirc.txw.progetto.servlet;

import java.io.IOException;
import java.util.Vector;

import it.unirc.txw.progetto.beans.giocatore.Giocatore;
import it.unirc.txw.progetto.beans.giocatore.GiocatoreDAO;
import it.unirc.txw.progetto.beans.squadra.Squadra;
import it.unirc.txw.progetto.beans.squadra.SquadraDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Tornei
 */
@WebServlet("/DettaglioSquadra")
public class DettaglioSquadra extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final SquadraDAO squadraDAO = new SquadraDAO();
	private final GiocatoreDAO giocatoreDAO = new GiocatoreDAO();

	/**
	 * Default constructor.
	 */
	public DettaglioSquadra() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String idParam = request.getParameter("id");
		if (idParam == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID mancante");
			return;
		}

		int id = Integer.parseInt(idParam);

		Squadra squadra = squadraDAO.getbyId(id);
		Vector<Giocatore> giocatori = giocatoreDAO.getAllbyId(id);

		// 3) Metti in request
		request.setAttribute("squadra", squadra);
		request.setAttribute("giocatori", giocatori);

		request.getRequestDispatcher("WEB-INF/dettagliosquadra.jsp").forward(request, response);
	}

}
