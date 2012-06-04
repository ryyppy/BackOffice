package config;

import dal.DatabaseAdapter;
import dal.MockAdapter;
import dal.MysqlAdapter;
import logging.LoggingLevel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: Patrick Stapfer
 * Date: 17.04.12
 * Time: 13:13
 *
 * This singleton-class provides a Configuration-Object, with data retrieved from external sources like a property-file.
 * Also, this class doesn't contain any magic, that means, it only encapsulates values, which should be used by the
 * business-logic, like creating additional ConsoleLogger or setting the loggingLevel in the LoggerManager.
 *
 * If the singleton-instance is not existent, a new instance will be created, that means, it uses the file "config.properties"
 * in the root-folder to load all configuration-specific-values. It may fail in the first run, because the config-file
 * will be created first. Adapt the values and re-run the application for a successful start!
 *
 * Everytime, there is an error in setting a configuration-object-value, a ConfigException will be thrown, so it would
 * be best practise to load all values in the start of the program and check its integrity (no exceptions thrown?),
 * before processing business-logic!
 *
 * Example to retrieve and work with the Configuration-object:
 * try{
 *   Configuration c = Configuration.getInstance();
 * }catch(ConfigException ce){
 *     System.err.println("Something went terribly wrong in creating the configuration!");
 * }
 * DatabaseAdapter db = c.getDatabaseAdapter();
 * File loggingDirectory = c.getLoggingDirectory();
 * .
 * .
 * .
 */
public class Configuration {
    //Readonly field for the configuration-file-name
    public static final String CONFIG_FILENAME = "config.properties";

    //Singleton-Instance
    private static Configuration instance = null;

    //Default-Values
    private Properties defaults = null;

    //Properties-Reference
    private Properties properties = null;

    //DatabaseAdapter-Instance
    private DatabaseAdapter databaseAdapter = null;

    //Logginglevel which should be used by the LoggerManager
    private LoggingLevel loggingLevel = null;

    //True, if there should be ConsoleAdapter added to each Logger-Object
    private boolean loggingStdout = false;

    //Directory for the logging-files, which will be created by FileAdapter
    private File loggingDirectory = null;

    //Directory for the document-storage
    private File documentDirectory = null;

    /**
     * Creates a new Configuration-Object with default-values only
     * Use the set-Methods to set it's right values
     */
    protected Configuration(){
        defaults = new Properties();
        defaults.setProperty("db_adapter", "mysql");
        defaults.setProperty("db_url", "localhost");
        defaults.setProperty("db_name", "backoffice");
        defaults.setProperty("db_user", "root");
        defaults.setProperty("db_pw", "");
        defaults.setProperty("logging_directory", "log");
        defaults.setProperty("logging_level", "DEBUG");
        defaults.setProperty("logging_stdout", "true");
        defaults.setProperty("document_store_directory", "documents");

        properties = new Properties(defaults);
    }

    /**
     * This constructor loads the configuration from a properties-file.
     * If there is no such file, it will be created with with the default-values
     * @param filename - Properties file to load in,... If there are properties missing, then the default-values will be taken
     * @throws ConfigException - If something went wrong in a set-method
     */
    protected Configuration(String filename) throws ConfigException {
        this(); //Getting the default-values first

        try{
            File f = new File(filename);
            if(!f.exists())
                defaults.store(new FileOutputStream(f), null);

            properties.load(new FileInputStream(f));

            //Set logging-directory
            setLoggingDirectory(properties.getProperty("logging_directory"));

            //Set logging-level
            setLoggingLevel(properties.getProperty("logging_level"));

            //Set database-adapter
            setDatabaseAdapter(properties.getProperty("db_adapter"),
                               properties.getProperty("db_url"),
                               properties.getProperty("db_name"),
                               properties.getProperty("db_user"),
                               properties.getProperty("db_pw"));

            //Set boolean for loggingStdout
            setLoggingStdout(properties.getProperty("logging_stdout"));

            //Load document-store-directory
            setDocumentDirectory(properties.getProperty("document_store_directory"));

            //Set properties
            this.properties = properties;

        }catch(IOException e){
            throw new ConfigException("IOError occurred - Configuration could not be instantiated!", e);
        }
    }

