package logging;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: Patrick Stapfer
 * Date: 28.03.12
 * Time: 12:59
 *
 * This concrete LoggerAdapter manages a File as destination-point and writes per-line-logmessages in it´s dedicated
 * file.
 */
public class FileAdapter extends LoggerAdapter{
    //Instancevariables
    private PrintWriter writer = null;
    private File logfile = null;
    private File logdir = null;
    private String filename = null;

    /**
     * Creates a FileAdapter-instance. This instance has to be registered in a LoggerManager, so it can be used for
     * logging!
     * There will be no validy-checks for the given parameters until the first try to open the file! (open() - method)
     * @param logdir - Logdir, where the file will be created
     * @param filename - Filename, which will be used (only the name of the file -> no directory!)
     */
    public FileAdapter(File logdir, String filename){
        this.filename = filename;
        this.logdir = logdir;
    }

    /**
     * Writes a log-line in the capsulated file
     * @param msg - Message which should be logged
     * @param loggingLevel - LoggingLevel which caused the logging - Just additional information which could be plotted too
     */
    @Override
    protected void write(String msg, LoggingLevel loggingLevel){
        writer.println(format(Calendar.getInstance().getTime(), loggingLevel, msg));
    }

    /**
     * Returns the logfile, which was created in the opening-procedure.
     * This method will return null, if the method open() was never used successfully!
     * @return Logfile, which is used for logging or null if the FileAdapter was not opened properly
     */
    public File getLogfile(){
        return logfile;
    }

    /**
     * Opens the file and creates a PrintWriter to log the data in the file.
     * If there are any path-errors (nonexisting directorys,..) then an IOException is thrown.
     * After opening the FileAdapter successfully, the method getLogfile() will return a File-instance.
     * @throws IOException - If there is a path-error or the log-directory could not be created
     */
    protected void open() throws IOException{
        if(!logdir.isDirectory())
            throw new IOException(String.format("Path '%s' is no valid directory!", logdir.getAbsolutePath()));

        if(!logdir.exists()){
            if(!logdir.mkdirs())
                throw new IOException(String.format("Directory '%s' could not be created!", logdir.getAbsolutePath()));
        }

        logfile = new File(logdir.getAbsolutePath() + File.separator + filename);
        writer = new PrintWriter(new FileWriter(logfile, false));
    }


    /**
     * Flushes and closes the created FileWriter
     * @throws IOException - If the file could not be closed or if another IO-Error occurrs
     */
    @Override
    protected void close() throws IOException{
        writer.flush();
        writer.close();
    }
}
