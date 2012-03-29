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
 */
public class FileLogger extends Logger{
    //Instancevariables
    private PrintWriter writer = null;
    private File logfile = null;

    protected FileLogger(File logdir, String filename) throws IOException {
        if(!logdir.isDirectory())
            throw new IOException(String.format("Path '%s' is no valid directory!", logdir.getAbsolutePath()));
        
        if(!logdir.exists()){
            if(!logdir.mkdirs())
                throw new IOException(String.format("Directory '%s' could not be created!", logdir.getAbsolutePath()));
        }
        
        logfile = new File(logdir.getAbsolutePath() + File.separator + filename);
        writer = new PrintWriter(new FileWriter(logfile, false));
    }

    @Override
    protected void log(String msg, LoggingLevel loggingLevel){
        writer.println(format(Calendar.getInstance().getTime(), loggingLevel, msg));
    }

    public File getLogfile(){
        return logfile;
    }

    public void close(){
        writer.flush();
        writer.close();
    }
}
