package it.unirc.txw.progetto.servlet.privato.admin;

import java.io.IOException;

import it.unirc.txw.progetto.beans.squadra.Squadra;
import it.unirc.txw.progetto.beans.squadra.SquadraDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class EliminaScontro
 */
@WebServlet("/privato/EliminaSquadra")
public class EliminaSquadra extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final SquadraDAO squadraDAO = new SquadraDAO();

	/**
	 * Default constructor.
	 */
	public EliminaSquadra() {
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

		String idParamSquadra = request.getParameter("id");
		int idScontro = -1;
		try {
			idScontro = Integer.parseInt(idParamSquadra);
		} catch (Exception e) {
			// lo mando in una pagina di errore ....
		}

		Squadra squadra = new Squadra();
		squadra.setId(idScontro);
		if (squadraDAO.elimina(squadra))
			response.sendRedirect("Index?successo");
		else
			response.sendRedirect("Index?errore");
	}

}
