package bl;

import java.io.InvalidObjectException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import bl.objects.Angebot;
import bl.objects.Ausgangsrechnung;
import bl.objects.Buchungszeile;
import bl.objects.Eingangsrechnung;
import bl.objects.Kategorie;
import bl.objects.Kontakt;
import bl.objects.Kunde;
import bl.objects.Projekt;
import bl.objects.Rechnung;
import bl.objects.Rechnungszeile;
import bl.objects.Rechnung_Buchungszeile;
import dal.DALException;
import dal.DatabaseAdapter;
import dal.MysqlAdapter;
import dal.WhereChain;
import dal.WhereChain.Chainer;
import dal.WhereOperator;

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
	private static ArrayList<Rechnung_Buchungszeile> rechnungen_buchungszeilen = new ArrayList<Rechnung_Buchungszeile>();

	private static DatabaseAdapter mysql = new MysqlAdapter("root", "dbsy",
			"localhost/swe");

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
		mysql.connect();
		ArrayList<Kontakt> ret = (ArrayList<Kontakt>) mysql
				.getEntityList(Kontakt.class);
		mysql.disconnect();
		return ret;
		// return kontakteliste;
	}

	public static ArrayList<Kontakt> getKontaktListe(String filter)
			throws DALException {
		String f = "%" + filter + "%";
		WhereChain where = new WhereChain(Kontakt.class, WhereOperator.LIKE, f,
				Chainer.OR);
		mysql.connect();
		ArrayList<Kontakt> ret = (ArrayList<Kontakt>) mysql.getEntitiesBy(
				where, Kontakt.class);
		mysql.disconnect();
		return ret;
		// return kontakteliste;
	}

	public static Integer containsKontakt(Kontakt k) throws DALException {
		WhereChain where = new WhereChain("firma", WhereOperator.EQUALS,
				k.getFirma());
		where.addAndCondition("name", WhereOperator.EQUALS, k.getName());
		where.addAndCondition("telefon", WhereOperator.EQUALS, k.getTelefon());
		mysql.connect();
		ArrayList<Kontakt> ret = (ArrayList<Kontakt>) mysql.getEntitiesBy(
				where, Kontakt.class);
		mysql.disconnect();
		if (ret.isEmpty()) {
			return -1;
		}
		return ret.get(0).getKontaktID();
	}

	public static Kontakt getKontakt(int kontaktID) throws DALException {
		// for (int i = 0; i < kontakteliste.size(); i++) {
		// if (kontakteliste.get(i).getKontaktID() == kontaktID) {
		// return kontakteliste.get(i);
		// }
		// }
		// throw new DALException("Kunden-ID nicht vorhanden");
		mysql.connect();
		Kontakt k = mysql.getEntityByID(kontaktID, Kontakt.class);
		mysql.disconnect();
		return k;
	}

	public static void deleteKontakt(int kontaktID) throws DALException {
		// Kontakt k = getKontakt(kontaktID);
		// if (k != null) {
		// kontakteliste.remove(k);
		// }
		mysql.connect();
		mysql.deleteEntity(kontaktID, Kontakt.class);
		mysql.disconnect();
	}

	public static Integer saveKontakt(Kontakt k) throws DALException,
			InvalidObjectException {
		// String exception = "";
		// // ...
		// if (!exception.isEmpty()) {
		// throw new InvalidObjectException(exception);
		// }
		//
		// k.setKontaktID(kontaktID++);
		// kontakteliste.add(k);
		mysql.connect();
		Object key = mysql.addEntity(k);
		mysql.disconnect();
		
		return Integer.valueOf(String.valueOf(key));
	}

	public static void updateKontakt(Kontakt k) throws DALException,
			InvalidObjectException {
		// String exception = "";
		// // ... überprüfen
		// if (!exception.isEmpty()) {
		// throw new InvalidObjectException(exception);
		// }
		//
		// for (Kontakt kontakt : kontakteliste) {
		// if (kontakt.getID() == k.getID()) {
		// kontakt = k;
		// }
		// }
		mysql.connect();
		mysql.updateEntity(k);
		mysql.disconnect();
	}

	public static ArrayList<Kunde> getKundeListe() throws DALException {
		// return kundenliste;
		mysql.connect();
		ArrayList<Kunde> ret = (ArrayList<Kunde>) mysql
				.getEntityList(Kunde.class);
		mysql.disconnect();
		return ret;
	}

	public static ArrayList<Kunde> getKundeListe(String filter)
			throws DALException {
		// return kundenliste;
		String f = "%" + filter + "%";
		WhereChain where = new WhereChain(Kunde.class, WhereOperator.LIKE, f,
				Chainer.OR);
		mysql.connect();
		ArrayList<Kunde> ret = (ArrayList<Kunde>) mysql.getEntitiesBy(where,
				Kunde.class);
		mysql.disconnect();
		return ret;
	}

	public static Kunde getKunde(int kundenID) throws DALException {
		// for (int i = 0; i < kundenliste.size(); i++) {
		// if (kundenliste.get(i).getKundenID() == kundenID) {
		// return kundenliste.get(i);
		// }
		// }
		// throw new DALException("Kunden-ID nicht vorhanden");
		mysql.connect();
		Kunde k = mysql.getEntityByID(kundenID, Kunde.class);
		mysql.disconnect();
		return k;
	}

	public static void deleteKunde(int kundenID) throws DALException {
		// Kunde k = getKunde(kundenID);
		// if (k != null) {
		// kundenliste.remove(k);
		// }
		mysql.connect();
		mysql.deleteEntity(kundenID, Kunde.class);
		mysql.disconnect();
	}

	public static void saveKunde(Kunde k) throws DALException,
			InvalidObjectException {
		// String exception = "";
		// // ...
		// if (!exception.isEmpty()) {
		// throw new InvalidObjectException(exception);
		// }
		//
		// k.setKundenID(kundenID++);
		// kundenliste.add(k);
		mysql.connect();
		mysql.addEntity(k);
		mysql.disconnect();
	}

	public static void updateKunde(Kunde k) throws DALException,
			InvalidObjectException {
		// String exception = "";
		// // ... überprüfen
		// if (!exception.isEmpty()) {
		// throw new InvalidObjectException(exception);
		// }
		//
		// for (Kunde kunde : kundenliste) {
		// if (kunde.getKundenID() == k.getKundenID()) {
		// kunde = k;
		// }
		// }
		mysql.connect();
		mysql.updateEntity(k);
		mysql.disconnect();
	}

	public static ArrayList<Projekt> getProjektListe() throws DALException {
		// return projektliste;
		mysql.connect();
		ArrayList<Projekt> ret = (ArrayList<Projekt>) mysql
				.getEntityList(Projekt.class);
		mysql.disconnect();
		return ret;
	}

	public static ArrayList<Projekt> getProjektListe(String filter)
			throws DALException {
		// return projektliste;
		String f = "%" + filter + "%";
		WhereChain where = new WhereChain(Projekt.class, WhereOperator.LIKE, f,
				Chainer.OR);
		mysql.connect();
		ArrayList<Projekt> ret = (ArrayList<Projekt>) mysql.getEntitiesBy(
				where, Projekt.class);
		mysql.disconnect();
		return ret;
	}

	public static Projekt getProjekt(int projektID) throws DALException {
		// for (int i = 0; i < projektliste.size(); i++) {
		// if (projektliste.get(i).getProjektID() == projektID) {
		// return projektliste.get(i);
		// }
		// }
		// throw new DALException("Projekt-ID nicht vorhanden");
		mysql.connect();
		Projekt p = mysql.getEntityByID(projektID, Projekt.class);
		mysql.disconnect();
		return p;
	}

	public static void deleteProjekt(int projektID) throws DALException {
		// Projekt p = getProjekt(projektID);
		// if (p != null) {
		// projektliste.remove(p);
		// }
		mysql.connect();
		mysql.deleteEntity(projektID, Projekt.class);
		mysql.disconnect();
	}

	public static void saveProjekt(Projekt p) throws DALException,
			InvalidObjectException {
		// String exception = "";
		// // ...
		// if (!exception.isEmpty()) {
		// throw new InvalidObjectException(exception);
		// }
		//
		// p.setProjektID(projektID++);
		// projektliste.add(p);
		mysql.connect();
		mysql.addEntity(p);
		mysql.disconnect();
	}

	public static void updateProjekt(Projekt p) throws DALException,
			InvalidObjectException {
		// String exception = "";
		// // ... überprüfen
		// if (!exception.isEmpty()) {
		// throw new InvalidObjectException(exception);
		// }
		//
		// for (Projekt projekt : projektliste) {
		// if (projekt.getProjektID() == p.getProjektID()) {
		// projekt = p;
		// }
		// }
		mysql.connect();
		mysql.updateEntity(p);
		mysql.disconnect();
	}

	public static ArrayList<Angebot> getAngebotListe() throws DALException {
		// return angebotsliste;
		mysql.connect();
		ArrayList<Angebot> ret = (ArrayList<Angebot>) mysql
				.getEntityList(Angebot.class);
		mysql.disconnect();
		return ret;
	}

	public static ArrayList<Angebot> getAngebotListe(String filter)
			throws DALException {
		// return angebotsliste;
		String f = "%" + filter + "%";
		WhereChain where = new WhereChain(Angebot.class, WhereOperator.LIKE, f,
				Chainer.OR);
		mysql.connect();
		ArrayList<Angebot> ret = (ArrayList<Angebot>) mysql.getEntitiesBy(
				where, Angebot.class);
		mysql.disconnect();
		return ret;
	}

	public static ArrayList<Angebot> getAngebotListe(int kundeID)
			throws DALException {
		// ArrayList<Angebot> ret = new ArrayList<Angebot>();
		// for (Angebot a : angebotsliste) {
		// if (a.getKundenID() == kundenID) {
		// ret.add(a);
		// }
		// }
		// return ret;
		WhereChain where = new WhereChain("kundeID", WhereOperator.EQUALS,
				kundeID);
		mysql.connect();
		ArrayList<Angebot> ret = (ArrayList<Angebot>) mysql.getEntitiesBy(
				where, Angebot.class);
		mysql.disconnect();
		return ret;

	}

	public static Angebot getAngebot(int angebotID) throws DALException {
		// for (int i = 0; i < angebotsliste.size(); i++) {
		// if (angebotsliste.get(i).getAngebotID() == angebotID) {
		// return angebotsliste.get(i);
		// }
		// }
		// throw new DALException("Angebot-ID nicht vorhanden");
		mysql.connect();
		Angebot a = mysql.getEntityByID(angebotID, Angebot.class);
		mysql.disconnect();
		return a;
	}

	public static void deleteAngebot(int angebotID) throws DALException {
		// Angebot a = getAngebot(angebotID);
		// if (a != null) {
		// projektliste.remove(a);
		// }
		mysql.connect();
		mysql.deleteEntity(angebotID, Angebot.class);
		mysql.disconnect();
	}

	public static void saveAngebot(Angebot a) throws DALException,
			InvalidObjectException {
		// String exception = "";
		// // ... Kunden bzw Projekt-ID überprüfen
		// if (!exception.isEmpty()) {
		// throw new InvalidObjectException(exception);
		// }
		//
		// a.setAngebotID(angebotID++);
		// angebotsliste.add(a);
		mysql.connect();
		mysql.addEntity(a);
		mysql.disconnect();
	}

	public static void updateAngebot(Angebot a) throws DALException,
			InvalidObjectException {
		// String exception = "";
		// // ... Kunden-ID und Projekt-ID überprüfen
		// if (!exception.isEmpty()) {
		// throw new InvalidObjectException(exception);
		// }
		//
		// for (Angebot angebot : angebotsliste) {
		// if (angebot.getAngebotID() == a.getAngebotID()) {
		// angebot = a;
		// }
		// }
		mysql.connect();
		mysql.updateEntity(a);
		mysql.disconnect();
	}

	public static Eingangsrechnung getEingangsrechnung(int rechnungID)
			throws DALException {
		// for (int i = 0; i < eingangsrechnungenliste.size(); i++) {
		// if (eingangsrechnungenliste.get(i).getRechnungID() == rechnungID) {
		// return eingangsrechnungenliste.get(i);
		// }
		// }
		// throw new DALException("Rechnung-ID nicht vorhanden");
		mysql.connect();
		Eingangsrechnung ret = mysql.getEntityByID(rechnungID,
				Eingangsrechnung.class);
		mysql.disconnect();
		return ret;
	}

	public static void deleteEingangsrechnung(int rechnungID)
			throws DALException {
		// Eingangsrechnung a = getEingangsrechnung(rechnungID);
		// if (a != null) {
		// eingangsrechnungenliste.remove(a);
		// }
		mysql.connect();
		mysql.deleteEntity(rechnungID, Rechnung.class);
		mysql.disconnect();
	}

	public static ArrayList<Eingangsrechnung> getEingangsrechnungListe()
			throws DALException {
		// return eingangsrechnungenliste;
		mysql.connect();
		ArrayList<Eingangsrechnung> ret = (ArrayList<Eingangsrechnung>) mysql
				.getEntityList(Eingangsrechnung.class);
		mysql.disconnect();
		return ret;
	}

	public static ArrayList<Eingangsrechnung> getEingangsrechnungListe(
			String filter) throws DALException {
		// return eingangsrechnungenliste;
		String f = "%" + filter + "%";
		WhereChain where = new WhereChain(Eingangsrechnung.class,
				WhereOperator.LIKE, f, Chainer.OR);
		mysql.connect();
		ArrayList<Eingangsrechnung> ret = (ArrayList<Eingangsrechnung>) mysql
				.getEntitiesBy(where, Eingangsrechnung.class);
		mysql.disconnect();
		return ret;
	}

	public static Integer saveEingangsrechnung(Eingangsrechnung e)
			throws DALException, InvalidObjectException {
		// String exception = "";
		// // ... Kontakt-ID überprüfen
		// if (!exception.isEmpty()) {
		// throw new InvalidObjectException(exception);
		// }
		//
		// e.setRechnungID(rechnungID++);
		// eingangsrechnungenliste.add(e);
		mysql.connect();
		mysql.beginTransaction();
		try {
			Rechnung r = new Rechnung(e.getStatus(), e.getDatum());
			Object key = mysql.addEntity(r);
			e.setRechnungID(Integer.valueOf(String.valueOf(key)));
			mysql.addEntity(e);
			mysql.commit();
		} catch (DALException d) {
			mysql.rollback();
			throw d;
		}
		mysql.disconnect();
		return e.getRechnungID();
	}

	public static void updateEingangsrechnung(Eingangsrechnung e)
			throws DALException, InvalidObjectException {
		// String exception = "";
		// // ... Kontakt-ID überprüfen
		// if (!exception.isEmpty()) {
		// throw new InvalidObjectException(exception);
		// }
		//
		// for (Eingangsrechnung er : eingangsrechnungenliste) {
		// if (er.getRechnungID() == e.getRechnungID()) {
		// er = e;
		// }
		// }
		mysql.connect();
		Rechnung r = new Rechnung(e.getRechnungID(), e.getStatus(),
				e.getDatum());
		mysql.updateEntity(r);
		mysql.updateEntity(e);
		mysql.disconnect();
	}

	public static ArrayList<Ausgangsrechnung> getAusgangsrechnungListe()
			throws DALException {
		// return ausgangsrechnungenliste;
		mysql.connect();
		ArrayList<Ausgangsrechnung> ret = (ArrayList<Ausgangsrechnung>) mysql
				.getEntityList(Ausgangsrechnung.class);
		mysql.disconnect();
		return ret;
	}

	public static ArrayList<Ausgangsrechnung> getAusgangsrechnungListe(
			String filter) throws DALException {
		// return ausgangsrechnungenliste;
		String f = "%" + filter + "%";
		WhereChain where = new WhereChain(Ausgangsrechnung.class,
				WhereOperator.LIKE, f, Chainer.OR);
		mysql.connect();
		ArrayList<Ausgangsrechnung> ret = (ArrayList<Ausgangsrechnung>) mysql
				.getEntitiesBy(where, Ausgangsrechnung.class);
		mysql.disconnect();
		return ret;
	}

	public static Ausgangsrechnung getAusgangsrechnung(int rechnungID)
			throws DALException {
		// for (int i = 0; i < ausgangsrechnungenliste.size(); i++) {
		// if (ausgangsrechnungenliste.get(i).getRechnungID() == rechnungID) {
		// return ausgangsrechnungenliste.get(i);
		// }
		// }
		// throw new DALException("Rechnung-ID nicht vorhanden");
		mysql.connect();
		Ausgangsrechnung ret = mysql.getEntityByID(rechnungID,
				Ausgangsrechnung.class);
		mysql.disconnect();
		return ret;
	}

	public static void deleteAusgangsrechnung(int rechnungID)
			throws DALException {
		// Ausgangsrechnung a = getAusgangsrechnung(rechnungID);
		// if (a != null) {
		// ausgangsrechnungenliste.remove(a);
		// }
		mysql.connect();
		mysql.deleteEntity(rechnungID, Rechnung.class);
		mysql.disconnect();
	}

	public static void saveAusgangsrechnung(Ausgangsrechnung a)
			throws DALException, InvalidObjectException {
		// String exception = "";
		// // ... Kunden-ID überprüfen
		// if (!exception.isEmpty()) {
		// throw new InvalidObjectException(exception);
		// }
		//
		// a.setRechnungID(rechnungID++);
		// ausgangsrechnungenliste.add(a);

		mysql.connect();
		mysql.beginTransaction();
		try {
			Rechnung r = new Rechnung(a.getStatus(), a.getDatum());
			Object key = mysql.addEntity(r);
			a.setRechnungID(Integer.valueOf(String.valueOf(key)));
			mysql.addEntity(a);
			mysql.commit();
		} catch (DALException d) {
			mysql.rollback();
			throw d;
		}
		mysql.disconnect();
	}

	public static void updateAusgangsrechnung(Ausgangsrechnung a)
			throws DALException, InvalidObjectException {
		// String exception = "";
		// // ... Kunden-ID überprüfen
		// if (!exception.isEmpty()) {
		// throw new InvalidObjectException(exception);
		// }
		//
		// for (Ausgangsrechnung ar : ausgangsrechnungenliste) {
		// if (ar.getRechnungID() == a.getRechnungID()) {
		// ar = a;
		// }
		// }
		mysql.connect();
		Rechnung r = new Rechnung(a.getRechnungID(), a.getStatus(),
				a.getDatum());
		mysql.updateEntity(r);
		mysql.updateEntity(a);
		mysql.disconnect();

	}

	public static ArrayList<Rechnung> getRechnungListe() throws DALException {
		// ArrayList<Rechnung> ar = new ArrayList<Rechnung>();
		// ar.addAll(ausgangsrechnungenliste);
		// ar.addAll(eingangsrechnungenliste);
		// return ar;

		mysql.connect();
		ArrayList<Ausgangsrechnung> al = (ArrayList<Ausgangsrechnung>) mysql
				.getEntityList(Ausgangsrechnung.class);
		ArrayList<Eingangsrechnung> el = (ArrayList<Eingangsrechnung>) mysql
				.getEntityList(Eingangsrechnung.class);

		ArrayList<Rechnung> ar = new ArrayList<Rechnung>();
		ar.addAll(al);
		ar.addAll(el);
		return ar;
	}

	public static ArrayList<Rechnungszeile> getRechnungszeileListe()
			throws DALException {
		// return rechnungszeilenliste;
		mysql.connect();
		ArrayList<Rechnungszeile> ret = (ArrayList<Rechnungszeile>) mysql
				.getEntityList(Rechnungszeile.class);
		mysql.disconnect();
		return ret;
	}

	public static ArrayList<Rechnungszeile> getRechnungszeileListe(
			int rechnungID) throws DALException {
		// ArrayList<Rechnungszeile> ret = new ArrayList<Rechnungszeile>();
		// for (Rechnungszeile r : rechnungszeilenliste) {
		// if (r.getRechnungID() == rechnungID) {
		// ret.add(r);
		// }
		// }
		// return ret;
		WhereChain where = new WhereChain("rechnungID", WhereOperator.EQUALS,
				rechnungID);
		mysql.connect();
		ArrayList<Rechnungszeile> ret = (ArrayList<Rechnungszeile>) mysql
				.getEntitiesBy(where, Rechnungszeile.class);
		mysql.disconnect();
		return ret;
	}

	public static Rechnungszeile getRechnungszeile(int rechnungszeileID)
			throws DALException {
		// for (int i = 0; i < rechnungszeilenliste.size(); i++) {
		// if (rechnungszeilenliste.get(i).getRechnungszeileID() ==
		// rechnungszeileID) {
		// return rechnungszeilenliste.get(i);
		// }
		// }
		// throw new DALException("Rechnungszeile-ID nicht vorhanden");
		mysql.connect();
		Rechnungszeile ret = mysql.getEntityByID(rechnungszeileID,
				Rechnungszeile.class);
		mysql.disconnect();
		return ret;
	}

	public static void deleteRechnungszeile(int rechnungszeileID)
			throws DALException {
		// Rechnungszeile r = getRechnungszeile(rechnungszeileID);
		// if (r != null) {
		// rechnungszeilenliste.remove(r);
		// }
		mysql.connect();
		mysql.deleteEntity(rechnungszeileID, Rechnungszeile.class);
		mysql.disconnect();
	}

	public static Integer saveRechnungszeile(Rechnungszeile r)
			throws DALException, InvalidObjectException {
		// String exception = "";
		// // ... rechnung bzw angebot-ID überprüfen
		// if (!exception.isEmpty()) {
		// throw new InvalidObjectException(exception);
		// }
		//
		// r.setRechnungszeileID(rechnungszeileID++);
		// rechnungszeilenliste.add(r);
		mysql.connect();
		Object key =mysql.addEntity(r);
		mysql.disconnect();
		return Integer.valueOf(String.valueOf(key));
	}

	public static void updateRechnungszeile(Rechnungszeile r)
			throws DALException, InvalidObjectException {
		// String exception = "";
		// // ... Rechnung- und Angebot-ID überprüfen
		// if (!exception.isEmpty()) {
		// throw new InvalidObjectException(exception);
		// }
		//
		// for (Rechnungszeile rz : rechnungszeilenliste) {
		// if (rz.getRechnungszeileID() == r.getRechnungszeileID()) {
		// rz = r;
		// }
		// }
		mysql.connect();
		mysql.updateEntity(r);
		mysql.disconnect();
	}

	public static ArrayList<Buchungszeile> getBuchungszeileListe()
			throws DALException {
		// return buchungszeilenliste;
		mysql.connect();
		ArrayList<Buchungszeile> ret = (ArrayList<Buchungszeile>) mysql
				.getEntityList(Buchungszeile.class);
		mysql.disconnect();
		return ret;
	}

	public static ArrayList<Buchungszeile> getBuchungszeileListe(String filter)
			throws DALException {
		// return buchungszeilenliste;
		String f = "%" + filter + "%";
		WhereChain where = new WhereChain(Buchungszeile.class,
				WhereOperator.LIKE, f, Chainer.OR);
		mysql.connect();
		ArrayList<Buchungszeile> ret = (ArrayList<Buchungszeile>) mysql
				.getEntitiesBy(where, Buchungszeile.class);
		mysql.disconnect();
		return ret;
	}

	public static void saveBuchungszeile(Buchungszeile b) throws DALException,
			InvalidObjectException {
		// String exception = "";
		// // ... kategorie-ID überprüfen
		// if (!exception.isEmpty()) {
		// throw new InvalidObjectException(exception);
		// }
		//
		// b.setBuchungszeileID(buchungszeileID++);
		// buchungszeilenliste.add(b);
		mysql.connect();
		mysql.addEntity(b);
		mysql.disconnect();
	}

	public static Buchungszeile getBuchungszeile(int buchungszeileID)
			throws DALException {
		// for (int i = 0; i < buchungszeilenliste.size(); i++) {
		// if (buchungszeilenliste.get(i).getBuchungszeileID() ==
		// buchungszeileID) {
		// return buchungszeilenliste.get(i);
		// }
		// }
		// throw new DALException("Buchungszeile-ID nicht vorhanden");
		mysql.connect();
		Buchungszeile b = mysql.getEntityByID(buchungszeileID,
				Buchungszeile.class);
		mysql.disconnect();
		return b;
	}

	public static void deleteBuchungszeile(int buchungszeileID)
			throws DALException {
		// Buchungszeile r = getBuchungszeile(buchungszeileID);
		// if (r != null) {
		// buchungszeilenliste.remove(r);
		// }
		mysql.connect();
		mysql.deleteEntity(buchungszeileID, Buchungszeile.class);
		mysql.disconnect();
	}

	public static void updateBuchungszeile(Buchungszeile b)
			throws DALException, InvalidObjectException {
		// String exception = "";
		// // ... Kategorie-ID überprüfen
		// if (!exception.isEmpty()) {
		// throw new InvalidObjectException(exception);
		// }
		//
		// for (Buchungszeile bz : buchungszeilenliste) {
		// if (bz.getBuchungszeileID() == b.getBuchungszeileID()) {
		// bz = b;
		// }
		// }
		mysql.connect();
		mysql.updateEntity(b);
		mysql.disconnect();
	}

	public static ArrayList<Kategorie> getKategorieListe() throws DALException {
		// // nur zum testen BEGIN
		// if (kategorieliste.size() == 0) {
		// kategorieliste.add(new Kategorie("Einnahme", "Einnahme"));
		// kategorieliste.add(new Kategorie("Ausgabe", "Ausgabe"));
		// kategorieliste.add(new Kategorie("Steuer", "Steuer"));
		// kategorieliste.add(new Kategorie("SVA",
		// "Sozialversicherungsanstalt-Beitrag"));
		// }
		// // nur zum teste END
		// return kategorieliste;
		mysql.connect();
		ArrayList<Kategorie> ret = (ArrayList<Kategorie>) mysql
				.getEntityList(Kategorie.class);
		mysql.disconnect();
		return ret;
	}

	public static void saveKategorie(Kategorie k) throws DALException,
			InvalidObjectException {
		// String exception = "";
		// // ... überprüfen ob kategorie vorhanden
		// if (!exception.isEmpty()) {
		// throw new InvalidObjectException(exception);
		// }
		//
		// kategorieliste.add(k);
		mysql.connect();
		mysql.addEntity(k);
		mysql.disconnect();
	}

	public static Kategorie getKategorie(String kbz) throws DALException {
		// for (int i = 0; i < kategorieliste.size(); i++) {
		// if (kategorieliste.get(i).getID() == kbz) {
		// return kategorieliste.get(i);
		// }
		// }
		// throw new DALException("Kategorie-ID nicht vorhanden");

		// String kkbz = "'" + kbz + "'";
		// WhereChain where = new WhereChain("kkbz", WhereOperator.EQUALS,
		// kkbz);
		// mysql.connect();
		// Kategorie ret = mysql.getEntitiesBy(where, Kategorie.class).get(0);
		// mysql.disconnect();

		mysql.connect();
		Kategorie ret = mysql.getEntityByID(kbz, Kategorie.class);
		mysql.disconnect();
		return ret;
	}

	public static ArrayList<Rechnung_Buchungszeile> getRechnung_BuchungszeileListe(
			int buchungszeileID) throws DALException {
		// ArrayList<Rechung_Buchungszeile> ret = new
		// ArrayList<Rechung_Buchungszeile>();
		// for (Rechung_Buchungszeile rb : rechnungen_buchungszeilen) {
		// if (rb.getBuchungszeileID() == buchungszeileID) {
		// ret.add(rb);
		// }
		// }
		// return ret;
		WhereChain where = new WhereChain("buchungszeileID",
				WhereOperator.EQUALS, buchungszeileID);
		mysql.connect();
		ArrayList<Rechnung_Buchungszeile> ret = (ArrayList<Rechnung_Buchungszeile>) mysql
				.getEntitiesBy(where, Rechnung_Buchungszeile.class);
		mysql.disconnect();
		return ret;
	}

	public static void saveRechnung_Buchungszeile(Rechnung_Buchungszeile rb)
			throws DALException, InvalidObjectException {
		// String exception = "";
		// // ... rechnung & buchungszeile-ID überprüfen
		// for (Rechnung_Buchungszeile rbz : rechnungen_buchungszeilen) {
		// if (rbz.getBuchungszeileID() == rb.getBuchungszeileID()
		// && rbz.getRechnungID() == rb.getRechnungID()) {
		// exception += "Verknüpfung bereits vorhanden";
		// break;
		// }
		// }
		// if (!exception.isEmpty()) {
		// throw new InvalidObjectException(exception);
		// }
		//
		// rechnungen_buchungszeilen.add(rb);
		mysql.connect();
		mysql.addEntity(rb);
		mysql.disconnect();
	}

	public static void saveRechnung_Buchungszeile(
			ArrayList<Rechnung_Buchungszeile> rbs) throws DALException,
			InvalidObjectException {
		for (Rechnung_Buchungszeile rbz : rbs) {
			saveRechnung_Buchungszeile(rbz);
		}
	}

	public static void deleteRechnung_Buchungszeile(int buchungszeileID)
			throws DALException, InvalidObjectException {
		// for (int i = 0; i < rechnungen_buchungszeilen.size(); i++) {
		// Rechnung_Buchungszeile rbz = rechnungen_buchungszeilen.get(i);
		// if (rbz.getBuchungszeileID() == buchungszeileID) {
		// rechnungen_buchungszeilen.remove(i);
		// i--;
		// }
		// }
		mysql.connect();
		mysql.deleteEntity(buchungszeileID, Rechnung_Buchungszeile.class);
		mysql.disconnect();
	}
}
