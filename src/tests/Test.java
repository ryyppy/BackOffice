package tests;

import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.Date;

import bl.BL;
import bl.objects.Eingangsrechnung;
import dal.DALException;

public class Test {
	public static void main(String[] args) {

		/** hier bekomm ich ne fehlermeldung, obwohl alles gepasst hat **/
		try {
			Eingangsrechnung e = new Eingangsrechnung("offen", new Date(), 0);
			BL.saveEingangsrechnung(e);
		} catch (InvalidObjectException e1) {
			e1.printStackTrace();
		} catch (DALException e1) {
			e1.printStackTrace();
		}

		/**
		 * ich will dass ich mit getEingangsrechnungenListe eine liste von
		 * EINGANGSRECHNUNGEN bekomme und anschließend auf ALLE attribute
		 * zugriff haben! genau so wie hier!!!
		 * 
		 * die methode geteingagnsrechnungenliste vom BL musst du dafür
		 * bearbeiten!
		 **/
		try {
			ArrayList<Eingangsrechnung> liste = BL.getEingangsrechnungenListe();
			Eingangsrechnung e = liste.get(0);
			System.out.println(e.getRechnungID());
			System.out.println(e.getStatus());
			System.out.println(e.getDatumString());
			System.out.println(e.getKontaktID());
		} catch (DALException e1) {
			e1.printStackTrace();
		}
	}
}
