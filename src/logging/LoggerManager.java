package logging;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Patrick Stapfer
 * Date: 27.03.12
 * Time: 10:32
 *
 * This class is capable of holding a global mapping of logger-objects and is also important for
 * opening and closing all logger-adapter-objects in the logger object.
 * Because of the nature of the logger-object, it is only possible to open it by registering the logger in the
 * LoggerManager class.
 *
 * Example for registering a new Logger-Object:
 * Logger l = new Logger();
 * l.addAdapter(new ConsoleAdapter());
 * LoggerManager.registerLogger("logger1", l);
 *
 * Without registering the Logger, it is not possible to use it like that:
 * l.warn("I want to warn something");
 * l.debug("This is some debug-information");
 * l.info("This should know the user too");
 *
 * The LoggerManager also implements a shutdown-hook to close all loggers before exiting the JVM
 */
public class LoggerManager {

    //Map for registering Loggers with a unique loggername
    private static Map<String, Logger> loggers = new HashMap<String, Logger>();

    //Current logging-level for all Logger-Objects
    private static LoggingLevel loggingLevel = LoggingLevel.INFO;

    static{
        //Shutdown-Hook for closing all Fileoperations
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                LoggerManager.unregisterLoggers();
            }
        });
    }

    /**
     * Sets the loggingLevel, so each Logger only log data, if an appropriate loggingmethod (log(), warn() or info())
     * was called.
     * @param loggingLevel - LoggingLevel for all loggers to be considered when trying to log
     */
    public static void setLoggingLevel(LoggingLevel loggingLevel){
        LoggerManager.loggingLevel = loggingLevel;
    }

    /**
     * Returns the current loggingLevel
     * @return Current set loggingLevel
     */
    public static LoggingLevel getLoggingLevel(){
        return LoggerManager.loggingLevel;
    }

    /**
     * Registers a logger with a given loggername. If there is a logger already registered with the given name, the
     * old logger will be closed and overridden.
     * After registering a Logger, it will be opened, that means, each LoggerAdapter in the logger-object will be opened.
     * @param loggername - Name of the logger (must be unique, or the old logger will be closed and truncated!)
     * @param l - Logger-Object to be registered with the given loggername (logger will be opened after registering)
     * @throws IOException - If an opening-error occurrs (most commonly caused by a LoggerAdapter)
     */
    public static void registerLogger(String loggername, Logger l) throws IOException {
        Logger logger = loggers.get(loggername);

        if(logger != null )
            unregisterLogger(loggername);

        loggers.put(loggername, l);
        l.open();
    }

    /**
     * Removes a logger from the LoggerManager.
     * The logger will be closed properly before it will be removed.
     * @param loggername - Unique name of the logger which has to be removed
     */
    public static void unregisterLogger(String loggername){
        Logger l = getLogger(loggername);

        try{
            l.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }

        loggers.remove(l);
    }

    /**
     * Gets a logger with the given loggername.
     * Throws an IllegalArgumentException if there is no logger with the given name.
     * @param loggername - Name of the logger
     * @return logger-object of the given loggername
     */
    public static Logger getLogger(String loggername){
        if(loggername == null)
            throw new IllegalArgumentException("Loggername must not be null!");

        Logger logger = loggers.get(loggername);

        if(logger == null)
            throw new IllegalArgumentException(String.format("Logger '%s' does not exist! Use Logger.registerLogger(...) to create the logger!", loggername));

        return logger;
    }

    /**
     * Closes all loggers and removes them from the LoggerManager.
     * This method stops all logging-activity in the LoggerManager! (Most commonly used while stopping the application)
     */
    public static void unregisterLoggers(){
        if(loggers.isEmpty())
           return;

        for(Logger l : loggers.values()){
            try{
                l.close();
            }catch(IOException ioe){
                ioe.printStackTrace();
            }
        }

        loggers.clear();
    }


}
