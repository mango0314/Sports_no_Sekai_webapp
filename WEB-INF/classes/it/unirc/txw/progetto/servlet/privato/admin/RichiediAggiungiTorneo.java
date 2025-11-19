package it.unirc.txw.progetto.servlet.privato.admin;

import java.io.IOException;
import java.util.Vector;

import it.unirc.txw.progetto.beans.sport.Sport;
import it.unirc.txw.progetto.beans.sport.SportDAO;
import it.unirc.txw.progetto.beans.torneo.Torneo;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class RichiediAggiungiTorneo
 */
@WebServlet("/privato/RichiediAggiungiTorneo")
public class RichiediAggiungiTorneo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final SportDAO sportDAO = new SportDAO();

	/**
	 * Default constructor.
	 */
	public RichiediAggiungiTorneo() {
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

		Torneo torneo = new Torneo();
		torneo = null;
		Vector<Sport> lista_sport = sportDAO.getAll();

		request.setAttribute("lista_sport", lista_sport);
		request.setAttribute("torneo", torneo);
		request.getRequestDispatcher("/WEB-INF/privato/admin/modifica_torneo.jsp").forward(request, response);
	}

}
