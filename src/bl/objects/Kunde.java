package bl.objects;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import dal.DBEntity;

public class Kunde extends DBEntity {
	private int kundenID;
	private String vorname, nachname;
	private Date geburtsdatum;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

	public Kunde(int id, String vorname, String nachname, Date geburtsdatum) {
		super();
		this.kundenID = id;
		this.vorname = vorname;
		this.nachname = nachname;
		this.geburtsdatum = geburtsdatum;
	}

	/**
	 * 
	 * @param inhalt
	 * @param inhalt
	 *            [0]=vorname;
	 * @param inhalt
	 *            [1]=nachname;
	 * @param inhalt
	 *            [2]=geburtsdatum;
	 * @throws ParseException
	 */
	public Kunde(String[] inhalt) throws ParseException,
			IllegalArgumentException {
		String exception = "";
		if (inhalt[0] == null || inhalt[0].isEmpty()) {
			exception += "Vorname ist ungültig\n";
		}
		if (inhalt[1] == null || inhalt[1].isEmpty()) {
			exception += "Nachname ist ungültig\n";
		}
		if (inhalt[2] == null) {
			exception += "Geburtsdatum ist ungültig\n";
		}
		if (!exception.isEmpty()) {
			throw new IllegalArgumentException(exception);
		}

		this.kundenID = -1;
		this.vorname = inhalt[0];
		this.nachname = inhalt[1];
		try {
			this.geburtsdatum = dateFormat.parse(inhalt[2]);
		} catch (ParseException e) {
			throw new ParseException("Datumsformat ungültig - (TT.MM.JJJJ)",
					e.getErrorOffset());
		}

	}

	public int getId() {
		return kundenID;
	}

	public void setId(int id) {
		this.kundenID = id;
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

	public String toString() {
		return "Kunden-ID: " + kundenID + "\nName: " + vorname + " " + nachname
				+ "\nGeburtsdatum: " + getGeburtsdatumString();
	}

}
