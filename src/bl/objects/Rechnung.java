package bl.objects;

import dal.DBEntity;

public abstract class Rechnung extends DBEntity {
	private int rechnungID;
	private String status;

	public Rechnung(int rechnungID, String status) {
		this.rechnungID = rechnungID;
		this.status = status;
	}

	public Object getID() {
		return getRechnungID();
	}

	public int getRechnungID() {
		return rechnungID;
	}

	public void setRechnungID(int rechnungID) {
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
		return "Rechnung-ID: " + rechnungID + "\nStatus: " + status;
	}

}
