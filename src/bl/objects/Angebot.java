package bl.objects;

import java.text.SimpleDateFormat;
import java.util.Date;

import dal.DBEntity;

public class Angebot extends DBEntity {
	private int angebotID, kundenID, projektID;
	private double summe, dauer, chance;
	private Date datum;

	public Angebot() {

	}

	public Angebot(int id, double summe, double dauer, Date date,
			double chance, int kundenID, int projektID) {
		super();
		this.angebotID = id;
		this.summe = summe;
		this.dauer = dauer;
		this.datum = date;
		this.chance = chance;
		this.kundenID = kundenID;
		this.projektID = projektID;
	}

	@Override
	public Object getID() {
		return getAngebotID();
	}

	public int getAngebotID() {
		return angebotID;
	}

	public void setAngebotID(int angebotID) {
		this.angebotID = angebotID;
	}

	public double getSumme() {
		return summe;
	}

	public void setSumme(double summe) {
		this.summe = summe;
	}

	public double getDauer() {
		return dauer;
	}

	public void setDauer(double dauer) {
		this.dauer = dauer;
	}

	public double getChance() {
		return chance;
	}

	public void setChance(double chance) {
		this.chance = chance;
	}

	public Date getDate() {
		return datum;
	}

	public String getDatumString() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		return new StringBuilder(dateFormat.format(datum)).toString();
	}

	public void setDate(Date date) {
		this.datum = date;
	}

	public int getKundenID() {
		return kundenID;
	}

	public void setKundenID(int kundenID) {
		this.kundenID = kundenID;
	}

	public int getProjektID() {
		return projektID;
	}

	public void setProjektID(int projektID) {
		this.projektID = projektID;
	}

	public String toString() {
		return "Angebot-ID: " + angebotID + "\nSumme: " + summe + "\nDauer: "
				+ dauer + "\nDatum: " + getDatumString() + "\nChance: "
				+ chance + "\nKunden-ID: " + kundenID + "\nProjektID: "
				+ projektID;
	}
}
