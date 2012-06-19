package dal;

import java.sql.SQLException;
import java.util.List;

public interface DBAdapterInt {

	/**
	 * Connects to the database with parameters which were given on creating this object.
	 * It is recommended to open a connection only for the shortest amount of time, to prevent locking use the
	 * commit() and disconnect() method, if you want your database-queries persistant.
	 * @throws SQLException
	 */
	public abstract void connect() throws DALException;

	/**
	 * This method begins a DML-Transaction, for updating, inserting and deleting datasets.
	 * After calling this method, you may call commit() or rollback() to make your queries persistant!
	 * If you close the connection without committing, the transaction will be rolled back (no changes occur in the
	 * database!)
	 * @throws DALException - If there is a database-error
	 */
	public abstract void beginTransaction() throws DALException;

	/**
	 * Commit all DML-Queries, which were fired since beginTransaction().
	 * It will not commit, if there is no transaction started with beginTransaction().
	 * After calling this method you may call beginTransaction() to start a new transaction.
	 * @throws DALException - If a database-error occurs
	 */
	public abstract void commit() throws DALException;

	/**
	 * Revert all DML-Queries, which were fired since beginTransaction().
	 * It will not rollback, if there is no transaction started with beginTransaction().
	 * After calling this method you may call beginTransaction() to start a new transaction.
	 * @throws DALException - If a database-error occurs
	 */
	public abstract void rollback() throws DALException;

	/**
	 * Closes the connection to the database, if connected.
	 * Make sure this method is called after each Transaction, or there will be connection-daedlocks!
	 * @throws DALException - If an close-error occurs
	 */
	public abstract void disconnect() throws DALException;

	/**
	 * Gives information wether the DatabaseAdapter has an active connection established or not
	 * @return True, if an connection exists, otherwise false
	 */
	public abstract boolean isConnected();

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
	public abstract <T extends DBEntity> T getEntityByID(Object id,
			Class<T> entityClass) throws DALException;

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
	public abstract boolean deleteEntity(Object id,
			Class<? extends DBEntity> entityClass) throws DALException;

	/**
	 * Get a list of all DBEntities from the database.
	 * Tableinformation will be retrieved by the DBEntity-Class-Definition by reflection. Be sure the DBEntity has a proper
	 * pkField defined (see tableMeta-Annotation). The db-table has to be named as the reflected class!
	 * Be sure to call connect() first, before you use this method, or it will fail with an connection-error.
	 * This method returns the same result as getEntitiesBy(null, entityClass)!
	 * @param entityClass -  Class-definition of the datasets which should be retrieved as a list
	 * @param <T> - Entity-Class, which should be returned in a list (a DBEntity-model-class)
	 * @return List of the wished data-classes with the aided class-type
	 * @throws DALException - If an database-error occurs
	 */
	public abstract <T extends DBEntity> List<T> getEntityList(
			Class<T> entityClass) throws DALException;

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
	public abstract <T extends DBEntity> List<T> getEntitiesBy(
			WhereChain where, Class<T> entityClass) throws DALException;

}