    /**
     * Sets the loggingStdout-Value in the config-object.
     * The value will be parsed from the String parameter and throws an exception if it is a non-known value.
     * @param loggingStdout - Either "true" or "false"
     * @throws ConfigException - If the value is not "true" or "false" or null
     */
    protected void setLoggingStdout(String loggingStdout) throws ConfigException{
        if(loggingStdout == null)
            throw new ConfigException("loggingStdout must not be null!");

        loggingStdout = loggingStdout.toLowerCase();

        if(!loggingStdout.equals("true") && !loggingStdout.equals("false"))
            throw new ConfigException(String.format("logging_stdout '%s' unknown - Only 'true' or 'false' allowed!", loggingStdout));

        this.loggingStdout = Boolean.parseBoolean(loggingStdout);
    }

    /**
     * Creates and sets the database-adapter in this configuration-object for the given values.
     * Throws an exception if the database-adpater could not be created.
     * @param dalName - Name of the databaseadapter ('mysql' for MysqlAdapter, 'mock' for MockAdapter)
     * @param dbUrl - URL of the database-server
     * @param dbName - Databasename according to the database-server
     * @param dbUser - Username
     * @param dbPw - Password
     * @throws ConfigException  - if the given dalName is not supported
     */
    protected void setDatabaseAdapter(String dalName, String dbUrl, String dbName, String dbUser, String dbPw) throws ConfigException{
        //Initialize databaseadapter
        if("mysql".equals(dalName.toLowerCase()))
            databaseAdapter = new MysqlAdapter(dbUser, dbPw, dbUrl, dbName);
        else if("mock".equals(dalName.toLowerCase()))
            databaseAdapter= new MockAdapter();
        else
            throw new ConfigException(String.format("db_adapter '%s' unknown", dalName));
    }

    /**
     * Sets the logginDirectory-value in this configuration-object for the given value.
     * @param path - Path of the directory
     * @throws ConfigException - If there are path-errors or the directory could not be created.
     */
    protected void setLoggingDirectory(String path) throws ConfigException{
        if(path == null)
            throw new ConfigException("Path must not be null!");

        File loggingDirectory = new File(path);

        if(!loggingDirectory.exists()){
            if(!loggingDirectory.mkdir())
                throw new ConfigException(String.format("logging_directory '%s' not found or could not be created!", path));
        }

        if(!loggingDirectory.isDirectory())
            throw new ConfigException(String.format("logging_directory '%s' is no directory!", path));

        this.loggingDirectory = loggingDirectory;
    }

    /**
     * Sets the documentDirectory-value in this configuration-object for the given value.
     * @param path - Path of the directory
     * @throws ConfigException - If there are path-errors or the directory could not be created.
     */
    protected void setDocumentDirectory(String path) throws ConfigException{
        if(path == null)
            throw new ConfigException("Path must not be null!");

        File documentDirectory = new File(path);

        if(!documentDirectory.exists()){
            if(!documentDirectory.mkdir())
                throw new ConfigException(String.format("Document_store_directory '%s' not found or could not be created!", path));
        }

        if(!documentDirectory.isDirectory())
            throw new ConfigException(String.format("logging_directory '%s' is no directory!", path));

        this.documentDirectory = documentDirectory;
    }

    /**
     * Sets the loggingLevel-value in this configuration-object for the given value.
     * @param level - According to the logging.LoggingLevel - class ("INFO", "WARNING", "DEBUG")
     * @throws ConfigException - If the given value could not be found in logging.LoggingLevel
     */
    protected void setLoggingLevel(String level) throws ConfigException {
        if(level == null)
            throw new ConfigException("Level must not be null!");

        LoggingLevel loggingLevel = LoggingLevel.get(level);

        if(loggingLevel == null)
            throw new ConfigException(String.format("logging_level '%s' unknown - Only 'DEBUG', 'WARNING' or 'INFO' allowed!", level));

        this.loggingLevel = loggingLevel;
    }

    public DatabaseAdapter getDatabaseAdapter() {
        return databaseAdapter;
    }

    public LoggingLevel getLoggingLevel() {
        return loggingLevel;
    }

    public boolean isLoggingStdout() {
        return loggingStdout;
    }

    public File getLoggingDirectory() {
        return loggingDirectory;
    }

    public File getDocumentDirectory() {
        return documentDirectory;
    }

    /**
     * Returns the singleton-instance
     * @return Singleton-Instance of type Configuration
     * @throws ConfigException - If the configuration could not be loaded from the default-config-file (config.properties)
     */
    public static Configuration getInstance() throws ConfigException{
        if(instance == null)
           instance = new Configuration(CONFIG_FILENAME);
        return instance;
    }

    @Override
    public String toString(){
        return String.format("DatabaseAdapter: '%s' - Logging-Directory: '%s' - Logging-Level: '%s' - STDOUT-Logging: '%s'", databaseAdapter, loggingDirectory.getAbsolutePath(), loggingLevel, loggingStdout);
    }
}
