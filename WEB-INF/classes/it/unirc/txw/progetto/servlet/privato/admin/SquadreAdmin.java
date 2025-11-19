package it.unirc.txw.progetto.servlet.privato.admin;

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
@WebServlet("/privato/SquadreAdmin")
public class SquadreAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final TorneoDAO torneoDAO = new TorneoDAO();
	private final SquadraDAO squadraDAO = new SquadraDAO();

	/**
	 * Default constructor.
	 */
	public SquadreAdmin() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		HttpSession session = request.getSession(false); // tutte le servlet dell'area riservata fanno un primo check
		// per
// vedere se c'è il valore autenticato
		if (session.getAttribute("autenticato") == null) {
			response.sendRedirect("/RichiediLogin?errore=1"); /// se non c'è lo riamndo alla pagina di login e passo un
			/// parametro errore, metto =1 come se fosse un vero
			/// parametro, ma funziona anche ?errore
			return;
		} // queste righe si codice sono in tutte le servlet dell'area riservata

		// 2. Recupera l’ID del torneo dalla sessione
		Integer idTorneo = (Integer) session.getAttribute("torneo_id");
		if (idTorneo == null) {
			// non è mai stato selezionato un torneo
			response.sendRedirect("/WEB-INF/privato/admin/dettagliotorneo_admin.jsp");
			return;
		}

		Torneo torneo = torneoDAO.getById(idTorneo);
		Vector<Squadra> squadre = squadraDAO.getAllby_torneo_id(idTorneo);

		// 3) Metti in request
		request.setAttribute("torneo", torneo);
		request.setAttribute("squadre", squadre);

		request.getRequestDispatcher("/WEB-INF/privato/admin/squadre_torneo_admin.jsp").forward(request, response);

	}

}
