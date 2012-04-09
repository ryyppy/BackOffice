package bl;

import java.io.InvalidObjectException;
import java.util.ArrayList;

import bl.objects.Angebot;
import bl.objects.Ausgangsrechnung;
import bl.objects.Buchungszeile;
import bl.objects.Eingangsrechnung;
import bl.objects.Kategorie;
import bl.objects.Kontakt;
import bl.objects.Kunde;
import bl.objects.Projekt;
import bl.objects.Rechnungszeile;
import dal.DALException;

public class BL {
	private static ArrayList<Projekt> projektliste = new ArrayList<Projekt>();
	private static int projektID = 0;
	private static ArrayList<Kunde> kundenliste = new ArrayList<Kunde>();
	private static int kundenID = 0;
	private static ArrayList<Kontakt> kontakteliste = new ArrayList<Kontakt>();
	private static int kontaktID = 0;
	private static ArrayList<Angebot> angebotsliste = new ArrayList<Angebot>();
	private static int angebotID = 0;
	private static ArrayList<Ausgangsrechnung> ausgangsrechnungenliste = new ArrayList<Ausgangsrechnung>();
	private static ArrayList<Eingangsrechnung> eingangsrechnungenliste = new ArrayList<Eingangsrechnung>();
	private static int rechnungID = 0;
	private static ArrayList<Rechnungszeile> rechnungszeilenliste = new ArrayList<Rechnungszeile>();
	private static int rechnungszeileID = 0;
	private static ArrayList<Buchungszeile> buchungszeilenliste = new ArrayList<Buchungszeile>();
	private static int buchungszeileID = 0;
	private static ArrayList<Kategorie> kategorieliste = new ArrayList<Kategorie>();
	private static int kategorieID = 0;

	public BL() {
		projektliste = new ArrayList<Projekt>();
		kundenliste = new ArrayList<Kunde>();
		kontakteliste = new ArrayList<Kontakt>();
		angebotsliste = new ArrayList<Angebot>();
		ausgangsrechnungenliste = new ArrayList<Ausgangsrechnung>();
		eingangsrechnungenliste = new ArrayList<Eingangsrechnung>();
		rechnungszeilenliste = new ArrayList<Rechnungszeile>();
		buchungszeilenliste = new ArrayList<Buchungszeile>();
		kategorieliste = new ArrayList<Kategorie>();
	}

	public static ArrayList<Kontakt> getKontaktListe() throws DALException {
		return kontakteliste;
	}

	public static Kontakt getKontakt(int kontaktID) throws DALException {
		for (int i = 0; i < kontakteliste.size(); i++) {
			if (kontakteliste.get(i).getId() == kontaktID) {
				return kontakteliste.get(i);
			}
		}
		throw new DALException("Kunden-ID nicht vorhanden");
	}

	public static void deleteKontakt(int kontaktID) throws DALException {
		Kontakt k = getKontakt(kontaktID);
		if (k != null) {
			kontakteliste.remove(k);
		}
	}

	public static void saveKontakt(Kontakt k) throws DALException,
			InvalidObjectException {
		String exception = "";
		// ...
		if (!exception.isEmpty()) {
			throw new InvalidObjectException(exception);
		}

		k.setId(kontaktID++);
		kontakteliste.add(k);
	}

	public static void updateKontakt(Kontakt k) throws DALException,
			InvalidObjectException {
		String exception = "";
		// ... überprüfen
		if (!exception.isEmpty()) {
			throw new InvalidObjectException(exception);
		}

		for (Kontakt kontakt : kontakteliste) {
			if (kontakt.getId() == k.getId()) {
				kontakt = k;
			}
		}
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
		// ...
		if (!exception.isEmpty()) {
			throw new InvalidObjectException(exception);
		}

		k.setId(kundenID++);
		kundenliste.add(k);
	}

	public static void updateKunde(Kunde k) throws DALException,
			InvalidObjectException {
		String exception = "";
		// ... überprüfen
		if (!exception.isEmpty()) {
			throw new InvalidObjectException(exception);
		}

		for (Kunde kunde : kundenliste) {
			if (kunde.getId() == k.getId()) {
				kunde = k;
			}
		}
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
		// ...
		if (!exception.isEmpty()) {
			throw new InvalidObjectException(exception);
		}

		p.setId(projektID++);
		projektliste.add(p);
	}

