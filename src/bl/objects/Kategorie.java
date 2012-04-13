package bl.objects;

import dal.DBEntity;
import dal.TableMeta;

@TableMeta(pkFieldName = "kbz")
public class Kategorie extends DBEntity {
	private String kbz, beschreibung;

	public Kategorie() {

	}

	public Kategorie(String kbz, String beschreibung) {
		this.kbz = kbz;
		this.beschreibung = beschreibung;
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
		return "Kurzbezeichnung: " + kbz + "\nBeschreibung: " + beschreibung;
	}
}
