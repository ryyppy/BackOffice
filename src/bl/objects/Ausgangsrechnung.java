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

	public Ausgangsrechnung(String status, Date datum, int kundenID) {
		super(status, datum);
		this.kundeID = kundenID;
	}

	public Ausgangsrechnung(int rechnungID, String status, Date datum,
			int kundenID) {
		super(rechnungID, status, datum);
		this.kundeID = kundenID;
		this.rechnungID = rechnungID;
	}

	public int getKundeID() {
		return kundeID;
	}

	public void setKundeID(int kundenID) {
		this.kundeID = kundenID;
	}

	public int getRechnungID() {
		return rechnungID;
	}

	public void setRechnungID(int rechnungID) {
		this.rechnungID = rechnungID;
		super.setRechnungID(rechnungID);
	}

	public String toString() {
		return "Ausgangsrechnung:\n" + super.toString() + "\nKunden-ID: "
				+ kundeID;
	}
}
