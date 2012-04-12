package bl.objects;

import dal.DBEntity;

public class Kategorie extends DBEntity {
	private int kategorieID;
	private String kbz, beschreibung;

	public Kategorie(int id, String kbz, String beschreibung) {
		this.kategorieID = id;
		this.kbz = kbz;
		this.beschreibung = beschreibung;
	}

	public Object getID() {
		return getKategorieID();
	}

	public int getKategorieID() {
		return kategorieID;
	}

	public void setKategorieID(int kategorieID) {
		this.kategorieID = kategorieID;
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
