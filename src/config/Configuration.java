package config;

import dal.DatabaseAdapter;
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
 */
public class Configuration {
    //Readonly field for the configuration-file-name
    public static final String CONFIG_FILENAME = "config.properties";

    //Singleton-Instance
    private static Configuration instance = null;

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

    protected Configuration(String filename) throws ConfigException {
        Properties defaults = new Properties();
        defaults.setProperty("db_adapter", "mysql");
        defaults.setProperty("db_url", "localhost");
        defaults.setProperty("db_name", "backoffice");
        defaults.setProperty("db_user", "root");
        defaults.setProperty("db_pw", "");
        defaults.setProperty("logging_directory", "log");
        defaults.setProperty("logging_level", "DEBUG");
        defaults.setProperty("logging_stdout", "true");

        Properties properties = new Properties(defaults);

        try{
            File f = new File(filename);
            if(!f.exists())
                defaults.store(new FileOutputStream(f), null);

            properties.load(new FileInputStream(f));

            //Set logging-directory
            loadLoggingDirectory(properties);

            //Set logging-level
            loadLoggingLevel(properties);

            //Set database-adapter
            loadDatabaseAdapter(properties);

            //Set boolean for loggingStdout
            loadLoggingStdout(properties);

            //Set properties
            this.properties = properties;

        }catch(IOException e){
            throw new ConfigException("IOError occurred - Configuration could not be instantiated!", e);
        }
    }

    private void loadLoggingStdout(Properties properties) throws ConfigException{
        String loggingStdout = properties.getProperty("logging_stdout").toLowerCase();

        if(!loggingStdout.equals("true") && !loggingStdout.equals("false"))
            throw new ConfigException(String.format("logging_stdout '%s' unknown - Only 'true' or 'false' allowed!", loggingStdout));

        this.loggingStdout = Boolean.parseBoolean(loggingStdout);
    }

    private void loadDatabaseAdapter(Properties properties) throws ConfigException{
        //Initialize databaseadapter
        String dalName = properties.getProperty("db_adapter");
        String dbUrl = properties.getProperty("db_url");
        String dbName = properties.getProperty("db_name");
        String dbUser = properties.getProperty("db_user");
        String dbPw = properties.getProperty("db_pw");

        if("mysql".equals(dalName.toLowerCase()))
            databaseAdapter = new MysqlAdapter(dbUser, dbPw, dbUrl, dbName);
        else
            throw new ConfigException(String.format("db_adapter '%s' unknown", dalName));
    }

    private void loadLoggingDirectory(Properties properties) throws ConfigException{
        String path = properties.getProperty("logging_directory");
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
     * Loads the "logging_level" Property-Key from the properties parameter and sets instance-variable "loggingLevel"
     * if valid.
     * @param properties - Properties-Object to search for "logging_level"
     * @throws ConfigException - If there was a validation-problem in it's logging_level value
     */
    private void loadLoggingLevel(Properties properties) throws ConfigException {
        String level = properties.getProperty("logging_level");
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
