package tests;

import java.io.InvalidObjectException;
import java.util.Date;

import dal.DALException;

import bl.BL;
import bl.objects.Eingangsrechnung;

public class Test {
	public static void main(String[] args) {
		Eingangsrechnung e = new Eingangsrechnung("offen", new Date(), 0);
		try {
			BL.saveEingangsrechnung(e);
		} catch (InvalidObjectException e1) {
			e1.printStackTrace();
		} catch (DALException e1) {
			e1.printStackTrace();
		}
	}
}
