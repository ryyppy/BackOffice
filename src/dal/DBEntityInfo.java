package dal;

/**
 * Created by IntelliJ IDEA.
 * User: Patrick Stapfer
 * Date: 16.04.12
 * Time: 15:29
 */

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

/**
 * This class represents reflection-data of a DBEntity-object, which will be used for simple data
 * caching for it's reflection-parts.
 * A EntityClassInfo is represented by it's set clazz - that means two EntityClassInfo objects are equal, if
 * they have equal clazz-objects set.
 * Also, this class offers a factory-method, where instances for specific classes can be retrieved.
 * Everytime a DBEntityInfo is retrieved for a class for the first time, a new instance of
 * this object will be created and also cached in an internal List (for performance reason)
 *
 * Example for retrieving data from the factory:
 * DBEntityInfo classInfo = DBEntityInfo.get(Angebot.class);
 * List<Field> allDeclaredFields = classInfo.getMergedFields();
 * Field entityPK = classInfo.getEntityPk();
 *
 */
public class DBEntityInfo {
    //Caching-Vector for preloaded Class-Information
    private static Vector<DBEntityInfo> classCache = new Vector<DBEntityInfo>();

    //Class-Defintion of the entity
    private Class<? extends DBEntity> clazz = null;

    //If this EntityClass has a superclass, which is not DBEntity itself
    private Class<? extends DBEntity> superClazz = null;

    //All fields declared on the entity and its inherited parts of it's superclass
    private List<Field> mergedFields = null;

    //Only fields, which are declared in the entityclass
    private List<Field> entityFields = null;

    //Only fields, which are declared in the superclass
    private List<Field> superFields = null;

    //PKField of the entityclass (has to be set, because of TableMeta-Relation)
    private Field entityPk = null;

    //PKField of the superclazz (if any existent!)
    private Field superPk = null;

    /**
     * This method sets it's clazz with setClazz() - For more information read the method-description of the named method.
     * @param clazz - Class, which represent the DBEntityInfo in it's reflected data
     * @throws DALException - If there is an error in reflecting the class (if there are PK-errors or such)
     */
    private DBEntityInfo(Class<? extends DBEntity> clazz) throws DALException{
        setClazz(clazz);
    }

    /**
     * This method enforces the DBEntityInfo-Object to reload all specific data for the given class.
     * That means, the whole reflection-procedure will be made, which is very costy in performance, so this
     * method should not be called very often!
     * @param clazz - Class, which represent the DBEntityInfo in it's reflected data
     * @throws DALException - If there is an error in reflecting the class (if there are PK-errors or such)
     */
    public void setClazz(Class<? extends DBEntity> clazz) throws DALException{
        //Runtime-Variables
        List<Field> mergedFields = null;
        List<Field> entityFields = null;
        List<Field> superFields = null;
        Field entityPk = null;
        Field superPk = null;

        //Retrieve superclass
        Class superClazz = clazz.getSuperclass();

        for(Class foo : superClazz.getClasses())
            System.out.println(foo);

        //Unset superClazz if it is just "DBEntity"
        if(superClazz.equals(DBEntity.class))
            superClazz = null;

        //Retrieve declared Fields
        mergedFields = new ArrayList<Field>(Arrays.asList(clazz.getDeclaredFields()));
        entityFields = new ArrayList<Field>(Arrays.asList(clazz.getDeclaredFields()));

        //Merge fields of the superclass (if existent)
        if(superClazz != null){
            superFields = new ArrayList<Field>(Arrays.asList(superClazz.getDeclaredFields()));
            //Only merge fields, which are not common in clazz
            for(Field superField : superFields){
                boolean alreadyExistent = false;
                for(Field entityField : mergedFields){
                    if(entityField.getName() == superField.getName()){
                        alreadyExistent = true;
                        break;
                    }
                }
                if(!alreadyExistent)
                    mergedFields.add(superField);
            }
        }

        //Retrieve PK-Fields of the clazz
        entityPk = extractPKField(clazz, mergedFields);

        //Retrieve PK-field of the superclass (if existent)
        if(superClazz != null)
            superPk = extractPKField(superClazz, superFields);

        //Set instance variables with computed values
        this.clazz = clazz;
        this.superClazz = superClazz;
        this.mergedFields = mergedFields;
        this.entityFields = entityFields;
        this.superFields = superFields;
        this.entityPk = entityPk;
        this.superPk = superPk;
    }

