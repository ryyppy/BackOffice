package logging;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: Patrick Stapfer
 * Date: 27.03.12
 * Time: 15:06
 */
public class FileLoggerTest extends TestCase {
    private Logger log = null;
    private File logfile = null;
    private File logdir = new File("log");
    private static int testrun = 0;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        String loggername = "test" + FileLoggerTest.testrun++;

        log = new Logger();
        log.addAdapter(new FileAdapter(logdir, loggername));
        log.addAdapter(new ConsoleAdapter());

        //Registers logger and opens all streams in all adapters
        LoggerManager.registerLogger(loggername, log);

        //Retrieve open logfile
        LoggerAdapter la = log.getAdapter(FileAdapter.class);

        if(la != null)
            logfile = ((FileAdapter)la).getLogfile();
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();

        //if(logfile != null)
            //logfile.delete();

    }

    @Test
    public void testInfoLogging() throws Exception {
        LoggerManager.setLoggingLevel(LoggingLevel.INFO);
        final int expectedCount = 1;

        String right = "Das ist der richtige Logsatz, der tatsächlich geloggt werden soll....";

        log.warn("Irgendwas...");
        log.debug("Dieser Datensatz stimmt nicht...");
        log.info(right);

        LoggerManager.unregisterLoggers();

        BufferedReader br = new BufferedReader(new FileReader(logfile));

        String line =   null;
        int count = 0;
        while((line = br.readLine()) != null && line.contains(right))
            count++;

        Assert.assertTrue(String.format("The logger logged more than %d info-line(s)!", expectedCount), count == expectedCount);
    }

    @Test
    public void testWarningLogging() throws Exception {
        LoggerManager.setLoggingLevel(LoggingLevel.WARNING);
        final int expectedCount = 3;

        List<String> rights = new ArrayList<String>();
        rights.add("Das ist ein richtiger Datensatz (Nummer 1)");
        rights.add("Das ist ein richtiger Datensatz (Nummer 2)");
        rights.add("Das ist ein richtiger Datensatz (Nummer 3)");

        for(int i=0; i < rights.size(); i++){
            switch(i){
                case 0:
                    log.warn(rights.get(i));
                    break;
                case 1:
                    log.info(rights.get(i));
                    break;
                case 2:
                    log.warn(rights.get(i));
                    break;
            }
        }

        log.debug("If theres nassing, theres nassing");

        LoggerManager.unregisterLoggers();

        BufferedReader br = new BufferedReader(new FileReader(logfile));

        String line = null;
        int count = 0;
        int found = 0;
        while((line = br.readLine()) != null)  {
            for(String s : rights){
                if(line.contains(s))
                    found++;
            }
            count++;
        }

        Assert.assertTrue(String.format("The logger logged more than %d warning / info lines !", expectedCount), (found == rights.size()) && (count == expectedCount));
    }

    @Test
    public void testDebugLogging() throws Exception {
        LoggerManager.setLoggingLevel(LoggingLevel.DEBUG);
        final int expectedCount = 4;
        List<String> rights = new ArrayList<String>();
        rights.add("Das ist ein richtiger Datensatz (Nummer 1)");
        rights.add("Das ist ein richtiger Datensatz (Nummer 2)");
        rights.add("Das ist ein richtiger Datensatz (Nummer 3)");
        rights.add("Das ist ein richtiger Datensatz (Nummer 4)");

        for(int i=0; i < rights.size(); i++){
            switch(i){
                case 0:
                    log.warn(rights.get(i));
                    break;
                case 1:
                    log.info(rights.get(i));
                    break;
                case 2:
                    log.debug(rights.get(i));
                    break;
                case 3:
                    log.info(rights.get(i));
                    break;
            }
        }

        LoggerManager.unregisterLoggers();

        BufferedReader br = new BufferedReader(new FileReader(logfile));

        String line = null;
        int count = 0;
        int found = 0;
        while((line = br.readLine()) != null)  {
            for(String s : rights){
                if(line.contains(s))
                    found++;
            }
            count++;
        }

        Assert.assertTrue(String.format("The logger logged more than %d warning / info lines !", expectedCount), (found == rights.size()) && (count == expectedCount));
    }


}
