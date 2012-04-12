package bl.objects;

import dal.DBEntity;

public class Buchungszeile extends DBEntity {
	private int buchungszeileID, kategorieID;
	private String kommentar;
	private double betrag, steuersatz;
	public boolean auswahl=false;

	public Buchungszeile(int id, String kommentar, double steuersatz,
			double betrag, int kategorieID) {
		super();
		this.buchungszeileID = id;
		this.kommentar = kommentar;
		this.betrag = betrag;
		this.steuersatz = steuersatz;
		this.kategorieID = kategorieID;
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
