package dal;

import java.sql.ResultSet;

/**
 * Created by IntelliJ IDEA.
 * User: Patrick Stapfer
 * Date: 25.03.12
 * Time: 18:04
 */
public abstract class DBEntity {
    public static final int ENTITY_NEW = 0;
    public static final int ENTITY_UPDATED = 1;
    public static final int ENTITY_DELETED = 2;

    public DBEntity(){

    }

    public DBEntity(ResultSet rs){

    }

}
