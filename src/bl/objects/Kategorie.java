package bl.objects;

import dal.DBEntity;

public class Kategorie extends DBEntity {
	private int kategorieID;
	private String kbz, beschreibung;

	public Kategorie(int id, String name, String beschreibung) {
		this.kategorieID = id;
		this.kbz = name;
		this.beschreibung = beschreibung;
	}

	/**
	 * 
	 * @param inhalt
	 * @param inhalt
	 *            [0] =kbz
	 * @param inhalt
	 *            [1] =beschreibung
	 */
	public Kategorie(String[] inhalt) throws IllegalArgumentException {
		String exception = "";
		if (inhalt[0] == null || inhalt[0].isEmpty()) {
			exception += "Kurzbezeichnung ist ungültig\n";
		}
		if (inhalt[1] == null || inhalt[1].isEmpty()) {
			exception += "Beschreibung ist ungültig\n";
		}
		if (!exception.isEmpty()) {
			throw new IllegalArgumentException(exception);
		}

		this.kategorieID = -1;
		this.kbz = inhalt[0];
		this.beschreibung = inhalt[1];

	}

	public int getId() {
		return kategorieID;
	}

	public void setId(int id) {
		this.kategorieID = id;
	}

	public String getKbz() {
		return kbz;
	}

	public void setKbz(String kbz) {
		this.kbz = kbz;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public String toString() {
		return "Kategorie-ID: " + kategorieID + "\nKurzbezeichnung: " + kbz
				+ "\nBeschreibung: " + beschreibung;
	}
}
