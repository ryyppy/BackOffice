package bl.objects;

import java.util.Date;

public class Eingangsrechnung extends Rechnung {
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
	}

	public int getKontaktID() {
		return kontaktID;
	}

	public void setKontaktID(int kontaktID) {
		this.kontaktID = kontaktID;
	}

	public String toString() {
		return "Eingangsrechnung:\n" + super.toString() + "\nKontakt-ID: "
				+ kontaktID;
	}
}
