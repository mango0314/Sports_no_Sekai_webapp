package it.unirc.txw.progetto.servlet.privato.admin;

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
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class RichiediModificaScontri
 */
@WebServlet("/privato/RichiediModificaSquadra")
public class RichiediModificaSquadra extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final GiocatoreDAO giocatoreDAO = new GiocatoreDAO();
	private final SquadraDAO squadraDAO = new SquadraDAO();

	/**
	 * Default constructor.
	 */
	public RichiediModificaSquadra() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			HttpSession session = request.getSession(false); // tutte le servlet dell'area riservata fanno un primo
																// check
			// per
// vedere se c'è il valore autenticato
			if (session.getAttribute("autenticato") == null) {
				response.sendRedirect("/RichiediLogin?errore=1"); /// se non c'è lo riamndo alla pagina di login e passo
																	/// un
				/// parametro errore, metto =1 come se fosse un vero
				/// parametro, ma funziona anche ?errore
				return;
			} // queste righe si codice sono in tutte le servlet dell'area riservata

			String idParam = request.getParameter("id");
			if (idParam == null || !idParam.matches("\\d+")) {
				response.sendError(400);
				return;
			}

			int squadra_id = Integer.parseInt(idParam);

			Squadra squadra = squadraDAO.getbyId(squadra_id);

			Vector<Giocatore> lista_giocatorisquadra = giocatoreDAO.getAllbyId(squadra_id);

			request.setAttribute("squadra", squadra);
			request.setAttribute("lista_giocatorisquadra", lista_giocatorisquadra);

			// … tutto il tuo codice …
			request.getRequestDispatcher("/WEB-INF/privato/admin/modifica_squadra.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace(); // Logga in console
		}
	}

}
