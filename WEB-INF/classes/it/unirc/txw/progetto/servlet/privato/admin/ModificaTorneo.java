package it.unirc.txw.progetto.servlet.privato.admin;

import java.io.IOException;
import java.util.Vector;

import it.unirc.txw.progetto.beans.torneo.Torneo;
import it.unirc.txw.progetto.beans.torneo.TorneoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class ModificaTorneo
 */
@WebServlet("/privato/ModificaTorneo")
public class ModificaTorneo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final TorneoDAO torneoDAO = new TorneoDAO();

	/**
	 * Default constructor.
	 */
	public ModificaTorneo() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
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

		Vector<String> lista_nomiTornei = torneoDAO.getNomi();
		String errore = null;

		String nome_torneo = request.getParameter("nome_torneo");

		// 2. Recupera l’ID del torneo dalla sessione
		int idTorneo = (int) session.getAttribute("torneo_id");

		if (lista_nomiTornei.contains(nome_torneo)) {
			errore = "Nome squadra già esistente";
		}

		if (errore != null) {
			Torneo torneo = new Torneo();

			request.setAttribute("torneo", torneo);
			request.setAttribute("errore", errore);
			request.getRequestDispatcher("/WEB-INF/privato/admin/modifica_torneo.jsp").forward(request, response);

			return;
		}

		Torneo torneo = new Torneo();

		torneo.setId(idTorneo);
		torneo.setNome(nome_torneo);

		torneoDAO.modifica(torneo);

		response.sendRedirect("Index");

	}

}
