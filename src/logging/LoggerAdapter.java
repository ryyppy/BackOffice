package logging;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Patrick Stapfer
 * Date: 02.04.12
 * Time: 23:50
 *
 * This class represents the interface for all LoggerAdapters used in the logging-system.
 * It consists of some methods, which have to be overridden and a static dateformat-object, which can be reused by the subclasses.
 * There is a format-method, which mostly will be reused by it´s subclasses
 */
public abstract class LoggerAdapter {
    protected static DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    /**
     * This utility-method returns a formatted String which contains a Date, the loggingLevel and the message of logdata.
     * Subclasses are encouraged to override this message, to get another Log-Format. In most cases, this origin method will
     * be used.
     * @param date - Date to put in the String
     * @param loggingLevel - LoggingLevel, which was used for the logdata and should appear an the String
     * @param msg - Log-Message which should appear in the return-String
     * @return formatted String with the given parameters
     */
    protected String format(Date date, LoggingLevel loggingLevel, String msg){
        if(loggingLevel == null)
            return String.format("[%s]: %s", LoggerAdapter.dateFormat.format(date), msg);

        return String.format("[%s] %s: %s", LoggerAdapter.dateFormat.format(date), loggingLevel, msg);
    }

    /**
     * Writes the message into the logging-medium, according to the usage of the concrete LoggerAdapter
     * Actually, here should not be any loggingLevel-checkings. Calling this method MUST LEAD to a writing into the medium.
     * @param msg - Message which should be logged
     * @param loggingLevel - LoggingLevel which caused the logging - Just additional information which could be plotted too
     */
    protected abstract void write(String msg, LoggingLevel loggingLevel);

    /**
     * Opens the LoggerAdapter, the method won´t throw an IOException in every concrete class, it was intended to
     * offer support for file- or pipe-manipulations.
     * After calling this method, the LoggerAdapter should be enabled to log data in its concrete destination
     * @throws IOException - If the LoggerAdapter causes an IO-Error while opening it´s loggingdestination
     */
    protected abstract void open() throws IOException;

    /**
     * Closes the LoggerAdapter, the method won´t throw an IOException in every concrete class, it was intended to
     * offer support for file- or pipe-manipulations.
     * After calling this method, the LoggerAdapter shuould be disabled, hence it cannot log any data in it´s concrete
     * destination anymore.
     * @throws IOException - If the LoggerAdapter causes an IO-Error while closing it´s loggingdestination
     */
    protected abstract void close() throws IOException;
}
