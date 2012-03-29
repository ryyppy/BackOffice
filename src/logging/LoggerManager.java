package logging;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: Patrick Stapfer
 * Date: 27.03.12
 * Time: 10:32
 */
public class LoggerManager {

    //Map for registering Loggers with a unique loggername
    private static HashMap<String, Logger> loggers = new HashMap<String, Logger>();

    //Current logging-level for all Logger-Objects
    private static LoggingLevel loggingLevel = LoggingLevel.INFO;

    //Logdir, where Filelogger-Files will be created
    private static File logdir = new File("log");

    static{
        //Shutdown-Hook for closing all Fileoperations
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                LoggerManager.unregisterLoggers();
            }
        });
    }

    public static void setLoggingLevel(LoggingLevel loggingLevel){
        LoggerManager.loggingLevel = loggingLevel;
    }

    public static LoggingLevel getLoggingLevel(){
        return LoggerManager.loggingLevel;
    }

    public static Logger registerLogger(String loggername, LoggerType type) throws IOException {
        Logger logger = loggers.get(loggername);

        //If a logger already exists, the existing logger will be returned
        if(logger != null)
            return logger;

        switch(type){
            case FILE:
                logger = new FileLogger(logdir, loggername + ".log");
                break;
            case CONSOLE:
                logger = new ConsoleLogger();
                break;
        }

        loggers.put(loggername, logger);

        return logger;
    }

    public static Logger getLogger(String loggername){
        Logger l = loggers.get(loggername);

        if(l == null)
            throw new IllegalArgumentException(String.format("Logger '%s' does not exist! Use Logger.registerLogger(...) to create the logger!", loggername));

        return l;
    }

    public static void unregisterLoggers(){
        if(loggers.isEmpty())
           return;

        for(Logger l : loggers.values())
            l.close();
        
        loggers.clear();
    }


}
