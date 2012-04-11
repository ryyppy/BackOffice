package dal;

import java.lang.reflect.Field;

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

    /**
     * Returns the Primary-Key-Value of the DBEntity-object
     * This method must return the proper value of a get...ID() method
     * For instance:
     * public class Angebot{
     *     private int angebotID;
     *
     *     public int getAngebotID(){
     *         return angebotID;
     *     }
     *
     *     public Object getID(){
     *         return getAngebotID();
     *     }
     * }
     * @return Value of the primary-key-field
     */
    public abstract Object getID();

    /**
     * Returns the Reflection-Field of a DBEntity-class-definition, which represents the Primary-Key in its database
     * @param entityClass - DBEntity-Class for retrieving the PK field
     * @return Field-Definition of the PK-Field or NULL if it could not be retrieved
     */
    public static Field getPKField(Class<? extends DBEntity> entityClass){
        String table = entityClass.getSimpleName();

        for(Field f : entityClass.getFields()){
            if(f.getType().equals(f.getName().equals(table.toLowerCase()+"ID")))
                return f;
        }
        return null;
    }
}
