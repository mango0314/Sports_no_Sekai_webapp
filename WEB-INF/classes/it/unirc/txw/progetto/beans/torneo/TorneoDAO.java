package it.unirc.txw.progetto.beans.torneo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import it.unirc.txw.progetto.beans.sport.Sport;
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

public class TorneoDAO {

	private static Connection conn = null;

	public Torneo get(Torneo torneo) {
		String query = "SELECT * FROM TORNEO WHERE id =?";

		Torneo res = null;
		PreparedStatement ps;
		conn = DBManager.startConnection();
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, torneo.getId());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				res = recordToTorneo(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBManager.closeConnection();
		return res;
	}

	public Torneo getById(int id) {
		String query = "SELECT * FROM TORNEO WHERE id =?";

		Torneo res = null;
		PreparedStatement ps;
		conn = DBManager.startConnection();
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				res = recordToTorneo(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBManager.closeConnection();
		return res;
	}

	public int getUltimoId() {
		String query = "SELECT id FROM Torneo order by id desc limit 1";

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

	public int getSportId(int torneo_id) {
		String query = "SELECT sport_id FROM Torneo where id = ?";

		int res = -1;
		PreparedStatement ps;
		conn = DBManager.startConnection();
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, torneo_id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				res = rs.getInt("sport_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBManager.closeConnection();
		return res;
	}

	public Vector<String> getNomi() {
		String query = "SELECT nome FROM Torneo";

		Vector<String> res = new Vector<String>();
		PreparedStatement ps;
		conn = DBManager.startConnection();
		try {
			ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String nome_squadra = rs.getString("nome");
				res.add(nome_squadra);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBManager.closeConnection();
		return res;
	}

	private Torneo recordToTorneo(ResultSet rs) throws SQLException {
		Torneo torneo = new Torneo();
		torneo.setId(rs.getInt("id"));
		torneo.setNome(rs.getString("nome"));

		torneo.setSport_id(rs.getInt("sport_id"));

		torneo.setLogo(rs.getString("logo"));

		return torneo;
	}

	public Vector<Torneo> getAll() {
		String query = "SELECT * FROM TORNEO order by id";

		Vector<Torneo> res = new Vector<Torneo>();
		PreparedStatement ps;
		conn = DBManager.startConnection();
		try {
			ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Torneo torneo = recordToTorneo(rs);
				res.add(torneo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBManager.closeConnection();
		return res;
	}

	public Vector<Torneo> getAll_bySport(Sport sport) {
		String query = "SELECT * FROM TORNEO WHERE sport_id = ? order by id";

		Vector<Torneo> res = new Vector<Torneo>();
		PreparedStatement ps;
		conn = DBManager.startConnection();

		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, sport.getId()); // perchè non può essere null
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Torneo torneo = recordToTorneo(rs);
				res.add(torneo);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		DBManager.closeConnection();
		return res;

	}

	public Vector<Torneo> getAll_bySportid(int sport_id) {
		String query = "SELECT * FROM TORNEO WHERE sport_id = ? order by id";

		Vector<Torneo> res = new Vector<Torneo>();
		PreparedStatement ps;
		conn = DBManager.startConnection();

		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, sport_id); // perchè non può essere null
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Torneo torneo = recordToTorneo(rs);
				res.add(torneo);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		DBManager.closeConnection();
		return res;

	}

	public Vector<Torneo> getAll_bySquadraid(int squadra_id) {
		String query = "SELECT * from torneo t join squadra s on t.id = s.torneo_id where s.id = ?";

		Vector<Torneo> res = new Vector<Torneo>();
		PreparedStatement ps;
		conn = DBManager.startConnection();

		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, squadra_id); // perchè non può essere null
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Torneo torneo = recordToTorneo(rs);
				res.add(torneo);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		DBManager.closeConnection();
		return res;

	}

	public boolean salva(Torneo torneo) {
		String query = "INSERT INTO TORNEO VALUES ( ?, ?, ?, ?)";
		boolean esito = false;

		PreparedStatement ps;
		conn = DBManager.startConnection();
		try {
//			ps = conn.prepareStatement(query);

			ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

			ps.setInt(1, torneo.getId());
			ps.setString(2, torneo.getNome());
			ps.setInt(3, torneo.getSport_id());

			ps.setString(4, torneo.getLogo());

			int tmp = ps.executeUpdate();
			if (tmp == 1)
				esito = true;
			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			// System.out.println("Chiave inserita "+rs.getInt(1));

		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBManager.closeConnection();
		return esito;
	}

	public boolean elimina(Torneo torneo) {
		String query = "DELETE FROM Torneo WHERE id = ?";
		boolean esito = false;

		PreparedStatement ps;
		conn = DBManager.startConnection();
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, torneo.getId());

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

	public boolean modifica(Torneo t) {
		String query = "UPDATE Torneo SET nome=? WHERE id=?";
		boolean esito = false;

		PreparedStatement ps;
		conn = DBManager.startConnection();
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, t.getNome());
			ps.setInt(2, t.getId());

			int tmp = ps.executeUpdate();
			if (tmp == 1)
				esito = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBManager.closeConnection();
		return esito;
	}
}
