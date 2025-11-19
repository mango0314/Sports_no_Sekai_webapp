package it.unirc.txw.progetto.beans.scontro;

import java.sql.Time;
import java.util.Date;

public class Scontro {
	private int id;
	private Integer torneo_id;
	private Integer squadra1_id;
	private Integer squadra2_id;
	private Integer punteggio1;
	private Integer punteggio2;
	private Time orario;

	private Date data; // ci sono ben 8 oggetti per gestire le date, noi usiamo Date che è la più
	// vecchia, ma ora si preferisce altro
	// potremmo scegliere l'import date del tipo sql esprimendo che è mappato in una
	// tabella
	// sennò lo considero come java, per renderlo pronto ad essere usato nel mondo
	// java
	// leggera sfumatura comunque, ma preferibile trasformarla nel mondo java per
	// renderla pronta ad essere usata

	public Scontro() {
	}

	@Override
	public String toString() {
		return "Scontro [id=" + id + ", torneo_id=" + torneo_id + ", squadra1_id=" + squadra1_id + ", squadra2_id="
				+ squadra2_id + ", punteggio1=" + punteggio1 + ", punteggio2=" + punteggio2 + ", orario=" + orario
				+ ", data=" + data + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getTorneo_id() {
		return torneo_id;
	}

	public void setTorneo_id(Integer torneo_id) {
		this.torneo_id = torneo_id;
	}

	public Integer getSquadra1_id() {
		return squadra1_id;
	}

	public void setSquadra1_id(Integer squadra1_id) {
		this.squadra1_id = squadra1_id;
	}

	public Integer getSquadra2_id() {
		return squadra2_id;
	}

	public void setSquadra2_id(Integer squadra2_id) {
		this.squadra2_id = squadra2_id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public void setPunteggio1(Integer punteggio1) {
		this.punteggio1 = punteggio1;
	}

	public void setPunteggio2(Integer punteggio2) {
		this.punteggio2 = punteggio2;
	}

	public Integer getPunteggio1() {
		return punteggio1;
	}

	public Integer getPunteggio2() {
		return punteggio2;
	}

	public void setOrario(Time orario) {
		this.orario = orario;
	}

	public Time getOrario() {
		return orario;
	}
}
