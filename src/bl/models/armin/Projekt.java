package bl.models.armin;

public class Projekt {
	private int projektID;
	private String name, beschreibung;

	public Projekt(int id, String name, String beschreibung) {
		this.projektID = id;
		this.name = name;
		this.beschreibung = beschreibung;
	}

	/**
	 * 
	 * @param inhalt
	 * @param inhalt
	 *            [0] =name
	 * @param inhalt
	 *            [1] =beschreibung
	 */
	public Projekt(String[] inhalt) {
		this.projektID = -1;
		this.name = inhalt[0];
		this.beschreibung = inhalt[1];
	}

	public Object[] getRow() {
		Object[] ret = { projektID, name, beschreibung };
		return ret;
	}

	public int getId() {
		return projektID;
	}

	public void setId(int id) {
		this.projektID = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public String toString() {
		return projektID + "\n" + name + "\n" + beschreibung;
	}
}
