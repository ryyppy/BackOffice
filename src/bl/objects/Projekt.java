package bl.objects;

import dal.DBEntity;

public class Projekt extends DBEntity {
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
	public Projekt(String[] inhalt) throws IllegalArgumentException {
		String exception = "";

		this.projektID = -1;
		try {
			setName(inhalt[0]);
		} catch (IllegalArgumentException e) {
			exception += e.getMessage() + "\n";
		}
		try {
			setBeschreibung(inhalt[1]);
		} catch (IllegalArgumentException e) {
			exception += e.getMessage() + "\n";
		}

		if (!exception.isEmpty()) {
			throw new IllegalArgumentException(exception);
		}

	}

	public Object getID() {
		return getProjektID();
	}

	public int getProjektID() {
		return projektID;
	}

	public void setProjektID(int projektID) {
		this.projektID = projektID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws IllegalArgumentException {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Name ist ungültig");
		}
		this.name = name;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		if (beschreibung == null || beschreibung.isEmpty()) {
			throw new IllegalArgumentException("Beschreibung ist ungültig");
		}
		this.beschreibung = beschreibung;
	}

	public String toString() {
		return "Projekt-ID: " + projektID + "\nName: " + name
				+ "\nBeschreibung: " + beschreibung;
	}
}
