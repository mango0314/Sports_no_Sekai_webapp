package it.unirc.txw.progetto.servlet;

import java.io.IOException;
import java.util.Vector;

import it.unirc.txw.progetto.beans.scontro.Scontro;
import it.unirc.txw.progetto.beans.scontro.ScontroDAO;
import it.unirc.txw.progetto.beans.torneo.Torneo;
import it.unirc.txw.progetto.beans.torneo.TorneoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class Tornei
 */
@WebServlet("/DettaglioTorneo")
public class DettaglioTorneo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final TorneoDAO torneoDAO = new TorneoDAO();
	private final ScontroDAO scontroDAO = new ScontroDAO();

	/**
	 * Default constructor.
	 */
	public DettaglioTorneo() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String idSport = request.getParameter("sport_id");
		if (idSport == null || !idSport.matches("\\d+")) {
			response.sendError(400);
			return;
		}

		int Sportid = Integer.parseInt(idSport);

		String idParam = request.getParameter("id");
		if (idParam == null || !idParam.matches("\\d+")) {
			response.sendError(400);
			return;
		}

		int Torneoid = Integer.parseInt(idParam);

		Torneo torneo = torneoDAO.getById(Torneoid);
		Vector<Scontro> scontri = scontroDAO.getAllby_TorneoId(Torneoid);

		Scontro ultimo_scontro = scontroDAO.getUltimoScontro(Torneoid);

		// 3) Metti in request
		request.setAttribute("torneo", torneo);
		request.setAttribute("scontri", scontri);
		request.setAttribute("ultimo scontro", ultimo_scontro);
		HttpSession session = request.getSession();
		session.setAttribute("id", Torneoid);
		session.setAttribute("sport_id", Sportid);
		// 4) Debug rapido su console
		System.out.println(
				"[DettaglioTorneoServlet] trovati " + scontri.size() + " tornei per sport id=" + torneo.getId());

		request.getRequestDispatcher("WEB-INF/dettagliotorneo.jsp").forward(request, response);

	}

}
