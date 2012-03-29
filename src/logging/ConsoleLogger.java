package logging;

import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: Patrick Stapfer
 * Date: 28.03.12
 * Time: 18:10
 */
public class ConsoleLogger extends Logger {

    protected ConsoleLogger(){

    }

    @Override
    protected void log(String msg, LoggingLevel loggingLevel) {
        System.out.println(format(Calendar.getInstance().getTime(), loggingLevel, msg));
    }

    @Override
    public void close() {
        System.out.println(format(Calendar.getInstance().getTime(), null, "ConsoleLogger '%s' closed"));
    }
}
