package bl.objects.view;

import dal.DBEntity;
import dal.TableMeta;

@TableMeta(pkFieldName = "rechnungszeileID")
public class RechnungszeileView extends DBEntity {
	private Integer rechnungszeileID;
	private String kommentar;
	private Double steuersatz;
	private Double betrag;
	private Integer rechnungID;
	private Integer angebotID;

	public RechnungszeileView() {

	}

	public RechnungszeileView(String kommentar, Double steuersatz, Double betrag,
			Integer rechnungID, Integer angebotsID) {
		super();
		this.rechnungID = rechnungID;
		this.angebotID = angebotsID;
		this.steuersatz = steuersatz;
		this.kommentar = kommentar;
		this.betrag = betrag;
	}

	public RechnungszeileView(Integer id, String kommentar, Double steuersatz,
			Double betrag, Integer rechnungID, Integer angebotsID) {
		super();
		this.rechnungszeileID = id;
		this.rechnungID = rechnungID;
		this.angebotID = angebotsID;
		this.steuersatz = steuersatz;
		this.kommentar = kommentar;
		this.betrag = betrag;
	}

	public Integer getRechnungszeileID() {
		return rechnungszeileID;
	}

	public void setRechnungszeileID(Integer rechnungszeileID) {
		this.rechnungszeileID = rechnungszeileID;
	}

	public Integer getRechnungID() {
		return rechnungID;
	}

	public void setRechnungID(Integer rechnungID) {
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

	public Double getBetrag() {
		return betrag;
	}

	public void setBetrag(Double betrag) {
		this.betrag = betrag;
	}

	public Double getSteuersatz() {
		return steuersatz;
	}

	public void setSteuersatz(Double steuersatz) {
		this.steuersatz = steuersatz;
	}

	@Override
	public String toString() {
		return "Rechnungszeile [rechnungszeileID=" + rechnungszeileID
				+ ", kommentar=" + kommentar + ", steuersatz=" + steuersatz
				+ ", betrag=" + betrag + ", rechnungID=" + rechnungID
				+ ", angebotID=" + angebotID + "]";
	}

	public String getValues() {
		return "Rechnungszeile-ID: " + rechnungszeileID + "\nRechnung-ID: "
				+ rechnungID + "\nKommentar: " + kommentar + "\nSteuersatz: "
				+ steuersatz + "\nBetrag: " + betrag + "\nAngebot-ID: "
				+ angebotID;
	}

}
