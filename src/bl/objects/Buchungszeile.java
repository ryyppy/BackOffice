package bl.objects;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import dal.DBEntity;
import dal.TableMeta;

@TableMeta(pkFieldName = "buchungszeileID")
public class Buchungszeile extends DBEntity {
	private Integer buchungszeileID;
	private Date datum;
	private String kommentar;
	private Double betrag;
	private Double steuersatz;
	private String kategorieKbz;

	public Buchungszeile() {

	}

	public Buchungszeile(Date datum, String kommentar, double steuersatz,
			double betrag, String kategorieKbz) {
		super();
		this.datum = datum;
		this.kommentar = kommentar;
		this.betrag = betrag;
		this.steuersatz = steuersatz;
		this.kategorieKbz = kategorieKbz;
	}

	public Buchungszeile(int id, Date datum, String kommentar,
			double steuersatz, double betrag, String kategorieKbz) {
		super();
		this.buchungszeileID = id;
		this.datum = datum;
		this.kommentar = kommentar;
		this.betrag = betrag;
		this.steuersatz = steuersatz;
		this.kategorieKbz = kategorieKbz;
	}

	public int getBuchungszeileID() {
		return buchungszeileID;
	}

	public void setBuchungszeileID(int buchungszeileID) {
		this.buchungszeileID = buchungszeileID;
	}

	public Date getDatum() {
		return datum;
	}

	public String getDatumString() {
		return new StringBuilder(
				new SimpleDateFormat("dd.MM.yyyy").format(datum)).toString();
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public void setDatum(String datum) throws ParseException {
		this.datum = new SimpleDateFormat("dd.MM.yyyy").parse(datum);
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

	public String getKategorieKbz() {
		return kategorieKbz;
	}

	public void setKategorieKbz(String kategorieKbz) {
		this.kategorieKbz = kategorieKbz;
	}

	public String toString() {
		return "Buchungszeile-ID: " + buchungszeileID + "\nDatum: "
				+ getDatumString() + "\nKommentar: " + kommentar
				+ "\nSteuersatz: " + steuersatz + "\nBetrag: " + betrag
				+ "\nKategorie-ID: " + kategorieKbz;
	}
}
