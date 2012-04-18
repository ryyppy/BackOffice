package bl.objects.view;

import java.util.Date;

import dal.TableMeta;

@TableMeta(pkFieldName = "rechnungID")
public class EingangsrechnungView extends RechnungView {
	private Integer rechnungID;
	// KONTAKT
	private Integer kontaktID;
	private String firma;

	public EingangsrechnungView() {
		super();
	}

	public EingangsrechnungView(String status, Date datum, Integer kontaktID) {
		super(status, datum);
		this.kontaktID = kontaktID;
	}

	public EingangsrechnungView(Integer rechnungID, String status, Date datum,
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

	public String getFirma() {
		return firma;
	}

	public void setFirma(String firma) {
		this.firma = firma;
	}

	@Override
	public String toString() {
		return "Eingangsrechnung [rechnungID=" + rechnungID + ", status="
				+ getStatus() + ", datum=" + getDatumString() + ", kontaktID="
				+ kontaktID + ", firma=" + firma + "]";
	}

	public String getValues() {
		return "Eingangsrechnung:\n" + super.getValues() + "\nKontakt: "
				+ kontaktID + " - " + firma;
	}

}
