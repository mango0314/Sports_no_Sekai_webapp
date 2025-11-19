package it.unirc.txw.progetto.beans.squadra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

public class SquadraDAO {

	private static Connection conn = null;

	public Squadra get(Squadra squadra) {
		String query = "SELECT * FROM SQUADRA WHERE id =?";

		Squadra res = null;
		PreparedStatement ps;
		conn = DBManager.startConnection();
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, squadra.getId());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				res = recordToSquadra(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBManager.closeConnection();
		return res;
	}

	public Squadra getbyId(int id) {
		String query = "SELECT * FROM SQUADRA WHERE id =?";

		Squadra res = null;
		PreparedStatement ps;
		conn = DBManager.startConnection();
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				res = recordToSquadra(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBManager.closeConnection();
		return res;
	}

	public Vector<String> getNomi() {
		String query = "SELECT nome FROM SQUADRA";

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

	public Squadra getbyTorneoId(int torneo_id) {
		String query = "SELECT * FROM SQUADRA WHERE torneo_id =?";

		Squadra res = null;
		PreparedStatement ps;
		conn = DBManager.startConnection();
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, torneo_id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				res = recordToSquadra(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBManager.closeConnection();
		return res;
	}

	public int getUltimoId() {
		String query = "SELECT id FROM SQUADRA order by id desc limit 1";

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

	private Squadra recordToSquadra(ResultSet rs) throws SQLException {
		Squadra squadra = new Squadra();
		squadra.setId(rs.getInt("id"));
		squadra.setNome(rs.getString("nome"));
		if (rs.getObject("torneo_id") != null)
			squadra.setTorneo_id(rs.getInt("torneo_id"));
		squadra.setLogo(rs.getString("logo"));
		return squadra;
	}

	public Vector<Squadra> getAll() {
		String query = "SELECT * FROM SQUADRA order by id";

		Vector<Squadra> res = new Vector<Squadra>();
		PreparedStatement ps;
		conn = DBManager.startConnection();
		try {
			ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Squadra squadra = recordToSquadra(rs);
				res.add(squadra);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBManager.closeConnection();
		return res;
	}

	public Vector<Squadra> getAllby_torneo_id(int torneo_id) {
		String query = "SELECT * FROM SQUADRA where torneo_id = ? order by id";

		Vector<Squadra> res = new Vector<Squadra>();
		PreparedStatement ps;
		conn = DBManager.startConnection();
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, torneo_id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Squadra squadra = recordToSquadra(rs);
				res.add(squadra);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBManager.closeConnection();
		return res;
	}

	public boolean salva(Squadra squadra) {
		String query = "INSERT INTO SQUADRA VALUES ( ?, ?, ?, ?)";
		boolean esito = false;

		PreparedStatement ps;
		conn = DBManager.startConnection();
		try {
//			ps = conn.prepareStatement(query);

			ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

			ps.setInt(1, squadra.getId());
			ps.setString(2, squadra.getNome());

			if (squadra.getTorneo_id() != null)
				ps.setInt(3, squadra.getTorneo_id());
			else
				ps.setNull(3, java.sql.Types.INTEGER);

			ps.setString(4, squadra.getLogo());

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

	public boolean elimina(Squadra squadra) {
		String query = "DELETE FROM Squadra WHERE id = ?";
		boolean esito = false;

		PreparedStatement ps;
		conn = DBManager.startConnection();
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, squadra.getId());

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

	public boolean modifica(Squadra s) {
		String query = "UPDATE Squadra SET nome=?, torneo_id=?, logo=? WHERE id=?";
		boolean esito = false;

		PreparedStatement ps;
		conn = DBManager.startConnection();
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, s.getNome());

			if (s.getTorneo_id() != null)
				ps.setInt(2, s.getTorneo_id());
			else
				ps.setNull(2, java.sql.Types.INTEGER);

			ps.setString(3, s.getLogo());
			ps.setInt(4, s.getId());

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
