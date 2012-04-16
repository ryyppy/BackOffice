package bl.objects;

import java.text.SimpleDateFormat;
import java.util.Date;

import dal.DBEntity;
import dal.TableMeta;

@TableMeta(pkFieldName = "angebotID")
public class Angebot extends DBEntity {
	private Integer angebotID;
	private Double summe;
	private Double dauer;
	private Double chance;
	private Date datum;
	private Integer kundeID;
	private Integer projektID;

	public Angebot() {

	}

	public Angebot(double summe, double dauer, Date date, double chance,
			int kundenID, int projektID) {
		this.summe = summe;
		this.dauer = dauer;
		this.datum = date;
		this.chance = chance;
		this.kundeID = kundenID;
		this.projektID = projektID;
	}

	public Angebot(int id, double summe, double dauer, Date date,
			double chance, int kundenID, int projektID) {
		this.angebotID = id;
		this.summe = summe;
		this.dauer = dauer;
		this.datum = date;
		this.chance = chance;
		this.kundeID = kundenID;
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

	public Date getDatum() {
		return datum;
	}

	public String getDatumString() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		return new StringBuilder(dateFormat.format(datum)).toString();
	}

	public void setDatum(Date date) {
		this.datum = date;
	}

	public int getKundeID() {
		return kundeID;
	}

	public void setKundeID(int kundenID) {
		this.kundeID = kundenID;
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
				+ chance + "\nKunden-ID: " + kundeID + "\nProjektID: "
				+ projektID;
	}
}
