package it.unirc.txw.progetto.beans.scontro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import it.unirc.txw.progetto.beans.torneo.Torneo;
import it.unirc.txw.progetto.utils.DBManager;

public class ScontroDAO {

	private static Connection conn = null; // presa dal DBManager, per interagire col database
	// anhce in questo caso la dichiaoro static così più istanze del dao condividono
	// tutti la stessa connection

	// QUERY DI LETTURA
	public Scontro get(Scontro scontro) {
		String query = "SELECT * FROM SCONTRO WHERE id =?"; // prima cosa metto la query che deve essere
															// eseguita e la uso PARAMETRICA, per evitare
															// attacchi sqlinjection
		// qui ho fatto l'accesso tramite matricola come chiave, ma funzionerebbe con
		// qualsiasi altra chiave univoca
		Scontro res = null; // la variabile che vado a restituire, mettondo stesso tipo che sto restituendo
		PreparedStatement ps;
		conn = DBManager.startConnection(); // questo è perchè nel DBManager lo startConnection è di tipo static,
											// infatti invoco il metodo sulla classe
		// senza dover ricordare l'istanza specifica
		try { // in questo modo risolvere l'eccezione che può essere geenerata da
				// recordToStudente, potrei utilizzare un throws ma rimanderei ulteriormente
				// l'eccezione, e non farei un buon DAO che ha l'obiettivo di nascondere ciò che
				// c'è sotto
			ps = conn.prepareStatement(query); // passo la query parametrica
			ps.setInt(1, scontro.getId()); // IMPORTANTE per ogni parametro ? di una query parametrca, devo istanziarlo
											// con un valore, per convertirlo
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
				res = recordToScontro(rs);
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

	public Scontro getById(int id) {
		String query = "SELECT * FROM SCONTRO WHERE id =?";

		Scontro res = null;
		PreparedStatement ps;
		conn = DBManager.startConnection();
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				res = recordToScontro(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBManager.closeConnection();
		return res;
	}

	public Scontro getUltimoScontro(int torneo_id) {
		String query = "SELECT * FROM SCONTRO WHERE torneo_id = ? AND punteggio1 IS NOT NULL ORDER BY DATA DESC, ORARIO DESC LIMIT 1";

		Scontro res = null;
		PreparedStatement ps;
		conn = DBManager.startConnection();

		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, torneo_id);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				res = recordToScontro(rs);
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		DBManager.closeConnection();

		return res;

	}

	public int getNumeroScontri_conslusi(int squadra_id, int torneo_id) {
		String query = "SELECT COUNT(*) AS partite_giocate FROM scontro WHERE  (squadra1_id = ? OR squadra2_id = ?) AND (punteggio1 IS NOT NULL OR punteggio2 IS NOT NULL) AND torneo_id = ?";

		int res = -1;
		PreparedStatement ps;
		conn = DBManager.startConnection();

		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, squadra_id);
			ps.setInt(2, squadra_id);
			ps.setInt(3, torneo_id);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				res = rs.getInt("partite_giocate");
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		DBManager.closeConnection();

		return res;

	}

	public int getSomma_puntifatti(int squadra_id, int torneo_id) {
		String query = " SELECT   SUM( CASE  WHEN squadra1_id = ? THEN punteggio1    WHEN squadra2_id = ? THEN punteggio2 ELSE 0 END  ) AS totale_goal  FROM scontro WHERE (squadra1_id = ? OR squadra2_id = ?) AND (punteggio1 IS NOT NULL OR punteggio2 IS NOT NULL) AND torneo_id = ?";

		int res = -1;
		PreparedStatement ps;
		conn = DBManager.startConnection();

		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, squadra_id);
			ps.setInt(2, squadra_id);
			ps.setInt(3, squadra_id);
			ps.setInt(4, squadra_id);
			ps.setInt(5, torneo_id);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				res = rs.getInt("totale_goal");
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		DBManager.closeConnection();

		return res;

	}

	public String getNomesquadra(int squadra_id) {
		String query = " SELECT sq.nome as nome_squadra FROM scontro sc JOIN squadra sq ON sq.id = sc.squadra1_id WHERE sq.id = ?";

		String res = null;
		PreparedStatement ps;
		conn = DBManager.startConnection();

		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, squadra_id);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				res = rs.getString("nome_squadra");
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		DBManager.closeConnection();

