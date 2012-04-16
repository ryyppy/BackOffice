package tests;

import bl.objects.*;
import dal.DALException;
import dal.DBEntity;
import dal.DBEntityInfo;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: Patrick Stapfer
 * Date: 16.04.12
 * Time: 18:53
 */
public class DBEntityInfoTest {

    @Test
    public void testDBEntityCaching() throws Exception{
        System.out.println(DBEntityInfo.get(Eingangsrechnung.class));
        System.out.println(DBEntityInfo.get(Ausgangsrechnung.class));
        System.out.println(DBEntityInfo.get(Rechnung.class));
        System.out.println(DBEntityInfo.get(Rechnung_Buchungszeile.class));

        DBEntityInfo eci1 = DBEntityInfo.get(Eingangsrechnung.class);
        DBEntityInfo eci2 = DBEntityInfo.get(Eingangsrechnung.class);

        Assert.assertEquals("eci1 and eci2 are not the same, thus should be both cached!", eci1, eci2);

        DBEntityInfo eci3 = DBEntityInfo.get(Angebot.class);
        System.out.println(eci3);

        DBEntityInfo eci4 = null;
        try{
            eci4 = DBEntityInfo.get(DBEntity.class);
        }catch(DALException dal){
            //dummy
        }

        Assert.assertNull("There must not any Information about DBEntity-Class or other classes!", eci4);
    }
}
