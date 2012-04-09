package bl.objects;

import java.text.SimpleDateFormat;
import java.util.Date;

import dal.DBEntity;

public class Angebot extends DBEntity {
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
		setInhalt(inhalt);
	}

	public void setInhalt(String[] inhalt) throws IllegalArgumentException {
		if (inhalt[0].isEmpty()) {
			throw new IllegalArgumentException("Summe muss festgelegt werden");
		}
		try {
			this.summe = Double.parseDouble(inhalt[0]);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Summe ungültig");
		}
		if (summe < 0) {
			throw new IllegalArgumentException("Summe kann nicht negativ sein");
		}
		if (inhalt[1].isEmpty()) {
			throw new IllegalArgumentException("Dauer muss festgelegt werden");
		}
		try {
			this.dauer = Double.parseDouble(inhalt[1]);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Dauer ungültig");
		}
		if (dauer < 0) {
			throw new IllegalArgumentException("Dauer kann nicht negativ sein");
		}
		this.date = new Date();
		if (inhalt[2].isEmpty()) {
			throw new IllegalArgumentException("Chance muss festgelegt werden");
		}
		try {
			this.chance = Double.parseDouble(inhalt[2]);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Chance ungültig");
		}
		if (chance < 0 || chance > 100) {
			throw new IllegalArgumentException(
					"Chance darf nur zw. 0.0 und 100.0 sein");
		}
		this.kundenID = Integer.valueOf(inhalt[3]);
		this.projektID = Integer.valueOf(inhalt[4]);
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
		return "Angebot-ID: " + angebotID + "\nSumme: " + summe + "\nDauer: "
				+ dauer + "\nDatum: " + getDatumString() + "\nChance: " + chance
				+ "\nKunden-ID: " + kundenID + "\nProjektID: " + projektID;
	}
}
