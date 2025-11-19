package it.unirc.txw.progetto.servlet.privato.admin;

import java.io.IOException;

import it.unirc.txw.progetto.beans.scontro.Scontro;
import it.unirc.txw.progetto.beans.scontro.ScontroDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class EliminaScontro
 */
@WebServlet("/privato/EliminaScontro")
public class EliminaScontro extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final ScontroDAO scontroDAO = new ScontroDAO();

	/**
	 * Default constructor.
	 */
	public EliminaScontro() {
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

		String idParamScontro = request.getParameter("id");
		int idScontro = -1;
		try {
			idScontro = Integer.parseInt(idParamScontro);
		} catch (Exception e) {
			// lo mando in una pagina di errore ....
		}

		Scontro scontro = new Scontro();
		scontro.setId(idScontro);
		if (scontroDAO.elimina(scontro))
			response.sendRedirect("Index?successo");
		else
			response.sendRedirect("Index?errore");
	}

}
