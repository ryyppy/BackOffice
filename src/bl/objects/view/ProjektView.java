package bl.objects.view;

import dal.DBEntity;
import dal.TableMeta;

@TableMeta(pkFieldName = "projektID")
public class ProjektView extends DBEntity {
	private Integer projektID;
	private String name;
	private String beschreibung;

	public ProjektView() {
	}

	public ProjektView(String name, String beschreibung) {
		this.name = name;
		this.beschreibung = beschreibung;
	}

	public ProjektView(Integer id, String name, String beschreibung) {
		this.projektID = id;
		this.name = name;
		this.beschreibung = beschreibung;
	}

	public Integer getProjektID() {
		return projektID;
	}

	public void setProjektID(Integer projektID) {
		this.projektID = projektID;
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
		if (beschreibung == null || beschreibung.isEmpty()) {
			throw new IllegalArgumentException("Beschreibung ist ungültig");
		}
		this.beschreibung = beschreibung;
	}

	@Override
	public String toString() {
		return "Projekt [projektID=" + projektID + ", name=" + name
				+ ", beschreibung=" + beschreibung + "]";
	}

	public String getValues() {
		return "Projekt-ID: " + projektID + "\nName: " + name
				+ "\nBeschreibung: " + beschreibung;
	}
}
