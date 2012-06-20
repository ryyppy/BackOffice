package bl;

import java.util.ArrayList;
import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;

import bl.objects.Angebot;
import bl.objects.Buchungszeile;
import bl.objects.Kontakt;
import bl.objects.Kunde;
import bl.objects.Projekt;

/**
 * @author Armin
 *
 */
public class BLTest {

	@Test
	public void getKontakt() throws Exception {
		Kontakt k = new Kontakt("firma", "name", "nummer");
		int id = BL.saveKontakt(k);
		Assert.assertEquals(k, BL.getKontakt(id));
	}

	@Test
	public void testSaveKontakt() throws Exception {
		Kontakt k = new Kontakt("firma", "name", "nummer");
		int id = BL.saveKontakt(k);

		Kontakt get = BL.getKontakt(id);
		Assert.assertEquals(k, get);
	}

	@Test
	public void testUpdateKontakt() throws Exception {
		Kontakt k = new Kontakt("firma", "name", "nummer");
		int id = BL.saveKontakt(k);

		String testFirma = "firma2";
		k.setFirma(testFirma);

		BL.updateKontakt(k);
		k = BL.getKontakt(id);

		Assert.assertEquals(testFirma, k.getFirma());
	}

	@Test
	public void testDeleteKontakt() throws Exception {
		Kontakt k = new Kontakt("firma", "name", "nummer");
		int id = BL.saveKontakt(k);
		BL.deleteKontakt(id);

		Assert.assertNull(BL.getKontakt(id));
	}

	@Test
	public void testGetKontaktListe() throws Exception {
		ArrayList<Kontakt> entries = BL.getKontaktListe();
		Assert.assertNotNull("Entries not initialized!", entries);
	}

	// @Test
	// public void testGetKontaktListeBy() throws Exception {
	// BL.saveKontakt(new Kontakt("firma1", "name1", "nummer1"));
	// BL.saveKontakt(new Kontakt("firma2", "name2", "nummer2"));
	// BL.saveKontakt(new Kontakt("firma3", "name3", "nummer3"));
	// BL.saveKontakt(new Kontakt("firma3", "name4", "nummer4"));
	// BL.saveKontakt(new Kontakt("firma5", "name5", "nummer5"));
	//
	//
	// String filter = "";
	// ArrayList<Kontakt> entries = BL.getKontaktListe(filter);
	// for (Kontakt k : entries) {
	// System.out.println(k.toString());
	// Assert.assertEquals(filter, k.getFirma());
	// }
	// Assert.assertEquals("Found wrong number of angebote!", 4,
	// entries.size());
	//
	// }

	@Test
	public void getKunde() throws Exception {
		Kunde k = new Kunde("vorname", "nachname", new Date());
		int id = BL.saveKunde(k);

		Assert.assertEquals(k, BL.getKunde(id));
	}

	@Test
	public void testSaveKunde() throws Exception {
		Kunde k = new Kunde("vorname", "nachname", new Date());
		int id = BL.saveKunde(k);

		Kunde get = BL.getKunde(id);
		Assert.assertEquals(k, get);
	}

	@Test
	public void testUpdateKunde() throws Exception {
		Kunde k = new Kunde("vorname", "nachname", new Date());
		int id = BL.saveKunde(k);

		String testvorname = "vorname2";
		k.setVorname(testvorname);

		BL.updateKunde(k);
		k = BL.getKunde(id);

		Assert.assertEquals(testvorname, k.getVorname());
	}

	@Test
	public void testDeleteKunde() throws Exception {
		Kunde k = new Kunde("vorname", "nachname", new Date());
		int id = BL.saveKunde(k);
		BL.deleteKunde(id);

		Assert.assertNull(BL.getKunde(id));
	}

	@Test
	public void testGetKundeListe() throws Exception {
		ArrayList<Kunde> entries = BL.getKundeListe();
		Assert.assertNotNull("Entries not initialized!", entries);
	}

	// Projekt
	@Test
	public void getProjekt() throws Exception {
		Projekt k = new Projekt("name", "besch", 12.0);
		int id = BL.saveProjekt(k);

		Assert.assertEquals(k, BL.getProjekt(id));
	}

