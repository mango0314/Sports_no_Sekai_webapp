package it.unirc.txw.progetto.beans.giocatore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import it.unirc.txw.progetto.utils.DBManager;

public class GiocatoreDAO {

	private static Connection conn = null; // presa dal DBManager, per interagire col database
	// anhce in questo caso la dichiaoro static così più istanze del dao condividono
	// tutti la stessa connection

	// QUERY DI LETTURA
	public Giocatore get(Giocatore giocatore) {
		String query = "SELECT * FROM GIOCATORE WHERE id =?"; // prima cosa metto la query che deve essere eseguita e la
																// uso PARAMETRICA, per evitare attacchi sqlinjection
		// qui ho fatto l'accesso tramite matricola come chiave, ma funzionerebbe con
		// qualsiasi altra chiave univoca
		Giocatore res = null; // la variabile che vado a restituire, mettondo stesso tipo che sto restituendo
		PreparedStatement ps;
		conn = DBManager.startConnection(); // questo è perchè nel DBManager lo startConnection è di tipo static,
											// infatti invoco il metodo sulla classe
		// senza dover ricordare l'istanza specifica
		try { // in questo modo risolvere l'eccezione che può essere geenerata da
				// recordToStudente, potrei utilizzare un throws ma rimanderei ulteriormente
				// l'eccezione, e non farei un buon DAO che ha l'obiettivo di nascondere ciò che
				// c'è sotto
			ps = conn.prepareStatement(query); // passo la query parametrica
			ps.setInt(1, giocatore.getId()); // IMPORTANTE per ogni parametro ? di una query parametrca, devo
												// istanziarlo con un valore, per convertirlo
			// per farlo faccio ps.set*tipo di parametro da passare* nel nostro caso Int
			// perchè id è un intero, questo per un ulteriore grado di sicurezza
			// "parameterIndex" è la posizione del punto interrogativo
			ResultSet rs = ps.executeQuery(); // esegue la query e il ResultSet è l'insieme di tuple restituite dalla
												// query
			if (rs.next()) { // inizialemente rs punta prima della prima riga e .next lo fa puntare al
								// prossimo risultato
				// se rs.next() restituisce true c'è un risultato e viene convertits in oggetto
				// java e lo passa a res, se non c'è allora chiudo la connessione e restituisco
				// res, ovvero null
				res = recordToGiocatore(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// POTREI INSERIRE IL FINALLY PER OTTIMIZZARE
		DBManager.closeConnection(); // dopo averla utilizzata la chiudo, solo se andata a buon fine
		// in un ambiente non concorrente posso anche non chiudere mai la connessione,
		// mentre se multi thread è importante gestirlo
		return res;
	}

	private Giocatore recordToGiocatore(ResultSet rs) throws SQLException { // è interno alla classe, non può essere
																			// visto, infatti è private
		// esso permettere di prendere una tupla e convertirlo in oggetto java
		// 'throws SQLException' non mi ritengo responsabile di questi problemi e viene
		// rimandato a chi invoca il metodo, quidi tutto funziona a patto che il
		// resuntset che mi dai è giusto
		Giocatore giocatore = new Giocatore();
		giocatore.setId(rs.getInt("id")); // qui equivalente al setInt, ultilizzo un get*tipo di attributo che
											// leggo*(*nome della colonna*)
		giocatore.setNome(rs.getString("nome"));
		giocatore.setCognome(rs.getString("cognome"));
		giocatore.setDataDiNascita(rs.getDate("data_di_nascita")); // rappresenterà un certo numero di millisecondi da
																	// una certsa data
		if (rs.getObject("squadra_id") != null) // prendo nella tupla la colonna come oggetto generico verifivando se è
												// diverso da null e se è null non facccio nulla
			giocatore.setSquadra_id(rs.getInt("squadra_id"));
		giocatore.setNumero_di_maglia(rs.getInt("numero_di_maglia"));
		return giocatore;
	}

	public Vector<Giocatore> getAll() { // restituisce una collezione di oggetti
		String query = "SELECT * FROM GIOCATORE order by id";

		Vector<Giocatore> res = new Vector<Giocatore>();
		PreparedStatement ps;
		conn = DBManager.startConnection();
		try {
			ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Giocatore giocatore = recordToGiocatore(rs);
				res.add(giocatore);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBManager.closeConnection();
		return res;
	}

	public Vector<Giocatore> getAllbyId(int squadra_id) { // restituisce una collezione di oggetti
		String query = "SELECT * FROM GIOCATORE where squadra_id=? order by id";

		Vector<Giocatore> res = new Vector<Giocatore>();
		PreparedStatement ps;
		conn = DBManager.startConnection();
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, squadra_id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Giocatore giocatore = recordToGiocatore(rs);
				res.add(giocatore);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBManager.closeConnection();
		return res;
	}

	public boolean salvaGiocatori(Vector<Giocatore> giocatori) { // il booleano mi dice se l'operazione è andata o meno
		// a buon fine
		String query = "INSERT INTO GIOCATORE (id, nome, cognome, data_di_nascita, squadra_id, numero_di_maglia)  VALUES (?, ?, ?, ?, ?, ?)";
		// potrei aggiungere al ?:*nome della
		// tabella*
		// per
		// evitare errori
		boolean esito = false; // per sapere se è andata a buon fine

		PreparedStatement ps;
		conn = DBManager.startConnection();
		try {
			ps = conn.prepareStatement(query);
			for (Giocatore g : giocatori) {
				ps.setInt(1, g.getId());
				ps.setString(2, g.getNome());
				ps.setString(3, g.getCognome());

				// conversione java.util.Date → java.sql.Date
				java.sql.Date sqlDate = new java.sql.Date(g.getDataDiNascita().getTime());
				ps.setDate(4, sqlDate);

				if (g.getSquadra_id() != null) {
					ps.setInt(5, g.getSquadra_id());
				} else {
					ps.setNull(5, java.sql.Types.INTEGER);
				}

				ps.setInt(6, g.getNumero_di_maglia());

				int tmp = ps.executeUpdate();
				if (tmp >= 1)
					esito = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBManager.closeConnection();
		return esito;
	}

	public int getUltimoId() {
		String query = "SELECT id FROM GIOCATORE order by id desc limit 1";

		int res = -1;
		PreparedStatement ps;
		conn = DBManager.startConnection();
		try {
			ps = conn.prepareStatement(query);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				res = rs.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBManager.closeConnection();
		return res;
	}
	// QUERY DI SCRITTURA/MODIFICA

	public boolean salva(Giocatore giocatore) { // il booleano mi dice se l'operazione è andata o meno a buon fine
		String query = "INSERT INTO GIOCATORE VALUES ( ?, ?, ?, ?, ?, ?)"; // potrei aggiungere al ?:*nome della
																			// tabella*
																			// per
		// evitare errori
		boolean esito = false; // per sapere se è andata a buon fine

		PreparedStatement ps;
		conn = DBManager.startConnection();
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, giocatore.getId());
			ps.setString(2, giocatore.getNome());
			ps.setString(3, giocatore.getCognome());

			// converto da util.Date a sql.Date
			java.sql.Date data = new java.sql.Date(giocatore.getDataDiNascita().getTime()); // una data util può essere
																							// costruita a partire dai
																							// millesecodni, ma non
																							// restituisce i
																							// millisecodni
			ps.setDate(4, data);

			if (giocatore.getSquadra_id() != null)
				ps.setInt(5, giocatore.getSquadra_id());
			else
				ps.setNull(4, java.sql.Types.INTEGER); // setta il null al quarto punto iinterrogstivo, ma col cull
														// specifico al tipo INTEGER, poichè ogni tipo del mondo sql lo
			ps.setInt(6, giocatore.getNumero_di_maglia());
			// ha
			int tmp = ps.executeUpdate(); // invece che un insieme di tuple, restitusce un int che è il numero di tuple
											// modificate, sennò 0 se l'esito non è avvenuto
			if (tmp == 1) // in questo caso specifico faccio solo una modifica perciò è massimo 1, ma
							// potrebbe essere magggiore in generale, quindi per essere sicuro inserisco >=
				esito = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBManager.closeConnection();
		return esito;
	}

	public boolean elimina(Giocatore giocatore) {
		String query = "DELETE FROM Giocatore WHERE id = ?";
		boolean esito = false;

		PreparedStatement ps;
		conn = DBManager.startConnection();
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, giocatore.getId());

			int tmp = ps.executeUpdate();
			if (tmp == 1)
				esito = true;

		} catch (SQLException e) {
			esito = false;
			e.printStackTrace();
		}
		DBManager.closeConnection();
		return esito;
	}

	public boolean modifica(Giocatore g) {
		String query = "UPDATE Giocatore SET nome=?, cognome=?, data_di_nascita=?, squadra_id=?, numero_di_maglia=? WHERE id=?"; // cerco
																																	// per
																																	// chiave
		boolean esito = false;

		PreparedStatement ps;
		conn = DBManager.startConnection();
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, g.getNome());

			ps.setString(2, g.getCognome());

			// converto da util.Date a sql.Date
			ps.setDate(3, new java.sql.Date(g.getDataDiNascita().getTime()));

			if (g.getSquadra_id() != null)
				ps.setInt(4, g.getSquadra_id());
			else
				ps.setNull(4, java.sql.Types.INTEGER);

			ps.setInt(5, g.getNumero_di_maglia());

			ps.setInt(6, g.getId());

			int tmp = ps.executeUpdate(); // invece che un insieme di tuple, restitusce un int che è il numero di tuple
											// modificate, sennò 0 se l'esito non è avvenuto
			if (tmp == 1) // in questo caso specifico faccio solo una modifica perciò è massimo 1, ma
							// potrebbe essere magggiore in generale, quindi per essere sicuro inserisco >=
				esito = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBManager.closeConnection();
		return esito;
	}

	public boolean modificaGiocatori(Vector<Giocatore> giocatori) { // il booleano mi dice se l'operazione è andata o
																	// meno
		// a buon fine
		String query = "UPDATE GIOCATORE SET nome=?, cognome=?, data_di_nascita=?, squadra_id=?, numero_di_maglia=? WHERE id=?";
		// potrei aggiungere al ?:*nome della
		// tabella*
		// per
		// evitare errori
		boolean esito = false; // per sapere se è andata a buon fine

		PreparedStatement ps;
		conn = DBManager.startConnection();
		try {
			ps = conn.prepareStatement(query);
			for (Giocatore g : giocatori) {
				ps.setString(1, g.getNome());
				ps.setString(2, g.getCognome());

				// conversione java.util.Date → java.sql.Date
				java.sql.Date sqlDate = new java.sql.Date(g.getDataDiNascita().getTime());
				ps.setDate(3, sqlDate);

				if (g.getSquadra_id() != null) {
					ps.setInt(4, g.getSquadra_id());
				} else {
					ps.setNull(4, java.sql.Types.INTEGER);
				}

				ps.setInt(5, g.getNumero_di_maglia());
				ps.setInt(6, g.getId());

				int tmp = ps.executeUpdate();
				if (tmp >= 1)
					esito = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBManager.closeConnection();
		return esito;
	}

}
