package bl.objects;

import dal.DBEntity;
import dal.TableMeta;

@TableMeta(pkFieldName = "rechnungszeileID")
public class Rechnungszeile extends DBEntity {
	private Integer rechnungszeileID;
	private String kommentar;
	private Double steuersatz;
	private Double betrag;
	private Integer rechnungID;
	private Integer angebotID;

	public Rechnungszeile() {

	}

	public Rechnungszeile(String kommentar, double steuersatz, double betrag,
			int rechnungID, Integer angebotsID) {
		super();
		this.rechnungID = rechnungID;
		this.angebotID = angebotsID;
		this.steuersatz = steuersatz;
		this.kommentar = kommentar;
		this.betrag = betrag;
	}

	public Rechnungszeile(int id, String kommentar, double steuersatz,
			double betrag, int rechnungID, int angebotsID) {
		super();
		this.rechnungszeileID = id;
		this.rechnungID = rechnungID;
		this.angebotID = angebotsID;
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

	public Integer getAngebotID() {
		return angebotID;
	}

	public void setAngebotID(Integer angebotsID) {
		this.angebotID = angebotsID;
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
				+ angebotID;
	}
}
