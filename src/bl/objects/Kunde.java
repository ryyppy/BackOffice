package bl.objects;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import dal.DBEntity;
import dal.TableMeta;

@TableMeta(pkFieldName = "kundeID")
public class Kunde extends DBEntity {
	private int kundeID;
	private String vorname, nachname;
	private Date geburtsdatum;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

	public Kunde() {

	}

	public Kunde(int id, String vorname, String nachname, Date geburtsdatum) {
		super();
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
		return new StringBuilder(dateFormat.format(geburtsdatum)).toString();
	}

	public void setGeburtsdatum(Date geburtsdatum) {
		this.geburtsdatum = geburtsdatum;
	}

	public void setGeburtsdatum(String geburtsdatum) throws ParseException {
		this.geburtsdatum = dateFormat.parse(geburtsdatum);
	}

	public String toString() {
		return "Kunden-ID: " + kundeID + "\nName: " + vorname + " " + nachname
				+ "\nGeburtsdatum: " + getGeburtsdatumString();
	}

}
