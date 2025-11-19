package it.unirc.txw.progetto.beans.squadra;

public class Squadra {
	private int id;
	private String nome;
	private Integer torneo_id;
	private String logo;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getTorneo_id() {
		return torneo_id;
	}

	public void setTorneo_id(Integer torneo_id) {
		this.torneo_id = torneo_id;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	@Override
	public String toString() {
		return "Squadra [id=" + id + ", nome=" + nome + ", torneo_id=" + torneo_id + ", logo=" + logo + "]";
	}

	public String getLogo() {
		return logo;
	}

}