package bl.objects.view.reports;

import dal.DBEntity;
import dal.TableMeta;

@TableMeta(pkFieldName = "projektid")
public class Jahresumsatz extends DBEntity {
	private Integer projektid;
	private String projektname;
	private Integer jahr;
	private Long anzahlAngebote;
	private Double summeAngebote;
	private Double avgAngebote;

	public Jahresumsatz() {

	}

	public Jahresumsatz(Integer projektid, String projektname, Integer jahr,
			Long anzahlAngebote, Double summeAngebote, Double avgAngebote) {
		super();
		this.projektid = projektid;
		this.projektname = projektname;
		this.jahr = jahr;
		this.anzahlAngebote = anzahlAngebote;
		this.summeAngebote = summeAngebote;
		this.avgAngebote = avgAngebote;
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

	public String getProjektname() {
		return projektname;
	}

	public void setProjektname(String projektname) {
		this.projektname = projektname;
	}

	public Integer getJahr() {
		return jahr;
	}

	public void setJahr(Integer jahr) {
		this.jahr = jahr;
	}

	public Long getAnzahlAngebote() {
		return anzahlAngebote;
	}

	public void setAnzahlAngebote(Long anzahlAngebote) {
		this.anzahlAngebote = anzahlAngebote;
	}

	public Double getSummeAngebote() {
		return summeAngebote;
	}

	public void setSummeAngebote(Double summeAngebote) {
		this.summeAngebote = summeAngebote;
	}

	public Double getAvgAngebote() {
		return avgAngebote;
	}

	public void setAvgAngebote(Double avgAngebote) {
		this.avgAngebote = avgAngebote;
	}

	@Override
	public String toString() {
		return "Jahresumsatz [projektid=" + projektid + ", projektname="
				+ projektname + ", jahr=" + jahr + ", anzahlAngebote="
				+ anzahlAngebote + ", summeAngebote=" + summeAngebote
				+ ", avgAngebote=" + avgAngebote + "]";
	}

}
