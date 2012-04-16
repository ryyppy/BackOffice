package dal;

import java.lang.reflect.Field;

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
        DBEntityInfo eci = DBEntityInfo.get(this.getClass());
        String pkName = eci.getEntityPk().getName();

        for(Field f : eci.getMergedFields()){
            String fieldname = f.getName();
            if(fieldname.equals(pkName)){
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
        DBEntityInfo eci = DBEntityInfo.get(this.getClass());
        String pkName = eci.getEntityPk().getName();

        for(Field f : eci.getMergedFields()){
            String fieldname = f.getName();
            if(fieldname.equals(pkName)){
                try{
                    f.setAccessible(true);
                    f.set(this, id);
                }catch(IllegalAccessException iae){
                    iae.printStackTrace();
                }
            }
        }
    }
}
