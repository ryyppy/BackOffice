package bl.objects;

import java.util.Date;

import dal.TableMeta;

@TableMeta(pkFieldName = "rechnungID")
public class Eingangsrechnung extends Rechnung {
	private Integer rechnungID;
	private Integer kontaktID;
	private String file;

	public Eingangsrechnung() {
		super();
	}

	public Eingangsrechnung(String status, Date datum, Integer kontaktID) {
		super(status, datum);
		this.kontaktID = kontaktID;
	}
	
	public Eingangsrechnung(String status, Date datum, Integer kontaktID, String file) {
		super(status, datum);
		this.kontaktID = kontaktID;
		this.file=file;
	}

	public Eingangsrechnung(Integer rechnungID, String status, Date datum,
			Integer kontaktID,String file) {
		super(rechnungID, status, datum);
		this.kontaktID = kontaktID;
		this.rechnungID = rechnungID;
		this.file=file;
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

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
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
