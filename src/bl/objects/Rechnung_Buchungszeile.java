package bl.objects;

import dal.DBEntity;
import dal.TableMeta;

@TableMeta(pkFieldName="buchungszeileID")
public class Rechnung_Buchungszeile extends DBEntity {
	private Integer rechnungID;
	private Integer buchungszeileID;

	public Rechnung_Buchungszeile(){
		
	}
	public Rechnung_Buchungszeile(Integer rechnungsID, Integer buchungszeileID) {
		this.rechnungID = rechnungsID;
		this.buchungszeileID = buchungszeileID;
	}

	@Override
	public Object getID() {
		return buchungszeileID;
	}

	public Integer getRechnungID() {
		return rechnungID;
	}

	public void setRechnungID(Integer rechnungsID) {
		this.rechnungID = rechnungsID;
	}

	public Integer getBuchungszeileID() {
		return buchungszeileID;
	}

	public void setBuchungszeileID(Integer buchungszeileID) {
		this.buchungszeileID = buchungszeileID;
	}

}
