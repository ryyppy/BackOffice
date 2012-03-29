package bl.models.armin;

public class Ausgangsrechnung implements Rechnung {
	private int ausgangsrechnungID, kundenID;
	private String status;

	public Ausgangsrechnung(int id,  String status, int kundenID) {
		super();
		this.ausgangsrechnungID = id;
		this.kundenID = kundenID;
		this.status = status;
	}

	public Object[] getRow() {
		Object[] ret = { ausgangsrechnungID,  status , kundenID};
		return ret;
	}

	public int getId() {
		return ausgangsrechnungID;
	}

	public void setId(int id) {
		this.ausgangsrechnungID = id;
	}

	public int getKundenID() {
		return kundenID;
	}

	public void setKundenID(int kundenID) {
		this.kundenID = kundenID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String toString() {
		return ausgangsrechnungID + "\n" + status + "\n" + kundenID;
	}
}
