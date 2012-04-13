package bl.objects;

import dal.DBEntity;
import dal.TableMeta;

@TableMeta(pkFieldName = "rechnungszeileID")
public class Rechnungszeile extends DBEntity {
	private int rechnungszeileID, rechnungID, angebotsID;
	private String kommentar;
	private double steuersatz, betrag;

	public Rechnungszeile() {

	}

	public Rechnungszeile(int id, String kommentar, double steuersatz,
			double betrag, int rechnungID, int angebotsID) {
		super();
		this.rechnungszeileID = id;
		this.rechnungID = rechnungID;
		this.angebotsID = angebotsID;
		this.steuersatz = steuersatz;
		this.kommentar = kommentar;
		this.betrag = betrag;
	}

	public int getRechnungszeileID() {
		return rechnungszeileID;
	}

	public void setRechnungszeileID(int rechnungszeileID) {
		this.rechnungszeileID = rechnungszeileID;
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
		return "Rechnungszeile-ID: " + rechnungszeileID + "\nRechnung-ID: "
				+ rechnungID + "\nKommentar: " + kommentar + "\nSteuersatz: "
				+ steuersatz + "\nBetrag: " + betrag + "\nAngebot-ID: "
				+ angebotsID;
	}
}
