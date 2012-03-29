package bl.models;

public class Rechnungszeile {
	private int rechnungszeileID, ausgangsrechnungsID, angebotsID;
	private String kommentar;
	private double preis;

	public Rechnungszeile(int id, String kommentar, double preis,
			int ausgangsrechnungsID, int angebotsID) {
		super();
		this.rechnungszeileID = id;
		this.ausgangsrechnungsID = ausgangsrechnungsID;
		this.angebotsID = angebotsID;
		this.kommentar = kommentar;
		this.preis = preis;
	}

	public Object[] getRow() {
		Object[] ret = { rechnungszeileID, kommentar, preis,
				ausgangsrechnungsID, angebotsID };
		return ret;
	}

	public int getId() {
		return rechnungszeileID;
	}

	public void setId(int id) {
		this.rechnungszeileID = id;
	}

	public int getAusgangsrechnungsID() {
		return ausgangsrechnungsID;
	}

	public void setAusgangsrechnungsID(int ausgangsrechnungsID) {
		this.ausgangsrechnungsID = ausgangsrechnungsID;
	}

	public int getAngebotsID() {
		return angebotsID;
	}

	public void setAngebotsID(int angebotsID) {
		this.angebotsID = angebotsID;
	}

	public String getKommentar() {
		return kommentar;
	}

	public void setKommentar(String kommentar) {
		this.kommentar = kommentar;
	}

	public double getPreis() {
		return preis;
	}

	public void setPreis(double preis) {
		this.preis = preis;
	}

	public String toString() {
		return rechnungszeileID + "\n" + kommentar + "\n"
				+ preis + "\n" + ausgangsrechnungsID + "\n" + angebotsID;
	}
}
