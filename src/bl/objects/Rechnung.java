package bl.objects;

import dal.DBEntity;

public abstract class Rechnung extends DBEntity{
	private int rechnungID;
	private String status;

	public Rechnung(int rechnungID, String status) {
		this.rechnungID = rechnungID;
		this.status = status;
	}

	public int getId() {
		return rechnungID;
	}

	public void setId(int rechnungID) {
		this.rechnungID = rechnungID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ID: " + rechnungID + "\nStatus: " + status;
	}

}
