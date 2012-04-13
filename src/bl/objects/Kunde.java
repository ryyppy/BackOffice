package bl.objects;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import dal.DBEntity;
import dal.TableMeta;

@TableMeta(pkFieldName = "kundeID")
public class Kunde extends DBEntity {
	private Integer kundeID;
	private String vorname;
	private String nachname;
	private Date geburtsdatum;

	public Kunde() {

	}

	public Kunde(String vorname, String nachname, Date geburtsdatum) {
		this.vorname = vorname;
		this.nachname = nachname;
		this.geburtsdatum = geburtsdatum;
	}

	public Kunde(int id, String vorname, String nachname, Date geburtsdatum) {
		this.kundeID = id;
		this.vorname = vorname;
		this.nachname = nachname;
		this.geburtsdatum = geburtsdatum;
	}

	public int getKundenID() {
		return kundeID;
	}

	public void setKundenID(int kundenID) {
		this.kundeID = kundenID;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public Date getGeburtsdatum() {
		return geburtsdatum;
	}

	public String getGeburtsdatumString() {
		if (geburtsdatum == null) {
			return null;
		}
		return new StringBuilder(
				new SimpleDateFormat("dd.MM.yyyy").format(geburtsdatum))
				.toString();
	}

	public void setGeburtsdatum(Date geburtsdatum) {
		this.geburtsdatum = geburtsdatum;
	}

	public void setGeburtsdatum(String geburtsdatum) throws ParseException {
		this.geburtsdatum = new SimpleDateFormat("dd.MM.yyyy")
				.parse(geburtsdatum);
	}

	public String toString() {
		return "Kunden-ID: " + kundeID + "\nName: " + vorname + " " + nachname
				+ "\nGeburtsdatum: " + getGeburtsdatumString();
	}

}