	public static void updateProjekt(Projekt p) throws DALException,
			InvalidObjectException {
		String exception = "";
		// ... überprüfen
		if (!exception.isEmpty()) {
			throw new InvalidObjectException(exception);
		}

		for (Projekt projekt : projektliste) {
			if (projekt.getId() == p.getId()) {
				projekt = p;
			}
		}
	}

	public static ArrayList<Angebot> getAngebotsListe() throws DALException {
		return angebotsliste;
	}

	public static ArrayList<Angebot> getAngebotsListe(int kundenID)
			throws DALException {
		ArrayList<Angebot> ret = new ArrayList<Angebot>();
		for (Angebot a : angebotsliste) {
			if (a.getKundenID() == kundenID) {
				ret.add(a);
			}
		}
		return ret;
	}

	public static Angebot getAngebot(int angebotID) throws DALException {
		for (int i = 0; i < angebotsliste.size(); i++) {
			if (angebotsliste.get(i).getId() == angebotID) {
				return angebotsliste.get(i);
			}
		}
		throw new DALException("Angebot-ID nicht vorhanden");
	}

	public static void deleteAngebot(int angebotID) throws DALException {
		Angebot a = getAngebot(angebotID);
		if (a != null) {
			projektliste.remove(a);
		}
	}

	public static void saveAngebot(Angebot a) throws DALException,
			InvalidObjectException {
		String exception = "";
		// ... Kunden bzw Projekt-ID überprüfen
		if (!exception.isEmpty()) {
			throw new InvalidObjectException(exception);
		}

		a.setId(angebotID++);
		angebotsliste.add(a);
	}

	public static void updateAngebot(Angebot a) throws DALException,
			InvalidObjectException {
		String exception = "";
		// ... Kunden-ID und Projekt-ID überprüfen
		if (!exception.isEmpty()) {
			throw new InvalidObjectException(exception);
		}

		for (Angebot angebot : angebotsliste) {
			if (angebot.getId() == a.getId()) {
				angebot = a;
			}
		}
	}

	public static ArrayList<Ausgangsrechnung> getAusgangsrechnungenListe()
			throws DALException {
		return ausgangsrechnungenliste;
	}

	public static Eingangsrechnung getEingangsrechnung(int rechnungID)
			throws DALException {
		for (int i = 0; i < eingangsrechnungenliste.size(); i++) {
			if (eingangsrechnungenliste.get(i).getId() == rechnungID) {
				return eingangsrechnungenliste.get(i);
			}
		}
		throw new DALException("Rechnung-ID nicht vorhanden");
	}

	public static void deleteEingangsrechnung(int rechnungID)
			throws DALException {
		Eingangsrechnung a = getEingangsrechnung(rechnungID);
		if (a != null) {
			eingangsrechnungenliste.remove(a);
		}
	}

	public static Ausgangsrechnung getAusgangsrechnung(int rechnungID)
			throws DALException {
		for (int i = 0; i < ausgangsrechnungenliste.size(); i++) {
			if (ausgangsrechnungenliste.get(i).getId() == rechnungID) {
				return ausgangsrechnungenliste.get(i);
			}
		}
		throw new DALException("Rechnung-ID nicht vorhanden");
	}

	public static void deleteAusgangsrechnung(int rechnungID)
			throws DALException {
		Ausgangsrechnung a = getAusgangsrechnung(rechnungID);
		if (a != null) {
			ausgangsrechnungenliste.remove(a);
		}
	}

	public static void saveAusgangsrechnung(Ausgangsrechnung a)
			throws DALException, InvalidObjectException {
		String exception = "";
		// ... Kunden-ID überprüfen
		if (!exception.isEmpty()) {
			throw new InvalidObjectException(exception);
		}

		a.setId(rechnungID++);
		ausgangsrechnungenliste.add(a);
	}

