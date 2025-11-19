package it.unirc.txw.progetto.servlet.privato.admin;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.Vector;

import it.unirc.txw.progetto.beans.giocatore.Giocatore;
import it.unirc.txw.progetto.beans.giocatore.GiocatoreDAO;
import it.unirc.txw.progetto.beans.squadra.Squadra;
import it.unirc.txw.progetto.beans.squadra.SquadraDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class Registrazione
 */
@WebServlet("/privato/ModificaSquadra")
public class ModificaSquadra extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final SquadraDAO squadraDAO = new SquadraDAO();
	private final GiocatoreDAO giocatoreDAO = new GiocatoreDAO();

	/**
	 * Default constructor.
	 */
	public ModificaSquadra() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false); // tutte le servlet dell'area riservata fanno un primo
		// check
// per
//vedere se c'è il valore autenticato
		if (session.getAttribute("autenticato") == null) {
			response.sendRedirect("/RichiediLogin?errore=1"); /// se non c'è lo riamndo alla pagina di login e passo
			/// un
/// parametro errore, metto =1 come se fosse un vero
/// parametro, ma funziona anche ?errore
			return;
		} // queste righe si codice sono in tutte le servlet dell'area riservata
			// TODO Auto-generated method stub

		// oggetti che mi possso servire
		Vector<String> errori = new Vector<>();
		Vector<String> lista_nomisquadra = squadraDAO.getNomi();

		// prendo i parametri
		String squadra_idParam = request.getParameter("squadra_id");

		int squadra_id = Integer.parseInt(squadra_idParam);
		String nome_squadra = request.getParameter("nomesquadra");
		int torneo_id = (int) session.getAttribute("torneo_id");
		String logo = request.getParameter("teamLogo");

		// Controllo nome squadra
		if (lista_nomisquadra.contains(nome_squadra)) {
			errori.add("Nome squadra già esistente");

		}

		int count = Integer.parseInt(request.getParameter("giocatori_count"));

		// Controllo date di nascite

		// in caso di valori non validati
		if (!errori.isEmpty()) {
			// Metto gli errori e i dati inseriti nella request

			Squadra squadra = squadraDAO.getbyId(squadra_id);

			Vector<Giocatore> lista_giocatorisquadra = giocatoreDAO.getAllbyId(squadra_id);

			request.setAttribute("squadra", squadra);
			request.setAttribute("errori", errori);
			request.setAttribute("lista_giocatorisquadra", lista_giocatorisquadra);

			// Forward al JSP di registrazione

			request.getRequestDispatcher("/WEB-INF/privato/admin/modifica_squadra.jsp").forward(request, response);

			return;
		}

		Vector<Giocatore> listaGiocatori = new Vector<Giocatore>();

		Squadra squadra = new Squadra();

		squadra.setId(squadra_id);

		squadra.setNome(nome_squadra);
		squadra.setTorneo_id(torneo_id);
		squadra.setLogo(logo);

		squadraDAO.modifica(squadra);

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date today = new Date();
		for (int i = 0; i < count; i++) {
			// 3) Prelevo ogni campo
			String idParam = request.getParameter("id_" + i);
			String nome = request.getParameter("nome_" + i);
			String cognome = request.getParameter("cognome_" + i);
			String dataStr = request.getParameter("dataNascita_" + i);
			try {
				Date nascita = sdf.parse(dataStr);
				int anni = Period.between(nascita.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
						today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()).getYears();
				if (anni < 18 || anni > 60) {
					errori.add("Giocatore " + i + ": età non valida (" + anni + " anni)");
				}
			} catch (ParseException e) {
				errori.add("Giocatore " + i + ": formato data non valido (gg/MM/yyyy)");
			}
			String magliaStr = request.getParameter("numeroMaglia_" + i);
			if (magliaStr == null || magliaStr.isEmpty()) {
				errori.add("Giocatore " + (i + 1) + ": numero di maglia mancante");
			}
			int maglia = Integer.parseInt(magliaStr);
			// ...

			// 4) Creo il bean e lo popolo
			Giocatore g = new Giocatore();
			g.setId(Integer.parseInt(idParam));
			g.setNome(nome);
			g.setCognome(cognome);

			try {
				Date nascita = sdf.parse(dataStr);
				g.setDataDiNascita(nascita);
			} catch (ParseException e) {
				// -> gestisci l’errore di parsing
			}

			g.setNumero_di_maglia(maglia);
			g.setSquadra_id(squadra_id); // lo passi da request o session

			listaGiocatori.add(g);
		}

		giocatoreDAO.modificaGiocatori(listaGiocatori);

		if (!errori.isEmpty()) {
			squadra = squadraDAO.getbyId(squadra_id);

			Vector<Giocatore> lista_giocatorisquadra = giocatoreDAO.getAllbyId(squadra_id);

			request.setAttribute("squadra", squadra);
			request.setAttribute("errori", errori);
			request.setAttribute("lista_giocatorisquadra", lista_giocatorisquadra);

			// Forward al JSP di registrazione

			request.getRequestDispatcher("/WEB-INF/privato/admin/modifica_squadra.jsp").forward(request, response);

			return;
		}

		response.sendRedirect("Index?successo");

	}

}