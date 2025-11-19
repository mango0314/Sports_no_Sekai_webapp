package it.unirc.txw.progetto.servlet.privato.admin;

import java.io.IOException;

import it.unirc.txw.progetto.beans.classifica.ClassificaDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class ModificaClassifica
 */
@WebServlet("/privato/ModificaClassifica")
public class ModificaClassifica extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final ClassificaDAO classificaDAO = new ClassificaDAO();

	/**
	 * Default constructor.
	 */
	public ModificaClassifica() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session.getAttribute("autenticato") == null) {
			response.sendRedirect("/RichiediLogin?errore=1");
			return;
		}

		// Prendo array di parametri dal form
		String[] id_Squadra = request.getParameterValues("id_squadra[]");
		String[] Bonus = request.getParameterValues("bonus[]");

		// Controllo di base
		if (id_Squadra == null || Bonus == null || id_Squadra.length != Bonus.length) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,
					"Parametri id_squadra e punteggio mancanti o di lunghezza diversa");
			return;
		}

		Integer torneoId = (Integer) session.getAttribute("torneo_id");
		if (torneoId == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Torneo non specificato");
			return;
		}

		// Eseguo update per ciascuna coppia id/punteggio
		for (int i = 0; i < id_Squadra.length; i++) {
			String idParam = id_Squadra[i];
			String bonusParam = Bonus[i];

			// Validazione: solo cifre
			if (!idParam.matches("\\d+") || !bonusParam.matches("-?\\d+")) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parametro non valido in posizione " + i);
				return;
			}

			int squadraId = Integer.parseInt(idParam);
			int bonus = Integer.parseInt(bonusParam);
			classificaDAO.updateBonus(squadraId, torneoId, bonus);
		}

		// Torno all'index (o dove preferisci)
		response.sendRedirect(request.getContextPath() + "/WEB-INF/privato/Index");
	}
}