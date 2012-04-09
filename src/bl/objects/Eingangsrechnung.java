package bl.objects;

public class Eingangsrechnung extends Rechnung {
	private int kontaktID;

	public Eingangsrechnung(int rechnungID, String status, int kontaktID) {
		super(rechnungID, status);
		this.kontaktID = kontaktID;
	}

	/**
	 * 
	 * @param inhalt
	 * @param inhalt
	 *            [0]=status;
	 * @param inhalt
	 *            [1]=kundenid;
	 */
	public Eingangsrechnung(String[] inhalt) throws IllegalArgumentException {
		super(-1, "");
		if (inhalt[0] == null || inhalt[0].isEmpty()) {
			throw new IllegalArgumentException("Status ist ungültig");
		}
		setStatus(inhalt[0]);
		this.kontaktID = Integer.valueOf(inhalt[1]);
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
