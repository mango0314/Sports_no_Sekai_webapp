package it.unirc.txw.progetto.beans.sport;

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

public class SportDAO {

	private static Connection conn = null;

	public Sport get(Sport sport) {
		String query = "SELECT * FROM TORNEO WHERE id =?";

		Sport res = null;
		PreparedStatement ps;
		conn = DBManager.startConnection();
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, sport.getId());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				res = recordToSport(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBManager.closeConnection();
		return res;
	}

	private Sport recordToSport(ResultSet rs) throws SQLException {
		Sport torneo = new Sport();
		torneo.setId(rs.getInt("id"));
		torneo.setNome(rs.getString("nome"));

		return torneo;
	}

	public Vector<Sport> getAll() {
		String query = "SELECT * FROM Sport order by id";

		Vector<Sport> res = new Vector<Sport>();
		PreparedStatement ps;
		conn = DBManager.startConnection();
		try {
			ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Sport sport = recordToSport(rs);
				res.add(sport);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBManager.closeConnection();
		return res;
	}

}
