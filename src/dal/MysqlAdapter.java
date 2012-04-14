package dal;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * This DatabaseAdapter is capable to connect to a MYSQL-database.
 * Make sure you included the mysql-driver in your project-settings, otherwise there will be a Class-not-found-exception
 */
public class MysqlAdapter extends DatabaseAdapter {

    /**
     * Constructs a MysqlAdapter with login-information and database-URL
     * To connect to the database use connect()!
     * @param dbUser - Username with appropriate privileges to the database
     * @param dbPassword - Password for the user
     * @param dbUrl - Url (no protocol!) to the database-server with databasename (for instance: localhost/backoffice)
     */
	public MysqlAdapter(String dbUser, String dbPassword,String dbUrl) {
		super("com.mysql.jdbc.Driver", dbUser, dbPassword, "jdbc:mysql://" + dbUrl);
	}

    @Override
    protected String createWhereClausel(WhereChain whereChain, Class<? extends DBEntity> entityClass) throws DALException{
        if(whereChain == null)
            return "";

        List<WhereCondition> conditions = whereChain.getConditions();

        Class<? extends DBEntity> entitySuper = DBEntity.getDBSuperclass(entityClass);

        String table = entityClass.getSimpleName();
        String superTable = entitySuper.getSimpleName();

        StringBuilder whereBuilder = new StringBuilder("WHERE ");

        for(int i = 0; i < conditions.size(); i++){
            WhereCondition condition = conditions.get(i);
            String field = condition.getFieldname();
            Object value = condition.getValue();

            String referenceTable = null;
            Field f = null;
            try{
                f = entityClass.getDeclaredField(field);
                referenceTable = table;
            }catch(NoSuchFieldException e){
                try{
                    f = entitySuper.getDeclaredField(field);
                    referenceTable = superTable;
                }catch(NoSuchFieldException nsfe){
                    throw new DALException(String.format("Field '%s' not found in DBEntity-definition '%s' - Check if there are typos!", field, table), nsfe);
                }
            }

            //TODO: Das ist nicht sauber, wenn man nicht auf Datentypen ueberprueft... Workaround fuer FILTER-WhereChains finden!
            /*
            if(value != null){
                Class<?> shouldClazz = f.getType();
                Class<?> actualClazz = value.getClass();

                if(!shouldClazz.equals(actualClazz))
                    throw new DALException(String.format("Field '%s' is a '%s' but given value is a '%s' - Wrong types!", field, shouldClazz.getName(), actualClazz.getName()));

            }
            */

            //Ensure that the first element will not have a leading chainer
            if(i > 0){
                //Add the chainer-phrase
                WhereChain.Chainer chainer = condition.getChainer();

                if(chainer == null)
                    chainer = WhereChain.Chainer.AND;

                whereBuilder.append(" ").append(chainer).append(" ");
            }
            //Table.fieldname >= 'value'
            whereBuilder.append(String.format("%s.%s %s '%s'", referenceTable, field, condition.getOperator(), value));

        }
        return whereBuilder.toString();
    }

    @Override
    protected String createJoinClause(Class<? extends DBEntity> entityClass) throws DALException{
        Class<? extends DBEntity> superClazz = DBEntity.getDBSuperclass(entityClass);

        //If there is no superClass, or the superclass is just the DBEntity, there is no join needed
        if(superClazz == null || superClazz.equals(DBEntity.class))
            return "";

        String pkSuper = DBEntity.getPKField(superClazz).getName();
        String pkSub = DBEntity.getPKField(entityClass).getName();

        String tableSuper = superClazz.getSimpleName();
        String tableSub = entityClass.getSimpleName();
        return String.format("JOIN %s ON %s.%s = %s.%s", tableSuper, tableSuper, pkSuper, tableSub, pkSub);
    }

    @Override
    public <T extends DBEntity> T getEntityByID(Object id, Class<T> entityClass) throws DALException{
        //Create a WHERE clausel out of the PKField (for instance "WHERE angebotID = 1")
        String pkName = DBEntity.getPKField(entityClass).getName();
        WhereChain where = new WhereChain(pkName, WhereOperator.EQUALS, id);

        List<T> result = getEntitiesBy(where, entityClass);

        if(result.isEmpty())
            return null;

        return result.get(0);
    }

