package bl.objects.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import dal.DBEntity;
import dal.TableMeta;

@TableMeta(pkFieldName = "rechnungID")
public class RechnungView extends DBEntity {
	private Integer rechnungID;
	private String status;
	private Date datum;

	public RechnungView() {

	}

	public RechnungView(String status, Date datum) {
		this.status = status;
		this.datum = datum;
	}

	public RechnungView(Integer rechnungID, String status, Date datum) {
		this.rechnungID = rechnungID;
		this.status = status;
		this.datum = datum;
	}

	public Integer getRechnungID() {
		return rechnungID;
	}

	public void setRechnungID(Integer rechnungID) {
		this.rechnungID = rechnungID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDatum() {
		if (datum == null) {
			return null;
		}
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
		return "Rechnung [rechnungID=" + rechnungID + ", status=" + status
				+ ", datum=" + getDatumString() + "]";
	}

	public String getValues() {
		return "Rechnung-ID: " + rechnungID + "\nStatus: " + status
				+ "\nDatum: " + getDatumString();
	}
}
