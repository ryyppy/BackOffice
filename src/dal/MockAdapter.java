package dal;

import bl.objects.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This DatabaseAdapter subclass represents a mocked database, which holds all
 * data in non-persistent lists.
 * This Adapter does not support transactions, but the methods beginTransaction() , commit() and rollback() are
 * still callable.
 * There is no need for database-connection data to use this class.
 */
public class MockAdapter extends DatabaseAdapter{

    Map<Class<? extends DBEntity>, List<? extends DBEntity>> listmap = new HashMap<Class<? extends DBEntity>, List<? extends DBEntity>>();
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
        listmap.put(Angebot.class, angebote);
        listmap.put(Ausgangsrechnung.class, ausgangsrechnungen);
        listmap.put(Buchungszeile.class, buchungszeilen);
        listmap.put(Eingangsrechnung.class, eingangsrechnungen);
        listmap.put(Kategorie.class, kategorien);
        listmap.put(Kontakt.class, kontakte);
        listmap.put(Kunde.class, kunden);
        listmap.put(Projekt.class, projekte);
        listmap.put(Rechnung.class, rechnungen);
        listmap.put(Rechnung_Buchungszeile.class, rechnung_buchungszeilen);
        listmap.put(Rechnungszeile.class, rechnungszeilen);
    }

    @Override
    public <T extends DBEntity> T getEntityByID(Object id, Class<T> entityClass) throws DALException {
        if(id == null)
            throw new IllegalArgumentException("ID must not be null!");

        String pkName = DBEntityInfo.get(entityClass).getEntityPk().getName();
        WhereChain where = new WhereChain(pkName, WhereOperator.EQUALS, id);

        List<T> result = getEntitiesBy(where, entityClass);

        if(result == null || result.isEmpty())
            return null;

        return result.get(0);

    }

    @Override
    public Object addEntity(DBEntity entity) throws DALException {
        List<DBEntity> list = (List<DBEntity>)getEntityList(entity.getClass());
        entity.setID(list.size()+1);

        if(list.add(entity))
            return entity.getID();
        return null;
    }

    @Override
    public boolean updateEntity(DBEntity entity) throws DALException {
        Object id = entity.getID();

        if(id == null)
            throw new DALException("Entity-ID must not be null!");

        List<DBEntity> list = (List<DBEntity>)getEntityList(entity.getClass());

        int updateIndex = -1;
        for(int i = 0; i < list.size(); i++){
            DBEntity dbe = list.get(i);

            if(id.equals(entity.getID()))
                return list.set(i, entity) != null;
        }
        return false;
    }

    @Override
    public boolean deleteEntity(Object id, Class<? extends DBEntity> entityClass) throws DALException {
        if(id == null)
            throw new IllegalArgumentException("ID must not be null!");

        List<? extends DBEntity> list = getEntityList(entityClass);

        for(int i = 0; i < list.size(); i++){
            DBEntity entity = list.get(i);

            if(id.equals(entity.getID()))
                return list.remove(i) != null;

        }

        return false;
    }

    @Override
    public <T extends DBEntity> List<T> getEntityList(Class<T> entityClass) throws DALException {
        return (List<T>)listmap.get(entityClass);
    }

    @Override
    public <T extends DBEntity> List<T> getEntitiesBy(WhereChain where, Class<T> entityClass) throws DALException {
        DBEntityInfo info = DBEntityInfo.get(entityClass);
        List<T> found = new ArrayList<T>();

        //Just get all entities for the wished entity-class and check for condition-values
        for(T entity : getEntityList(entityClass)){
            //Each object will be treated as a potential found entity, until proven wrong by the conditions
            boolean entityOkay = true;

            for(WhereCondition condition : where.getConditions()){
                //If there was one condition violated, the condition iterating for the actual entity-object will be skipped
                if(!entityOkay)
                    break;

                boolean conditionOkay = true;

                //Check for each field in the entity the condition-value
                for(Field field :info.getMergedFields()){
                    field.setAccessible(true);
                    try{
                        if(condition.getFieldname().equals(field.getName())){
                            Comparable expected = (Comparable)condition.getValue();
                            Comparable actual = (Comparable)field.get(entity);
                            WhereOperator operator = condition.getOperator();
                            WhereChain.Chainer chainer = condition.getChainer();

                            if(operator.equals(WhereOperator.EQUALS)){
                                if((expected == null && actual != null) || !expected.equals(actual))
                                    conditionOkay = false;
                            }

                            if(operator.equals(WhereOperator.NOT)){
                                if((expected == null && actual == null) || expected.equals(actual))
                                    conditionOkay = false;

                            }

                            //Here, it should not be possible that expected == null
                            if(expected == null){
                                entityOkay = false;
                            }

                            else if(operator.equals(WhereOperator.GREATER) ){
                                if(expected.compareTo(actual) <= 0)
                                    conditionOkay = false;
                            }

                            else if(operator.equals(WhereOperator.GREATEREQUALS)){
                                if(expected.compareTo(actual) == -1)
                                    conditionOkay = false;
                            }

                            else if(operator.equals(WhereOperator.LIKE)){
                                String expectedString = expected.toString().toLowerCase().replace("%", "");

                                if(actual != null){
                                    String actualString = actual.toString().toLowerCase();

                                    //If there is no such string contained in the actual string -> condition fail
                                    if(!actualString.contains(expectedString))
                                        conditionOkay = false;

                                }
                                else{
                                    conditionOkay = false;
                                }
                            }

                            else if(operator.equals(WhereOperator.LOWER)){
                                if(expected.compareTo(actual) >= 0)
                                    conditionOkay = false;
                            }

                            //If there is only one condition and this condition fails, there is no way the entity is appropriate for the search
                            if(chainer == null && where.getConditions().size() == 1 && !conditionOkay) {
                                entityOkay = false;
                                break;
                            }
                            //If one condition-part is invalid, there could still be a chance to fullfill the condition with an OR-clausel
                            if(chainer != null && chainer.equals(WhereChain.Chainer.AND) && !conditionOkay){
                                entityOkay = false;
                                break;
                            }

                        }
                    }catch(IllegalAccessException iae){
                        throw new DALException("An exception occurred while reflecting a field in an entity!", iae);
                    }
                }
            }

            if(entityOkay)
                found.add(entity);
        }

        return found;
    }

    @Override
    public void commit() throws DALException {
        transaction = false;
    }

    @Override
    public void rollback() throws DALException {
        transaction = false;
    }

    @Override
    public void beginTransaction() throws DALException {
        transaction = true;
    }

    @Override
    public void connect() throws DALException {
        if(!connected)
            connected = true;
    }


    @Override
    public void disconnect() throws DALException {
        connected = false;
    }
}
