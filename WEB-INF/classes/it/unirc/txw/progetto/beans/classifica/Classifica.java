package it.unirc.txw.progetto.beans.classifica;

public class Classifica {
	public Classifica(Integer id_squadra, int punteggio, Integer torneo_id, int vittorie, int pareggi, int sconfitte,
			float media, int bonus) {
		super();
		this.id_squadra = id_squadra;
		this.punteggio = punteggio;
		this.torneo_id = torneo_id;
		this.vittorie = vittorie;
		this.pareggi = pareggi;
		this.sconfitte = sconfitte;
		this.media = media;
		this.bonus = bonus;

	}

	private Integer id_squadra;
	private int punteggio;
	private Integer torneo_id;
	private int vittorie;
	private int pareggi;
	private int sconfitte;
	private float media;
	private int bonus;

	public Classifica() {
		super();
	}

	public Integer getId_squadra() {
		return id_squadra;
	}

	public void setId_squadra(Integer id_squadra) {
		this.id_squadra = id_squadra;
	}

	public int getPunteggio() {
		return punteggio;
	}

	public void setPunteggio(int punteggio) {
		this.punteggio = punteggio;
	}

	@Override
	public String toString() {
		return "Classifica [id_squadra=" + id_squadra + ", punteggio=" + punteggio + ", torneo_id=" + torneo_id
				+ ", vittorie=" + vittorie + ", pareggi=" + pareggi + ", sconfitte=" + sconfitte + ", media=" + media
				+ ", bonus=" + bonus + "]";
	}

	public Integer getTorneo_id() {
		return torneo_id;
	}

	public void setTorneo_id(Integer torneo_id) {
		this.torneo_id = torneo_id;
	}

	public int getVittorie() {
		return vittorie;
	}

	public void setVittorie(int vittorie) {
		this.vittorie = vittorie;
	}

	public int getSconfitte() {
		return sconfitte;
	}

	public void setSconfitte(int sconfitte) {
		this.sconfitte = sconfitte;
	}

	public void setPareggi(int pareggi) {
		this.pareggi = pareggi;
	}

	public int getPareggi() {
		return pareggi;
	}

	public float getMedia() {
		return media;
	}

	public void setMedia(float media) {
		this.media = media;
	}

	public int getBonus() {
		return bonus;
	}

	public void setBonus(int bonus) {
		this.bonus = bonus;
	}

	public int getPunteggioTotale() {
		return punteggio + bonus;
	}

}