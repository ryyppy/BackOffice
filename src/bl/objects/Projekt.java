package bl.objects;

import dal.DBEntity;

public class Projekt extends DBEntity{
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
		if (inhalt[0] == null || inhalt[0].isEmpty()) {
			exception += "Name ist ungültig\n";
		}
		if (inhalt[1] == null || inhalt[1].isEmpty()) {
			exception += "Beschreibung ist ungültig\n";
		}
		if (!exception.isEmpty()) {
			throw new IllegalArgumentException(exception);
		}

		this.projektID = -1;
		this.name = inhalt[0];
		this.beschreibung = inhalt[1];

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
