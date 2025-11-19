package it.unirc.txw.progetto.beans.account;

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

public class AccountDAO {

	private static Connection conn = null;

	public Account get(Account account) {
		String query = "SELECT * FROM ACCOUNT WHERE username =?";

		Account res = null;
		PreparedStatement ps;
		conn = DBManager.startConnection();
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, account.getUsername());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				res = recordToAccount(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBManager.closeConnection();
		return res;
	}

	public boolean getEsiste(Account account) {
		String query = "SELECT * FROM ACCOUNT WHERE username =?";
		boolean esito = false;

		PreparedStatement ps;
		conn = DBManager.startConnection();
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, account.getUsername());

			ResultSet rs = ps.executeQuery();

			esito = rs.next();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBManager.closeConnection();
		return esito;
	}

	public boolean getEsistebypassword(Account account) {
		String query = "SELECT * FROM ACCOUNT WHERE username =? and BINARY password=?";
		boolean res = false;

		PreparedStatement ps;
		conn = DBManager.startConnection();
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, account.getUsername());
			ps.setString(2, account.getPassword());

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				res = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBManager.closeConnection();
		return res;
	}

	public int getRuolo(String username, String password) {
		String query = "SELECT ruolo FROM ACCOUNT WHERE username = ? and password = ?";

		int ruolo = -1;
		PreparedStatement ps;
		conn = DBManager.startConnection();
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, username);
			ps.setString(2, password);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				ruolo = rs.getInt("ruolo");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBManager.closeConnection();
		return ruolo;
	}

	public int getSq(String username, String password) {
		String query = "SELECT id_Sq FROM ACCOUNT WHERE username = ? and password = ?";

		int sq = -1;
		PreparedStatement ps;
		conn = DBManager.startConnection();
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, username);
			ps.setString(2, password);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				sq = rs.getInt("id_Sq");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBManager.closeConnection();
		return sq;
	}

	public Vector<String> getUsername() {
		String query = "SELECT username FROM ACCOUNT";

		Vector<String> res = new Vector<String>();
		PreparedStatement ps;
		conn = DBManager.startConnection();
		try {
			ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String username = rs.getString("username");
				res.add(username);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBManager.closeConnection();
		return res;
	}

	private Account recordToAccount(ResultSet rs) throws SQLException {
		Account account = new Account();
		account.setUsername(rs.getString("username"));
		account.setPassword(rs.getString("password"));
		account.setRuolo(rs.getInt("ruolo"));
		if (rs.getObject("id_Sq") != null)
			account.setId_Sq(rs.getInt("id_Sq"));

		return account;
	}

	public Vector<Account> getAll() {
		String query = "SELECT * FROM ACCOUNT order by username";

		Vector<Account> res = new Vector<Account>();
		PreparedStatement ps;
		conn = DBManager.startConnection();
		try {
			ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Account account = recordToAccount(rs);
				res.add(account);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBManager.closeConnection();
		return res;
	}

	public boolean salva(Account account) {
		String query = "INSERT INTO ACCOUNT (username, password, ruolo, id_Sq) VALUES ( ?, ?, ?, ?)";
		System.out.println("DEBUG-DAO: Query = " + query);
		boolean esito = false;

		PreparedStatement ps;
		conn = DBManager.startConnection();
		try {
//			ps = conn.prepareStatement(query);

			ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, account.getUsername());
			ps.setString(2, account.getPassword());
			ps.setInt(3, account.getRuolo());
			if (account.getId_Sq() != null)
				ps.setInt(4, account.getId_Sq());
			else
				ps.setNull(4, java.sql.Types.INTEGER);

			System.out.println("DEBUG-DAO: ps[1] = " + account.getUsername());
			System.out.println("DEBUG-DAO: ps[2] = " + account.getPassword());
			System.out.println("DEBUG-DAO: ps[3] = " + account.getRuolo());
			System.out.println("DEBUG-DAO: ps[4] = " + account.getId_Sq());
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

	public boolean elimina(Account account) {
		String query = "DELETE FROM Account WHERE username = ?";
		boolean esito = false;

		PreparedStatement ps;
		conn = DBManager.startConnection();
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, account.getUsername());

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

	public boolean modifica(Account a) {
		String query = "UPDATE Account SET password=?, ruolo=?, id_Sq = ? WHERE username=?";
		boolean esito = false;

		PreparedStatement ps;
		conn = DBManager.startConnection();
		try {
			ps = conn.prepareStatement(query);
			ps.setString(4, a.getUsername());
			ps.setString(1, a.getPassword());
			if (a.getId_Sq() != null)
				ps.setInt(3, a.getId_Sq());
			else
				ps.setNull(3, java.sql.Types.INTEGER);

			ps.setInt(2, a.getRuolo());

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