		return res;

	}

	public int getUltimoId() {
		String query = "SELECT id FROM Scontro order by id desc limit 1";

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

	private Scontro recordToScontro(ResultSet rs) throws SQLException { // è interno alla classe, non può essere visto,
																		// infatti è private
		// esso permettere di prendere una tupla e convertirlo in oggetto java
		// 'throws SQLException' non mi ritengo responsabile di questi problemi e viene
		// rimandato a chi invoca il metodo, quidi tutto funziona a patto che il
		// resuntset che mi dai è giusto
		Scontro scontro = new Scontro();
		scontro.setId(rs.getInt("id")); // qui equivalente al setInt, ultilizzo un get*tipo di attributo che
										// leggo*(*nome della colonna*)
		if (rs.getObject("torneo_id") != null) // prendo nella tupla la colonna come oggetto generico verifivando se è
												// diverso da null e se è null non facccio nulla
			scontro.setTorneo_id(rs.getInt("torneo_id"));
		if (rs.getObject("squadra1_id") != null)
			scontro.setSquadra1_id(rs.getInt("squadra1_id"));
		if (rs.getObject("squadra2_id") != null)
			scontro.setSquadra2_id(rs.getInt("squadra2_id"));
		if (rs.getObject("punteggio1") != null)
			scontro.setPunteggio1(rs.getInt("punteggio1"));
		if (rs.getObject("punteggio2") != null)
			scontro.setPunteggio2(rs.getInt("punteggio2"));

		scontro.setData(rs.getDate("data")); // rappresenterà un certo numero di millisecondi da una certsa
												// data
		scontro.setOrario(rs.getTime("orario"));
		return scontro;
	}

	public Vector<Scontro> getAll() { // restituisce una collezione di oggetti
		String query = "SELECT * FROM SCONTRO order by id";

		Vector<Scontro> res = new Vector<Scontro>();
		PreparedStatement ps;
		conn = DBManager.startConnection();
		try {
			ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Scontro scontro = recordToScontro(rs);
				res.add(scontro);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBManager.closeConnection();
		return res;
	}

	public Vector<Scontro> getAllby_Torneo(Torneo torneo) {
		String query = "SELECT * FROM SCONTRO WHERE torneo_id = ? order by id";

		Vector<Scontro> res = new Vector<Scontro>();
		PreparedStatement ps;
		conn = DBManager.startConnection();
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, torneo.getId());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Scontro scontro = recordToScontro(rs);
				res.add(scontro);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		DBManager.closeConnection();
		return res;
	}

	public Vector<Scontro> getAllDateScontri(int torneo_id) {
		String query = "SELECT data FROM SCONTRO WHERE torneo_id = ? order by id";

		Vector<Scontro> res = new Vector<Scontro>();
		PreparedStatement ps;
		conn = DBManager.startConnection();
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, torneo_id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Scontro scontro = recordToScontro(rs);
				res.add(scontro);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		DBManager.closeConnection();
		return res;
	}

//	public Vector<Scontro> getScontriBySquadraETorneo(int squadraId, int torneoId) {
//
//		String query = "SELECT s.* FROM scontro s JOIN squadra sq1 ON s.squadra1_id = sq1.id JOIN squadra sq2 ON s.squadra2_id = sq2.id WHERE (sq1.id = ? OR sq2.id = ?) AND sq1.torneo_id = ? AND sq2.torneo_id = ?";
//
//		Vector<Scontro> res = new Vector<Scontro>();
//		PreparedStatement ps;
//		conn = DBManager.startConnection();
//		try {
//			ps = conn.prepareStatement(query);
//			ps.setInt(1, squadraId);
//			ps.setInt(2, squadraId);
//			ps.setInt(3, torneoId);
//			ps.setInt(4, torneoId);
//
//			ResultSet rs = ps.executeQuery();
//
//			while (rs.next()) {
//				Scontro scontro = recordToScontro(rs);
//				res.add(scontro);
//
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		DBManager.closeConnection();
//
//		return res;
//	}

	public Vector<Scontro> getScontriBySquadra(int squadraId) {

		String query = "SELECT s.* FROM scontro s JOIN squadra sq1 ON s.squadra1_id = sq1.id JOIN squadra sq2 ON s.squadra2_id = sq2.id WHERE (sq1.id = ? OR sq2.id = ?) ";

		Vector<Scontro> res = new Vector<Scontro>();
		PreparedStatement ps;
		conn = DBManager.startConnection();
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, squadraId);
			ps.setInt(2, squadraId);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Scontro scontro = recordToScontro(rs);
				res.add(scontro);

			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		DBManager.closeConnection();

		return res;
	}

	public Vector<Scontro> getAllby_TorneoId(int torneo_id) {
		String query = "SELECT * FROM SCONTRO WHERE torneo_id = ? order by data asc, orario asc";
		System.out.println("[ScontroDAO] Eseguo query: " + query + " con torneo_id=" + torneo_id);

		Vector<Scontro> res = new Vector<Scontro>();
		PreparedStatement ps;
		conn = DBManager.startConnection();
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, torneo_id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Scontro scontro = recordToScontro(rs);
				res.add(scontro);
				System.out.println("[ScontroDAO] Trovato Scontro ID=" + scontro.getId());

			}
			System.out.println("[ScontroDAO] Totale scontri per torneo_id=" + torneo_id + " --> " + res.size());

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		DBManager.closeConnection();
		return res;
	}

	public Vector<Scontro> getAllby_idSquadra(int squadra_id) {
		String query = "SELECT * FROM SCONTRO WHERE squadra_id = ? order by id";
		System.out.println("[ScontroDAO] Eseguo query: " + query + " con squadra_id=" + squadra_id);

		Vector<Scontro> res = new Vector<Scontro>();
		PreparedStatement ps;
		conn = DBManager.startConnection();
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, squadra_id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Scontro scontro = recordToScontro(rs);
				res.add(scontro);
				System.out.println("[ScontroDAO] Trovato Scontro ID=" + scontro.getId());

			}
			System.out.println("[ScontroDAO] Totale scontri per torneo_id=" + squadra_id + " --> " + res.size());

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		DBManager.closeConnection();
		return res;
	}

	// QUERY DI SCRITTURA/MODIFICA

	public boolean salva(Scontro scontro) { // il booleano mi dice se l'operazione è andata o meno a buon fine
		String query = "INSERT INTO SCONTRO VALUES ( ?, ?, ?, ?, ?, ?, ?, ?)"; // potrei aggiungere al ?:*nome della
																				// tabella*
																				// per
		// evitare errori
		boolean esito = false; // per sapere se è andata a buon fine

		PreparedStatement ps;
		conn = DBManager.startConnection();
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, scontro.getId());
			if (scontro.getTorneo_id() != null)
				ps.setInt(2, scontro.getTorneo_id()); // FORSE POSSIAMO CONSIDERARE CHE NON POSSONO ESSERE NULLI
			else
				ps.setNull(2, java.sql.Types.INTEGER); // setta il null al quarto punto iinterrogstivo, ma col cull
														// specifico al tipo INTEGER, poichè ogni tipo del mondo sql lo
														// ha
			if (scontro.getSquadra1_id() != null)
				ps.setInt(3, scontro.getSquadra1_id());
			else
				ps.setNull(3, java.sql.Types.INTEGER);

			if (scontro.getSquadra2_id() != null)
				ps.setInt(4, scontro.getSquadra2_id());
			else
				ps.setNull(4, java.sql.Types.INTEGER);

			if (scontro.getPunteggio1() != null)
				ps.setInt(5, scontro.getPunteggio1());
			else
				ps.setNull(5, java.sql.Types.INTEGER);

			if (scontro.getPunteggio2() != null)
				ps.setInt(6, scontro.getPunteggio2());
			else
				ps.setNull(6, java.sql.Types.INTEGER);

			// converto da util.Date a sql.Date
			java.sql.Date data = new java.sql.Date(scontro.getData().getTime()); // una data util può essere costruita a
																					// partire dai
			// millesecodni, ma non restituisce i millisecodni
			ps.setDate(7, data);
			ps.setTime(8, scontro.getOrario());

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

	public boolean elimina(Scontro scontro) {
		String query = "DELETE FROM Scontro WHERE id = ?";
		boolean esito = false;

		PreparedStatement ps;
		conn = DBManager.startConnection();
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, scontro.getId());

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

	public boolean modifica(Scontro s) {
		String query = "UPDATE Scontro SET torneo_id=?, squadra1_id=?,  squadra2_id=?, punteggio1=?, punteggio2=?, data=?, orario=? WHERE id=?"; // cerco
		// per
		// chiave
		boolean esito = false;

		PreparedStatement ps;
		conn = DBManager.startConnection();
		try {
			ps = conn.prepareStatement(query);
			if (s.getTorneo_id() != null)
				ps.setInt(1, s.getTorneo_id());
			else
				ps.setNull(1, java.sql.Types.INTEGER);

			if (s.getSquadra1_id() != null)
				ps.setInt(2, s.getSquadra1_id());
			else
				ps.setNull(2, java.sql.Types.INTEGER);

			if (s.getSquadra2_id() != null)
				ps.setInt(3, s.getSquadra2_id());
			else
				ps.setNull(3, java.sql.Types.INTEGER);

			if (s.getPunteggio1() != null)
				ps.setInt(4, s.getPunteggio1());
			else
				ps.setNull(4, java.sql.Types.INTEGER);

			if (s.getPunteggio2() != null)
				ps.setInt(5, s.getPunteggio2());
			else
				ps.setNull(5, java.sql.Types.INTEGER);

			// converto da util.Date a sql.Date
			ps.setDate(6, new java.sql.Date(s.getData().getTime()));
			ps.setTime(7, s.getOrario());

			ps.setInt(8, s.getId());

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
}
