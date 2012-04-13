package bl.objects;

import dal.DBEntity;

public class Rechung_Buchungszeile extends DBEntity {
	private Integer rechnungsID, buchungszeileID;

	public Rechung_Buchungszeile(int rechnungsID, int buchungszeileID) {
		this.rechnungsID = rechnungsID;
		this.buchungszeileID = buchungszeileID;
	}

	@Override
	public Object getID() {
		return null;
	}

	public int getRechnungsID() {
		return rechnungsID;
	}

	public void setRechnungsID(int rechnungsID) {
		this.rechnungsID = rechnungsID;
	}

	public int getBuchungszeileID() {
		return buchungszeileID;
	}

	public void setBuchungszeileID(int buchungszeileID) {
		this.buchungszeileID = buchungszeileID;
	}

}
