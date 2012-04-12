package bl.objects;

import java.util.Date;

public class Ausgangsrechnung extends Rechnung {
	private int kundenID;

	public Ausgangsrechnung(int rechnungID, String status, Date datum,
			int kundenID) {
		super(rechnungID, status, datum);
		this.kundenID = kundenID;
	}

	public int getKundenID() {
		return kundenID;
	}

	public void setKundenID(int kundenID) {
		this.kundenID = kundenID;
	}

	public String toString() {
		return "Ausgangsrechnung:\n" + super.toString() + "\nKunden-ID: "
				+ kundenID;
	}
}
