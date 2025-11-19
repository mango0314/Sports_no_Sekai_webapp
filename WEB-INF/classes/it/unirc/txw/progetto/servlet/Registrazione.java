package it.unirc.txw.progetto.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.Vector;
import java.util.regex.Pattern;

import it.unirc.txw.progetto.beans.account.Account;
import it.unirc.txw.progetto.beans.account.AccountDAO;
import it.unirc.txw.progetto.beans.giocatore.Giocatore;
import it.unirc.txw.progetto.beans.giocatore.GiocatoreDAO;
import it.unirc.txw.progetto.beans.squadra.Squadra;
import it.unirc.txw.progetto.beans.squadra.SquadraDAO;
import it.unirc.txw.progetto.beans.torneo.Torneo;
import it.unirc.txw.progetto.beans.torneo.TorneoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Registrazione
 */
@WebServlet("/Registrazione")
public class Registrazione extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final SquadraDAO squadraDAO = new SquadraDAO();
	private final AccountDAO accountDAO = new AccountDAO();
	private final TorneoDAO torneoDAO = new TorneoDAO();
	private final GiocatoreDAO giocatoreDAO = new GiocatoreDAO();
	private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.{8,}$)(?=.*[^A-Za-z0-9]).+$");

	/**
	 * Default constructor.
	 */
	public Registrazione() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		// oggetti che mi possso servire
		Vector<String> errori = new Vector<>();
		Vector<String> lista_nomisquadra = squadraDAO.getNomi();
		Vector<String> lista_username = accountDAO.getUsername();

		// prendo i parametri
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String nome_squadra = request.getParameter("nomesquadra");
		String torneo = request.getParameter("torneo");
		String logo = request.getParameter("teamLogo");

		// Controllo username
		if (lista_username.contains(username)) {
			// **Ripopolo la lista dei tornei prima di tornare alla JSP**
			errori.add("Username già esistente");

		}

		// Controllo password
		if (password == null || !PASSWORD_PATTERN.matcher(password).matches()) {
			// **Ripopolo la lista dei tornei prima di tornare alla JSP**
			errori.add("La password deve contenere almeno 8 caratteri e un carattere speciale");
		}

		// Controllo nome squadra
		if (lista_nomisquadra.contains(nome_squadra)) {
			errori.add("Nome squadra già esistente");

		}

		// Controllo date di nascite
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date today = new Date();
		for (int i = 1; i <= 7; i++) {
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
		}

		// in caso di valori non validati
		if (!errori.isEmpty()) {
			// Metto gli errori e i dati inseriti nella request
			Vector<Torneo> lista_tornei = torneoDAO.getAll();
			request.setAttribute("lista tornei", lista_tornei);
			request.setAttribute("errori", errori);

			// Forward al JSP di registrazione

			request.getRequestDispatcher("WEB-INF/registrazione.jsp").forward(request, response);

			return;
		}

		Vector<Giocatore> lista_giocatore = new Vector<Giocatore>();

		Squadra squadra = new Squadra();

		int torneoid = Integer.parseInt(torneo);
		int ultimoid = (squadraDAO.getUltimoId() + 1);

		squadra.setId(ultimoid);

		squadra.setNome(nome_squadra);
		squadra.setTorneo_id(torneoid);
		squadra.setLogo(logo);

		squadraDAO.salva(squadra);

		Account account = new Account();

		account.setUsername(username);
		account.setPassword(password);
		account.setRuolo(1);
		account.setId_Sq(ultimoid);

		accountDAO.salva(account);

		int baseId = giocatoreDAO.getUltimoId();
		for (int i = 1; i <= 7; i++) {
			Giocatore g = new Giocatore();
			g.setId(++baseId);
			g.setNome(request.getParameter("nome_" + i));
			g.setCognome(request.getParameter("cognome_" + i));

			String dataStr = request.getParameter("dataNascita_" + i);
			try {
				Date nascita = sdf.parse(dataStr);
				g.setDataDiNascita(nascita);
			} catch (ParseException e) {
				// Non dovrebbe succedere, perché abbiamo già validato le date
				System.out.println("Formato data non valido per giocatore " + i + ": " + dataStr);
			}
			g.setSquadra_id(ultimoid);
			g.setNumero_di_maglia(Integer.parseInt(request.getParameter("numeroMaglia_" + i)));
			lista_giocatore.add(g);
		}

		giocatoreDAO.salvaGiocatori(lista_giocatore);

		response.sendRedirect("RichiediLogin");

	}

}