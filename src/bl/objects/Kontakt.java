package bl.objects;

import java.text.ParseException;

import dal.DBEntity;

public class Kontakt extends DBEntity {
	private int kontaktID;
	private String firma, name, telefon;

	public Kontakt(int id, String firma, String name, String telefon) {
		super();
		this.kontaktID = id;
		this.firma = firma;
		this.name = name;
		this.telefon = telefon;
	}

	public Object getID() {
		return getKontaktID();
	}

	public int getKontaktID() {
		return kontaktID;
	}

	public void setKontaktID(int kontaktID) {
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

	public String toString() {
		return "Kontakt-ID: " + kontaktID + "\nFirma: " + firma + "\nName: "
				+ name + "\nTelefon: " + telefon;
	}

}
