package it.unirc.txw.progetto.servlet;

import java.io.IOException;
import java.util.Vector;

import it.unirc.txw.progetto.beans.classifica.Classifica;
import it.unirc.txw.progetto.beans.classifica.ClassificaDAO;
import it.unirc.txw.progetto.beans.torneo.Torneo;
import it.unirc.txw.progetto.beans.torneo.TorneoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class Classifica
 */
@WebServlet("/ClassificaServlet")
public class ClassificaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final ClassificaDAO classificaDAO = new ClassificaDAO();
	private final TorneoDAO torneoDAO = new TorneoDAO();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		// Verifica che la sessione esista e che l'utente sia autenticato
		if (session == null) {
			response.sendRedirect("WEB-INF/dettagliotorneo.jsp");
			return;
		}

		// Recupera l'id del torneo dalla sessione
		Integer torneoId = (Integer) session.getAttribute("id");

		if (torneoId == null) {
			// Nessun torneo selezionato, torna alla pagina di dettaglio
			response.sendRedirect("WEB-INF/dettagliotorneo.jsp");
			return;
		}

		// Recupera il torneo
		Torneo torneo = torneoDAO.getById(torneoId);
		int sportId = torneo.getSport_id();

		// Recupera e ordina la classifica
		Vector<Classifica> classifica = classificaDAO.getAllby_torneo_id(torneoId);
		classifica.sort((c1, c2) -> Integer.compare(c2.getPunteggioTotale(), c1.getPunteggioTotale()));

		// Passa i dati alla JSP
		request.setAttribute("classifica", classifica);
		request.setAttribute("torneo", torneo);
		request.setAttribute("sport_id", sportId);

		request.getRequestDispatcher("WEB-INF/classifica.jsp").forward(request, response);
	}
}