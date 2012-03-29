package dal;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class MysqlAdapter extends DatabaseAdapter {

	public MysqlAdapter(String dbUser, String dbPassword,String dbUrl) {
		super("com.mysql.jdbc.Driver", dbUser, dbPassword, "jdbc:mysql://" + dbUrl);
	}

    @Override
    public void generateTable(Class<? extends DBEntity> entityClass) {
        Field[] fields = entityClass.getDeclaredFields();
        Field idField = null;

        String table = entityClass.getSimpleName();

        if(fields.length <= 0)
            throw new DALException(String.format("No fields in DBEntity '%s' declared (needs at least one ID-field)", table));

        //Integer-ID-Feld suchen (Name des ID-Felds muss das Format '[Tabellenname]ID' haben
        for(Field f : fields){
            if(f.getName().equals(table.toLowerCase()+"ID")){
                idField = f;
                break;
            }
        }

        if(idField == null)
            throw new DALException(String.format("No id-Field alias '%s' found in class '%s'", table.toLowerCase()+"ID", table));


    }

    public DBEntity getEntityByID(Object id, Class<? extends DBEntity> entityClass) throws DALException{
        List<Field> fields = Arrays.asList(entityClass.getDeclaredFields());
        Field idField = null;

        String table = entityClass.getSimpleName();

        if(fields.size() <= 0)
            throw new DALException(String.format("No fields in DBEntity '%s' declared (needs at least one ID-field)", table));

        //Integer-ID-Feld suchen (Name des ID-Felds muss das Format '[Tabellenname]ID' haben
        for(Field f : fields){
            if(f.getType().equals(id.getClass()) && f.getName().equals(table.toLowerCase()+"ID")){
                idField = f;
                break;
            }
        }
        
        if(idField == null)
            throw new DALException(String.format("No id-Field alias '%s' with Classtype '%s' found in class '%s'", table.toLowerCase()+"ID",id.getClass(), table));

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
            DBEntity ret;
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
        return null;
    }

    @Override
    public void updateEntity(DBEntity entity) throws DALException {

    }

    @Override
    public void deleteEntity(Object id, Class<? extends DBEntity> entityClass) throws DALException {

    }

    @Override
    public List<DBEntity> getEntityList(Class<? extends DBEntity> entityClass) throws DALException {
        return null;
    }
}
