package bl.objects;

import dal.DBEntity;

public class Buchungszeile extends DBEntity {
	private int buchungszeileID, kategorieID;
	private String kommentar;
	private double betrag, steuersatz;

	public Buchungszeile(int id, String kommentar, double steuersatz,
			double betrag, int kategorieID) {
		super();
		this.buchungszeileID = id;
		this.kommentar = kommentar;
		this.betrag = betrag;
		this.steuersatz = steuersatz;
		this.kategorieID = kategorieID;
	}

	/**
	 * 
	 * @param inhalt
	 * @param inhalt
	 *            [0]=kommentar
	 * @param inhalt
	 *            [1]=steuersatz
	 * @param inhalt
	 *            [2]=betrag
	 * @param inhalt
	 *            [3]=kategorieID
	 */
	public Buchungszeile(String[] inhalt) throws IllegalArgumentException {
		this.buchungszeileID = -1;

		if (inhalt[0].isEmpty() || inhalt[0] == null) {
			throw new IllegalArgumentException("Kommentar ist ungültig");
		}
		this.kommentar = inhalt[1];

		if (inhalt[1].isEmpty()) {
			throw new IllegalArgumentException(
					"Steuersatz muss festgelegt werden");
		}
		this.steuersatz = Double.parseDouble(inhalt[1]);
		if (steuersatz < 0 || steuersatz > 100) {
			throw new IllegalArgumentException(
					"Steuersatz darf nur zw. 0 und 100 sein");
		}

		if (inhalt[2].isEmpty()) {
			throw new IllegalArgumentException("Betrag muss festgelegt werden");
		}
		this.betrag = Double.parseDouble(inhalt[2]);
		if (betrag < 0) {
			throw new IllegalArgumentException("Betrag darf nicht negativ sein");
		}

		this.kategorieID = Integer.valueOf(inhalt[3]);
	}

	public Object getID() {
		return getBuchungszeileID();
	}

	public int getBuchungszeileID() {
		return buchungszeileID;
	}

	public void setBuchungszeileID(int buchungszeileID) {
		this.buchungszeileID = buchungszeileID;
	}

	public String getKommentar() {
		return kommentar;
	}

	public void setKommentar(String kommentar) {
		this.kommentar = kommentar;
	}

	public double getBetrag() {
		return betrag;
	}

	public void setBetrag(double betrag) {
		this.betrag = betrag;
	}

	public double getSteuersatz() {
		return steuersatz;
	}

	public void setSteuersatz(double steuersatz) {
		this.steuersatz = steuersatz;
	}

	public int getKategorieID() {
		return kategorieID;
	}

	public void setKategorieID(int kategorieID) {
		this.kategorieID = kategorieID;
	}

	public String toString() {
		return "Buchungszeile-ID: " + buchungszeileID + "\nKommentar: "
				+ kommentar + "\nSteuersatz: " + steuersatz + "\nBetrag: "
				+ betrag + "\nKategorie-ID: " + kategorieID;
	}
}
