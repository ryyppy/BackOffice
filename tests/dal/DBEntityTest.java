package dal;

import bl.objects.Angebot;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: Patrick Stapfer
 * Date: 16.04.12
 * Time: 16:46
 */
public class DBEntityTest {

    @Test
    public void testGetID() throws Exception {
        Angebot angebot = new Angebot();
        Integer id = 12;
        angebot.setAngebotID(id);

        //System.out.println(angebot);
        Assert.assertEquals(String.format("The fetched getID() - ID (%d) should be %d!",angebot.getID(), id), id, angebot.getID());
    }

    @Test
    public void testSetID() throws Exception{
        Angebot angebot = new Angebot();
        Integer id = 12;
        angebot.setID(id);

        //System.out.println(angebot);
        Assert.assertEquals(String.format("Found angebotID (%d) should be %d!", angebot.getAngebotID(), id), id, (Integer) angebot.getAngebotID());
    }
}
