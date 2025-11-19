package it.unirc.txw.progetto.utils;

//"organizzazione"
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager { // questa classe ha il compito di aprire e chiudere la connessione
	private static Connection conn = null;

	private static final String DbDriver = "com.mysql.cj.jdbc.Driver";
	private static final String DbURL = "jdbc:mysql://localhost:3306/torneisportividb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"; // dopo
	// il
	// ?
	// sto
	// passando
	// tre
	// proprità di mysql per la sicurezza, come se debba avere un certificato per
	// sabilire la connessione
	// allowPublicKeyRetrieval corrisponde alla chiave pubblica del client, che ci
	// viene detta da lui ed è MOLTO PERICOLOSA
	// ma simao in ambiente locale
	// serverTimeZone stabilisce il fuso orario sono specificati gli orari per
	// stabilire la connessione
	private static final String username = "root";
	private static final String password = "root";

	private DBManager() {
	}

	/**
	 * Metododo che restituisce true se la connessione è aperta.
	 */
	// STATIC attributi relativi alla classe e chiamare un metodo static vuol dire
	// che quel metodo può essere invocato sulla classe, senza fare prima una new
	// (un'istanza)
	// tutti i metodi static possono lavorare solo su proprietà static.
	public static boolean isOpen() {
		// if (conn == null)
		// return false;
		// else
		// return true;
		return (conn != null);
	}

	public static Connection startConnection() {
		if (isOpen()) // se è gia aperta
			return conn;
		try { // codice per avviare la connessione
			Class.forName(DbDriver);// Carica il Driver del DBMS
			conn = DriverManager.getConnection(DbURL, username, password);// Apertura connessione
			
			System.out.println("[DBManager] Connesso a schema = " + conn.getCatalog());

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return conn;
	}

	public static boolean closeConnection() {
		if (!isOpen())
			return true;
		try { // se è aperta la chiude
			conn.close();
			conn = null;
		} catch (SQLException e) {
			e.printStackTrace();
			return false; // se c'è un problema nella chiusura
		}
		return true;
	}
}
