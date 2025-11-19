package it.unirc.txw.progetto.beans.account;

public class Account {
	private String username;
	private String password;
	private int ruolo;
	private Integer id_Sq;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Account [username=" + username + ", password=" + password + ", ruolo=" + ruolo + ", id_Sq=" + id_Sq
				+ "]";
	}

	public int getRuolo() {
		return ruolo;
	}

	public void setRuolo(int ruolo) {
		this.ruolo = ruolo;
	}

	

	public Integer getId_Sq() {
		return id_Sq;
	}

	public void setId_Sq(Integer id_Sq) {
		this.id_Sq = id_Sq;
	}

}