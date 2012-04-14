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
    public <T extends DBEntity> T getEntityByID(Object id, Class<T> entityClass) throws DALException{
        //Create a WHERE clausel out of the PKField (for instance "WHERE angebotID = 1")
        String pkName = DBEntity.getPKField(entityClass).getName();
        WhereChain where = new WhereChain(pkName, WhereOperator.EQUALS, id);

        List<T> result = getEntitiesBy(where, entityClass);

        if(result == null || result.isEmpty())
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

        //Builder for the Statement Elements
        StringBuilder builder = null;

        //SQL-Statement-Elements
        String fieldClausel = null;
        String joinClausel = "";
        String whereClausel = "";

        //Classes
        Class<? extends DBEntity> entitySuper = DBEntity.getDBSuperclass(entityClass);

        //PKFields of the classes
        Field entityPk = DBEntity.getPKField(entityClass);
        Field superPk = null;

        //Fielddeclarations
        List<Field> fields = DBEntity.getAllDeclaredFields(entityClass);

        //Tablenames
        String entityTable = entityClass.getSimpleName();
        String superTable = null;

        //Value-List for the PreparedStatement
        List<Object> preparedValues = new ArrayList<Object>();

        //Set some information about the superclass and also the join-clausel
        if(entitySuper != null && !entitySuper.equals(DBEntity.class)){
            superTable = entitySuper.getSimpleName();
            superPk = DBEntity.getPKField(entitySuper);
            joinClausel = String.format("JOIN %s ON %s.%s = %s.%s", superTable, superTable, superPk.getName(), entityTable, entityPk.getName());
        }

        //Creating the fieldClausel
        builder = new StringBuilder();
        for(int i = 0; i < fields.size(); i++){
            String f = fields.get(i).getName();
            if(i > 0)
                builder.append(",");

            if(f.equals(entityPk.getName())){
                builder.append(entityTable + ".").append(f);
            }
            else{
                builder.append(f);
            }

        }
        fieldClausel = builder.toString();

        //CREATING WHERE-CLAUSEL
        if(where != null){
            List<WhereCondition> conditions = where.getConditions();

            builder = new StringBuilder("WHERE ");

            for(int i = 0; i < conditions.size(); i++){
                WhereCondition condition = conditions.get(i);
                String field = condition.getFieldname();
                Object value = condition.getValue();

                String referenceTable = null;
                Field f = null;
                try{
                    f = entityClass.getDeclaredField(field);
                    referenceTable = entityTable;
                }catch(NoSuchFieldException e){
                    try{
                        f = entitySuper.getDeclaredField(field);
                        referenceTable = superTable;
                    }catch(NoSuchFieldException nsfe){
                        throw new DALException(String.format("Field '%s' not found in DBEntity-definition '%s' - Check if there are typos!", field, entityTable), nsfe);
                    }
                }

                //Ensure that the first element will not have a leading chainer
                if(i > 0){
                    //Add the chainerword (AND / OR)
                    WhereChain.Chainer chainer = condition.getChainer();
                    builder.append(" ").append(chainer).append(" ");
                }
                //Table.fieldname >= ?
                builder.append(String.format("%s.%s %s ?", referenceTable, field, condition.getOperator()));

                //Add the value for parameter-setting in the PreparedStatement
                preparedValues.add(value);
            }
            whereClausel = builder.toString();
        }

        ResultSet rs = null;
        PreparedStatement ps = null;

        //SELECT Rechnung.datum,... JOIN Rechnung ON Rechnung.rechnungID = Eingangsrechnung.rechnungID WHERE angebotID = ? AND chance = ?;
        String sql = String.format("SELECT %s FROM %s %s %s", fieldClausel, entityTable, joinClausel,whereClausel);
        System.err.println(sql);
        try{

            ps = con.prepareStatement(sql);

            for(int i = 0; i < preparedValues.size(); i++){
                ps.setObject(i + 1, preparedValues.get(i));
            }

            rs = ps.executeQuery();

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

                if(ps != null)
                    ps.close();
            }catch(SQLException sqle){
                throw new DALException("Statement- or ResultSet-Object could not be closed...", sqle);
            }
        }
    }
}
