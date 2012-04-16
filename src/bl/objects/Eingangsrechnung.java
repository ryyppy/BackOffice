package bl.objects;

import java.util.Date;

import dal.TableMeta;

@TableMeta(pkFieldName = "rechnungID")
public class Eingangsrechnung extends Rechnung {
	private Integer rechnungID;
	private Integer kontaktID;

	public Eingangsrechnung() {
		super();
	}

	public Eingangsrechnung(String status, Date datum, int kontaktID) {
		super(status, datum);
		this.kontaktID = kontaktID;
	}

	public Eingangsrechnung(int rechnungID, String status, Date datum,
			int kontaktID) {
		super(rechnungID, status, datum);
		this.kontaktID = kontaktID;
		this.rechnungID = rechnungID;
	}

	public int getKontaktID() {
		return kontaktID;
	}

	public void setKontaktID(int kontaktID) {
		this.kontaktID = kontaktID;
	}

	public int getRechnungID() {
		return rechnungID;
	}

	public void setRechnungID(int rechnungID) {
		this.rechnungID = rechnungID;
		super.setRechnungID(rechnungID);
	}

	public String toString() {
		return "Eingangsrechnung:\n" + super.toString() + "\nKontakt-ID: "
				+ kontaktID;
	}
}
