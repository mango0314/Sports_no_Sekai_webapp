package it.unirc.txw.progetto.servlet.privato;

import java.io.IOException;
import java.util.Vector;

import it.unirc.txw.progetto.beans.giocatore.Giocatore;
import it.unirc.txw.progetto.beans.giocatore.GiocatoreDAO;
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
 * Servlet implementation class Index
 */
@WebServlet("/privato/Index")
public class Index extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final SquadraDAO squadraDAO = new SquadraDAO();
	private final GiocatoreDAO giocatoreDAO = new GiocatoreDAO();
	private final TorneoDAO torneoDAO = new TorneoDAO();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Index() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP/1.1
		response.setHeader("Pragma", "no-cache"); // HTTP/1.0
		response.setDateHeader("Expires", 0); // Proxy

		// passo 1: nessun parametro

		// passo 2:
		HttpSession session = request.getSession(false); // tutte le servlet dell'area riservata fanno un primo check
															// per
		// vedere se c'è il valore autenticato
		if (session.getAttribute("autenticato") == null) {
			response.sendRedirect("/RichiediLogin?errore=1"); /// se non c'è lo riamndo alla pagina di login e passo un
																/// parametro errore, metto =1 come se fosse un vero
																/// parametro, ma funziona anche ?errore
			return;
		} // queste righe si codice sono in tutte le servlet dell'area riservata

		Integer idParam = (Integer) session.getAttribute("squadra_id");
		if (idParam == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID mancante");
			return;
		}

		Integer ruolo = (Integer) session.getAttribute("ruolo");
		if (ruolo == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID mancante");
			return;
		}

		Squadra squadra = squadraDAO.getbyId(idParam);
		Vector<Giocatore> giocatori = giocatoreDAO.getAllbyId(idParam);

		Vector<Torneo> lista_tuttitornei = torneoDAO.getAll();

		// 3) Metti in request
		session.setAttribute("squadra", squadra);
		request.setAttribute("giocatori", giocatori);
		request.setAttribute("ruolo", ruolo);
		request.setAttribute("lista_tuttitornei", lista_tuttitornei);

		request.getRequestDispatcher("/WEB-INF/privato/index_privato.jsp").forward(request, response);
	}

}
