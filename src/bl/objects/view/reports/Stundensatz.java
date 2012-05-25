package bl.objects.view.reports;

import dal.DBEntity;
import dal.TableMeta;

@TableMeta(pkFieldName = "angebotid")
public class Stundensatz extends DBEntity {
	private Integer projektid;
	private String projekt;
	private Integer angebotid;
	private String angebot;
	private Double dauer;
	private Double summe;
	private Double stundensatz;

	public Stundensatz() {

	}

	public Stundensatz(Integer projektid, String projekt, Integer angebotid,
			String angebot, Double dauer, Double summe, Double stundensatz) {
		super();
		this.projektid = projektid;
		this.projekt = projekt;
		this.angebotid = angebotid;
		this.angebot = angebot;
		this.dauer = dauer;
		this.summe = summe;
		this.stundensatz = stundensatz;
	}

	@Override
	public Object getID() {
		return getProjektid();
	}

	public Integer getProjektid() {
		return projektid;
	}

	public void setProjektid(Integer projektid) {
		this.projektid = projektid;
	}

	public String getProjekt() {
		return projekt;
	}

	public void setProjekt(String projekt) {
		this.projekt = projekt;
	}

	public Integer getAngebotid() {
		return angebotid;
	}

	public void setAngebotid(Integer angebotid) {
		this.angebotid = angebotid;
	}

	public String getAngebot() {
		return angebot;
	}

	public void setAngebot(String angebot) {
		this.angebot = angebot;
	}

	public Double getDauer() {
		return dauer;
	}

	public void setDauer(Double dauer) {
		this.dauer = dauer;
	}

	public Double getSumme() {
		return summe;
	}

	public void setSumme(Double summe) {
		this.summe = summe;
	}

	public Double getStundensatz() {
		return stundensatz;
	}

	public void setStundensatz(Double stundensatz) {
		this.stundensatz = stundensatz;
	}

	@Override
	public String toString() {
		return "Stundensatz [projektid=" + projektid + ", projekt=" + projekt
				+ ", angebotid=" + angebotid + ", angebot=" + angebot
				+ ", dauer=" + dauer + ", summe=" + summe + ", stundensatz="
				+ stundensatz + "]";
	}

}
