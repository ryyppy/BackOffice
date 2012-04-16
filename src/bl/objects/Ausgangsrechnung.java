package bl.objects;

import java.util.Date;

import dal.TableMeta;

@TableMeta(pkFieldName = "rechnungID")
public class Ausgangsrechnung extends Rechnung {
	private Integer rechnungID;
	private Integer kundeID;

	public Ausgangsrechnung() {
		super();
	}

	public Ausgangsrechnung(String status, Date datum, Integer kundenID) {
		super(status, datum);
		this.kundeID = kundenID;
	}

	public Ausgangsrechnung(Integer rechnungID, String status, Date datum,
			Integer kundenID) {
		super(rechnungID, status, datum);
		this.kundeID = kundenID;
		this.rechnungID = rechnungID;
	}

	public Integer getKundeID() {
		return kundeID;
	}

	public void setKundeID(Integer kundenID) {
		this.kundeID = kundenID;
	}

	public Integer getRechnungID() {
		return rechnungID;
	}

	public void setRechnungID(Integer rechnungID) {
		this.rechnungID = rechnungID;
		super.setRechnungID(rechnungID);
	}

	@Override
	public String toString() {
		return "Ausgangsrechnung [rechnungID=" + rechnungID + ", status="
				+ getStatus() + ", datum=" + getDatumString() + ", kundeID="
				+ kundeID + "]";
	}

	public String getValues() {
		return "Ausgangsrechnung:\n" + super.toString() + "\nKunden-ID: "
				+ kundeID;
	}
}
