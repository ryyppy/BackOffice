package bl.objects;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import dal.DBEntity;

public abstract class Rechnung extends DBEntity {
	private int rechnungID;
	private String status;
	private Date datum;
	public boolean ausgewaehlt=false;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

	public Rechnung(int rechnungID, String status, Date datum) {
		this.rechnungID = rechnungID;
		this.status = status;
		this.datum = datum;
	}

	public Object getID() {
		return getRechnungID();
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
		return new StringBuilder(dateFormat.format(datum)).toString();
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public void setDatum(String datum) throws ParseException {
		this.datum = dateFormat.parse(datum);
	}

	@Override
	public String toString() {
		return "Rechnung-ID: " + rechnungID + "\nStatus: " + status
				+ "\nDatum: " + getDatumString();
	}
}