    @Override
    public Object addEntity(DBEntity entity) throws DALException {
        if(!isConnected())
            throw new DALException("Connection not established! Use method connect() before requesting data from the database!");

        Class<? extends DBEntity> entityClass = entity.getClass();

        //Convenience variables
        String table = entityClass.getSimpleName();
        Field pkField = DBEntity.getPKField(entityClass);
        List<Field> fields = Arrays.asList(entityClass.getDeclaredFields());

        if(fields.size() <= 0)
            throw new DALException(String.format("No fields found in DBEntity-class '%s'", table));

        //Build String with targeted fieldnames for INSERT-Statement
        StringBuilder statementFieldBuilder = new StringBuilder();

        for(int i = 0; i < fields.size(); i++){
            Field field = fields.get(i);

            if(i > 0)
                statementFieldBuilder.append(",");

            statementFieldBuilder.append(field.getName());

        }

        //Build right count of '%s' - for prepared Values in the INSERT-Statement
        StringBuilder statementValueBuilder = new StringBuilder();

        int maxValues = fields.size();

        for(int i = 0; i < maxValues; i++){
            if(i > 0)
                statementValueBuilder.append(",");
            statementValueBuilder.append("?");
        }

        String sql = String.format("INSERT INTO %s (%s) VALUES (%s)", table, statementFieldBuilder.toString(), statementValueBuilder.toString());
        ResultSet generatedKeys = null;
        PreparedStatement ps = null;

        try{
            ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            try{
                //Prepare statement with the given ID
                for(int i = 0; i < fields.size(); i++){
                    Field field = fields.get(i);
                    field.setAccessible(true);
                    ps.setObject(i + 1, field.get(entity));
                }
            }catch(IllegalAccessException iae){
                throw new DALException("Access-Error in DBEntity occurred!", iae);
            }

            int result = ps.executeUpdate();
            if (result == 0) {
                throw new DALException(String.format("Insert-Statement failed: %s", sql));
            }

            generatedKeys = ps.getGeneratedKeys();
            if(generatedKeys.next())
                return generatedKeys.getObject(1);

        }catch(SQLException sqle){
            throw new DALException(String.format("Following Statement could not be executed: '%s'", sql), sqle);
        }finally{
            try{
                if(generatedKeys != null && !generatedKeys.isClosed())
                    generatedKeys.close();

                if(ps != null)
                    ps.close();

            }catch(SQLException sqle){
                throw new DALException("Statement- or ResultSet-Object could not be closed...", sqle);
            }
        }

        return entity.getID();
    }

    @Override
    public boolean updateEntity(DBEntity entity) throws DALException {
        if(!isConnected())
            throw new DALException("Connection not established! Use method connect() before requesting data from the database!");

        if(entity.getID() == null)
            throw new DALException("Entity has no valid ID (= null) - Entities must have a valid PK-ID for an update in it's database!");

        Class<? extends DBEntity> entityClass = entity.getClass();

        //Convenience variables
        String table = entityClass.getSimpleName();
        Field pkField = DBEntity.getPKField(entityClass);
        List<Field> fields = Arrays.asList(entityClass.getDeclaredFields());

        if(fields.size() <= 0)
            throw new DALException(String.format("No fields found in DBEntity-class '%s'", table));

        //Build String with targeted fieldnames for INSERT-Statement
        StringBuilder statementFieldBuilder = new StringBuilder();

        for(int i = 0; i < fields.size(); i++){
            Field field = fields.get(i);

            if(i > 0)
                statementFieldBuilder.append(",");

            statementFieldBuilder.append(field.getName() + "=?");

        }

        String sql = String.format("UPDATE %s SET %s WHERE %s = ?", table, statementFieldBuilder.toString(), pkField.getName());
        PreparedStatement ps = null;
        try{
            ps = con.prepareStatement(sql);

            try{
                //Prepare statement with the given ID
                for(int i = 0; i < fields.size(); i++){
                    Field field = fields.get(i);
                    field.setAccessible(true);
                    ps.setObject(i + 1, field.get(entity));
                }

                //Set ID-Parameter in WHERE clausel
                pkField.setAccessible(true);
                ps.setObject(fields.size() + 1, pkField.get(entity));

            }catch(IllegalAccessException iae){
                throw new DALException("Access-Error in DBEntity occurred!", iae);
            }

            int result = ps.executeUpdate();
            ps.close();

            if(result > 0)
                return true;

            return false;
        }catch(SQLException sqle){
            throw new DALException(String.format("Following Statement could not be executed: '%s'", sql), sqle);
        }finally{
            try{
                if(ps != null)
                    ps.close();
            }catch(SQLException sqle){
                throw new DALException("Statement-Object could not be closed...", sqle);
            }
        }

    }