    /**
     * Retrieves the entityClazz which was provided in the constructor or
     * set by setClazz().
     * @return Class, which is represented by this DBEntityInfo-object
     */
    public Class<? extends DBEntity> getClazz() {
        return clazz;
    }

    /**
     * Returns the superClazz of the entityClazz.
     * If entityClazz is already a subclass of DBEntity, then superClazz will be null!
     * @return superclass of entityclass, or null if nonexistent
     */
    public Class<? extends DBEntity> getSuperClazz() {
        return superClazz;
    }

    /**
     * Returns the merged list of superClazz and entityClazz fields.
     * That means, that list represent all inherited and self-declared values of entityClazz.
     * @return List of merged fields of entityClazz and superClazz
     */
    public List<Field> getMergedFields() {
        return this.mergedFields;
    }

    public Field getSuperPk() {
        return superPk;
    }

    public Field getEntityPk() {
        return entityPk;
    }

    public List<Field> getEntityFields() {
        return entityFields;
    }

    public List<Field> getSuperFields() {
        return superFields;
    }

    public boolean hasDBSuperclass(){
        if(superClazz != null)
            return true;
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        DBEntityInfo that = (DBEntityInfo) o;

        if (clazz != null ? !clazz.equals(that.clazz) : that.clazz != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return clazz != null ? clazz.hashCode() : 0;
    }


    /**
     * Retrieves the PK-Field from the given class,it's declaredFields and the with it's defined TableMeta-Annotation
     * Make sure the given entityFields are really part of this class.
     * @param entityClass - DBEntity-kind class, which should be parsed for it's PK-field
     * @param entityFields - List of Fields provided in the given entityClass
     * @return PK-Reflection Field (if not found an exception occur)
     * @throws DALException - If the tableMeta-annotation or the pkField could not be found
     */
    private Field extractPKField(Class<? extends DBEntity> entityClass, List<Field> entityFields) throws DALException{
        if(entityClass == null)
            throw new NullPointerException("Parameter entityClass must not be null!");

        TableMeta tableMeta =  entityClass.getAnnotation(TableMeta.class);

        if(tableMeta == null)
            throw new DALException(String.format("DBEntity '%s' has no TableMeta - Annotation defined! Cannot retrieve PK-Field...", entityClass.getSimpleName()));

        String pkFieldName = tableMeta.pkFieldName();

        for(Field f : entityFields){
            String fieldname = f.getName();
            if(fieldname.equals(pkFieldName))
                return f;
        }

        throw new DALException(String.format("DBEntity '%s' has no PK-Field named '%s' (Check TableMeta-Annotation for typos)!", entityClass.getSimpleName(), pkFieldName));
    }

    /**
     * Returns reflection data as DBEntityInfo - object for the given entityClass.
     * If the class-info is retrieved the first time, the data will be reflected and then cached for later access.
     * @param entityClass - Class, for which the info should be returned
     * @return DBENtityClassInfo-object for the specific class
     * @throws DALException - If there is an error while creating a new DBEntityInfo-object (for a first-time-access)
     */
    public static DBEntityInfo get(Class<? extends DBEntity> entityClass) throws DALException{
        //Check if there is a DBEntityInfo already cached and return it
        for(DBEntityInfo eci : classCache){
            if(eci.getClazz().equals(entityClass))
                return eci;
        }

        //Else generate a new DBECI and cache it
        DBEntityInfo eci = new DBEntityInfo(entityClass);
        classCache.add(eci);

        return eci;
    }

    @Override
    public String toString(){
        String superName = (superClazz != null? superClazz.getSimpleName() : null);
        String superPkName = (superPk != null? superPk.getName() : null);
        return String.format("Class: %s - Superclass: %s - DeclaredFieldCount: %d - ClassPK: %s - SuperPK: %s", clazz.getSimpleName(), superName, mergedFields.size(), entityPk.getName(), superPkName);
    }
}
