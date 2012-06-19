package bl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.nio.channels.FileChannel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import logging.LoggerManager;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import bl.objects.Angebot;
import bl.objects.Ausgangsrechnung;
import bl.objects.Buchungszeile;
import bl.objects.Eingangsrechnung;
import bl.objects.Kategorie;
import bl.objects.Kontakt;
import bl.objects.Kunde;
import bl.objects.Projekt;
import bl.objects.Rechnung;
import bl.objects.Rechnung_Buchungszeile;
import bl.objects.Rechnungszeile;
import bl.objects.view.AngebotView;
import bl.objects.view.AusgangsrechnungView;
import bl.objects.view.BuchungszeileView;
import bl.objects.view.EingangsrechnungView;
import bl.objects.view.KontaktView;
import bl.objects.view.KundeView;
import bl.objects.view.ProjektView;
import bl.objects.view.RechnungszeileView;
import bl.objects.view.reports.Ausgaben;
import bl.objects.view.reports.Einnahmen;
import bl.objects.view.reports.Jahresumsatz;
import bl.objects.view.reports.OffeneProjekte;
import bl.objects.view.reports.OffeneRechnungen;
import bl.objects.view.reports.Stundensatz;
import config.ConfigException;
import config.Configuration;
import dal.DALException;
import dal.DBAdapterInt;
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

	private static DBAdapterInt db = null;
	// private static DatabaseAdapter db = new MysqlAdapter("root", "dbsy",
	// "localhost", "swe");

	static {
		try {
			db = Configuration.getInstance().getDatabaseAdapter();
		} catch (ConfigException e) {
			LoggerManager.getLogger("runtime").warn("Konfiguration fehlerhaft");
			System.exit(1);
		}
	}

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
		try {
			db.connect();
			ArrayList<Kontakt> ret = (ArrayList<Kontakt>) db
					.getEntityList(Kontakt.class);

			return ret;
		} finally {
			db.disconnect();

		}
		// return kontakteliste;
	}

	public static ArrayList<KontaktView> getKontaktViewListe()
			throws DALException {
		db.connect();
		ArrayList<KontaktView> ret = (ArrayList<KontaktView>) db
				.getEntityList(KontaktView.class);
		db.disconnect();
		return ret;
		// return kontakteliste;
	}

	public static ArrayList<Kontakt> getKontaktListe(String filter)
			throws DALException {
		String f = "%" + filter + "%";
		WhereChain where = new WhereChain(Kontakt.class, WhereOperator.LIKE, f,
				Chainer.OR);
		db.connect();
		ArrayList<Kontakt> ret = (ArrayList<Kontakt>) db.getEntitiesBy(where,
				Kontakt.class);
		db.disconnect();
		return ret;
		// return kontakteliste;
	}

	public static ArrayList<KontaktView> getKontaktViewListe(String filter)
			throws DALException {
		String f = "%" + filter + "%";
		WhereChain where = new WhereChain(KontaktView.class,
				WhereOperator.LIKE, f, Chainer.OR);
		db.connect();
		ArrayList<KontaktView> ret = (ArrayList<KontaktView>) db.getEntitiesBy(
				where, KontaktView.class);
		db.disconnect();
		return ret;
		// return kontakteliste;
	}

	public static ArrayList<KontaktView> getKontaktViewListe(WhereChain where)
			throws DALException {
		db.connect();
		ArrayList<KontaktView> ret = (ArrayList<KontaktView>) db.getEntitiesBy(
				where, KontaktView.class);
		db.disconnect();
		return ret;
		// return kontakteliste;
	}

	public static Integer containsKontakt(Kontakt k) throws DALException {
		WhereChain where = new WhereChain("firma", WhereOperator.EQUALS,
				k.getFirma());
		where.addAndCondition("name", WhereOperator.EQUALS, k.getName());
		where.addAndCondition("telefon", WhereOperator.EQUALS, k.getTelefon());
		boolean a = false;
		if (!db.isConnected()) {
			db.connect();
			a = true;
		}
		ArrayList<Kontakt> ret = (ArrayList<Kontakt>) db.getEntitiesBy(where,
				Kontakt.class);
		if (a) {
			db.disconnect();
		}
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
		db.connect();
		Kontakt k = db.getEntityByID(kontaktID, Kontakt.class);
		db.disconnect();
		return k;
	}

	public static void deleteKontakt(int kontaktID) throws DALException {
		// Kontakt k = getKontakt(kontaktID);
		// if (k != null) {
		// kontakteliste.remove(k);
		// }
		db.connect();
		db.deleteEntity(kontaktID, Kontakt.class);
		db.disconnect();
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
		db.connect();
		Object key = db.addEntity(k);
		db.disconnect();

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
		db.connect();
		db.updateEntity(k);
		db.disconnect();
	}

	public static ArrayList<Kunde> getKundeListe() throws DALException {
		// return kundenliste;
		db.connect();
		ArrayList<Kunde> ret = (ArrayList<Kunde>) db.getEntityList(Kunde.class);
		db.disconnect();
		return ret;
	}

	public static ArrayList<KundeView> getKundeViewListe() throws DALException {
		// return kundenliste;
		db.connect();
		ArrayList<KundeView> ret = (ArrayList<KundeView>) db
				.getEntityList(KundeView.class);
		db.disconnect();
		return ret;
	}

	public static ArrayList<Kunde> getKundeListe(String filter)
			throws DALException {
		// return kundenliste;
		String f = "%" + filter + "%";
		WhereChain where = new WhereChain(Kunde.class, WhereOperator.LIKE, f,
				Chainer.OR);
		db.connect();
		ArrayList<Kunde> ret = (ArrayList<Kunde>) db.getEntitiesBy(where,
				Kunde.class);
		db.disconnect();
		return ret;
	}

	public static ArrayList<KundeView> getKundeViewListe(String filter)
			throws DALException {
		// return kundenliste;
		String f = "%" + filter + "%";
		WhereChain where = new WhereChain(KundeView.class, WhereOperator.LIKE,
				f, Chainer.OR);
		db.connect();
		ArrayList<KundeView> ret = (ArrayList<KundeView>) db.getEntitiesBy(
				where, KundeView.class);
		db.disconnect();
		return ret;
	}

	public static ArrayList<KundeView> getKundeViewListe(WhereChain where)
			throws DALException {
		// return kundenliste;
		db.connect();
		ArrayList<KundeView> ret = (ArrayList<KundeView>) db.getEntitiesBy(
				where, KundeView.class);
		db.disconnect();
		return ret;
	}

	public static Kunde getKunde(int kundenID) throws DALException {
		// for (int i = 0; i < kundenliste.size(); i++) {
		// if (kundenliste.get(i).getKundenID() == kundenID) {
		// return kundenliste.get(i);
		// }
		// }
		// throw new DALException("Kunden-ID nicht vorhanden");
		db.connect();
		Kunde k = db.getEntityByID(kundenID, Kunde.class);
		db.disconnect();
		return k;
	}

	public static void deleteKunde(int kundenID) throws DALException {
		// Kunde k = getKunde(kundenID);
		// if (k != null) {
		// kundenliste.remove(k);
		// }
		db.connect();
		db.deleteEntity(kundenID, Kunde.class);
		db.disconnect();
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
		db.connect();
		db.addEntity(k);
		db.disconnect();
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
		db.connect();
		db.updateEntity(k);
		db.disconnect();
	}

	public static ArrayList<Projekt> getProjektListe() throws DALException {
		// return projektliste;
		db.connect();
		ArrayList<Projekt> ret = (ArrayList<Projekt>) db
				.getEntityList(Projekt.class);
		db.disconnect();
		return ret;
	}

	public static ArrayList<ProjektView> getProjektViewListe()
			throws DALException {
		// return projektliste;
		db.connect();
		ArrayList<ProjektView> ret = (ArrayList<ProjektView>) db
				.getEntityList(ProjektView.class);
		db.disconnect();
		return ret;
	}

	public static ArrayList<Projekt> getProjektListe(String filter)
			throws DALException {
		// return projektliste;
		String f = "%" + filter + "%";
		WhereChain where = new WhereChain(Projekt.class, WhereOperator.LIKE, f,
				Chainer.OR);
		db.connect();
		ArrayList<Projekt> ret = (ArrayList<Projekt>) db.getEntitiesBy(where,
				Projekt.class);
		db.disconnect();
		return ret;
	}

	public static ArrayList<ProjektView> getProjektViewListe(String filter)
			throws DALException {
		// return projektliste;
		String f = "%" + filter + "%";
		WhereChain where = new WhereChain(ProjektView.class,
				WhereOperator.LIKE, f, Chainer.OR);
		db.connect();
		ArrayList<ProjektView> ret = (ArrayList<ProjektView>) db.getEntitiesBy(
				where, ProjektView.class);
		db.disconnect();
		return ret;
	}

	public static ArrayList<ProjektView> getProjektViewListe(WhereChain where)
			throws DALException {
		// return projektliste;
		db.connect();
		ArrayList<ProjektView> ret = (ArrayList<ProjektView>) db.getEntitiesBy(
				where, ProjektView.class);
		db.disconnect();
		return ret;
	}

	public static Projekt getProjekt(int projektID) throws DALException {
		// for (int i = 0; i < projektliste.size(); i++) {
		// if (projektliste.get(i).getProjektID() == projektID) {
		// return projektliste.get(i);
		// }
		// }
		// throw new DALException("Projekt-ID nicht vorhanden");
		db.connect();
		Projekt p = db.getEntityByID(projektID, Projekt.class);
		db.disconnect();
		return p;
	}

	public static void deleteProjekt(int projektID) throws DALException {
		// Projekt p = getProjekt(projektID);
		// if (p != null) {
		// projektliste.remove(p);
		// }
		db.connect();
		db.deleteEntity(projektID, Projekt.class);
		db.disconnect();
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
		db.connect();
		db.addEntity(p);
		db.disconnect();
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
		db.connect();
		db.updateEntity(p);
		db.disconnect();
	}

	public static ArrayList<Angebot> getAngebotListe() throws DALException {
		// return angebotsliste;
		db.connect();
		ArrayList<Angebot> ret = (ArrayList<Angebot>) db
				.getEntityList(Angebot.class);
		db.disconnect();
		return ret;
	}

	public static ArrayList<AngebotView> getAngebotViewListe()
			throws DALException {
		// return angebotsliste;
		db.connect();
		ArrayList<AngebotView> ret = (ArrayList<AngebotView>) db
				.getEntityList(AngebotView.class);
		db.disconnect();
		return ret;
	}

	public static ArrayList<Angebot> getAngebotListe(String filter)
			throws DALException {
		// return angebotsliste;
		String f = "%" + filter + "%";
		WhereChain where = new WhereChain(Angebot.class, WhereOperator.LIKE, f,
				Chainer.OR);
		db.connect();
		ArrayList<Angebot> ret = (ArrayList<Angebot>) db.getEntitiesBy(where,
				Angebot.class);
		db.disconnect();
		return ret;
	}

	public static ArrayList<AngebotView> getAngebotViewListe(String filter)
			throws DALException {
		// return angebotsliste;
		String f = "%" + filter + "%";
		WhereChain where = new WhereChain(AngebotView.class,
				WhereOperator.LIKE, f, Chainer.OR);
		db.connect();
		ArrayList<AngebotView> ret = (ArrayList<AngebotView>) db.getEntitiesBy(
				where, AngebotView.class);
		db.disconnect();
		return ret;
	}

	public static ArrayList<AngebotView> getAngebotViewListe(WhereChain where)
			throws DALException {
		// return angebotsliste;
		db.connect();
		ArrayList<AngebotView> ret = (ArrayList<AngebotView>) db.getEntitiesBy(
				where, AngebotView.class);
		db.disconnect();
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
		db.connect();
		ArrayList<Angebot> ret = (ArrayList<Angebot>) db.getEntitiesBy(where,
				Angebot.class);
		db.disconnect();
		return ret;

	}

	public static Angebot getAngebot(int angebotID) throws DALException {
		// for (int i = 0; i < angebotsliste.size(); i++) {
		// if (angebotsliste.get(i).getAngebotID() == angebotID) {
		// return angebotsliste.get(i);
		// }
		// }
		// throw new DALException("Angebot-ID nicht vorhanden");
		db.connect();
		Angebot a = db.getEntityByID(angebotID, Angebot.class);
		db.disconnect();
		return a;
	}

	public static void deleteAngebot(int angebotID) throws DALException {
		// Angebot a = getAngebot(angebotID);
		// if (a != null) {
		// projektliste.remove(a);
		// }
		db.connect();
		db.deleteEntity(angebotID, Angebot.class);
		db.disconnect();
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
		db.connect();
		db.addEntity(a);
		db.disconnect();
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
		db.connect();
		db.updateEntity(a);
		db.disconnect();
	}

	public static Eingangsrechnung getEingangsrechnung(int rechnungID)
			throws DALException {
		// for (int i = 0; i < eingangsrechnungenliste.size(); i++) {
		// if (eingangsrechnungenliste.get(i).getRechnungID() == rechnungID) {
		// return eingangsrechnungenliste.get(i);
		// }
		// }
		// throw new DALException("Rechnung-ID nicht vorhanden");
		db.connect();
		Eingangsrechnung ret = db.getEntityByID(rechnungID,
				Eingangsrechnung.class);
		db.disconnect();
		return ret;
	}

	public static void deleteEingangsrechnung(int rechnungID)
			throws DALException {
		// Eingangsrechnung a = getEingangsrechnung(rechnungID);
		// if (a != null) {
		// eingangsrechnungenliste.remove(a);
		// }
		db.connect();
		Eingangsrechnung e = db.getEntityByID(rechnungID,
				Eingangsrechnung.class);

		if (e.getFile() != null) {
			File del = new File(e.getFile());
			try {
				del.delete();
			} catch (Exception ee) {
			}
		}
		db.deleteEntity(rechnungID, Rechnung.class);
		db.disconnect();
	}

	public static ArrayList<Eingangsrechnung> getEingangsrechnungListe()
			throws DALException {
		// return eingangsrechnungenliste;
		db.connect();
		ArrayList<Eingangsrechnung> ret = (ArrayList<Eingangsrechnung>) db
				.getEntityList(Eingangsrechnung.class);
		db.disconnect();
		return ret;
	}

	public static ArrayList<EingangsrechnungView> getEingangsrechnungViewListe()
			throws DALException {
		// return eingangsrechnungenliste;
		db.connect();
		ArrayList<EingangsrechnungView> ret = (ArrayList<EingangsrechnungView>) db
				.getEntityList(EingangsrechnungView.class);
		db.disconnect();
		return ret;
	}

	public static ArrayList<Eingangsrechnung> getEingangsrechnungListe(
			String filter) throws DALException {
		// return eingangsrechnungenliste;
		String f = "%" + filter + "%";
		WhereChain where = new WhereChain(Eingangsrechnung.class,
				WhereOperator.LIKE, f, Chainer.OR);
		db.connect();
		ArrayList<Eingangsrechnung> ret = (ArrayList<Eingangsrechnung>) db
				.getEntitiesBy(where, Eingangsrechnung.class);
		db.disconnect();
		return ret;
	}

	public static ArrayList<EingangsrechnungView> getEingangsrechnungViewListe(
			String filter) throws DALException {
		// return eingangsrechnungenliste;
		String f = "%" + filter + "%";
		WhereChain where = new WhereChain(EingangsrechnungView.class,
				WhereOperator.LIKE, f, Chainer.OR);
		db.connect();
		ArrayList<EingangsrechnungView> ret = (ArrayList<EingangsrechnungView>) db
				.getEntitiesBy(where, EingangsrechnungView.class);
		db.disconnect();
		return ret;
	}

	public static ArrayList<EingangsrechnungView> getEingangsrechnungViewListe(
			WhereChain where) throws DALException {
		// return eingangsrechnungenliste;
		db.connect();
		ArrayList<EingangsrechnungView> ret = (ArrayList<EingangsrechnungView>) db
				.getEntitiesBy(where, EingangsrechnungView.class);
		db.disconnect();
		return ret;
	}

	public static Integer saveEingangsrechnung(Eingangsrechnung e, File f)
			throws DALException, IOException {
		// String exception = "";
		// // ... Kontakt-ID überprüfen
		// if (!exception.isEmpty()) {
		// throw new InvalidObjectException(exception);
		// }
		//
		// e.setRechnungID(rechnungID++);
		// eingangsrechnungenliste.add(e);
		if (f != null) {
			String pfad = "file/"
					+ new StringBuilder(
							new SimpleDateFormat("yyyy/MM/dd")
									.format(new Date())).toString();
			File ff = new File(pfad);

			if (!ff.exists()) {
				ff.mkdirs();
			}
			ff = new File(ff.getPath() + "/" + e.getRechnungID() + "_"
					+ f.getName());
			if (!ff.exists()) {
				ff.createNewFile();
			}

			FileChannel inChannel = new FileInputStream(f).getChannel();
			FileChannel outChannel = new FileOutputStream(ff).getChannel();
			inChannel.transferTo(0, inChannel.size(), outChannel);
			inChannel.close();
			outChannel.close();
			e.setFile(ff.getPath());
		}
		db.connect();
		db.beginTransaction();
		try {
			Rechnung r = new Rechnung(e.getStatus(), e.getDatum());
			Object key = db.addEntity(r);
			e.setRechnungID(Integer.valueOf(String.valueOf(key)));

			db.addEntity(e);
			db.commit();
		} catch (DALException d) {
			db.rollback();
			throw d;
		}
		db.disconnect();
		return e.getRechnungID();
	}

	public static void updateEingangsrechnung(Eingangsrechnung e, File f)
			throws DALException, IOException {
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

		if (e.getFile() != null) {
			File del = new File(e.getFile());
			try {
				del.delete();
			} catch (Exception ee) {
			}
			e.setFile(null);
		}
		if (f != null) {

			String pfad = "file/"
					+ new StringBuilder(
							new SimpleDateFormat("yyyy/MM/dd")
									.format(new Date())).toString();
			File ff = new File(pfad);

			if (!ff.exists()) {
				ff.mkdirs();
			}
			ff = new File(ff.getPath() + "/" + e.getRechnungID() + "_"
					+ f.getName());
			if (!ff.exists()) {
				ff.createNewFile();
			}

			FileChannel inChannel = new FileInputStream(f).getChannel();
			FileChannel outChannel = new FileOutputStream(ff).getChannel();
			inChannel.transferTo(0, inChannel.size(), outChannel);
			inChannel.close();
			outChannel.close();
			e.setFile(ff.getPath());
		}
		db.connect();
		Rechnung r = new Rechnung(e.getRechnungID(), e.getStatus(),
				e.getDatum());
		db.updateEntity(r);
		db.updateEntity(e);
		db.disconnect();
	}

	public static ArrayList<Ausgangsrechnung> getAusgangsrechnungListe()
			throws DALException {
		// return ausgangsrechnungenliste;
		db.connect();
		ArrayList<Ausgangsrechnung> ret = (ArrayList<Ausgangsrechnung>) db
				.getEntityList(Ausgangsrechnung.class);
		db.disconnect();
		return ret;
	}

	public static ArrayList<AusgangsrechnungView> getAusgangsrechnungViewListe()
			throws DALException {
		// return ausgangsrechnungenliste;
		db.connect();
		ArrayList<AusgangsrechnungView> ret = (ArrayList<AusgangsrechnungView>) db
				.getEntityList(AusgangsrechnungView.class);
		db.disconnect();
		return ret;
	}

	public static ArrayList<Ausgangsrechnung> getAusgangsrechnungListe(
			String filter) throws DALException {
		// return ausgangsrechnungenliste;
		String f = "%" + filter + "%";
		WhereChain where = new WhereChain(Ausgangsrechnung.class,
				WhereOperator.LIKE, f, Chainer.OR);
		db.connect();
		ArrayList<Ausgangsrechnung> ret = (ArrayList<Ausgangsrechnung>) db
				.getEntitiesBy(where, Ausgangsrechnung.class);
		db.disconnect();
		return ret;
	}

	public static ArrayList<AusgangsrechnungView> getAusgangsrechnungViewListe(
			String filter) throws DALException {
		// return ausgangsrechnungenliste;
		String f = "%" + filter + "%";
		WhereChain where = new WhereChain(AusgangsrechnungView.class,
				WhereOperator.LIKE, f, Chainer.OR);
		db.connect();
		ArrayList<AusgangsrechnungView> ret = (ArrayList<AusgangsrechnungView>) db
				.getEntitiesBy(where, AusgangsrechnungView.class);
		db.disconnect();
		return ret;
	}

	public static ArrayList<AusgangsrechnungView> getAusgangsrechnungViewListe(
			WhereChain where) throws DALException {
		// return ausgangsrechnungenliste;
		db.connect();
		ArrayList<AusgangsrechnungView> ret = (ArrayList<AusgangsrechnungView>) db
				.getEntitiesBy(where, AusgangsrechnungView.class);
		db.disconnect();
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
		db.connect();
		Ausgangsrechnung ret = db.getEntityByID(rechnungID,
				Ausgangsrechnung.class);
		db.disconnect();
		return ret;
	}

	public static void deleteAusgangsrechnung(int rechnungID)
			throws DALException {
		// Ausgangsrechnung a = getAusgangsrechnung(rechnungID);
		// if (a != null) {
		// ausgangsrechnungenliste.remove(a);
		// }
		db.connect();
		db.deleteEntity(rechnungID, Rechnung.class);
		db.disconnect();
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

		db.connect();
		db.beginTransaction();
		try {
			Rechnung r = new Rechnung(a.getStatus(), a.getDatum());
			Object key = db.addEntity(r);
			a.setRechnungID(Integer.valueOf(String.valueOf(key)));
			db.addEntity(a);
			db.commit();
		} catch (DALException d) {
			db.rollback();
			throw d;
		}
		db.disconnect();
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
		db.connect();
		Rechnung r = new Rechnung(a.getRechnungID(), a.getStatus(),
				a.getDatum());
		db.updateEntity(r);
		db.updateEntity(a);
		db.disconnect();

	}

	public static ArrayList<Rechnung> getRechnungListe() throws DALException {
		// ArrayList<Rechnung> ar = new ArrayList<Rechnung>();
		// ar.addAll(ausgangsrechnungenliste);
		// ar.addAll(eingangsrechnungenliste);
		// return ar;

		db.connect();
		ArrayList<Ausgangsrechnung> al = (ArrayList<Ausgangsrechnung>) db
				.getEntityList(Ausgangsrechnung.class);
		ArrayList<Eingangsrechnung> el = (ArrayList<Eingangsrechnung>) db
				.getEntityList(Eingangsrechnung.class);

		ArrayList<Rechnung> ar = new ArrayList<Rechnung>();
		ar.addAll(al);
		ar.addAll(el);
		return ar;
	}

	public static ArrayList<Rechnungszeile> getRechnungszeileListe()
			throws DALException {
		// return rechnungszeilenliste;
		db.connect();
		ArrayList<Rechnungszeile> ret = (ArrayList<Rechnungszeile>) db
				.getEntityList(Rechnungszeile.class);
		db.disconnect();
		return ret;
	}

	public static ArrayList<RechnungszeileView> getRechnungszeileViewListe()
			throws DALException {
		// return rechnungszeilenliste;
		db.connect();
		ArrayList<RechnungszeileView> ret = (ArrayList<RechnungszeileView>) db
				.getEntityList(RechnungszeileView.class);
		db.disconnect();
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
		db.connect();
		ArrayList<Rechnungszeile> ret = (ArrayList<Rechnungszeile>) db
				.getEntitiesBy(where, Rechnungszeile.class);
		db.disconnect();
		return ret;
	}

	public static ArrayList<RechnungszeileView> getRechnungszeileViewListe(
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
		db.connect();
		ArrayList<RechnungszeileView> ret = (ArrayList<RechnungszeileView>) db
				.getEntitiesBy(where, RechnungszeileView.class);
		db.disconnect();
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
		db.connect();
		Rechnungszeile ret = db.getEntityByID(rechnungszeileID,
				Rechnungszeile.class);
		db.disconnect();
		return ret;
	}

	public static void deleteRechnungszeile(int rechnungszeileID)
			throws DALException {
		// Rechnungszeile r = getRechnungszeile(rechnungszeileID);
		// if (r != null) {
		// rechnungszeilenliste.remove(r);
		// }
		db.connect();
		db.deleteEntity(rechnungszeileID, Rechnungszeile.class);
		db.disconnect();
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
		db.connect();
		Object key = db.addEntity(r);
		db.disconnect();
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
		db.connect();
		db.updateEntity(r);
		db.disconnect();
	}

	public static ArrayList<Buchungszeile> getBuchungszeileListe()
			throws DALException {
		// return buchungszeilenliste;
		db.connect();
		ArrayList<Buchungszeile> ret = (ArrayList<Buchungszeile>) db
				.getEntityList(Buchungszeile.class);
		db.disconnect();
		return ret;
	}

	public static ArrayList<BuchungszeileView> getBuchungszeileViewListe()
			throws DALException {
		// return buchungszeilenliste;
		db.connect();
		ArrayList<BuchungszeileView> ret = (ArrayList<BuchungszeileView>) db
				.getEntityList(BuchungszeileView.class);
		db.disconnect();
		return ret;
	}

	public static ArrayList<Buchungszeile> getBuchungszeileListe(String filter)
			throws DALException {
		// return buchungszeilenliste;
		String f = "%" + filter + "%";
		WhereChain where = new WhereChain(Buchungszeile.class,
				WhereOperator.LIKE, f, Chainer.OR);
		db.connect();
		ArrayList<Buchungszeile> ret = (ArrayList<Buchungszeile>) db
				.getEntitiesBy(where, Buchungszeile.class);
		db.disconnect();
		return ret;
	}

	public static ArrayList<BuchungszeileView> getBuchungszeileViewListe(
			String filter) throws DALException {
		// return buchungszeilenliste;
		String f = "%" + filter + "%";
		WhereChain where = new WhereChain(BuchungszeileView.class,
				WhereOperator.LIKE, f, Chainer.OR);
		db.connect();
		ArrayList<BuchungszeileView> ret = (ArrayList<BuchungszeileView>) db
				.getEntitiesBy(where, BuchungszeileView.class);
		db.disconnect();
		return ret;
	}

	public static ArrayList<BuchungszeileView> getBuchungszeileViewListe(
			WhereChain where) throws DALException {
		// return buchungszeilenliste;
		db.connect();
		ArrayList<BuchungszeileView> ret = (ArrayList<BuchungszeileView>) db
				.getEntitiesBy(where, BuchungszeileView.class);
		db.disconnect();
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
		db.connect();
		db.addEntity(b);
		db.disconnect();
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
		db.connect();
		Buchungszeile b = db
				.getEntityByID(buchungszeileID, Buchungszeile.class);
		db.disconnect();
		return b;
	}

	public static void deleteBuchungszeile(int buchungszeileID)
			throws DALException {
		// Buchungszeile r = getBuchungszeile(buchungszeileID);
		// if (r != null) {
		// buchungszeilenliste.remove(r);
		// }
		db.connect();
		db.deleteEntity(buchungszeileID, Buchungszeile.class);
		db.disconnect();
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
		db.connect();
		db.updateEntity(b);
		db.disconnect();
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
		db.connect();
		ArrayList<Kategorie> ret = (ArrayList<Kategorie>) db
				.getEntityList(Kategorie.class);
		db.disconnect();
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
		db.connect();
		db.addEntity(k);
		db.disconnect();
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

		db.connect();
		Kategorie ret = db.getEntityByID(kbz, Kategorie.class);
		db.disconnect();
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
		db.connect();
		ArrayList<Rechnung_Buchungszeile> ret = (ArrayList<Rechnung_Buchungszeile>) db
				.getEntitiesBy(where, Rechnung_Buchungszeile.class);
		db.disconnect();
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
		db.connect();
		db.addEntity(rb);
		db.disconnect();
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
		db.connect();
		db.deleteEntity(buchungszeileID, Rechnung_Buchungszeile.class);
		db.disconnect();
	}

	private static String getTagValue(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0)
				.getChildNodes();
		Node nValue = (Node) nlList.item(0);
		return nValue.getNodeValue();
	}

	private static String log;

	private static void addLogLine(String txt) {
		log += txt + "\n\n";
	}

	public static String importEingangsrechnung(File file)
			throws ParserConfigurationException, SAXException, IOException,
			DALException {
		db.connect();
		db.beginTransaction();
		log = "";
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentbuilder;
		Document document = null;

		documentbuilder = dbf.newDocumentBuilder();
		document = documentbuilder.parse(file);
		document.getDocumentElement().normalize();
		// name=document.getDocumentElement().getNodeName();

		NodeList nl = document.getElementsByTagName("eingangsrechnung");
		for (int i = 0; i < nl.getLength(); i++) {
			addLogLine("----- Eingangsrechnung #" + (i + 1));
			Node n = nl.item(i);
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				Element eingangsrechnung = (Element) n;
				Eingangsrechnung r = new Eingangsrechnung();
				try {
					r.setDatum(getTagValue("datum", eingangsrechnung));
				} catch (ParseException e) {
					r.setDatum(new Date());
				}
				r.setStatus(getTagValue("status", eingangsrechnung));

				Element kontakt = (Element) eingangsrechnung
						.getElementsByTagName("kontakt").item(0);
				Kontakt k = new Kontakt(getTagValue("firma", kontakt),
						getTagValue("name", kontakt), getTagValue("telefon",
								kontakt));
				try {
					Integer kontaktID = BL.containsKontakt(k);
					if (kontaktID != -1) {
						r.setKontaktID(kontaktID);
						k.setKontaktID(kontaktID);
						addLogLine("USE " + k.toString());
					} else {
						kontaktID = Integer.valueOf(String.valueOf(db
								.addEntity(k)));
						r.setKontaktID(kontaktID);
						k.setKontaktID(kontaktID);
						addLogLine("ADD " + k.toString());
					}
				} catch (DALException e) {
					e.printStackTrace();
					addLogLine("ERROR: " + k.toString() + "\nERRORMESSAGE: "
							+ e.getMessage());
					db.rollback();
					db.disconnect();
					return log;
				}
				try {
					Rechnung rr = new Rechnung(r.getStatus(), r.getDatum());
					int key = Integer.valueOf(String.valueOf(db.addEntity(rr)));
					r.setRechnungID(key);
					db.addEntity(r);

					addLogLine("ADD " + r.toString());
				} catch (DALException e) {
					e.printStackTrace();
					addLogLine("ERROR " + r.toString() + "\nERRORMESSAGE: "
							+ e.getMessage());
					db.rollback();
					db.disconnect();
					return log;
				}

				NodeList rechnungszeilen = eingangsrechnung
						.getElementsByTagName("rechnungszeile");
				for (int j = 0; j < rechnungszeilen.getLength(); j++) {
					Element rechnungszeile = (Element) rechnungszeilen.item(j);
					Rechnungszeile p = null;
					String kommentar = getTagValue("kommentar", rechnungszeile);
					String steuersatz = getTagValue("steuersatz",
							rechnungszeile);
					String betrag = getTagValue("betrag", rechnungszeile);
					try {
						p = new Rechnungszeile(kommentar,
								Double.parseDouble(steuersatz),
								Double.parseDouble(betrag), r.getRechnungID(),
								null);
					} catch (Exception e) {
						addLogLine("ERROR Rechnungszeile [kommentar="
								+ kommentar + ", steuersatz=" + steuersatz
								+ ", betrag=" + betrag + "]\nERRORMESSAGE: "
								+ e.getMessage());
						db.rollback();
						db.disconnect();
						return log;
					}
					try {
						if (p != null) {
							int rechnungszeileID = Integer.valueOf(String
									.valueOf(db.addEntity(p)));
							p.setRechnungszeileID(rechnungszeileID);
							addLogLine("ADD " + p.toString());
						}
					} catch (DALException e) {
						e.printStackTrace();
						addLogLine("ERROR " + p.toString() + "\nERRORMESSAGE: "
								+ e.getMessage());
						db.rollback();
						db.disconnect();
						return log;
					}
				}
			}

			addLogLine("");
		}
		db.commit();
		db.disconnect();
		return log;
	}

	public static String importZeit(File file)
			throws ParserConfigurationException, SAXException, IOException,
			DALException {
		db.connect();
		db.beginTransaction();
		log = "";
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentbuilder;
		Document document = null;

		documentbuilder = dbf.newDocumentBuilder();
		document = documentbuilder.parse(file);
		document.getDocumentElement().normalize();
		// name=document.getDocumentElement().getNodeName();

		NodeList nl = document.getElementsByTagName("projekt");
		for (int i = 0; i < nl.getLength(); i++) {
			log = "";
			addLogLine("----- Projekt Eintrag#" + (i + 1));
			Node n = nl.item(i);
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				Element projekt = (Element) n;

				try {
					int projektid = Integer.valueOf(getTagValue("projektID",
							projekt));

					Projekt p = db.getEntityByID(projektid, Projekt.class);

					if (p == null) {
						addLogLine("ERRORMESSAGE: Projekt mit projektID="
								+ projektid + " nicht vorhanden");
						db.rollback();
						db.disconnect();
						return log;
					}
					double verbrauchteStunden = Double.valueOf(getTagValue(
							"verbrauchteStunden", projekt));

					p.setVerbrauchteStunden(verbrauchteStunden);

					db.updateEntity(p);
				} catch (DALException e) {
					e.printStackTrace();
					addLogLine("ERRORMESSAGE: " + e.getMessage());
					db.rollback();
					db.disconnect();
					return log;
				} catch (NumberFormatException e) {
					e.printStackTrace();
					addLogLine("ERRORMESSAGE: " + e.getMessage());
					db.rollback();
					db.disconnect();
					return log;
				}
			}
		}
		db.commit();
		db.disconnect();
		return "";
	}

	public static ArrayList<Jahresumsatz> getJahresumsatzListe()
			throws DALException {
		db.connect();
		ArrayList<Jahresumsatz> ret = (ArrayList<Jahresumsatz>) db
				.getEntityList(Jahresumsatz.class);
		db.disconnect();
		return ret;
	}

	public static ArrayList<Jahresumsatz> getJahresumsatzListe(WhereChain where)
			throws DALException {
		// return buchungszeilenliste;
		db.connect();
		ArrayList<Jahresumsatz> ret = (ArrayList<Jahresumsatz>) db
				.getEntitiesBy(where, Jahresumsatz.class);
		db.disconnect();
		return ret;
	}

	public static ArrayList<OffeneProjekte> getOffeneProjekteListe()
			throws DALException {
		db.connect();
		ArrayList<OffeneProjekte> ret = (ArrayList<OffeneProjekte>) db
				.getEntityList(OffeneProjekte.class);
		db.disconnect();
		return ret;
	}

	public static ArrayList<OffeneProjekte> getOffeneProjekteListe(
			WhereChain where) throws DALException {
		// return buchungszeilenliste;
		db.connect();
		ArrayList<OffeneProjekte> ret = (ArrayList<OffeneProjekte>) db
				.getEntitiesBy(where, OffeneProjekte.class);
		db.disconnect();
		return ret;
	}

	public static ArrayList<Stundensatz> getStundensatzListe()
			throws DALException {
		db.connect();
		ArrayList<Stundensatz> ret = (ArrayList<Stundensatz>) db
				.getEntityList(Stundensatz.class);
		db.disconnect();
		return ret;
	}

	public static ArrayList<Stundensatz> getStundensatzListe(WhereChain where)
			throws DALException {
		// return buchungszeilenliste;
		db.connect();
		ArrayList<Stundensatz> ret = (ArrayList<Stundensatz>) db.getEntitiesBy(
				where, Stundensatz.class);
		db.disconnect();
		return ret;
	}

	public static ArrayList<OffeneRechnungen> getOffeneRechnungenListe()
			throws DALException {
		db.connect();
		ArrayList<OffeneRechnungen> ret = (ArrayList<OffeneRechnungen>) db
				.getEntityList(OffeneRechnungen.class);
		db.disconnect();
		return ret;
	}

	public static ArrayList<OffeneRechnungen> getOffeneRechnungenListe(
			WhereChain where) throws DALException {
		// return buchungszeilenliste;
		db.connect();
		ArrayList<OffeneRechnungen> ret = (ArrayList<OffeneRechnungen>) db
				.getEntitiesBy(where, OffeneRechnungen.class);
		db.disconnect();
		return ret;
	}

	public static ArrayList<Einnahmen> getEinnahmenListe() throws DALException {
		db.connect();
		ArrayList<Einnahmen> ret = (ArrayList<Einnahmen>) db
				.getEntityList(Einnahmen.class);
		db.disconnect();
		return ret;
	}

	public static ArrayList<Einnahmen> getEinnahmenListe(WhereChain where)
			throws DALException {
		// return buchungszeilenliste;
		db.connect();
		ArrayList<Einnahmen> ret = (ArrayList<Einnahmen>) db.getEntitiesBy(
				where, Einnahmen.class);
		db.disconnect();
		return ret;
	}

	public static ArrayList<Ausgaben> getAusgabenListe() throws DALException {
		db.connect();
		ArrayList<Ausgaben> ret = (ArrayList<Ausgaben>) db
				.getEntityList(Ausgaben.class);
		db.disconnect();
		return ret;
	}

	public static ArrayList<Ausgaben> getAusgabenListe(WhereChain where)
			throws DALException {
		// return buchungszeilenliste;
		db.connect();
		ArrayList<Ausgaben> ret = (ArrayList<Ausgaben>) db.getEntitiesBy(where,
				Ausgaben.class);
		db.disconnect();
		return ret;
	}
}
