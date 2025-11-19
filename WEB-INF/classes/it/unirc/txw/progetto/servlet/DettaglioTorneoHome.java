package it.unirc.txw.progetto.servlet;

import java.io.IOException;
import java.util.Vector;

import it.unirc.txw.progetto.beans.scontro.Scontro;
import it.unirc.txw.progetto.beans.scontro.ScontroDAO;
import it.unirc.txw.progetto.beans.torneo.Torneo;
import it.unirc.txw.progetto.beans.torneo.TorneoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class Tornei
 */
@WebServlet("/DettaglioTorneoHome")
public class DettaglioTorneoHome extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final TorneoDAO torneoDAO = new TorneoDAO();
	private final ScontroDAO scontroDAO = new ScontroDAO();

	/**
	 * Default constructor.
	 */
	public DettaglioTorneoHome() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		HttpSession session = request.getSession(false);
		if (session == null) {
			// nessuna sessione => utente non ha passato per /SelezionaTorneo
			response.sendRedirect("WEB-INF/dettagliotorneo.jsp");
			return;
		}

		// 2. Recupera l’ID del torneo dalla sessione
		Integer idTorneo = (Integer) session.getAttribute("id");
		if (idTorneo == null) {
			// non è mai stato selezionato un torneo
			response.sendRedirect("WEB-INF/dettagliotorneo.jsp");
			return;
		}

		Torneo torneo = torneoDAO.getById(idTorneo);
		Vector<Scontro> scontri = scontroDAO.getAllby_TorneoId(idTorneo);

		Scontro ultimo_scontro = scontroDAO.getUltimoScontro(idTorneo);

		// 3) Metti in request
		request.setAttribute("torneo", torneo);
		request.setAttribute("scontri", scontri);
		request.setAttribute("ultimo scontro", ultimo_scontro);

		// 4) Debug rapido su console
		System.out.println(
				"[DettaglioTorneoServlet] trovati " + scontri.size() + " tornei per sport id=" + torneo.getId());

		request.getRequestDispatcher("WEB-INF/dettagliotorneo.jsp").forward(request, response);

	}

}
