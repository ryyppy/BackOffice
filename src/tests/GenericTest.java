package tests;
import logging.LoggingLevel;
import model.Angebot;
import org.junit.Test;


import dal.*;

import java.lang.reflect.Field;
import java.util.*;

public class GenericTest {
	@Test
	public void mysqlTest() throws Exception{
		MysqlAdapter da = new MysqlAdapter("root", "4chaos", "localhost/backoffice");
		da.connect();

        Angebot a = new Angebot();


        //System.out.println(builder.toString());


        //System.out.println(Angebot.class.getConstructor().newInstance());
        System.out.println(da.getEntityByID(1, Angebot.class));

        System.out.println(LoggingLevel.INFO.compareTo(LoggingLevel.WARNING));
        //System.out.println(a.getClass().getSimpleName());
	}
}
