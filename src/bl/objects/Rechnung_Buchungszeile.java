package bl.objects;

import dal.DBEntity;
import dal.TableMeta;

@TableMeta(pkFieldName="buchungszeileID")
public class Rechnung_Buchungszeile extends DBEntity {
	private Integer rechnungID;
	private Integer buchungszeileID;

	public Rechnung_Buchungszeile(){
		
	}
	public Rechnung_Buchungszeile(int rechnungsID, int buchungszeileID) {
		this.rechnungID = rechnungsID;
		this.buchungszeileID = buchungszeileID;
	}

	@Override
	public Object getID() {
		return buchungszeileID;
	}

	public int getRechnungID() {
		return rechnungID;
	}

	public void setRechnungID(int rechnungsID) {
		this.rechnungID = rechnungsID;
	}

	public int getBuchungszeileID() {
		return buchungszeileID;
	}

	public void setBuchungszeileID(int buchungszeileID) {
		this.buchungszeileID = buchungszeileID;
	}

}
