package bl.objects.view;

import java.text.SimpleDateFormat;
import java.util.Date;

import dal.DBEntity;
import dal.TableMeta;

@TableMeta(pkFieldName = "angebotID")
public class AngebotView extends DBEntity {
	private Integer angebotID;
	private String beschreibung;
	private Double summe;
	private Double dauer;
	private Double chance;
	private Date datum;
	private String kunde;
	private String projekt;

	public AngebotView() {

	}

	public AngebotView(String beschreibung, Double summe, Double dauer,
			Date date, Double chance, String nachname, String name) {
		this.beschreibung = beschreibung;
		this.summe = summe;
		this.dauer = dauer;
		this.datum = date;
		this.chance = chance;
		this.kunde = nachname;
		this.projekt = name;
	}

	public AngebotView(Integer id, Double summe, Double dauer, Date date,
			Double chance, String nachname, String name) {
		this.angebotID = id;
		this.summe = summe;
		this.dauer = dauer;
		this.datum = date;
		this.chance = chance;
		this.kunde = nachname;
		this.projekt = name;
	}

	@Override
	public Object getID() {
		return getAngebotID();
	}

	public Integer getAngebotID() {
		return angebotID;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
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

	public String getKunde() {
		return kunde;
	}

	public void setKunde(String nachname) {
		this.kunde = nachname;
	}

	public String getProjekt() {
		return projekt;
	}

	public void setProjekt(String name) {
		this.projekt = name;
	}

	@Override
	public String toString() {
		return "Angebot [angebotID=" + angebotID + ", summe=" + summe
				+ ", dauer=" + dauer + ", chance=" + chance + ", datum="
				+ datum + ", kunde=" + kunde + ", projekt=" + projekt + "]";
	}

	public String getValues() {
		return "Angebot-ID: " + angebotID + "\nSumme: " + summe + "\nDauer: "
				+ dauer + "\nDatum: " + getDatumString() + "\nChance: "
				+ chance + "\nKunde: " + kunde + "\nProjekt: " + projekt;
	}
}
