package bl.objects;

import java.util.Date;

import dal.TableMeta;

@TableMeta(pkFieldName = "rechnungID")
public class Ausgangsrechnung extends Rechnung {
	private Integer kundeID;
	private Integer rechnungID;

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

	public int getKundenID() {
		return kundeID;
	}

	public void setKundenID(int kundenID) {
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
