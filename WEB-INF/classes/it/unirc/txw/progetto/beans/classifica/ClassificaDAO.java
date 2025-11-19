package it.unirc.txw.progetto.beans.classifica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import it.unirc.txw.progetto.utils.DBManager;

/*
package it.unirc.txw.dao.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import it.unirc.txw.dao.utils.DBManager;
*/

public class ClassificaDAO {

	private static Connection conn = null;

	public Classifica get(Classifica classifica) {
		String query = "SELECT * FROM CLASSIFICA WHERE id_squadra =?";

		Classifica res = null;
		PreparedStatement ps;
		conn = DBManager.startConnection();
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, classifica.getId_squadra());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				res = recordToClassifica(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBManager.closeConnection();
		return res;
	}

	private Classifica recordToClassifica(ResultSet rs) throws SQLException {
		Classifica classifica = new Classifica();
		classifica.setId_squadra(rs.getInt("id_squadra"));
		classifica.setPunteggio(rs.getInt("punteggio"));
		classifica.setTorneo_id(rs.getInt("id_torneo"));
		classifica.setVittorie(rs.getInt("vittorie"));
		classifica.setPareggi(rs.getInt("pareggi"));
		classifica.setSconfitte(rs.getInt("sconfitte"));
		classifica.setMedia(rs.getFloat("media"));
		classifica.setBonus(rs.getInt("bonus"));

		return classifica;
	}

