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
public abstract class DatabaseAdapter implements DBAdapterInt {
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

    /* (non-Javadoc)
	 * @see dal.DBAdapterInt#connect()
	 */
    @Override
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

    /* (non-Javadoc)
	 * @see dal.DBAdapterInt#beginTransaction()
	 */
    @Override
	public void beginTransaction() throws DALException{
        try{
            con.setAutoCommit(false);
            transaction = true;
        }catch(SQLException sqle){
            throw new DALException("Transaction could not be initialized...", sqle);
        }
    }

    /* (non-Javadoc)
	 * @see dal.DBAdapterInt#commit()
	 */
    @Override
	public void commit() throws DALException{
        try{
            if(transaction)
                con.commit();
        }catch(SQLException sqle){
            throw new DALException("Error while trying to commit!", sqle);
        }finally{
            transaction = false;
            try{
                con.setAutoCommit(true);
            }catch(SQLException sqle){
                throw new DALException("Error while setting auto-commit-mode!", sqle);
            }
        }
    }

    /* (non-Javadoc)
	 * @see dal.DBAdapterInt#rollback()
	 */
    @Override
	public void rollback() throws DALException{
        try{
            if(transaction)
                con.rollback();
        }catch(SQLException sqle){
            throw new DALException("Error while trying to rollback!", sqle);
        }finally{
            transaction = false;
            try{
                con.setAutoCommit(true);
            }catch(SQLException sqle){
                throw new DALException("Error while setting auto-commit-mode!", sqle);
            }
        }
    }

    /* (non-Javadoc)
	 * @see dal.DBAdapterInt#disconnect()
	 */
    @Override
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

    /* (non-Javadoc)
	 * @see dal.DBAdapterInt#isConnected()
	 */
    @Override
	public boolean isConnected(){
        return connected;
    }

    /* (non-Javadoc)
	 * @see dal.DBAdapterInt#getEntityByID(java.lang.Object, java.lang.Class)
	 */
    @Override
	public abstract <T extends DBEntity> T getEntityByID(Object id, Class<T> entityClass) throws DALException;


    /* (non-Javadoc)
	 * @see dal.DBAdapterInt#addEntity(dal.DBEntity)
	 */
    @Override
	public abstract Object addEntity(DBEntity entity) throws DALException;

    /* (non-Javadoc)
	 * @see dal.DBAdapterInt#updateEntity(dal.DBEntity)
	 */
    @Override
	public abstract boolean updateEntity(DBEntity entity) throws DALException;

    /* (non-Javadoc)
	 * @see dal.DBAdapterInt#deleteEntity(java.lang.Object, java.lang.Class)
	 */
    @Override
	public abstract boolean deleteEntity(Object id, Class<? extends DBEntity> entityClass) throws DALException;

    /* (non-Javadoc)
	 * @see dal.DBAdapterInt#getEntityList(java.lang.Class)
	 */
    @Override
	public abstract <T extends DBEntity> List<T> getEntityList(Class<T> entityClass) throws DALException;

    /* (non-Javadoc)
	 * @see dal.DBAdapterInt#getEntitiesBy(dal.WhereChain, java.lang.Class)
	 */
    @Override
	public abstract <T extends DBEntity> List<T> getEntitiesBy(WhereChain where, Class<T> entityClass) throws DALException;
}
