package it.unirc.txw.progetto.servlet;

import java.io.IOException;
import java.util.Vector;

import it.unirc.txw.progetto.beans.squadra.Squadra;
import it.unirc.txw.progetto.beans.squadra.SquadraDAO;
import it.unirc.txw.progetto.beans.torneo.Torneo;
import it.unirc.txw.progetto.beans.torneo.TorneoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class Squadre
 */
@WebServlet("/Squadre")
public class Squadre extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final TorneoDAO torneoDAO = new TorneoDAO();
	private final SquadraDAO squadraDAO = new SquadraDAO();

	/**
	 * Default constructor.
	 */
	public Squadre() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if (session == null) {
			// nessuna sessione => utente non ha passato per /SelezionaTorneo
			response.sendRedirect("dettagliotorneo.jsp");
			return;
		}

		// 2. Recupera l’ID del torneo dalla sessione
		Integer idTorneo = (Integer) session.getAttribute("id");
		if (idTorneo == null) {
			// non è mai stato selezionato un torneo
			response.sendRedirect("dettagliotorneo.jsp");
			return;
		}

		Torneo torneo = torneoDAO.getById(idTorneo);
		Vector<Squadra> squadre = squadraDAO.getAllby_torneo_id(idTorneo);

		// 3) Metti in request
		request.setAttribute("torneo", torneo);
		request.setAttribute("squadre", squadre);

		// 4) Debug rapido su console
		System.out.println("[Servlet] trovati " + squadre.size() + " tornei per sport id=" + torneo.getId());

		request.getRequestDispatcher("WEB-INF/squadre_torneo.jsp").forward(request, response);

	}

}
