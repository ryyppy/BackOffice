package dal;

import bl.objects.Angebot;
import bl.objects.Eingangsrechnung;
import bl.objects.Rechnung;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Patrick Stapfer
 * Date: 13.04.12
 * Time: 11:19
 */
public class MysqlAdapterTest {
    DatabaseAdapter db = null;

    @Before
    public void setUp() throws Exception {
        db = new MysqlAdapter("root","4chaos", "localhost", "backoffice");
    }

    @Test
    public void testAddEntity() throws Exception {

        db.connect();
        /*
        Angebot a = new Angebot();
        a.setChance(20.0);
        a.setDatum(GregorianCalendar.getInstance().getTime());
        a.setDauer(100);
        a.setSumme(20.0);

        Object newKey = db.addEntity(a);

        Assert.assertNotNull("Insert did not succeed", newKey);
        */
        db.connect();

        db.disconnect();

    }

    @Test
    public void testGetEntityByID() throws Exception {
        db.connect();
        Angebot a = db.getEntityByID(1, Angebot.class);
        System.out.println(a);
        db.disconnect();
    }

    @Test
    public void testUpdateEntity() throws Exception {

    }

    @Test
    public void testDeleteEntity() throws Exception {

    }

    @Test
    public void testGetEntityList() throws Exception {
        System.out.println("\n\nALL ENTITIES");
        db.connect();

        List<Angebot> angebote = db.getEntityList(Angebot.class);
        for(Angebot a : angebote){
            System.out.println(a);
        }
        db.disconnect();
    }

    @Test
    public void testGetEntitiesBy() throws Exception {
        System.out.println("\n\nENTITIES BY WHERE CLAUSEL");
        db.connect();
        WhereChain where = new WhereChain("angebotID", WhereOperator.GREATEREQUALS, 1);
        where.addAndCondition("chance", WhereOperator.GREATER, 10.0);

        List<Angebot> angebote = db.getEntitiesBy(where, Angebot.class);
        for(Angebot a : angebote){
            System.out.println(a);
        }
        db.disconnect();
    }

    @Test
    public void arminTest() throws Exception {
        /** hier bekomm ich ne fehlermeldung, obwohl alles gepasst hat **/

        db.connect();
        Eingangsrechnung e = new Eingangsrechnung("offen", new Date(), 0);
        Rechnung r = new Rechnung(e.getStatus(), e.getDatum());
        Object key = db.addEntity(r);
        e.setRechnungID(Integer.valueOf(String.valueOf(key)));
        db.addEntity(e);


        /**
         * ich will dass ich mit getEingangsrechnungenListe eine liste von
         * EINGANGSRECHNUNGEN bekomme und anschließend auf ALLE attribute
         * zugriff haben! genau so wie hier!!!
         *
         * die methode geteingagnsrechnungenliste vom BL musst du dafür
         * bearbeiten!
         **/

        //ArrayList<Eingangsrechnung> liste = BL.getEingangsrechnungenListe();
        List<Eingangsrechnung> liste = db.getEntityList(Eingangsrechnung.class);

        for(Eingangsrechnung eingangsrechnung : liste){
            System.out.println(eingangsrechnung);
        }


        db.disconnect();
    }


    @Test
    public void random() throws Exception{
        db.connect();
        List<Angebot> liste = db.getEntityList(Angebot.class);

        for(Angebot a : liste){
            System.out.println(a);
        }

        List<Eingangsrechnung> liste2 = db.getEntityList(Eingangsrechnung.class);

        for(Eingangsrechnung a : liste2){
            System.out.println(a);
        }

        System.out.println("\n\n GETENTITYBYID:");
        Eingangsrechnung e = db.getEntityByID(1, Eingangsrechnung.class);
        System.out.println(e);

        System.out.println("\n\n GETENTITIESBY WHERE:");
        WhereChain where = new WhereChain("rechnungID", WhereOperator.GREATEREQUALS, 1);
        where.addAndCondition("status", WhereOperator.LIKE, "%offen%");

        List<Eingangsrechnung> liste3 = db.getEntitiesBy(where, Eingangsrechnung.class);
        for(Eingangsrechnung a : liste3){
            System.out.println(a);
        }

        System.out.println("\n\n FILTER WHERCHAIN:");
        where = new WhereChain(Eingangsrechnung.class, WhereOperator.EQUALS, "offen", WhereChain.Chainer.OR);

        List<Eingangsrechnung> liste4 = db.getEntitiesBy(null, Eingangsrechnung.class);
        for(Eingangsrechnung a : liste4){
            System.out.println(a);
        }

        db.disconnect();
    }

}
