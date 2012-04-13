package bl.objects;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import dal.DBEntity;
import dal.TableMeta;

@TableMeta(pkFieldName = "rechnungID")
public class Rechnung extends DBEntity {
	private Integer rechnungID;
	private String status;
	private Date datum;

	public Rechnung() {

	}

	public Rechnung(String status, Date datum) {
		this.status = status;
		this.datum = datum;
	}

	public Rechnung(int rechnungID, String status, Date datum) {
		this.rechnungID = rechnungID;
		this.status = status;
		this.datum = datum;
	}

	public int getRechnungID() {
		return rechnungID;
	}

	public void setRechnungID(int rechnungID) {
		this.rechnungID = rechnungID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDatum() {
		return datum;
	}

	public String getDatumString() {
		if (datum == null) {
			return null;
		}
		return new StringBuilder(
				new SimpleDateFormat("dd.MM.yyyy").format(datum)).toString();
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public void setDatum(String datum) throws ParseException {
		this.datum = new SimpleDateFormat("dd.MM.yyyy").parse(datum);
	}

	@Override
	public String toString() {
		return "Rechnung-ID: " + rechnungID + "\nStatus: " + status
				+ "\nDatum: " + getDatumString();
	}
}
