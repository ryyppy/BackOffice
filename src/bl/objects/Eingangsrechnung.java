package bl.objects;

public class Eingangsrechnung extends Rechnung {
	private int kontaktID;

	public Eingangsrechnung(int rechnungID, String status, int kontaktID) {
		super(rechnungID, status);
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
