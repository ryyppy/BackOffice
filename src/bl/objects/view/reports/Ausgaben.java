package bl.objects.view.reports;

import java.util.Date;

import dal.DBEntity;
import dal.TableMeta;

@TableMeta(pkFieldName = "iD")
public class Ausgaben extends DBEntity {
	private Integer iD;
	private String kommentar;
	private Double steuersatz;
	private Double betrag;
	private Date datum;

	public Ausgaben() {

	}

	public Ausgaben(Integer iD, String kommentar, Double steuersatz,
			Double betrag, Date datum) {
		super();
		this.iD = iD;
		this.kommentar = kommentar;
		this.steuersatz = steuersatz;
		this.betrag = betrag;
		this.datum = datum;
	}

	@Override
	public Object getID() {
		return getiD();
	}

	public Integer getiD() {
		return iD;
	}

	public void setiD(Integer iD) {
		this.iD = iD;
	}

	public String getKommentar() {
		return kommentar;
	}

	public void setKommentar(String kommentar) {
		this.kommentar = kommentar;
	}

	public Double getSteuersatz() {
		return steuersatz;
	}

	public void setSteuersatz(Double steuersatz) {
		this.steuersatz = steuersatz;
	}

	public Double getBetrag() {
		return betrag;
	}

	public void setBetrag(Double betrag) {
		this.betrag = betrag;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	@Override
	public String toString() {
		return "Ausgaben [ID=" + iD + ", kommentar=" + kommentar
				+ ", steuersatz=" + steuersatz + ", betrag=" + betrag
				+ ", datum=" + datum + "]";
	}

}
