package bl.objects;

import dal.DBEntity;
import dal.TableMeta;

@TableMeta(pkFieldName = "kkbz")
public class Kategorie extends DBEntity {
	private String kkbz;
	private String beschreibung;

	public Kategorie() {

	}

	public Kategorie(String kbz, String beschreibung) {
		this.kkbz = kbz;
		this.beschreibung = beschreibung;
	}

	public String getKKbz() {
		return kkbz;
	}

	public void setKKbz(String kbz) {
		this.kkbz = kbz;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public String toString() {
		return "Kurzbezeichnung: " + kkbz + "\nBeschreibung: " + beschreibung;
	}
}