	@Test
	public void testSaveProjekt() throws Exception {
		Projekt k = new Projekt("name", "besch", 12.0);
		int id = BL.saveProjekt(k);

		Projekt get = BL.getProjekt(id);
		Assert.assertEquals(k, get);
	}

	@Test
	public void testUpdateProjekt() throws Exception {
		Projekt k = new Projekt("name", "besch", 12.0);
		int id = BL.saveProjekt(k);

		String testname = "name2";
		k.setName(testname);

		BL.updateProjekt(k);
		k = BL.getProjekt(id);

		Assert.assertEquals(testname, k.getName());
	}

	@Test
	public void testDeleteProjekt() throws Exception {
		Projekt k = new Projekt("name", "besch", 12.0);
		int id = BL.saveProjekt(k);
		BL.deleteProjekt(id);

		Assert.assertNull(BL.getProjekt(id));
	}

	@Test
	public void testGetProjektListe() throws Exception {
		ArrayList<Projekt> entries = BL.getProjektListe();
		Assert.assertNotNull("Entries not initialized!", entries);
	}

	// Angebot
	@Test
	public void getAngebot() throws Exception {
		Angebot k = new Angebot("besch", 12.0, 12.0, new Date(), 12.0, 0, 0);
		int id = BL.saveAngebot(k);

		Assert.assertEquals(k, BL.getAngebot(id));
	}

	@Test
	public void testSaveAngebot() throws Exception {
		Angebot k = new Angebot("besch", 12.0, 12.0, new Date(), 12.0, 0, 0);
		int id = BL.saveAngebot(k);

		Angebot get = BL.getAngebot(id);
		Assert.assertEquals(k, get);
	}

	@Test
	public void testUpdateAngebot() throws Exception {
		Angebot k = new Angebot("besch", 12.0, 12.0, new Date(), 12.0, 0, 0);
		int id = BL.saveAngebot(k);

		String testname = "besch2";
		k.setBeschreibung(testname);

		BL.updateAngebot(k);
		k = BL.getAngebot(id);

		Assert.assertEquals(testname, k.getBeschreibung());
	}

	@Test
	public void testDeleteAngebot() throws Exception {
		Angebot k = new Angebot("besch", 12.0, 12.0, new Date(), 12.0, 0, 0);
		int id = BL.saveAngebot(k);
		BL.deleteAngebot(id);

		Assert.assertNull(BL.getAngebot(id));
	}

	@Test
	public void testGetAngebotListe() throws Exception {
		ArrayList<Angebot> entries = BL.getAngebotListe();
		Assert.assertNotNull("Entries not initialized!", entries);
	}

	// Buchungszeile
	@Test
	public void getBuchungszeile() throws Exception {
		Buchungszeile k = new Buchungszeile(new Date(), "komm", 12.0, 12.0, "k");
		int id = BL.saveBuchungszeile(k);

		Assert.assertEquals(k, BL.getBuchungszeile(id));
	}

	@Test
	public void testSaveBuchungszeile() throws Exception {
		Buchungszeile k = new Buchungszeile(new Date(), "komm", 12.0, 12.0, "k");
		int id = BL.saveBuchungszeile(k);

		Buchungszeile get = BL.getBuchungszeile(id);
		Assert.assertEquals(k, get);
	}

	@Test
	public void testUpdateBuchungszeile() throws Exception {
		Buchungszeile k = new Buchungszeile(new Date(), "komm", 12.0, 12.0, "k");
		int id = BL.saveBuchungszeile(k);

		String testname = "besch2";
		k.setKommentar(testname);

		BL.updateBuchungszeile(k);
		k = BL.getBuchungszeile(id);

		Assert.assertEquals(testname, k.getKommentar());
	}

	@Test
	public void testDeleteBuchungszeile() throws Exception {
		Buchungszeile k = new Buchungszeile(new Date(), "komm", 12.0, 12.0, "k");
		int id = BL.saveBuchungszeile(k);
		BL.deleteBuchungszeile(id);

		Assert.assertNull(BL.getBuchungszeile(id));
	}

	@Test
	public void testGetBuchungszeileListe() throws Exception {
		ArrayList<Buchungszeile> entries = BL.getBuchungszeileListe();
		Assert.assertNotNull("Entries not initialized!", entries);
	}

}
