package bl.objects.view;

import dal.DBEntity;
import dal.TableMeta;

@TableMeta(pkFieldName = "kontaktID")
public class KontaktView extends DBEntity {
	private Integer kontaktID;
	private String firma, name, telefon;

	public KontaktView() {

	}

	public KontaktView(String firma, String name, String telefon) {
		this.firma = firma;
		this.name = name;
		this.telefon = telefon;
	}

	public KontaktView(Integer id, String firma, String name, String telefon) {
		this.kontaktID = id;
		this.firma = firma;
		this.name = name;
		this.telefon = telefon;
	}

	public Integer getKontaktID() {
		return kontaktID;
	}

	public void setKontaktID(Integer kontaktID) {
		this.kontaktID = kontaktID;
	}

	public String getFirma() {
		return firma;
	}

	public void setFirma(String firma) {
		this.firma = firma;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;

	}

	@Override
	public String toString() {
		return "Kontakt [kontaktID=" + kontaktID + ", firma=" + firma
				+ ", name=" + name + ", telefon=" + telefon + "]";
	}

	public String getValues() {
		return "Kontakt-ID: " + kontaktID + "\nFirma: " + firma + "\nName: "
				+ name + "\nTelefon: " + telefon;
	}

}
