package config;

import dal.DatabaseAdapter;
import dal.MysqlAdapter;
import junit.framework.Assert;
import org.junit.Test;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: Patrick Stapfer
 * Date: 18.04.12
 * Time: 13:09
 */
public class ConfigurationTest {


    @Test
    public void testValidConfig() throws Exception{
        System.out.println("Reading config-file 'tests/config/valid_config.properties' ...");
        Configuration config = new Configuration("tests/config/valid_config.properties");
        System.out.println(config);

        DatabaseAdapter db = config.getDatabaseAdapter();
        Assert.assertNotNull("Database-Adapter not initialized!", db);
        Assert.assertEquals("Did not load the right DB-Adapter!", MysqlAdapter.class, db.getClass());

        Assert.assertTrue("LoggingStdout should be true!", config.isLoggingStdout());

        File expectedDir = new File("loggy");
        File foundDir = config.getLoggingDirectory();
        Assert.assertEquals(String.format("Loggingdirectory should be %s - found: %s", expectedDir.getAbsolutePath(), foundDir.getAbsolutePath()), expectedDir, foundDir);

        expectedDir = new File("files");
        foundDir = config.getDocumentDirectory();
        Assert.assertEquals(String.format("DocumentDirectory should be %s - found: %s", expectedDir.getAbsolutePath(), foundDir.getAbsolutePath()), expectedDir, foundDir);
    }

    /**
     * Tests the config-file of the production-environment (config.properties) for the right DB-configuration
     * @throws Exception - If there could not be made any connection to the db
     */
    @Test
    public void testGetInstance() throws Exception{
        System.out.println(String.format("Reading config-file '%s' ...", Configuration.CONFIG_FILENAME));
        Configuration config = Configuration.getInstance();

        DatabaseAdapter db = config.getDatabaseAdapter();
        try{
            db.connect();
            System.out.println(String.format("Connection could be established with '%s'",db.toString()));
        }finally{
            if(db.isConnected())
                db.disconnect();
        }
    }
}
