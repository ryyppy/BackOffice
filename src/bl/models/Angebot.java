package bl.models;

public class Angebot {
	private int angebotID, kundenID, projektID;
	private double summe, dauer, chance;
	private String date;

	public Angebot(int id, double summe, double dauer, double chance,
			String date, int kundenID, int projektID) {
		super();
		this.angebotID = id;
		this.summe = summe;
		this.dauer = dauer;
		this.chance = chance;
		this.date = date;
		this.kundenID = kundenID;
		this.projektID = projektID;
	}

	public Object[] getRow() {
		Object[] ret = { angebotID, summe, dauer, chance, date, kundenID, projektID };
		return ret;
	}

	public int getId() {
		return angebotID;
	}

	public void setId(int id) {
		this.angebotID = id;
	}

	public double getSumme() {
		return summe;
	}

	public void setSumme(double summe) {
		this.summe = summe;
	}

	public double getDauer() {
		return dauer;
	}

	public void setDauer(double dauer) {
		this.dauer = dauer;
	}

	public double getChance() {
		return chance;
	}

	public void setChance(double chance) {
		this.chance = chance;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getKundenID() {
		return kundenID;
	}

	public void setKundenID(int kundenID) {
		this.kundenID = kundenID;
	}

	public int getProjektID() {
		return projektID;
	}

	public void setProjektID(int projektID) {
		this.projektID = projektID;
	}
	public String toString() {
		return angebotID + "\n" + kundenID + " " + projektID;
	}
}
