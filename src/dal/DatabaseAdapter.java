package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * This abstract class defines standard-functionality, which a concrete DatabaseAdapter should deploy
 * Each DatabaseAdapter should deploy a forwarder to the super-class-constructor to encapsulate connection-information.
 * You may read through the DBEntity-Class and TableMeta-Annotation to get a hint how the data-encapsulation between
 * POJOS and database works. It is build to represent a simple ORM-Mapper.
 * Methods should be that generic, that you only have to use DBEntities or class-definitions of DBEntities. The methods
 * should build their SQL-Statements with field-reflection of the class-definition and they should retrieve PK-information
 * over the TableMeta-Annotation.
 * There should only be DALExceptions thrown in the scope of a DatabaseAdapter, so catch all SQLExceptions, etc. and
 * bind them to a DALException.
 */
public abstract class DatabaseAdapter {
    private String jdbcDriver = null;
    private String dbUser = null;
    private String dbPassword = null;
    private String dbUrl = null;
    protected Connection con = null;

    private boolean connected = false;
    private boolean transaction = false;

    /**
     * Initializes the Database-Driver and saves connection-data for connecting to the database.
     * To open a connection to the database use the connect() - Method!
     * @param jdbcDriver - Drivername like in following example: com.mysql.jdbc.Driver
     * @param dbUser - Database-user with right privileges
     * @param dbPassword - Password of the database user
     * @param dbUrl - Driverspecific connection-string with URL and databasename (for instance: jdbc:mysql://localhost/mydatabase")
     */
    protected DatabaseAdapter(String jdbcDriver, String dbUser, String dbPassword, String dbUrl){
        this.jdbcDriver = jdbcDriver;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
        this.dbUrl = dbUrl;

        try {
            Class.forName(jdbcDriver);
        } catch (ClassNotFoundException ex) {
            System.err.println("Fehler beim Laden des JDBC-Treibers");
            return;
        }
    }

    /**
     * Connects to the database with parameters which were given on creating this object.
     * It is recommended to open a connection only for the shortest amount of time, to prevent locking use the
     * commit() and disconnect() method, if you want your database-queries persistant.
     * @throws SQLException
     */
    public void connect() throws DALException {
        try{
            if (con == null) {
                con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
                connected = true;
            }
        }catch(SQLException sqle){
            throw new DALException("Could not connect!", sqle);
        }
    }

    /**
     * This method begins a DML-Transaction, for updating, inserting and deleting datasets.
     * After calling this method, you may call commit() or rollback() to make your queries persistant!
     * If you close the connection without committing, the transaction will be rolled back (no changes occur in the
     * database!)
     * @throws DALException - If there is a database-error
     */
    public void beginTransaction() throws DALException{
        try{
            con.setAutoCommit(false);
            transaction = true;
        }catch(SQLException sqle){
            throw new DALException("Transaction could not be initialized...", sqle);
        }
    }

    /**
     * Commit all DML-Queries, which were fired since beginTransaction().
     * It will not commit, if there is no transaction started with beginTransaction().
     * After calling this method you may call beginTransaction() to start a new transaction.
     * @throws DALException - If a database-error occurs
     */
    public void commit() throws DALException{
        try{
            if(transaction)
                con.commit();
        }catch(SQLException sqle){
            throw new DALException("Error while trying to commit!", sqle);
        }finally{
            transaction = false;
        }
    }

    /**
     * Revert all DML-Queries, which were fired since beginTransaction().
     * It will not rollback, if there is no transaction started with beginTransaction().
     * After calling this method you may call beginTransaction() to start a new transaction.
     * @throws DALException - If a database-error occurs
     */
    public void rollback() throws DALException{
        try{
            if(transaction)
                con.rollback();
        }catch(SQLException sqle){
            throw new DALException("Error while trying to rollback!", sqle);
        }finally{
            transaction = false;
        }
    }

    /**
     * Closes the connection to the database, if connected.
     * Make sure this method is called after each Transaction, or there will be connection-daedlocks!
     * @throws DALException - If an close-error occurs
     */
    public void disconnect() throws DALException {
        if (con != null) {
            rollback();
            try{
                con.close();
            }catch(SQLException sqle){
                throw new DALException("Error while closing database-connection!", sqle);
            }finally{
                con = null;
                connected = false;
            }
        }
    }

    /**
     * Gives information wether the DatabaseAdapter has an active connection established or not
     * @return True, if an connection exists, otherwise false
     */
    public boolean isConnected(){
        return connected;
    }

