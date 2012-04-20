package bl.objects.view;

import java.util.Date;

import dal.TableMeta;

@TableMeta(pkFieldName = "rechnungID")
public class AusgangsrechnungView extends RechnungView {
	private Integer rechnungID;
	private String kunde;

	public AusgangsrechnungView() {
		super();
	}

	public AusgangsrechnungView(String status, Date datum, String kundenname) {
		super(status, datum);
		this.kunde = kundenname;

	}

	public AusgangsrechnungView(Integer rechnungID, String status, Date datum,
			String kundenname) {
		super(rechnungID, status, datum);
		this.kunde = kundenname;
		this.rechnungID = rechnungID;
	}

	public Integer getRechnungID() {
		return rechnungID;
	}

	public void setRechnungID(Integer rechnungID) {
		this.rechnungID = rechnungID;
		super.setRechnungID(rechnungID);
	}

	public String getKunde() {
		return kunde;
	}

	public void setKunde(String nachname) {
		this.kunde = nachname;
	}

	@Override
	public String toString() {
		return "Ausgangsrechnung [rechnungID=" + rechnungID + ", status="
				+ getStatus() + ", datum=" + getDatumString() + ", kunde="
				+ kunde + "]";
	}

	public String getValues() {
		return "Ausgangsrechnung:\n" + super.getValues() + "\nKunde: " + kunde;
	}
}
