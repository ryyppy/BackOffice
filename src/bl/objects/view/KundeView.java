package bl.objects.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import dal.DBEntity;
import dal.TableMeta;

@TableMeta(pkFieldName = "kundeID")
public class KundeView extends DBEntity {
	private Integer kundeID;
	private String vorname;
	private String nachname;
	private Date geburtsdatum;

	public KundeView() {

	}

	public KundeView(String vorname, String nachname, Date geburtsdatum) {
		this.vorname = vorname;
		this.nachname = nachname;
		this.geburtsdatum = geburtsdatum;
	}

	public KundeView(Integer id, String vorname, String nachname, Date geburtsdatum) {
		this.kundeID = id;
		this.vorname = vorname;
		this.nachname = nachname;
		this.geburtsdatum = geburtsdatum;
	}

	public Integer getKundeID() {
		return kundeID;
	}

	public void setKundeID(Integer kundenID) {
		this.kundeID = kundenID;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public Date getGeburtsdatum() {
		if (geburtsdatum == null) {
			return null;
		}
		return geburtsdatum;
	}

	public String getGeburtsdatumString() {
		if (geburtsdatum == null) {
			return null;
		}
		return new StringBuilder(
				new SimpleDateFormat("dd.MM.yyyy").format(geburtsdatum))
				.toString();
	}

	public void setGeburtsdatum(Date geburtsdatum) {
		this.geburtsdatum = geburtsdatum;
	}

	public void setGeburtsdatum(String geburtsdatum) throws ParseException {
		this.geburtsdatum = new SimpleDateFormat("dd.MM.yyyy")
				.parse(geburtsdatum);
	}

	@Override
	public String toString() {
		return "Kunde [kundeID=" + kundeID + ", vorname=" + vorname
				+ ", nachname=" + nachname + ", geburtsdatum=" + getGeburtsdatumString()
				+ "]";
	}

	public String getValues() {
		return "Kunden-ID: " + kundeID + "\nName: " + vorname + " " + nachname
				+ "\nGeburtsdatum: " + getGeburtsdatumString();
	}

}
