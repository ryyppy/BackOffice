package bl.models.armin;

public class Kunde {
	private int kundenID;
	private String vorname, nachname, geburtsdatum;

	public Kunde(int id, String vorname, String nachname, String geburtsdatum) {
		super();
		this.kundenID = id;
		this.vorname = vorname;
		this.nachname = nachname;
		this.geburtsdatum = geburtsdatum;
	}

	public Object[] getRow() {
		Object[] ret = { kundenID, vorname, nachname, geburtsdatum };
		return ret;
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

	public String getGeburtsdatum() {
		return geburtsdatum;
	}

	public void setGeburtsdatum(String geburtsdatum) {
		this.geburtsdatum = geburtsdatum;
	}

	public String toString() {
		return kundenID + "\n" + vorname + " " + nachname + "\n" + geburtsdatum;
	}

}
