package it.unirc.txw.progetto.beans.torneo;

public class Torneo {
	private int id;
	private String nome;
	private Integer sport_id;
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

	@Override
	public String toString() {
		return "Torneo [id=" + id + ", nome=" + nome + ", sport_id=" + sport_id + ", logo=" + logo + "]";
	}

	public Integer getSport_id() {
		return sport_id;
	}

	public void setSport_id(Integer sport_id) {
		this.sport_id = sport_id;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

}