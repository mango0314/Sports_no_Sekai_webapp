package it.unirc.txw.progetto.beans.giocatore;

import java.util.Date;

public class Giocatore {
	private int id;
	private String nome;
	private String cognome;
	private Date dataDiNascita; // ci sono ben 8 oggetti per gestire le date, noi usiamo Date che è la più
	// vecchia, ma ora si preferisce altro
	// potremmo scegliere l'import date del tipo sql esprimendo che è mappato in una
	// tabella
	// sennò lo considero come java, per renderlo pronto ad essere usato nel mondo
	// java
	// leggera sfumatura comunque, ma preferibile trasformarla nel mondo java per
	// renderla pronta ad essere usata
	private Integer squadra_id;

	private int numero_di_maglia;

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

	public Date getDataDiNascita() {
		return dataDiNascita;
	}

	public void setDataDiNascita(Date dataDiNascita) {
		this.dataDiNascita = dataDiNascita;
	}

	public Giocatore() {
	}

	@Override
	public String toString() {
		return "Giocatore [id=" + id + ", nome=" + nome + ", cognome=" + cognome + ", dataDiNascita=" + dataDiNascita
				+ ", squadra_id=" + squadra_id + ", numero_di_maglia=" + numero_di_maglia + "]";
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public Integer getSquadra_id() {
		return squadra_id;
	}

	public void setSquadra_id(Integer squadra_id) {
		this.squadra_id = squadra_id;
	}

	public int getNumero_di_maglia() {
		return numero_di_maglia;
	}

	public void setNumero_di_maglia(int numero_di_maglia) {
		this.numero_di_maglia = numero_di_maglia;
	}

}