	public Vector<Classifica> getAll() {
		String query = "SELECT * FROM CLASSIFICA order by id_squadra";

		Vector<Classifica> res = new Vector<Classifica>();
		PreparedStatement ps;
		conn = DBManager.startConnection();
		try {
			ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Classifica classifica = recordToClassifica(rs);
				res.add(classifica);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBManager.closeConnection();
		return res;
	}

	public Vector<Classifica> getAllby_torneo_id(Integer torneo_id) {
		// Modificata la query per includere punteggio + bonus come campo calcolato e
		// ordinamento corretto
		String query = "SELECT * FROM CLASSIFICA WHERE id_torneo = ? ORDER BY punteggio + bonus DESC";

		Vector<Classifica> res = new Vector<Classifica>();

		PreparedStatement ps;
		conn = DBManager.startConnection();

		try {
			ps = conn.prepareStatement(query);
			ps.setObject(1, torneo_id);
			System.out.println("DEBUG DAO: cerco classifica per torneo_id = " + torneo_id);
			ResultSet rs = ps.executeQuery();
			int count = 0;
			while (rs.next()) {
				count++;
				Classifica classifica = recordToClassifica(rs);
				res.add(classifica);
			}
			System.out.println("Classifica trovata: " + res.size() + " elementi");
			System.out.println("DEBUG DAO: trovate " + count + " righe");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		DBManager.closeConnection();
		return res;
	}

//	public void upsertClassifica(Vector<Classifica> lista) {
//	    String sql = """
//	        INSERT INTO classifica
//	          (id_squadra, punteggio, id_torneo, vittorie, pareggi, sconfitte, media)
//	        VALUES (?, ?, ?, ?, ?, ?, ?)
//	        ON DUPLICATE KEY UPDATE
//	          vittorie  = VALUES(vittorie),
//	          pareggi   = VALUES(pareggi),
//	          sconfitte = VALUES(sconfitte),
//	          media     = VALUES(media),
//	          punteggio = VALUES(punteggio)
//	        """;
//
//	    PreparedStatement ps = null;
//	    conn = DBManager.startConnection();
//	    try {
//	        ps = conn.prepareStatement(sql);
//	        for (Classifica c : lista) {
//	            ps.setInt(1, c.getId_squadra());
//	            ps.setInt(2, c.getPunteggio());
//	            ps.setInt(3, c.getTorneo_id());
//	            ps.setInt(4, c.getVittorie());
//	            ps.setInt(5, c.getPareggi());
//	            ps.setInt(6, c.getSconfitte());
//	            ps.setFloat(7, c.getMedia());
//	            ps.addBatch();
//	        }
//	        ps.executeBatch();
//	    } catch (SQLException e) {
//	        e.printStackTrace();
//	    }
//	    DBManager.closeConnection();
//	}

	public boolean salva(Vector<Classifica> classifica) {
		String query = "INSERT INTO CLASSIFICA (id_squadra, punteggio, id_torneo, vittorie, pareggi, sconfitte, media, bonus) VALUES  (?, ?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE\r\n"
				+ "	          vittorie  = VALUES(vittorie),\r\n" + "	          pareggi   = VALUES(pareggi),\r\n"
				+ "	          sconfitte = VALUES(sconfitte),\r\n" + "	          media     = VALUES(media),\r\n"
				+ "	          punteggio = VALUES(punteggio)";
		boolean esito = true; // inizialmente assumiamo che andrà tutto bene

		PreparedStatement ps;
		conn = DBManager.startConnection();
		try {
			ps = conn.prepareStatement(query);

			for (Classifica c : classifica) {
				ps.setInt(1, c.getId_squadra());
				ps.setInt(2, c.getPunteggio());
				ps.setInt(3, c.getTorneo_id());
				ps.setInt(4, c.getVittorie());
				ps.setInt(5, c.getPareggi());
				ps.setInt(6, c.getSconfitte());
				ps.setFloat(7, c.getMedia());
				ps.setInt(8, c.getBonus());

				int tmp = ps.executeUpdate(); // esegui subito ogni insert
				if (tmp != 1) {
					System.err.println("⚠ Inserimento fallito per squadra " + c.getId_squadra());
					esito = false; // se anche uno fallisce, metti esito a false
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			esito = false;
		}
		DBManager.closeConnection();
		return esito;
	}

	public boolean classificaEsiste(int id_squadra, int id_torneo) {
		String query = "SELECT 1 FROM CLASSIFICA WHERE id_squadra = ? AND id_torneo = ?";
		boolean esiste = false;

		PreparedStatement ps;
		conn = DBManager.startConnection();
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, id_squadra);
			ps.setInt(2, id_torneo);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				esiste = true; // almeno una riga trovata → classifica già presente
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		DBManager.closeConnection();
		return esiste;
	}

	public boolean elimina(Classifica classifica) {
		String query = "DELETE FROM Classifica WHERE id_squadra = ?";
		boolean esito = false;

		PreparedStatement ps;
		conn = DBManager.startConnection();
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, classifica.getId_squadra());

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

	public boolean modifica(Classifica c) {
		String query = "UPDATE Classifica SET punteggio=?, id_torneo = ?, vittorie= ?, pareggi = ?, sconfitte = ?, media = ?, bonus = ? WHERE id_squadra=?";
		boolean esito = false;

		PreparedStatement ps;
		conn = DBManager.startConnection();
		try {
			ps = conn.prepareStatement(query);

			ps.setInt(1, c.getPunteggio());
			ps.setInt(2, c.getTorneo_id());
			ps.setInt(3, c.getVittorie());
			ps.setInt(4, c.getPareggi());
			ps.setInt(5, c.getSconfitte());
			ps.setFloat(6, c.getMedia());
			ps.setInt(7, c.getBonus());
			ps.setInt(8, c.getId_squadra());

			int tmp = ps.executeUpdate();
			if (tmp == 1)
				esito = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBManager.closeConnection();
		return esito;
	}

	public boolean modificaPunteggio(Classifica c) {
		String query = "UPDATE Classifica SET punteggio=? WHERE id_squadra=?";
		boolean esito = false;

		PreparedStatement ps;
		conn = DBManager.startConnection();
		try {
			ps = conn.prepareStatement(query);

			ps.setInt(1, c.getPunteggio());
			ps.setInt(2, c.getId_squadra());

			int tmp = ps.executeUpdate();
			if (tmp == 1)
				esito = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBManager.closeConnection();
		return esito;
	}

	public int getBonus(int squadraId, int torneoId) {
		int bonus = 0;

		String query = "SELECT bonus FROM classifica WHERE id_squadra = ? AND id_torneo = ?";
		conn = DBManager.startConnection();

		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, squadraId);
			ps.setInt(2, torneoId);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				bonus = rs.getInt("bonus");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		DBManager.closeConnection();
		return bonus;
	}

	public void updatePunteggi(Vector<Classifica> lista_classifica) {
		String query = "UPDATE Classifica SET punteggio=?, vittorie = ?, pareggi= ?, sconfitte = ? WHERE id_squadra=? AND id_torneo = ?";

		PreparedStatement ps;
		conn = DBManager.startConnection();
		try {
			ps = conn.prepareStatement(query);

			for (Classifica c : lista_classifica) {
				ps.setInt(1, c.getPunteggio());
				ps.setInt(2, c.getVittorie());
				ps.setInt(3, c.getPareggi());
				ps.setInt(4, c.getSconfitte());
				ps.setInt(5, c.getId_squadra());
				ps.setInt(6, c.getTorneo_id());

				System.out.printf(
						"DEBUG: aggiorno squadra=%d torneo=%d → punteggio=%d, vittorie=%d, pareggi=%d, sconfitte=%d%n",
						c.getId_squadra(), c.getTorneo_id(), c.getPunteggio(), c.getVittorie(), c.getPareggi(),
						c.getSconfitte());
				int tmp = ps.executeUpdate();
				System.out.println("DEBUG: righe modificate = " + tmp);
				if (tmp != 1) {
					System.err.println("⚠ Nessuna riga aggiornata per squadra " + c.getId_squadra() + " / torneo "
							+ c.getTorneo_id());
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBManager.closeConnection();

	}

	public boolean updateBonus(int squadraId, int torneoId, int bonus) {
		String query = "UPDATE CLASSIFICA " + "SET bonus = ? " + "WHERE id_squadra = ? AND id_torneo = ?";
		boolean success = false;
		PreparedStatement ps = null;
		conn = DBManager.startConnection();
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, bonus);
			ps.setInt(2, squadraId);
			ps.setInt(3, torneoId);

			int updated = ps.executeUpdate();
			if (updated == 1) {
				success = true;
			} else {
				System.err.println("⚠ updateBonus: righe modificate = " + updated + " per squadra=" + squadraId
						+ " torneo=" + torneoId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBManager.closeConnection();
		return success;
	}

	public boolean updatePunteggio(int squadraId, int torneoId, int punteggio) {
		String query = "UPDATE CLASSIFICA " + "SET punteggio = ? " + "WHERE id_squadra = ? AND id_torneo = ?";
		boolean success = false;
		PreparedStatement ps = null;
		conn = DBManager.startConnection();
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, punteggio);
			ps.setInt(2, squadraId);
			ps.setInt(3, torneoId);

			int updated = ps.executeUpdate();
			if (updated == 1) {
				success = true;
			} else {
				System.err.println("⚠ updatePunteggio: righe modificate = " + updated + " per squadra=" + squadraId
						+ " torneo=" + torneoId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBManager.closeConnection();
		return success;
	}

	public void updatePunteggieStatistiche(Vector<Classifica> lista_classifica) {
		String query = "UPDATE Classifica SET punteggio=?, vittorie=?, pareggi=?, sconfitte=?, media=? WHERE id_squadra=? AND id_torneo=?";

		PreparedStatement ps;
		conn = DBManager.startConnection();
		try {
			ps = conn.prepareStatement(query);

			for (Classifica c : lista_classifica) {
				ps.setInt(1, c.getPunteggio());
				ps.setInt(2, c.getVittorie());
				ps.setInt(3, c.getPareggi());
				ps.setInt(4, c.getSconfitte());
				ps.setFloat(5, c.getMedia());
				ps.setInt(6, c.getId_squadra());
				ps.setInt(7, c.getTorneo_id());

				System.out.printf(
						"DEBUG: aggiorno squadra=%d torneo=%d → punteggio=%d, vittorie=%d, pareggi=%d, sconfitte=%d, media=%.2f%n",
						c.getId_squadra(), c.getTorneo_id(), c.getPunteggio(), c.getVittorie(), c.getPareggi(),
						c.getSconfitte(), c.getMedia());

				int tmp = ps.executeUpdate();
				System.out.println("DEBUG: righe modificate = " + tmp);

				if (tmp != 1) {
					System.err.println("⚠ Nessuna riga aggiornata per squadra " + c.getId_squadra() + " / torneo "
							+ c.getTorneo_id());
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBManager.closeConnection();
	}
}