package it.unirc.txw.progetto.servlet.privato.admin;

import java.io.IOException;

import it.unirc.txw.progetto.beans.torneo.Torneo;
import it.unirc.txw.progetto.beans.torneo.TorneoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class EliminaTorneo
 */
@WebServlet("/privato/EliminaTorneo")
public class EliminaTorneo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final TorneoDAO torneoDAO = new TorneoDAO();

	/**
	 * Default constructor.
	 */
	public EliminaTorneo() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		if (session.getAttribute("autenticato") == null) {
			response.sendRedirect("/RichiediLogin?errore=1");
			return;
		}

		String idParamTorneo = request.getParameter("id");
		int idTorneo = -1;
		try {
			idTorneo = Integer.parseInt(idParamTorneo);
		} catch (Exception e) {
			// lo mando in una pagina di errore ....
		}

		Torneo torneo = new Torneo();
		torneo.setId(idTorneo);
		if (torneoDAO.elimina(torneo))
			response.sendRedirect("Index?successo");
		else
			response.sendRedirect("Index?errore");
	}

}
