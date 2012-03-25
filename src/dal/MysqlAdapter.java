package dal;

import org.hamcrest.internal.ArrayIterator;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class MysqlAdapter extends DatabaseAdapter {

	public MysqlAdapter(String dbUser, String dbPassword,String dbUrl) {
		super("com.mysql.jdbc.Driver", dbUser, dbPassword, "jdbc:mysql://" + dbUrl);
	}

    public DBEntity getEntityByID(Object id, Class<? extends DBEntity> entityClass) throws SQLException{
        Field[] fields = entityClass.getDeclaredFields();
        Field idField = null;

        String table = entityClass.getSimpleName();

        if(fields.length <= 0)
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

        ArrayIterator iter = new ArrayIterator(fields);

        StringBuilder builder = new StringBuilder(((Field) iter.next()).getName());
        while(iter.hasNext()){
            builder.append(",").append(((Field)iter.next()).getName());
        }

        System.out.println(String.format("SELECT %s FROM %s WHERE %s = ?", builder.toString(), table, table+"ID"));
        PreparedStatement ps = con.prepareStatement(String.format("SELECT %s FROM %s WHERE %s = ?", builder.toString(), table, table+"ID"));

        //Prepare statement with the given ID
        ps.setObject(1, id);
        ResultSet rs = ps.executeQuery();

        //Instantiate a new Object of the given DBEntity
        DBEntity ret = null;
        try{
            ret = entityClass.getConstructor().newInstance();

            while(rs.next()){
                for(int i = 0; i < fields.length; i++){
                    Field f = fields[i];
                    f.setAccessible(true);
                    f.set(ret, rs.getObject(i+1));
                }
            }
        } catch(Exception nsme){
            throw new DALException("DBEntity could not be instantiated!", nsme);
        }finally{
            //Close ResultSet immediately
            if(!rs.isClosed())
                rs.close();
        }
        return ret;
    }
}
