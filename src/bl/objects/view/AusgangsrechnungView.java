package bl.objects.view;

import java.util.Date;

import dal.TableMeta;

@TableMeta(pkFieldName = "rechnungID")
public class AusgangsrechnungView extends RechnungView {
	private Integer rechnungID;
	// KUNDE
	private Integer kundeID;
	private String nachname;

	public AusgangsrechnungView() {
		super();
	}

	public AusgangsrechnungView(String status, Date datum, Integer kundenID) {
		super(status, datum);
		this.kundeID = kundenID;

	}

	public AusgangsrechnungView(Integer rechnungID, String status, Date datum,
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

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	@Override
	public String toString() {
		return "Ausgangsrechnung [rechnungID=" + rechnungID + ", status="
				+ getStatus() + ", datum=" + getDatumString() + ", kundeID="
				+ kundeID + ", nachname=" + nachname + "]";
	}

	public String getValues() {
		return "Ausgangsrechnung:\n" + super.getValues() + "\nKunde: "
				+ kundeID + " - " + nachname;
	}
}
