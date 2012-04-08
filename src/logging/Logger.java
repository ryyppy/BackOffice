package logging;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Patrick Stapfer
 * Date: 26.03.12
 * Time: 16:15
 */
public class Logger {
    //Static stored Dateformatter

    protected List<LoggerAdapter> adapters = new ArrayList<LoggerAdapter>();

    //This flag will be set by the open and close methods
    private boolean open = false;

    public Logger(){

    }

    /**
     * Adds an Adapter to the Logger, which will be used for the logging-procedure
     * @param la - LoggerAdapter-Instance
     * @throws IOException - If the LoggerAdapter throws an Exception caused by IO operations
     */
    public void addAdapter(LoggerAdapter la) throws IOException{
        if(la == null)
            throw new NullPointerException("LoggerAdapter must not be null!");

        //Do not add, if already listed
        if(containsAdapter(la.getClass()))
            return;
        
        adapters.add(la);
    }

    /**
     * Removes an adapter from the logger. The adapter will automatically be closed while removing.
     * @param la - LoggerAdapter-instance, which must be the same reference as the object which was added by addAdapter()
     * @return true, if the adapter was removed successfully
     * @throws IOException - If the LoggerAdapter throws an Exception caused by IO operations
     */
    public boolean removeAdapter(LoggerAdapter la) throws IOException{
        la.close();
        return adapters.remove(la);
    }

    public LoggerAdapter getAdapter(Class<? extends LoggerAdapter> adapterClass){
        if(!containsAdapter(adapterClass))
            return null;

        for(LoggerAdapter a : adapters){
            if(a.getClass().equals(adapterClass))
                return a;
        }
        return null;
    }

    public boolean containsAdapter(Class<? extends LoggerAdapter> adapterClass){
        if(adapterClass == null)
            throw new NullPointerException("AdapterClass must not be null!");

        for(LoggerAdapter a : adapters){
            if(a.getClass().equals(adapterClass))
                return true;
        }
        return false;
    }

    public void debug(String msg){
        LoggingLevel currentLevel = LoggerManager.getLoggingLevel();

        if(LoggingLevel.DEBUG.equals(currentLevel))
            log(msg, LoggingLevel.DEBUG);
    }

    public void info(String msg){
        LoggingLevel currentLevel = LoggerManager.getLoggingLevel();

        if(LoggingLevel.INFO.equals(currentLevel) || LoggingLevel.DEBUG.equals(currentLevel)  || LoggingLevel.WARNING.equals(currentLevel))
            log(msg, LoggingLevel.INFO);
    }

    /**
     * This method triggers the write-method of each registered LoggerAdapter, if the current set LoggingLevel is either
     * WARNING or DEBUG. If the current level is INFO, this method will not log anything.
     * @param msg - Message to be logged
     *
     */
    public void warn(String msg){
        LoggingLevel currentLevel = LoggerManager.getLoggingLevel();

        if(LoggingLevel.WARNING.equals(currentLevel) || LoggingLevel.DEBUG.equals(currentLevel))
            log(msg, LoggingLevel.WARNING);
    }

    /**
     * This method triggers the write-method of each registered LoggerAdapter and is independend from the current
     * loggingLevel set in the LoggingManager. All LoggerAdapters just do their print-work.
     * @param msg - Message to be logged
     * @param loggingLevel - Additional logginglevel information for writing in the log-message
     * @throws LoggerNotRegisteredException - If the Logger was not officially registered and opened by the LoggerManager
     */
    protected void log(String msg, LoggingLevel loggingLevel){
        if(!open)
            throw new LoggerNotRegisteredException("This logger has to be opened by the LoggerManager-Class first! See LoggerManager.registerLogger(...) ");

        for(LoggerAdapter adapter : adapters)
            adapter.write(msg, loggingLevel);
    }

    /**
     * Opens all registered LoggerAdapters in the Logger-instance
     * This method is protected, hence should only be called by a LoggerManager
     * @throws IOException - If one LoggerAdapter throws an Exception caused by IO operations
     */
    protected void open() throws IOException{
        if(open)
            return;

        open = true;

        for(LoggerAdapter la : adapters)
            la.open();
    }

    /**
     * Closes all registered LoggerAdapters in the Logger-instance
     * This method is protected, hence should only be called by a LoggerManager
     * @throws IOException - If one LoggerAdapter throws an Exception caused by IO operations
     */
    protected void close() throws IOException{
        if(!open)
            return;

        open = false;

        for(LoggerAdapter la : adapters)
            la.close();
    }
}