	public static void updateAusgangsrechnung(Ausgangsrechnung a)
			throws DALException, InvalidObjectException {
		String exception = "";
		// ... Kunden-ID überprüfen
		if (!exception.isEmpty()) {
			throw new InvalidObjectException(exception);
		}

		for (Ausgangsrechnung ar : ausgangsrechnungenliste) {
			if (ar.getId() == a.getId()) {
				ar = a;
			}
		}
	}

	public static ArrayList<Eingangsrechnung> getEingangsrechnungenListe()
			throws DALException {
		return eingangsrechnungenliste;
	}

	public static void saveEingangsrechnung(Eingangsrechnung e)
			throws DALException, InvalidObjectException {
		String exception = "";
		// ... Kontakt-ID überprüfen
		if (!exception.isEmpty()) {
			throw new InvalidObjectException(exception);
		}

		e.setId(rechnungID++);
		eingangsrechnungenliste.add(e);
	}

	public static ArrayList<Rechnungszeile> getRechnungszeilenListe()
			throws DALException {
		return rechnungszeilenliste;
	}

	public static ArrayList<Rechnungszeile> getRechnungszeilenListe(
			int rechnungID) throws DALException {
		ArrayList<Rechnungszeile> ret = new ArrayList<Rechnungszeile>();
		for (Rechnungszeile r : rechnungszeilenliste) {
			if (r.getRechnungID() == rechnungID) {
				ret.add(r);
			}
		}
		return ret;
	}

	public static Rechnungszeile getRechnungszeile(int rechnungszeileID)
			throws DALException {
		for (int i = 0; i < rechnungszeilenliste.size(); i++) {
			if (rechnungszeilenliste.get(i).getId() == rechnungszeileID) {
				return rechnungszeilenliste.get(i);
			}
		}
		throw new DALException("Rechnungszeile-ID nicht vorhanden");
	}

	public static void deleteRechnungszeile(int rechnungszeileID)
			throws DALException {
		Rechnungszeile r = getRechnungszeile(rechnungszeileID);
		if (r != null) {
			rechnungszeilenliste.remove(r);
		}
	}

	public static void saveRechnungszeile(Rechnungszeile r)
			throws DALException, InvalidObjectException {
		String exception = "";
		// ... rechnung bzw angebot-ID überprüfen
		if (!exception.isEmpty()) {
			throw new InvalidObjectException(exception);
		}

		r.setId(rechnungszeileID++);
		rechnungszeilenliste.add(r);
	}

	public static ArrayList<Buchungszeile> getBuchungszeilenListe()
			throws DALException {
		return buchungszeilenliste;
	}

	public static void saveBuchungszeile(Buchungszeile b) throws DALException,
			InvalidObjectException {
		String exception = "";
		// ... kategorie-ID überprüfen
		if (!exception.isEmpty()) {
			throw new InvalidObjectException(exception);
		}

		b.setId(buchungszeileID++);
		buchungszeilenliste.add(b);
	}

	public static Buchungszeile getBuchungszeile(int buchungszeileID)
			throws DALException {
		for (int i = 0; i < buchungszeilenliste.size(); i++) {
			if (buchungszeilenliste.get(i).getId() == buchungszeileID) {
				return buchungszeilenliste.get(i);
			}
		}
		throw new DALException("Buchungszeile-ID nicht vorhanden");
	}

	public static void deleteBuchungszeile(int buchungszeileID)
			throws DALException {
		Buchungszeile r = getBuchungszeile(buchungszeileID);
		if (r != null) {
			buchungszeilenliste.remove(r);
		}
	}

	public static ArrayList<Kategorie> getKategorieListe() throws DALException {
		return kategorieliste;
	}

	public static void saveKategorie(Kategorie k) throws DALException,
			InvalidObjectException {
		String exception = "";
		// ... überprüfen ob kategorie vorhanden
		if (!exception.isEmpty()) {
			throw new InvalidObjectException(exception);
		}

		k.setId(kategorieID++);
		kategorieliste.add(k);
	}

	public static Kategorie getKategorie(int kategorieID) throws DALException {
		for (int i = 0; i < kategorieliste.size(); i++) {
			if (kategorieliste.get(i).getId() == kategorieID) {
				return kategorieliste.get(i);
			}
		}
		throw new DALException("Kategorie-ID nicht vorhanden");
	}
}
