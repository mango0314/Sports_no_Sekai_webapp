package it.unirc.txw.progetto.servlet.privato.admin;

import java.io.IOException;
import java.util.Vector;

import it.unirc.txw.progetto.beans.classifica.Classifica;
import it.unirc.txw.progetto.beans.classifica.ClassificaDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class RichiediModificaClassifica
 */
@WebServlet("/privato/RichiediModificaClassifica")
public class RichiediModificaClassifica extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final ClassificaDAO classificaDAO = new ClassificaDAO();

	/**
	 * Default constructor.
	 */
	public RichiediModificaClassifica() {
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

		String idParam = request.getParameter("id");
		if (idParam == null || !idParam.matches("\\d+")) {
			response.sendError(400);
			return;
		}

		int Torneoid = Integer.parseInt(idParam);

		String idSport = request.getParameter("sport_id");
		if (idSport == null || !idSport.matches("\\d+")) {
			response.sendError(400);
			return;
		}

		int Sportid = Integer.parseInt(idSport);
		Vector<Classifica> classifica = classificaDAO.getAllby_torneo_id(Torneoid);

		classifica.sort((c1, c2) -> Integer.compare(c2.getPunteggioTotale(), c1.getPunteggioTotale()));

		request.setAttribute("classifica", classifica);
		session.setAttribute("torneo_id", Torneoid);
		session.setAttribute("sport_id", Sportid);
		request.getRequestDispatcher("/WEB-INF/privato/admin/modifica_classifica.jsp").forward(request, response);
	}

}