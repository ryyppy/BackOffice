package dal;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Patrick Stapfer
 * Date: 25.03.12
 * Time: 18:04
 */
public abstract class DBEntity {

    public DBEntity(){

    }

    /**
     * Returns the Primary-Key-Value of the DBEntity-object
     * This method return the proper value defined by the tableMeta-annotation.
     * If you overwrite, make sure you use the proper ID-field (equals to the tableMeta-annotation!)
     * For instance:
     * @TableMeta(pkFieldName="angebotID")
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
     * @throws DALException - If there is no tableMeta-Annotation found
     * @return Value of the primary-key-field
     */
    public Object getID() throws DALException{
        Class<? extends DBEntity> entityClass = this.getClass();
        TableMeta tableMeta = DBEntity.getTableMeta(entityClass);
        String pkFieldName = tableMeta.pkFieldName();

        for(Field f : entityClass.getDeclaredFields()){
            String fieldname = f.getName();
            if(fieldname.equals(pkFieldName)){
                try{
                    f.setAccessible(true);
                    return f.get(this);
                }catch(IllegalAccessException iae){
                    iae.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * Sets the primary-key-value of the DBEntity-object
     * This method sets the proper field defined by the tableMeta-annotation.
     * If you overwrite, make sure you use the proper ID-field (equals to the tableMeta-annotation!)
     * For instance:
     * @TableMeta(pkFieldName="angebotID")
     * public class Angebot{
     *     private int angebotID;
     *
     *     public void setAngebotID(int angebotID){
     *          this.angebotID = angebotID;
     *     }
     *
     *     public void setID(Object id){
     *          if(id instanceof Integer)
     *              setAngebotID((int)id);
     *          throw IllegalArgumentException("Wrong ID-Type!");
     *     }
     * }
     * @param id
     * @throws DALException - If there is no tableMeta-Annotation found
     */
    public void setID(Object id) throws DALException{
        Class<? extends DBEntity> entityClass = this.getClass();
        TableMeta tableMeta = DBEntity.getTableMeta(entityClass);
        String pkFieldName = tableMeta.pkFieldName();

        for(Field f : entityClass.getDeclaredFields()){
            String fieldname = f.getName();
            if(fieldname.equals(pkFieldName)){
                try{
                    f.setAccessible(true);
                    f.set(this, id);
                }catch(IllegalAccessException iae){
                    iae.printStackTrace();
                }
            }
        }
    }
    /**
     * Returns the Reflection-Field of a DBEntity-class-definition, which represents the Primary-Key in its database
     * @param entityClass - DBEntity-Class for retrieving the PK field
     * @throws DALException - If there is no tableMeta-Annotation found
     * @return Field-Definition of the PK-Field or NULL if it could not be retrieved
     */
    public static Field getPKField(Class<? extends DBEntity> entityClass) throws DALException{
        TableMeta tableMeta = DBEntity.getTableMeta(entityClass);
        String pkFieldName = tableMeta.pkFieldName();

        for(Field f : entityClass.getDeclaredFields()){
            String fieldname = f.getName();
            if(fieldname.equals(pkFieldName))
                return f;
        }

        throw new DALException(String.format("DBEntity '%s' has no PK-Field named '%s' (Check TableMeta-Annotation for typos)!", entityClass.getSimpleName(), pkFieldName));
    }

    /**
     * Returns the defined tableMeta-Annotation of the given class
     * @param entityClass - DBEntity - class definition, from which the annotation should be retrieved
     * @throws DALException - If there is no tableMeta-Annotation found
     *
     * @return tableMeta-Annotation of the given entityClass
     */
    public static TableMeta getTableMeta(Class<? extends DBEntity> entityClass) throws DALException{
        TableMeta tableMeta =  entityClass.getAnnotation(TableMeta.class);

        if(tableMeta != null && tableMeta instanceof TableMeta)
            return tableMeta;

        throw new DALException(String.format("DBEntity '%s' has no TableMeta - Annotation defined! Cannot retrieve PK-Field...", entityClass.getSimpleName()));
    }

    public static List<Field> getAllDeclaredFields(Class<? extends DBEntity> entityClass){
        List<Field> fields = new ArrayList<Field>();
        List<String> fieldNames = new ArrayList<String>();

        Class<?> superClazz = entityClass.getSuperclass();

        for(Field f : entityClass.getDeclaredFields()){
            fields.add(f);
            fieldNames.add(f.getName());
        }

        if(!superClazz.getClass().equals(Object.class)){
            for(Field f : superClazz.getDeclaredFields()){
                if(!fieldNames.contains(f.getName()))
                    fields.add(f);
            }
        }

        return fields;
    }

    public static Class<? extends DBEntity> getDBSuperclass(Class<? extends DBEntity> entityClass){
        Class<?> superClazz = entityClass.getSuperclass();

        if(!superClazz.getClass().equals(Object.class))
            return (Class<? extends DBEntity>)superClazz;

        return null;
    }
}
