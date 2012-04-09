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

	/**
	 * 
	 * @param inhalt
	 * @param inhalt
	 *            [0]=firma;
	 * @param inhalt
	 *            [1]=name;
	 * @param inhalt
	 *            [2]=telefon;
	 * @throws ParseException
	 */
	public Kontakt(String[] inhalt) throws ParseException,
			IllegalArgumentException {
		String exception = "";
		this.kontaktID = -1;
		try {
			setFirma(inhalt[0]);
		} catch (IllegalArgumentException e) {
			exception += e.getMessage() + "\n";
		}
		try {
			setName(inhalt[1]);
		} catch (IllegalArgumentException e) {
			exception += e.getMessage() + "\n";
		}
		try {
			setTelefon(inhalt[2]);
		} catch (IllegalArgumentException e) {
			exception += e.getMessage() + "\n";
		}

		if (!exception.isEmpty()) {
			throw new IllegalArgumentException(exception);
		}

	}

	public int getId() {
		return kontaktID;
	}

	public void setId(int id) {
		this.kontaktID = id;
	}

	public String getFirma() {
		return firma;
	}

	public void setFirma(String firma) throws IllegalArgumentException {
		if (firma == null || firma.isEmpty() || firma == "") {
			throw new IllegalArgumentException("Firma ist ungültig");
		}
		this.firma = firma;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws IllegalArgumentException {
		if (name == null || name.isEmpty() || name == "") {
			throw new IllegalArgumentException("Name ist ungültig");
		}
		this.name = name;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) throws ParseException,
			IllegalArgumentException {
		if (telefon == null || telefon.isEmpty()) {
			throw new IllegalArgumentException("Telefon ist ungültig");
		}
		this.telefon = telefon;

	}

	public String toString() {
		return "Kontakt-ID: " + kontaktID + "\nFirma: " + firma + "\nName: "
				+ name + "\nTelefon: " + telefon;
	}

}
