package bl.objects.view;

import java.text.SimpleDateFormat;
import java.util.Date;

import dal.DBEntity;
import dal.TableMeta;

@TableMeta(pkFieldName = "angebotID")
public class AngebotView extends DBEntity {
	private Integer angebotID;
	private Double summe;
	private Double dauer;
	private Double chance;
	private Date datum;
	// KUNDE
	private Integer kundeID;
	private String nachname;
	// PROJEKT
	private Integer projektID;
	private String name;

	public AngebotView() {

	}

	public AngebotView(Double summe, Double dauer, Date date, Double chance,
			Integer kundenID, Integer projektID) {
		this.summe = summe;
		this.dauer = dauer;
		this.datum = date;
		this.chance = chance;
		this.kundeID = kundenID;
		this.projektID = projektID;
	}

	public AngebotView(Integer id, Double summe, Double dauer, Date date,
			Double chance, Integer kundenID, Integer projektID) {
		this.angebotID = id;
		this.summe = summe;
		this.dauer = dauer;
		this.datum = date;
		this.chance = chance;
		this.kundeID = kundenID;
		this.projektID = projektID;
	}

	@Override
	public Object getID() {
		return getAngebotID();
	}

	public Integer getAngebotID() {
		return angebotID;
	}

	public void setAngebotID(Integer angebotID) {
		this.angebotID = angebotID;
	}

	public Double getSumme() {
		return summe;
	}

	public void setSumme(Double summe) {
		this.summe = summe;
	}

	public Double getDauer() {
		return dauer;
	}

	public void setDauer(Double dauer) {
		this.dauer = dauer;
	}

	public Double getChance() {
		return chance;
	}

	public void setChance(Double chance) {
		this.chance = chance;
	}

	public Date getDatum() {
		return datum;
	}

	public String getDatumString() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		return new StringBuilder(dateFormat.format(datum)).toString();
	}

	public void setDatum(Date date) {
		this.datum = date;
	}

	public Integer getKundeID() {
		return kundeID;
	}

	public void setKundeID(Integer kundenID) {
		this.kundeID = kundenID;
	}

	public Integer getProjektID() {
		return projektID;
	}

	public void setProjektID(Integer projektID) {
		this.projektID = projektID;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Angebot [angebotID=" + angebotID + ", summe=" + summe
				+ ", dauer=" + dauer + ", chance=" + chance + ", datum="
				+ datum + ", kundeID=" + kundeID + ", nachname=" + nachname
				+ ", projektID=" + projektID + ", name=" + name + "]";
	}

	public String getValues() {
		return "Angebot-ID: " + angebotID + "\nSumme: " + summe + "\nDauer: "
				+ dauer + "\nDatum: " + getDatumString() + "\nChance: "
				+ chance + "\nKunde: " + kundeID + " - " + nachname
				+ "\nProjekt: " + projektID + " - " + name;
	}
}
