package logging;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Patrick Stapfer
 * Date: 26.03.12
 * Time: 16:15
 */
public abstract class Logger {
    //Static stored Dateformatter
    protected static DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    public void debug(String msg){
        LoggingLevel currentLevel = LoggerManager.getLoggingLevel();

        if(LoggingLevel.DEBUG.compareTo(currentLevel) == 0)
            log(msg, LoggingLevel.DEBUG);
    }

    public void info(String msg){
        LoggingLevel currentLevel = LoggerManager.getLoggingLevel();

        if(LoggingLevel.INFO.compareTo(currentLevel) == 0)
            log(msg, LoggingLevel.INFO);
    }

    public void warn(String msg){
        LoggingLevel currentLevel = LoggerManager.getLoggingLevel();

        if(LoggingLevel.WARNING.compareTo(currentLevel) <= 0)
            log(msg, LoggingLevel.WARNING);
    }

    protected String format(Date date, LoggingLevel loggingLevel, String msg){
        if(loggingLevel == null)
            return String.format("[%s]: %s", Logger.dateFormat.format(date), msg);

        return String.format("[%s] %s: %s", Logger.dateFormat.format(date), loggingLevel, msg);
    }

    protected abstract void log(String msg, LoggingLevel loggingLevel);

    public abstract void close();
}
