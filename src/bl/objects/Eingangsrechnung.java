package bl.objects;

import java.util.Date;

import dal.TableMeta;

@TableMeta(pkFieldName = "rechnungID")
public class Eingangsrechnung extends Rechnung {
	private Integer rechnungID;
	private Integer kontaktID;

	public Eingangsrechnung() {
		super();
	}

	public Eingangsrechnung(String status, Date datum, Integer kontaktID) {
		super(status, datum);
		this.kontaktID = kontaktID;
	}

	public Eingangsrechnung(Integer rechnungID, String status, Date datum,
			Integer kontaktID) {
		super(rechnungID, status, datum);
		this.kontaktID = kontaktID;
		this.rechnungID = rechnungID;
	}

	public Integer getKontaktID() {
		return kontaktID;
	}

	public void setKontaktID(Integer kontaktID) {
		this.kontaktID = kontaktID;
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
		return "Eingangsrechnung [rechnungID=" + rechnungID + ", status="
				+ getStatus() + ", datum=" + getDatumString() + ", kontaktID="
				+ kontaktID + "]";
	}

	public String getValues() {
		return "Eingangsrechnung:\n" + super.getValues() + "\nKontakt-ID: "
				+ kontaktID;
	}

}
