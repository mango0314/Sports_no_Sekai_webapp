package it.unirc.txw.progetto.servlet.privato.utente;

import java.io.IOException;
import java.util.Vector;

import it.unirc.txw.progetto.beans.scontro.Scontro;
import it.unirc.txw.progetto.beans.scontro.ScontroDAO;
import it.unirc.txw.progetto.beans.squadra.Squadra;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class Scontri
 */
@WebServlet("/privato/Scontri")
public class Scontri extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final ScontroDAO scontroDAO = new ScontroDAO();

	/**
	 * Default constructor.
	 */
	public Scontri() {
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

		Squadra squadra = (Squadra) session.getAttribute("squadra");
		if (squadra == null) {
			System.out.println("DEBUG: squadra in sessione è null!");
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Squadra non trovata in sessione");
			return;
		}

		Vector<Scontro> scontri = scontroDAO.getScontriBySquadra(squadra.getId());

		Scontro ultimo_scontro = scontroDAO.getUltimoScontro(squadra.getTorneo_id());

		request.setAttribute("squadra", squadra);
		request.setAttribute("scontri", scontri);
		request.setAttribute("ultimo_scontro", ultimo_scontro);
		request.getRequestDispatcher("/WEB-INF/privato/utente/scontri.jsp").forward(request, response);
	}

}
