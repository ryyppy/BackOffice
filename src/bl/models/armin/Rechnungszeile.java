package bl.models.armin;

import java.util.Date;

public class Rechnungszeile {
	private int rechnungszeileID, rechnungID, angebotsID;
	private String kommentar;
	private double steuersatz, betrag;

	public Rechnungszeile(int id, String kommentar, double betrag,
			int rechnungID, int angebotsID) {
		super();
		this.rechnungszeileID = id;
		this.rechnungID = rechnungID;
		this.angebotsID = angebotsID;
		this.kommentar = kommentar;
		this.betrag = betrag;
	}

	/**
	 * 
	 * @param inhalt
	 * @param inhalt
	 *            [0]=rechnungid
	 * @param inhalt
	 *            [1]=kommentar
	 * @param inhalt
	 *            [2]=steuersatz
	 * @param inhalt
	 *            [3]=betrag
	 * @param inhalt
	 *            [4]=angebotid
	 */
	public Rechnungszeile(String[] inhalt) {
		this.rechnungszeileID = -1;
		this.rechnungID = Integer.valueOf(inhalt[0]);

		if (inhalt[1].isEmpty() || inhalt[1] == null) {
			throw new IllegalArgumentException("Kommentar ist ungültig");
		}
		this.kommentar = inhalt[1];
		if (inhalt[2].isEmpty()) {
			throw new IllegalArgumentException(
					"Steuersatz muss festgelegt werden");
		}
		this.steuersatz = Double.parseDouble(inhalt[2]);
		if (steuersatz < 0 || steuersatz > 100) {
			throw new IllegalArgumentException(
					"Steuersatz darf nur zw. 0 und 100 sein");
		}
		if (inhalt[3].isEmpty()) {
			throw new IllegalArgumentException("Chance muss festgelegt werden");
		}
		this.betrag = Double.parseDouble(inhalt[3]);
		if (betrag < 0) {
			throw new IllegalArgumentException("Betrag darf nicht negativ sein");
		}
		
		this.angebotsID = Integer.valueOf(inhalt[4]);
	}

	public int getId() {
		return rechnungszeileID;
	}

	public void setId(int id) {
		this.rechnungszeileID = id;
	}

	public int getRechnungID() {
		return rechnungID;
	}

	public void setRechnungID(int rechnungID) {
		this.rechnungID = rechnungID;
	}

	public int getAngebotsID() {
		return angebotsID;
	}

	public void setAngebotsID(int angebotsID) {
		this.angebotsID = angebotsID;
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

	public String toString() {
		return rechnungszeileID + "\n" + rechnungID + "\n" + kommentar + "\n"
				+ steuersatz + "\n" + betrag + "\n" + angebotsID;
	}
}
