package bl.objects.view;

import dal.DBEntity;
import dal.TableMeta;

@TableMeta(pkFieldName = "rechnungszeileID")
public class RechnungszeileView extends DBEntity {
	private Integer rechnungszeileID;
	private Integer rechnungID;
	private String kommentar;
	private Double steuersatz;
	private Double betrag;
//	private Integer angebotID;
	private String angebot;

	public RechnungszeileView() {

	}

	public RechnungszeileView(String kommentar, Double steuersatz,
			Double betrag, String angebot) {
		super();
		this.angebot = angebot;
		this.steuersatz = steuersatz;
		this.kommentar = kommentar;
		this.betrag = betrag;
	}

	public RechnungszeileView(Integer id, String kommentar, Double steuersatz,
			Double betrag, String angebot) {
		super();
		this.rechnungszeileID = id;
		this.angebot = angebot;
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

	public String getAngebot() {
		return angebot;
	}

	public void setAngebot(String angebot) {
		this.angebot = angebot;
	}

	public Integer getRechnungID() {
		return rechnungID;
	}

	public void setRechnungID(Integer rechnungid) {
		this.rechnungID = rechnungid;
	}

//	public Integer getAngebotID() {
//		return angebotID;
//	}
//
//	public void setAngebotID(Integer angebotID) {
//		this.angebotID = angebotID;
//	}

	@Override
	public String toString() {
		return "Rechnungszeile [rechnungszeileID=" + rechnungszeileID
				+ ", kommentar=" + kommentar + ", steuersatz=" + steuersatz
				+ ", betrag=" + betrag + ", angebot=" + angebot + "]";
	}

	public String getValues() {
		return "Rechnungszeile-ID: " + rechnungszeileID + "\nKommentar: "
				+ kommentar + "\nSteuersatz: " + steuersatz + "\nBetrag: "
				+ betrag + "\nAngebot: " + angebot;
	}

}