    @Override
    public boolean deleteEntity(Object id, Class<? extends DBEntity> entityClass) throws DALException {
        if(!isConnected())
            throw new DALException("Connection not established! Use method connect() before requesting data from the database!");

        //Convenience variables
        String table = entityClass.getSimpleName();
        Field pkField = DBEntity.getPKField(entityClass);
        List<Field> fields = Arrays.asList(entityClass.getDeclaredFields());

        //Check if PK-field was found
        if(pkField == null)
            throw new DALException(String.format("No id-Field alias '%s' with classtype '%s' found in class '%s'", table.toLowerCase()+"ID",id.getClass(), table));

        //Check if key has the same type as PK-field
        if(!pkField.getType().equals(id.getClass()))
            throw new DALException(String.format("Given key has not the same type as the primary-key-field of classtype '%s'", table));

        //DELETE FROM angebot WHERE angebotID = 1;
        String sql = String.format("DELETE FROM %s WHERE %s = ?", table, pkField.getName());
        PreparedStatement ps = null;
        try{
            ps = con.prepareStatement(sql);

            //Prepare ?-parameters
            ps.setObject(1, id);

            int result = ps.executeUpdate();
            if(result > 0)
                return true;
            return false;
        }catch(SQLException sqle){
            throw new DALException(String.format("Following Statement could not be executed: '%s'", sql), sqle);
        }finally{
            try{
            if(ps != null)
                ps.close();

            }catch(SQLException sqle){
                throw new DALException("PreparedStatetment-Object could not be closed correctly...", sqle);
            }
         }
    }

    @Override
    public <T extends DBEntity> List<T> getEntityList(Class<T> entityClass) throws DALException{
        return getEntitiesBy(null, entityClass);
    }

    @Override
    public <T extends DBEntity> List<T> getEntitiesBy(WhereChain where, Class<T> entityClass) throws DALException {
        if(!isConnected())
            throw new DALException("Connection not established! Use method connect() before requesting data from the database!");

        List<Field> fields = DBEntity.getAllDeclaredFields(entityClass);
        Field pkField = DBEntity.getPKField(entityClass);
        String table = entityClass.getSimpleName();

        //Check fields
        if(fields.size() <= 0)
            throw new DALException(String.format("No fields in DBEntity '%s' declared (so no mapping possible)", table));

        StringBuilder builder = new StringBuilder();

        for(int i = 0; i < fields.size(); i++){
            String f = fields.get(i).getName();
            if(i > 0)
                builder.append(",");

            if(f.equals(pkField.getName())){
                builder.append(table + ".").append(f);
            }
            else{
                builder.append(f);
            }

        }

        ResultSet rs = null;
        Statement st = null;

        //TODO: alle Felder immer mit FQ-Name ansprechen (tablename.fieldname)
        //TODO: createJoinClausel() hierher auslagern , Methode generell loeschen und mit ?-Params arbeiten
        //TODO: createWhereClausel() hierher auslagern, Methode generell loeschen und mit ?-Params arbeiten
        //SELECT * FROM angebot WHERE angebotID = 1 AND chance = 200;
        String sql = String.format("SELECT %s FROM %s %s %s", builder.toString(), table, createJoinClause(entityClass),createWhereClausel(where, entityClass));
        try{
            st = con.createStatement();
            rs = st.executeQuery(sql);

            //Instantiate a new Object of the given DBEntity
            List<T> ret = new ArrayList<T>();
            T instance;
            try{
                while(rs.next()){
                    instance = entityClass.getConstructor().newInstance();
                    for(int i = 0; i < fields.size(); i++){
                        Field f = fields.get(i);
                        f.setAccessible(true);
                        f.set(instance, rs.getObject(i + 1));
                    }
                    ret.add(instance);
                }
            } catch(Exception nsme){
                throw new DALException("DBEntity could not be instantiated!", nsme);
            }
            return ret;
        }catch(SQLException sqle){
            throw new DALException(String.format("Statement could not be executed: %s", sql), sqle);
        }finally{
            try{
                //Close ResultSet immediately
                if(rs != null && !rs.isClosed())
                    rs.close();

                if(st != null)
                    st.close();
            }catch(SQLException sqle){
                throw new DALException("Statement- or ResultSet-Object could not be closed...", sqle);
            }
        }
    }


}
