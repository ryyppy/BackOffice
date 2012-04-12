package dal;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


public class MysqlAdapter extends DatabaseAdapter {

	public MysqlAdapter(String dbUser, String dbPassword,String dbUrl) {
		super("com.mysql.jdbc.Driver", dbUser, dbPassword, "jdbc:mysql://" + dbUrl);
	}

    @Override
    public <T extends DBEntity> T getEntityByID(Object id, Class<T> entityClass) throws DALException{
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

        Iterator<Field> iter = fields.iterator();

        StringBuilder builder = new StringBuilder(iter.next().getName());
        while(iter.hasNext()){
            builder.append(",").append(iter.next().getName());
        }

        ResultSet rs = null;

        try{
            PreparedStatement ps = con.prepareStatement(String.format("SELECT %s FROM %s WHERE %s = ?", builder.toString(), table, table+"ID"));

            //Prepare statement with the given ID
            ps.setObject(1, id);
            rs = ps.executeQuery();

            //Instantiate a new Object of the given DBEntity
            T ret;
            try{
                ret = entityClass.getConstructor().newInstance();

                while(rs.next()){
                    for(int i = 0; i < fields.size(); i++){
                        Field f = fields.get(i);
                        f.setAccessible(true);
                        f.set(ret, rs.getObject(i + 1));
                    }
                }
            } catch(Exception nsme){
                throw new DALException("DBEntity could not be instantiated!", nsme);
            }
            return ret;
        }catch(SQLException sqle){
            throw new DALException("Statement could not be executed...", sqle);
        }finally{
            try{
                //Close ResultSet immediately
                if(!rs.isClosed())
                    rs.close();
            }catch(SQLException sqle){
                sqle.printStackTrace();
            }
        }
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

        ResultSet generatedKeys = null;
        try{
            String sql = String.format("INSERT INTO %s (%s) VALUES (%s)", table, statementFieldBuilder.toString(), statementValueBuilder.toString());
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

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
            throw new DALException("Insert-Statement could not be executed...", sqle);
        }finally{
            if(generatedKeys != null){
                try{
                    generatedKeys.close();
                }catch(SQLException sqle){
                    sqle.printStackTrace();
                }
            }
        }

        throw new DALException("Insert-Statement could not be executed...");
    }

    @Override
    public void updateEntity(DBEntity entity) throws DALException {
        if(!isConnected())
            throw new DALException("Connection not established! Use method connect() before requesting data from the database!");



    }

    @Override
    public void deleteEntity(Object id, Class<? extends DBEntity> entityClass) throws DALException {
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

        Iterator<Field> iter = fields.iterator();

        StringBuilder builder = new StringBuilder(iter.next().getName());
        while(iter.hasNext()){
            builder.append(",").append(iter.next().getName());
        }

        ResultSet rs = null;

        try{
            String sql = String.format("SELECT %s FROM %s WHERE %s = ?", builder.toString(), table, table+"ID");
            PreparedStatement ps = con.prepareStatement(sql);


        }catch(SQLException sqle){
            throw new DALException("Statement could not be executed...", sqle);
        }
    }

    @Override
    public <T extends DBEntity> List<T> getEntityList(Class<T> entityClass) throws DALException{
        if(!isConnected())
            throw new DALException("Connection not established! Use method connect() before requesting data from the database!");

        List<Field> fields = Arrays.asList(entityClass.getDeclaredFields());
        Field idField = null;

        String table = entityClass.getSimpleName();

        if(fields.size() <= 0)
            throw new DALException(String.format("No fields in DBEntity '%s' declared (so no mapping possible)", table));

        Iterator<Field> iter = fields.iterator();

        StringBuilder builder = new StringBuilder(iter.next().getName());
        while(iter.hasNext()){
            builder.append(",").append(iter.next().getName());
        }

        ResultSet rs = null;

        try{
            Statement st = con.createStatement();

            rs = st.executeQuery(String.format("SELECT %s FROM %s", builder.toString(), table));


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
            throw new DALException("Statement could not be executed...", sqle);
        }finally{
            try{
                //Close ResultSet immediately
                if(!rs.isClosed())
                    rs.close();
            }catch(SQLException sqle){
                sqle.printStackTrace();
            }
        }
    }
}
