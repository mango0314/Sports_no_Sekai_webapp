package it.unirc.txw.progetto.servlet.privato.admin;

import java.io.IOException;
import java.util.Vector;

import it.unirc.txw.progetto.beans.sport.Sport;
import it.unirc.txw.progetto.beans.sport.SportDAO;
import it.unirc.txw.progetto.beans.torneo.Torneo;
import it.unirc.txw.progetto.beans.torneo.TorneoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class AggiungiTorneo
 */
@WebServlet("/privato/AggiungiTorneo")
public class AggiungiTorneo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final TorneoDAO torneoDAO = new TorneoDAO();
	private final SportDAO sportDAO = new SportDAO();

	/**
	 * Default constructor.
	 */
	public AggiungiTorneo() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
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

		// preleva i paramatri necessari
		String nome_torneo = request.getParameter("nome_torneo");
		String idSport = request.getParameter("sport");
		if (idSport == null || !idSport.matches("\\d+")) {
			response.sendError(400);
			return;
		}

		int Sportid = Integer.parseInt(idSport);

		String logo = request.getParameter("teamLogo");

		int ultimoId = torneoDAO.getUltimoId();

		// controlli di validità
		if (lista_nomiTornei.contains(nome_torneo)) {
			errore = "Nome squadra già esistente";
		}

		// creo il torneo
		Torneo torneo = new Torneo();
		torneo.setId(ultimoId + 1);
		torneo.setNome(nome_torneo);
		torneo.setSport_id(Sportid);
		torneo.setLogo(logo);

		// lo salvo

		if (errore != null) {
			Vector<Sport> lista_sport = sportDAO.getAll();

			request.setAttribute("lista_sport", lista_sport);
			request.setAttribute("errore", errore);
			request.getRequestDispatcher("/WEB-INF/privato/admin/modifica_torneo.jsp").forward(request, response);
			return;
		}

		// lo salvo
		torneoDAO.salva(torneo);

		response.sendRedirect("Index");

	}

}
