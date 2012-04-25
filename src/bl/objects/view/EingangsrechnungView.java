package bl.objects.view;

import java.util.Date;

import dal.TableMeta;

@TableMeta(pkFieldName = "rechnungID")
public class EingangsrechnungView extends RechnungView {
	private Integer rechnungID;
	private String kontakt;

	public EingangsrechnungView() {
		super();
	}

	public EingangsrechnungView(String status, Date datum, String kontakt) {
		super(status, datum);
		this.kontakt = kontakt;
	}

	public EingangsrechnungView(Integer rechnungID, String status, Date datum,
			String kontakt) {
		super(rechnungID, status, datum);
		this.kontakt = kontakt;
		this.rechnungID = rechnungID;
	}

	public String getKontakt() {
		return kontakt;
	}

	public void setKontakt(String kontakt) {
		this.kontakt = kontakt;
	}

	public Integer getRechnungID() {
		return rechnungID;
	}

	public void setRechnungID(Integer rechnungID) {
		this.rechnungID = rechnungID;
		super.setRechnungID(rechnungID);
	}

	public String getKontaktfirma() {
		return kontakt;
	}

	public void setKontaktfirma(String firma) {
		this.kontakt = firma;
	}

	@Override
	public String toString() {
		return "Eingangsrechnung [rechnungID=" + rechnungID + ", status="
				+ getStatus() + ", datum=" + getDatumString() + ", kontakt="
				+ kontakt + "]";
	}

	public String getValues() {
		return "Eingangsrechnung:\n" + super.getValues() + "\nKontakt: "
				+ kontakt;
	}

}
