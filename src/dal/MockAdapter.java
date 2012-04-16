package dal;

import bl.objects.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Patrick Stapfer
 * Date: 13.04.12
 * Time: 13:56
 */
public class MockAdapter extends DatabaseAdapter{

    List<Angebot> angebote = new ArrayList<Angebot>();
    List<Ausgangsrechnung> ausgangsrechnungen = new ArrayList<Ausgangsrechnung>();
    List<Buchungszeile> buchungszeilen = new ArrayList<Buchungszeile>();
    List<Eingangsrechnung> eingangsrechnungen = new ArrayList<Eingangsrechnung>();
    List<Kategorie> kategorien = new ArrayList<Kategorie>();
    List<Kontakt> kontakte = new ArrayList<Kontakt>();
    List<Kunde> kunden = new ArrayList<Kunde>();
    List<Projekt> projekte = new ArrayList<Projekt>();
    List<Rechnung> rechnungen = new ArrayList<Rechnung>();
    List<Rechnung_Buchungszeile> rechnung_buchungszeilen = new ArrayList<Rechnung_Buchungszeile>();
    List<Rechnungszeile> rechnungszeilen = new ArrayList<Rechnungszeile>();

    public MockAdapter(){
        super("mock", "mock", "mock", "mock");
    }

    @Override
    public <T extends DBEntity> T getEntityByID(Object id, Class<T> entityClass) throws DALException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object addEntity(DBEntity entity) throws DALException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean updateEntity(DBEntity entity) throws DALException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean deleteEntity(Object id, Class<? extends DBEntity> entityClass) throws DALException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <T extends DBEntity> List<T> getEntityList(Class<T> entityClass) throws DALException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <T extends DBEntity> List<T> getEntitiesBy(WhereChain where, Class<T> entityClass) throws DALException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
