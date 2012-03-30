package bl;

import java.io.InvalidObjectException;
import java.util.ArrayList;

import dal.DALException;

import bl.models.armin.*;

public class BL {
	private ProjektListe projekte;
	private static ArrayList<Projekt> projektliste;
	private static int projektID = 0;
	private KundenListe kunden;
	private static ArrayList<Kunde> kundenliste;
	private static int kundenID = 0;
	private AngebotsListe angebote;
	private AusgangsrechnungenListe ausgangsrechnungen;
	private RechnungszeilenListe rechnungszeilen;

	public BL() {
		projekte = new ProjektListe();
		projektliste= new ArrayList<Projekt>();
		kunden = new KundenListe();
		kundenliste = new ArrayList<Kunde>();
		angebote = new AngebotsListe(this);
		ausgangsrechnungen = new AusgangsrechnungenListe(this);
		rechnungszeilen = new RechnungszeilenListe(this);

	}

	public static ArrayList<Kunde> getKundenListe() throws DALException {
		return kundenliste;
	}

	public static Kunde getKunde(int kundenID) throws DALException {
		for (int i = 0; i < kundenliste.size(); i++) {
			if (kundenliste.get(i).getId() == kundenID) {
				return kundenliste.get(i);
			}
		}
		throw new DALException("Kunden-ID nicht vorhanden");
	}

	public static void deleteKunde(int kundenID) throws DALException {
		Kunde k = getKunde(kundenID);
		if (k != null) {
			kundenliste.remove(k);
		}
	}

	public static void saveKunde(Kunde k) throws DALException,
			InvalidObjectException {
		String exception = "";
		if (k.getVorname() == null || k.getVorname().isEmpty()) {
			exception += "Vorname ist ungültig\n";
		}
		if (k.getNachname() == null || k.getNachname().isEmpty()) {
			exception += "Nachname ist ungültig\n";
		}
		if (k.getGeburtsdatum() == null) {
			exception += "Geburtsdatum ist ungültig\n";
		}
		if (!exception.isEmpty()) {
			throw new InvalidObjectException(exception);
		}

		k.setId(kundenID++);
		kundenliste.add(k);
	}

	public static ArrayList<Projekt> getProjektListe() throws DALException {
		return projektliste;
	}

	public static Projekt getProjekt(int projektID) throws DALException {
		for (int i = 0; i < projektliste.size(); i++) {
			if (projektliste.get(i).getId() == projektID) {
				return projektliste.get(i);
			}
		}
		throw new DALException("Projekt-ID nicht vorhanden");
	}

	public static void deleteProjekt(int projektID) throws DALException {
		Projekt p = getProjekt(projektID);
		if (p != null) {
			projektliste.remove(p);
		}
	}

	public static void saveProjekt(Projekt p) throws DALException,
			InvalidObjectException {
		String exception = "";
		if (p.getName() == null || p.getName().isEmpty()) {
			exception += "Name ist ungültig\n";
		}
		if (p.getBeschreibung() == null || p.getBeschreibung().isEmpty()) {
			exception += "Beschreibung ist ungültig\n";
		}
		if (!exception.isEmpty()) {
			throw new InvalidObjectException(exception);
		}

		p.setId(projektID++);
		projektliste.add(p);
	}

	public ProjektListe getProjektListe2() {
		return projekte;
	}

	public void setProjekte(ProjektListe projekte) {
		this.projekte = projekte;
	}

	public KundenListe getKundenListe2() {
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