    /**
     * Retrieves a capsulated data-object with all fields and the given id (foreign-keys only with ID - no eager loading or something)
     * and returns it.
     * Tableinformation will be retrieved by the DBEntity-Class-Definition by reflection. Be sure the DBEntity has a proper
     * pkField defined (see tableMeta-Annotation). The db-table has to be named as the reflected class!
     * Be sure to call connect() first, before you use this method, or it will fail with an connection-error.
     * @param id - ID of the dataset in the connected database
     * @param entityClass - Class-definition of the needed dataset
     * @param <T> - Entity-Class, which should be returned and also reflected (a DBEntity-model-class)
     * @return The wished DBEntity-object with the wished ID and it's data or NULL if there is no such ID found
     * @throws DALException - If a database-error occurs
     */
    public abstract <T extends DBEntity> T getEntityByID(Object id, Class<T> entityClass) throws DALException;


    /**
     * Adds an capsulated DBEntity-object to the connected database. If the PK-field is null, then it assumes the pkField
     * to be an autoincrement-field!
     * Tableinformation will be retrieved by the DBEntity-Class-Definition by reflection. Be sure the DBEntity has a proper
     * pkField defined (see tableMeta-Annotation). The db-table has to be named as the reflected class!
     * Be sure to call connect() and beginTransaction() first, before you use this method, or it will fail with an connection-error.
     * After inserting data you may call commit() to make your changes persistant!
     * @param entity - DBEntity, which should be made persistant
     * @return true, if the dataset could be inserted. Otherwise false
     * @throws DALException - If a database-error occurs
     */
    public abstract Object addEntity(DBEntity entity) throws DALException;

    /**
     * Updates an object with the given ID (see DBEntity.setID() / getID()) in the connected database. The dataset with
     * the given ID has to exist or it will fail with an exception!
     * Tableinformation will be retrieved by the DBEntity-Class-Definition by reflection. Be sure the DBEntity has a proper
     * pkField defined (see tableMeta-Annotation). The db-table has to be named as the reflected class!
     * Be sure to call connect() and beginTransaction() first, before you use this method, or it will fail with an connection-error.
     * After updating your data you may call commit() to make your changes persistant!
     * @param entity - DBEntity (with existing pk-value) which should be changed in the database
     * @return True, if the update was successful, otherwise false
     * @throws DALException - If a database-error occurs
     */
    public abstract boolean updateEntity(DBEntity entity) throws DALException;

    /**
     * Deletes an dataset with the given ID from the connected database. The dataset has to exist, before you can delete it
     * or this method will fail with an exception!
     * Tableinformation will be retrieved by the DBEntity-Class-Definition by reflection. Be sure the DBEntity has a proper
     * pkField defined (see tableMeta-Annotation). The db-table has to be named as the reflected class!
     * Be sure to call connect() and beginTransaction() first, before you use this method, or it will fail with an connection-error.
     * After deleting a dataset you may call commit() to make your changes persistant!
     * @param id - Database-ID of the dataset you want to delete
     * @param entityClass - Class-definition of the needed dataset
     * @return True, if the deletion was successful, otherwise false
     * @throws DALException
     */
    public abstract boolean deleteEntity(Object id, Class<? extends DBEntity> entityClass) throws DALException;

    /**
     * Get a list of all DBEntities from the database.
     * Tableinformation will be retrieved by the DBEntity-Class-Definition by reflection. Be sure the DBEntity has a proper
     * pkField defined (see tableMeta-Annotation). The db-table has to be named as the reflected class!
     * Be sure to call connect() first, before you use this method, or it will fail with an connection-error.
     * @param entityClass -  Class-definition of the datasets which should be retrieved as a list
     * @param <T> - Entity-Class, which should be returned in a list (a DBEntity-model-class)
     * @return List of the wished data-classes with the aided class-type
     * @throws DALException - If an database-error occurs
     */
    public abstract <T extends DBEntity> List<T> getEntityList(Class<T> entityClass) throws DALException;

    /**
     * Get a list of all DBEntities from the database which fulfill the where-dependencies.
     * Tableinformation will be retrieved by the DBEntity-Class-Definition by reflection. Be sure the DBEntity has a proper
     * pkField defined (see tableMeta-Annotation). The db-table has to be named as the reflected class! Also be sure that
     * the where-dependency-field exist in the given class-definition or an exception will be thrown.
     * Be sure to call connect() first, before you use this method, or it will fail with an connection-error.
     * @param where - WhereChain-Object (see definition) or null, for no WHERE-Clausle (would retrieve the whole table-data)
     * @param entityClass - Class-definition of the datasets which should be retrieved as a list
     * @param <T> - Entity-Class, which should be returned in a list (a DBEntity-model-class)
     * @return List of the wished data-classes with the aided class-type according to the WhereChain
     * @throws DALException - If field-definitions are not properly defined in the given class or if a database-error occurs
     */
    public abstract <T extends DBEntity> List<T> getEntitiesBy(WhereChain where, Class<T> entityClass) throws DALException;
}
