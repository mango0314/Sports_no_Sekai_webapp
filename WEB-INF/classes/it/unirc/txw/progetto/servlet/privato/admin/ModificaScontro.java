package it.unirc.txw.progetto.servlet.privato.admin;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
//import java.time.Duration;
//import java.time.LocalTime;
import java.util.Date;
import java.util.Vector;

import it.unirc.txw.progetto.beans.scontro.Scontro;
import it.unirc.txw.progetto.beans.scontro.ScontroDAO;
import it.unirc.txw.progetto.beans.squadra.Squadra;
import it.unirc.txw.progetto.beans.squadra.SquadraDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class ModificaScontri
 */
@WebServlet("/privato/ModificaScontro")
public class ModificaScontro extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final ScontroDAO scontroDAO = new ScontroDAO();
	private final SquadraDAO squadraDAO = new SquadraDAO();

	/**
	 * Default constructor.
	 */
	public ModificaScontro() {
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

		Vector<String> errori = new Vector<String>();

		int torneo_id = (int) session.getAttribute("torneo_id");

		Vector<Scontro> lista_scontritorneo = scontroDAO.getAllby_TorneoId(torneo_id);

		int sport_idtorneo = (int) session.getAttribute("sport_id");

		String scontro_idParam = request.getParameter("scontro_id");

		int scontro_id = Integer.parseInt(scontro_idParam);

		String idSquadra1Param = request.getParameter("squadra1id");

		int idSquadra1 = Integer.parseInt(idSquadra1Param);

		String idSquadra2Param = request.getParameter("squadra2id");

		int idSquadra2 = Integer.parseInt(idSquadra2Param);

		if (idSquadra1 == idSquadra2) {
			errori.add("Non si possono scontrare due squadre identiche");
		}

		String punteggio1Param = request.getParameter("punteggio1");
		String punteggio2Param = request.getParameter("punteggio2");

		Integer punteggio1 = null;
		Integer punteggio2 = null;

		try {
			if (punteggio1Param != null && !punteggio1Param.trim().isEmpty()) {
				punteggio1 = Integer.parseInt(punteggio1Param);
			}
		} catch (NumberFormatException e) {
			errori.add("Punteggio 1 non è un numero valido.");
		}

		try {
			if (punteggio2Param != null && !punteggio2Param.trim().isEmpty()) {
				punteggio2 = Integer.parseInt(punteggio2Param);
			}
		} catch (NumberFormatException e) {
			errori.add("Punteggio 2 non è un numero valido.");
		}

		if (punteggio1 != null && punteggio2 != null) {

			if (sport_idtorneo == 3 && punteggio1 != 3 && punteggio2 != 3) {
				errori.add("Nella pallavolo si vince a 3 set");
			}

			if (sport_idtorneo == 2 || sport_idtorneo == 3) {
				if (punteggio1 == punteggio2) {
					errori.add("Non è possibile il pareggio in questo sport");
				}
			}

			if (punteggio1 < 0 || punteggio2 < 0) {
				errori.add("Punteggio  non può essere negativo.");
			}
		}

		// 2) Validazione del formato (HH:mm)
		String orarioParam = request.getParameter("orario"); // ad es. "14:30"

		// Aggiungi questa riga per il debug:
		System.out.println("Valore ricevuto per orarioParam: '" + orarioParam + "'");

		if (orarioParam == null || !orarioParam.matches("^([01]\\d|2[0-3]):([0-5]\\d)(:([0-5]\\d))?$")) {
			errori.add("Orario non valido. Usa il formato HH:mm.");
		}

		Scontro scontro = new Scontro();
		scontro.setId(scontro_id);
		scontro.setTorneo_id(torneo_id);
		scontro.setSquadra1_id(idSquadra1);
		scontro.setSquadra2_id(idSquadra2);
		scontro.setPunteggio1(punteggio1);
		scontro.setPunteggio2(punteggio2);

		String dataStr = request.getParameter("data");
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			sdf.setLenient(false);
			Date data = sdf.parse(dataStr);
			scontro.setData(data);
		} catch (ParseException e) {
			errori.add("Formato data non valido (gg/MM/yyyy)");
		}

		java.sql.Time orarioSql = null;
		try {
			SimpleDateFormat sdfTime = orarioParam.length() == 5 ? new SimpleDateFormat("HH:mm")
					: new SimpleDateFormat("HH:mm:ss");
			sdfTime.setLenient(false);
			java.util.Date tmp = sdfTime.parse(orarioParam);
			orarioSql = new java.sql.Time(tmp.getTime());
			scontro.setOrario(orarioSql);
		} catch (ParseException e) {
			errori.add("Formato orario non valido (HH:mm).");
		}

		if (lista_scontritorneo.contains(scontro)) {
			errori.add("Scontro già esistente");
		}

//		for (Scontro s : lista_scontritorneo) {
//			if (((scontro.getSquadra1_id() == s.getSquadra1_id() && scontro.getSquadra2_id() == s.getSquadra2_id())
//					|| scontro.getSquadra1_id() == s.getSquadra2_id() && scontro.getSquadra2_id() == s.getSquadra1_id())
//					&& scontro.getData().equals(s.getData()) && scontro.getOrario().equals(s.getOrario())) {
//
//				LocalTime orarioScontro = scontro.getOrario().toLocalTime();
//				LocalTime orarioS = s.getOrario().toLocalTime();
//
//				Duration differenzaOrario = Duration.between(orarioScontro, orarioS).abs(); // abs() per avere un valore
//																							// assoluto
//
//				// Converti due ore in una Duration
//				Duration dueOre = Duration.ofHours(2);
//
//				if (differenzaOrario.compareTo(dueOre) < 0) { // Controlla se la differenza è inferiore a 2 ore
//					errori.add(
//							"Non si possono scontrare le stesse squadre a meno di due ore di distanza nello stesso giorno.");
//				}
//				errori.add("Non si possono scontrare le stesse squadre nello stesso momento");
//			}
//		}

		if (!errori.isEmpty()) {
			Vector<Squadra> lista_squadretorneo = squadraDAO.getAllby_torneo_id(torneo_id);

			request.setAttribute("scontro", null);
			request.setAttribute("errori", errori);
			request.setAttribute("lista_squadretorneo", lista_squadretorneo);
			request.getRequestDispatcher("/WEB-INF/privato/admin/modifica_scontro.jsp").forward(request, response);
			return;
		}

		scontroDAO.modifica(scontro);

		response.sendRedirect("Index?successo");

	}

}
