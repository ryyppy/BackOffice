package logging;

import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: Patrick Stapfer
 * Date: 28.03.12
 * Time: 18:10
 *
 * Concrete LoggerAdapter for logging Messages on STDOUT
 * Because of the nature of STDOUT, there is no need for opening any destination-points.
 * When closed, the instance writes a close-message on STDOUT
 */
public class ConsoleAdapter extends LoggerAdapter {

    /**
     * Creates a ConsoleAdapter-instance. This instance has to be registered in a LoggerManager, so it can be used for
     * logging!
     */
    public ConsoleAdapter(){

    }

    /**
     * Plotts the loggingdata in a formatted String on STDOUT
     * @param msg - Message which should be logged
     * @param loggingLevel - LoggingLevel which caused the logging - Just additional information which could be plotted too
     */
    @Override
    protected void write(String msg, LoggingLevel loggingLevel) {
        System.out.println(format(Calendar.getInstance().getTime(), loggingLevel, msg));
    }

    /**
     * No need for opening anything
     */
    @Override
    protected void open(){

    }

    /**
     * Prints a closing-message on STDOUT
     */
    @Override
    protected void close(){
        System.out.println(format(Calendar.getInstance().getTime(), null, String.format("ConsoleLogger '%s' closed", this)));
    }
}
