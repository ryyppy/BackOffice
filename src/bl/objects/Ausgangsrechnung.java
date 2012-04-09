package bl.objects;

public class Ausgangsrechnung extends Rechnung {
	private int kundenID;

	public Ausgangsrechnung(int rechnungID, String status, int kundenID) {
		super(rechnungID, status);
		this.kundenID = kundenID;
	}

	/**
	 * 
	 * @param inhalt
	 * @param inhalt
	 *            [0]=status;
	 * @param inhalt
	 *            [1]=kundenid;
	 */
	public Ausgangsrechnung(String[] inhalt) throws IllegalArgumentException {
		super(-1, "");
		if (inhalt[0] == null || inhalt[0].isEmpty()) {
			throw new IllegalArgumentException("Status ist ungültig");
		}
		setStatus(inhalt[0]);
		this.kundenID = Integer.valueOf(inhalt[1]);
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
