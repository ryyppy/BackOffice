package bl;

public class BL {
	private ProjektListe projekte;
	private KundenListe kunden;
	private AngebotsListe angebote;
	private AusgangsrechnungenListe ausgangsrechnungen;
	private RechnungszeilenListe rechnungszeilen;

	public BL() {
		projekte = new ProjektListe();
		kunden = new KundenListe();
		angebote = new AngebotsListe(this);
		ausgangsrechnungen = new AusgangsrechnungenListe(this);
		rechnungszeilen = new RechnungszeilenListe(this);

	}

	public ProjektListe getProjektListe() {
		return projekte;
	}

	public void setProjekte(ProjektListe projekte) {
		this.projekte = projekte;
	}

	public KundenListe getKundenListe() {
		return kunden;
	}

	public void setKunden(KundenListe kunden) {
		this.kunden = kunden;
	}

	public AngebotsListe getAngebotsListe() {
		return angebote;
	}

	public void setAngebote(AngebotsListe angebote) {
		this.angebote = angebote;
	}

	public AusgangsrechnungenListe getAusgangsrechnungenListe() {
		return ausgangsrechnungen;
	}

	public void setAusgangsrechnungen(AusgangsrechnungenListe ausgangsrechnungen) {
		this.ausgangsrechnungen = ausgangsrechnungen;
	}

	public RechnungszeilenListe getRechnungszeilenListe() {
		return rechnungszeilen;
	}

	public void setRechnungszeilen(RechnungszeilenListe rechnungszeilen) {
		this.rechnungszeilen = rechnungszeilen;
	}

}
