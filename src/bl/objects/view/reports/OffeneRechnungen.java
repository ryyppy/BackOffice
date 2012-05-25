package bl.objects.view.reports;

import java.util.Date;

import dal.DBEntity;
import dal.TableMeta;

@TableMeta(pkFieldName = "rechnungid")
public class OffeneRechnungen extends DBEntity {
	private Integer rechnungid;
	private String status;
	private Date datum;
	private String kunde_Kontakt;
	private String art;

	public OffeneRechnungen() {

	}

	public OffeneRechnungen(Integer rechnungid, String status, Date datum,
			String kunde_kontakt, String art) {
		super();
		this.rechnungid = rechnungid;
		this.status = status;
		this.datum = datum;
		this.kunde_Kontakt = kunde_kontakt;
		this.art = art;
	}

	@Override
	public Object getID() {
		return getRechnungid();
	}

	public Integer getRechnungid() {
		return rechnungid;
	}

	public void setRechnungid(Integer rechnungid) {
		this.rechnungid = rechnungid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public String getKunde_Kontakt() {
		return kunde_Kontakt;
	}

	public void setKunde_Kontakt(String kunde_kontakt) {
		this.kunde_Kontakt = kunde_kontakt;
	}

	public String getArt() {
		return art;
	}

	public void setArt(String art) {
		this.art = art;
	}

	@Override
	public String toString() {
		return "OffeneRechnungen [rechnungid=" + rechnungid + ", status="
				+ status + ", datum=" + datum + ", kunde_kontakt="
				+ kunde_Kontakt + ", art=" + art + "]";
	}

}
