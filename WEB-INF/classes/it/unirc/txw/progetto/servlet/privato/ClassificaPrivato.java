package it.unirc.txw.progetto.servlet.privato;

import java.io.IOException;
import java.util.Vector;

import it.unirc.txw.progetto.beans.classifica.Classifica;
import it.unirc.txw.progetto.beans.classifica.ClassificaDAO;
import it.unirc.txw.progetto.beans.scontro.Scontro;
import it.unirc.txw.progetto.beans.scontro.ScontroDAO;
import it.unirc.txw.progetto.beans.squadra.Squadra;
import it.unirc.txw.progetto.beans.squadra.SquadraDAO;
import it.unirc.txw.progetto.beans.torneo.Torneo;
import it.unirc.txw.progetto.beans.torneo.TorneoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class Classifica
 */
@WebServlet("/privato/ClassificaPrivato")
public class ClassificaPrivato extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final ClassificaDAO classificaDAO = new ClassificaDAO();
	private final ScontroDAO scontroDAO = new ScontroDAO();
	private final SquadraDAO squadraDAO = new SquadraDAO();
	private final TorneoDAO torneoDAO = new TorneoDAO();

	/**
	 * Default constructor.
	 */
	public ClassificaPrivato() {
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

		int ruolo = (int) session.getAttribute("ruolo");

		Squadra squadra;

		int torneo_id;
		if (ruolo == 0) {
			torneo_id = (int) session.getAttribute("torneo_id");
		} else {
			squadra = (Squadra) session.getAttribute("squadra");
			if (squadra == null) {
				System.out.println("DEBUG: squadra in sessione è null!");
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Squadra non trovata in sessione");
				return;
			}

			torneo_id = squadra.getTorneo_id();
		}

		System.out.println("DEBUG – torneo_id ricevuto dalla request = " + torneo_id);

		// Prima carico gli scontri per vedere quali squadre sono coinvolte
		System.out.println("DEBUG – Carico scontri per torneo_id = " + torneo_id);
		Vector<Scontro> scontri = scontroDAO.getAllby_TorneoId(torneo_id);

		// Raccolgo tutti gli ID delle squadre coinvolte negli scontri
		Vector<Integer> squadreIds = new Vector<Integer>();
		for (Scontro s : scontri) {
			if (s.getSquadra1_id() != null && !squadreIds.contains(s.getSquadra1_id())) {
				squadreIds.add(s.getSquadra1_id());
			}
			if (s.getSquadra2_id() != null && !squadreIds.contains(s.getSquadra2_id())) {
				squadreIds.add(s.getSquadra2_id());
			}
		}

		// Carico le squadre specifiche coinvolte negli scontri
		Vector<Squadra> squadre = new Vector<Squadra>();
		for (Integer squadraId : squadreIds) {
			Squadra sq = squadraDAO.getbyId(squadraId);
			if (sq != null) {
				squadre.add(sq);
			}
		}

		System.out.println("DEBUG – Caricate " + squadre.size() + " squadre coinvolte negli scontri:");
		for (Squadra sq : squadre) {
			System.out.println(
					"  Squadra ID: " + sq.getId() + ", Nome: " + sq.getNome() + ", Torneo: " + sq.getTorneo_id());
		}
		Vector<Classifica> lista_classifica = new Vector<Classifica>();
		Torneo torneo = torneoDAO.getById(torneo_id);

		int Sportid = torneo.getSport_id();
		System.out.println("DEBUG – torneo_id = " + torneo_id + ", sport = " + Sportid);

		if (ruolo == 0) {
			for (Squadra sq : squadre) {

				System.out.println("DEBUG – Squadra caricata: id=" + sq.getId() + ", nome=" + sq.getNome());
				int somma_punti = scontroDAO.getSomma_puntifatti(sq.getId(), torneo_id);
				int numero_scontri_conclusi = scontroDAO.getNumeroScontri_conslusi(sq.getId(), torneo_id);

				// DEBUG: stampa i valori grezzi
				System.out.printf("DEBUG squadra %d → somma_punti = %d, incontri = %d%n", sq.getId(), somma_punti,
						numero_scontri_conclusi);

				float media = 0.0f;
				if (numero_scontri_conclusi > 0) {
					media = (float) somma_punti / numero_scontri_conclusi;
				}
				System.out.println("DEBUG – media raw = " + media);
				int bonus = classificaDAO.getBonus(sq.getId(), torneo_id);

				lista_classifica.add(new Classifica(sq.getId(), 0, torneo_id, 0, 0, 0, media, bonus));
			}
			System.out.println("DEBUG – Carico scontri per torneo_id = " + torneo_id);

			for (Scontro s : scontri) {
				System.out.printf("Scontro id=%d: squadra1=%d, squadra2=%d%n", s.getId(), s.getSquadra1_id(),
						s.getSquadra2_id());
				Integer p1 = s.getPunteggio1();
				Integer p2 = s.getPunteggio2();
				if (p1 == null || p2 == null)
					continue;

				int idx1 = -1, idx2 = -1;
				for (int i = 0; i < lista_classifica.size(); i++) {
					if (lista_classifica.get(i).getId_squadra() == s.getSquadra1_id())
						idx1 = i;

					if (lista_classifica.get(i).getId_squadra() == s.getSquadra2_id())
						idx2 = i;
				}
				if (idx1 == -1 || idx2 == -1) {
					System.out.printf(
							"DEBUG: Squadra non trovata in classifica – scontro id: %d, squadra1: %d, squadra2: %d%n",
							s.getId(), s.getSquadra1_id(), s.getSquadra2_id());
					continue;
				}

				if (p1 > p2) {
					int old = lista_classifica.get(idx1).getPunteggio();
					int old2 = lista_classifica.get(idx1).getVittorie();
					int old3 = lista_classifica.get(idx2).getSconfitte();

					// Calcio
					if (Sportid == 1) {
						lista_classifica.get(idx1).setPunteggio(old + 3);
						lista_classifica.get(idx1).setVittorie(old2 + 1);
						lista_classifica.get(idx2).setSconfitte(old3 + 1);

					} else if (Sportid == 2) { // Basket
						lista_classifica.get(idx1).setPunteggio(old + 2);
						lista_classifica.get(idx1).setVittorie(old2 + 1);
						lista_classifica.get(idx2).setSconfitte(old3 + 1);

					} else { // Pallavolo
						if (p2 == 2) {
							// Vittoria 3-2 -> 2 punti al vincitore, 1 al perdente
							lista_classifica.get(idx1).setPunteggio(old + 2);
							lista_classifica.get(idx1).setVittorie(old2 + 1);
							lista_classifica.get(idx2).setPunteggio(lista_classifica.get(idx2).getPunteggio() + 1);
							lista_classifica.get(idx2).setSconfitte(old3 + 1);
						} else {
							// Vittoria 3-0 o 3-1 -> 3 punti al vincitore, 0 al perdente
							lista_classifica.get(idx1).setPunteggio(old + 3);
							lista_classifica.get(idx1).setVittorie(old2 + 1);
							lista_classifica.get(idx2).setSconfitte(old3 + 1);
						}
					}

				} else if (p2 > p1) {
					int old = lista_classifica.get(idx2).getPunteggio();
					int old2 = lista_classifica.get(idx2).getVittorie();
					int old3 = lista_classifica.get(idx1).getSconfitte();
					if (Sportid == 1) {
						lista_classifica.get(idx2).setPunteggio(old + 3);
						lista_classifica.get(idx2).setVittorie(old2 + 1);
						lista_classifica.get(idx1).setSconfitte(old3 + 1);

					} else if (Sportid == 2) { // Basket
						lista_classifica.get(idx2).setPunteggio(old + 2);
						lista_classifica.get(idx2).setVittorie(old2 + 1);
						lista_classifica.get(idx1).setSconfitte(old3 + 1);

					} else { // Pallavolo
						if (p1 == 2) {
							// Vittoria 3-2 -> 2 punti al vincitore, 1 al perdente
							lista_classifica.get(idx2).setPunteggio(old + 2);
							lista_classifica.get(idx2).setVittorie(old2 + 1);
							lista_classifica.get(idx1).setPunteggio(lista_classifica.get(idx1).getPunteggio() + 1);
							lista_classifica.get(idx1).setSconfitte(old3 + 1);
						} else {
							// Vittoria 3-0 o 3-1 -> 3 punti al vincitore, 0 al perdente
							lista_classifica.get(idx2).setPunteggio(old + 3);
							lista_classifica.get(idx2).setVittorie(old2 + 1);
							lista_classifica.get(idx1).setSconfitte(old3 + 1);
						}

					}
				}

				else if (Sportid == 1) { // Calcio
					lista_classifica.get(idx1).setPunteggio(lista_classifica.get(idx1).getPunteggio() + 1);
					lista_classifica.get(idx2).setPunteggio(lista_classifica.get(idx2).getPunteggio() + 1);
					lista_classifica.get(idx1).setPareggi(lista_classifica.get(idx1).getPareggi() + 1);
					lista_classifica.get(idx2).setPareggi(lista_classifica.get(idx2).getPareggi() + 1);

					// Nel Basket non esiste pareggio
				}

			}
			classificaDAO.salva(lista_classifica);
		} else {

			classificaDAO.getAllby_torneo_id(torneo_id);
		}
		lista_classifica.sort((c1, c2) -> Integer.compare(c2.getPunteggioTotale(), c1.getPunteggioTotale()));

		request.setAttribute("classifica", lista_classifica);
		request.setAttribute("torneo", torneo);
		request.setAttribute("sport_id", Sportid);

		request.getRequestDispatcher("/WEB-INF/privato/classifica_privato.jsp").forward(request, response);

	}

}