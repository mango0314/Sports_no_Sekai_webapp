package it.unirc.txw.progetto.servlet.privato.admin;

import java.io.IOException;
import java.util.Vector;

import it.unirc.txw.progetto.beans.scontro.Scontro;
import it.unirc.txw.progetto.beans.squadra.Squadra;
import it.unirc.txw.progetto.beans.squadra.SquadraDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class RichiediAggiungiScontri
 */
@WebServlet("/privato/RichiediAggiungiScontri")
public class RichiediAggiungiScontri extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final SquadraDAO squadraDAO = new SquadraDAO();

	/**
	 * Default constructor.
	 */
	public RichiediAggiungiScontri() {
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

		String idparam = request.getParameter("torneo_id");
		if (idparam == null || !idparam.matches("\\d+")) {
			response.sendError(400);
			return;
		}

		int torneo_id = Integer.parseInt(idparam);

		Scontro scontro = new Scontro();
		scontro = null;
		Vector<Squadra> lista_squadretorneo = squadraDAO.getAllby_torneo_id(torneo_id);

		request.setAttribute("scontro", scontro);
		request.setAttribute("lista_squadretorneo", lista_squadretorneo);
		request.getRequestDispatcher("/WEB-INF/privato/admin/modifica_scontro.jsp").forward(request, response);

	}

}
