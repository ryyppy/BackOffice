package bl.objects;

import dal.DBEntity;
import dal.TableMeta;

@TableMeta(pkFieldName = "kKbz")
public class Kategorie extends DBEntity {
	private String kKbz;
	private String beschreibung;

	public Kategorie() {

	}

	public Kategorie(String kbz, String beschreibung) {
		this.kKbz = kbz;
		this.beschreibung = beschreibung;
	}

	public String getKKbz() {
		return kKbz;
	}

	public void setKKbz(String kbz) {
		this.kKbz = kbz;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	@Override
	public String toString() {
		return "Kategorie [kKbz=" + kKbz + ", beschreibung=" + beschreibung
				+ "]";
	}

	public String getValues() {
		return "Kurzbezeichnung: " + kKbz + "\nBeschreibung: " + beschreibung;
	}
}
