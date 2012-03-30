package bl.objects.armin;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Angebot {
	private int angebotID, kundenID, projektID;
	private double summe, dauer, chance;
	private Date date;

	public Angebot(int id, double summe, double dauer, Date date,
			double chance, int kundenID, int projektID) {
		super();
		this.angebotID = id;
		this.summe = summe;
		this.dauer = dauer;
		this.date = date;
		this.chance = chance;
		this.kundenID = kundenID;
		this.projektID = projektID;
	}

	/**
	 * 
	 * @param inhalt
	 * @param inhalt
	 *            [0]=summe
	 * @param inhalt
	 *            [1]=dauer
	 * @param inhalt
	 *            [2]=chance
	 * @param inhalt
	 *            [3]=kundenID
	 * @param inhalt
	 *            [4]=projektID
	 */
	public Angebot(String[] inhalt) throws IllegalArgumentException {
		this.angebotID = -1;
		if (inhalt[0].isEmpty()) {
			throw new IllegalArgumentException("Summe muss festgelegt werden");
		}
		this.summe = Double.parseDouble(inhalt[0]);
		if (summe < 0) {
			throw new IllegalArgumentException("Summe kann nicht negativ sein");
		}
		if (inhalt[1].isEmpty()) {
			throw new IllegalArgumentException("Dauer muss festgelegt werden");
		}
		this.dauer = Double.parseDouble(inhalt[1]);
		if (dauer < 0) {
			throw new IllegalArgumentException("Dauer kann nicht negativ sein");
		}
		this.date = new Date();
		if (inhalt[2].isEmpty()) {
			throw new IllegalArgumentException("Chance muss festgelegt werden");
		}
		this.chance = Double.parseDouble(inhalt[2]);
		if (chance < 0 || chance > 100) {
			throw new IllegalArgumentException(
					"Chance darf nur zw. 0.0 und 100.0 sein");
		}
		this.kundenID = Integer.valueOf(inhalt[3]);
		this.projektID = Integer.valueOf(inhalt[4]);
	}

	public Object[] getRow() {
		Object[] ret = { angebotID, summe, dauer, date, chance, kundenID,
				projektID };
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

	public Date getDate() {
		return date;
	}

	public String getDatumString() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		return new StringBuilder(dateFormat.format(date)).toString();
	}

	public void setDate(Date date) {
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
		return angebotID + "\n" + summe + "\n" + dauer + "\n"
				+ getDatumString() + "\n" + chance + "\n" + kundenID + "\n"
				+ projektID;
	}
}
