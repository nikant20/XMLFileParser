package tech.nikant.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MichelData {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column
	private String tournamentname;
	
	@Column
	private String date;
	
	@Column
	private String place;
	
	public MichelData(){}

	public MichelData(String tournamentname, String date, String place) {
		super();
		this.tournamentname = tournamentname;
		this.date = date;
		this.place = place;
	}
	
	public String getTournamentname() {
		return tournamentname;
	}

	public void setTournamentname(String tournamentname) {
		this.tournamentname = tournamentname;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}
	
	

	@Override
	public String toString() {
		return "MichelData [tournamentname=" + tournamentname + ", date=" + date + ", place=" + place + "]";
	}
	
}
