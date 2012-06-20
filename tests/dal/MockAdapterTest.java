package dal;

import bl.objects.*;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Patrick Stapfer
 * Date: 11.06.12
 * Time: 11:55
 */
public class MockAdapterTest {

    MockAdapter db = null;

    @Before
    public void setUp() throws Exception {
        db = new MockAdapter();
    }

    @Test
    public void testGetEntityByID() throws Exception {
        Angebot a = new Angebot("Angebot1", 20.0, 20.0, GregorianCalendar.getInstance().getTime(), 5.0, 1, 2);
        db.addEntity(a);

        Assert.assertEquals("Angebot with ID 1 not found!", a, db.getEntityByID(1, Angebot.class));
    }

    @Test
    public void testAddEntity() throws Exception {
        Angebot a = new Angebot("Angebot1", 20.0, 20.0, GregorianCalendar.getInstance().getTime(), 5.0, 1, 2);

        Assert.assertEquals("Angebot not added to the mocked db!", 1, db.addEntity(a));
    }

    @Test
    public void testUpdateEntity() throws Exception {
        Angebot a = new Angebot("Angebot1", 20.0, 20.0, GregorianCalendar.getInstance().getTime(), 5.0, 1, 2);
        Object key = db.addEntity(a);

        String testBeschreibung = "Neues Angebot1";
        Double testChance = 100.0;
        a.setID(key);
        a.setBeschreibung(testBeschreibung);
        a.setChance(testChance);

        db.updateEntity(a);
        a = db.getEntityByID(key, Angebot.class);

        Assert.assertEquals("Angebot seems not to be updated...", testBeschreibung, a.getBeschreibung());
        Assert.assertEquals("Angebot seems not to be updated...", testChance, a.getChance());
    }

    @Test
    public void testDeleteEntity() throws Exception {
        Angebot a = new Angebot("Angebot1", 20.0, 20.0, GregorianCalendar.getInstance().getTime(), 5.0, 1, 2);
        Object key = db.addEntity(a);

        Assert.assertTrue("MockAdapter seems not to delete the entity...", db.deleteEntity(key, Angebot.class));

        a = db.getEntityByID(key, Angebot.class);
        Assert.assertNull("Angebot should be deleted!", a);
    }

    @Test
    public void testGetEntityList() throws Exception {
        Assert.assertNotNull(db.getEntityList(Ausgangsrechnung.class));
        Assert.assertNotNull(db.getEntityList(Buchungszeile.class));
        Assert.assertNotNull(db.getEntityList(Eingangsrechnung.class));
        Assert.assertNotNull(db.getEntityList(Kategorie.class));
        Assert.assertNotNull(db.getEntityList(Kontakt.class));
        Assert.assertNotNull(db.getEntityList(Kunde.class));
        Assert.assertNotNull(db.getEntityList(Projekt.class));
        Assert.assertNotNull(db.getEntityList(Rechnung.class));
        Assert.assertNotNull(db.getEntityList(Rechnung_Buchungszeile.class));
        Assert.assertNotNull(db.getEntityList(Rechnungszeile.class));
    }

    @Test
    public void testGetEntitiesBy() throws Exception {
        Object key1 = db.addEntity(new Angebot("Angebot1", 20.0, 20.0, GregorianCalendar.getInstance().getTime(), 5.0, 1, 2));
        Object key2 = db.addEntity(new Angebot("Angebot2", 30.0, 20.0, GregorianCalendar.getInstance().getTime(), 5.0, 1, 2));
        Object key3 = db.addEntity(new Angebot("Angebot3", 40.0, 20.0, GregorianCalendar.getInstance().getTime(), 5.0, 1, 2));
        Object key4 = db.addEntity(new Angebot("Angebot4", 50.0, 20.0, GregorianCalendar.getInstance().getTime(), 5.0, 1, 2));
        Object key5 = db.addEntity(new Angebot("Begebot5", 60.0, 20.0, GregorianCalendar.getInstance().getTime(), 5.0, 1, 2));

        WhereChain where = new WhereChain("summe", WhereOperator.GREATEREQUALS, 20.0);
        where.addAndCondition("beschreibung", WhereOperator.LIKE, "Angebot");

       List<Angebot> angebote = db.getEntitiesBy(where, Angebot.class);

        for(Angebot a : angebote){
            System.out.println(a);
        }
        Assert.assertEquals("Found wrong number of angebote!", 4, angebote.size());

    }
}
