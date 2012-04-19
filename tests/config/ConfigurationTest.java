package config;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: Patrick Stapfer
 * Date: 18.04.12
 * Time: 13:09
 */
public class ConfigurationTest {

    private Properties original = new Properties();

    @Before
    public void setUp() throws Exception {
        try{
            original.load(new FileInputStream(Configuration.CONFIG_FILENAME));
        }catch(IOException ioe){
            //If there is no file, there will be no original-file to be restored
            original = null;
        }
    }

    @After
    public void tearDown() throws Exception {
        try{
        //Restore the original file
        if(original != null)
            original.store(new FileOutputStream(Configuration.CONFIG_FILENAME), null);
        }catch(IOException ioe){
            ioe.printStackTrace();
        }

    }

    @Test
    public void getInstanceTest() throws Exception{
        Configuration config = Configuration.getInstance();

        System.out.println(config);
    }
}
