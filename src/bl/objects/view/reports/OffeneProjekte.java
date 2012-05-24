package bl.objects.view.reports;

import dal.DBEntity;
import dal.TableMeta;

@TableMeta(pkFieldName = "projektid")
public class OffeneProjekte extends DBEntity {
	private Integer projektid;
	private String name;
	private String beschreibung;
	private Double verbrauchteStunden;

	public OffeneProjekte() {

	}

	public OffeneProjekte(Integer projektid, String name, String beschreibung,
			Double verbrauchteStunden) {
		super();
		this.projektid = projektid;
		this.name = name;
		this.beschreibung = beschreibung;
		this.verbrauchteStunden = verbrauchteStunden;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public Double getVerbrauchteStunden() {
		return verbrauchteStunden;
	}

	public void setVerbrauchteStunden(Double verbrauchteStunden) {
		this.verbrauchteStunden = verbrauchteStunden;
	}

	@Override
	public String toString() {
		return "OffeneProjekte [projektid=" + projektid + ", name=" + name
				+ ", beschreibung=" + beschreibung + ", verbrauchteStunden="
				+ verbrauchteStunden + "]";
	}

}
