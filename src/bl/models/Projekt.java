package bl.models;

public class Projekt {
	private int projektID;
	private String name, beschreibung;

	public Projekt(int id, String name, String beschreibung) {
		this.projektID = id;
		this.name = name;
		this.beschreibung = beschreibung;
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
		return projektID + "\n" + name + " " + beschreibung;
	}
}
