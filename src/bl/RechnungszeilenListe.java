package bl;

import java.util.ArrayList;

import bl.models.armin.Angebot;
import bl.models.armin.Rechnungszeile;

public class RechnungszeilenListe {
	private ArrayList<Rechnungszeile> rechnungszeilen;
	private BL data;

	public RechnungszeilenListe(BL data) {
		rechnungszeilen = new ArrayList<>();
		this.data = data;
	}

	public void add(String[] inhalt) {
		rechnungszeilen.add(new Rechnungszeile(Integer.valueOf(inhalt[0]),
				inhalt[1], Double.valueOf(inhalt[2]), Integer
						.valueOf(inhalt[3]), Integer.valueOf(inhalt[4])));
	}

	public void delete(int id) {
		Rechnungszeile p = getObjectById(id);
		if (p != null) {
			rechnungszeilen.remove(p);
		}

	}

	public Rechnungszeile getObjectById(int id) {
		for (int i = 0; i < rechnungszeilen.size(); i++) {
			if (rechnungszeilen.get(i).getId() == id) {
				return rechnungszeilen.get(i);
			}
		}
		return null;
	}

	public String[] getColumnNames() {
		String[] ret = { "Rechnungszeile-ID", "Kommentar", "Betrag",
				"Ausgangsrechnungs-ID", "Angebots-ID" };
		return ret;
	}

	public Object[][] getRows() {
		Object[][] ret = new Object[rechnungszeilen.size()][this
				.getColumnNames().length];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = rechnungszeilen.get(i).getRow();
		}
		return ret;
	}

	public Object[][] getRows(int ausgangsrechnungsID) {
		ArrayList<Object[]> retList = new ArrayList<>();
		for (Rechnungszeile r : rechnungszeilen) {
			if (r.getAusgangsrechnungsID() == ausgangsrechnungsID) {
				retList.add(r.getRow());
			}
		}

		Object[][] ret = new Object[retList.size()][this.getColumnNames().length];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = retList.get(i);
		}
		return ret;
	}

	public void validate(String[] inhalt) throws IllegalArgumentException {
		if (inhalt.length != this.getColumnNames().length) {
			throw new IllegalArgumentException("Ungültige Anzahl von Parameter");
		}
		try {
			int id = Integer.valueOf(inhalt[0]);
			if (id < 0) {
				throw new IllegalArgumentException("ID ungültig");
			}
			if (this.containsID(id)) {
				throw new IllegalArgumentException("ID bereits vergeben");
			}
		} catch (NumberFormatException nfe) {
			throw new IllegalArgumentException("ID ungültig");
		}

		try {
			int arid = Integer.valueOf(inhalt[3]);
			if (arid < 0) {
				throw new IllegalArgumentException(
						"Ausgangsrechnungs-ID ungültig");
			}
			if (data.getAusgangsrechnungenListe().containsID(arid) == false) {
				throw new IllegalArgumentException(
						"Ausgangsrechnungs-ID nicht vorhanden");
			}
		} catch (NumberFormatException nfe) {
			throw new IllegalArgumentException("Ausgangsrechnungs-ID ungültig");
		}
		try {
			int aid = Integer.valueOf(inhalt[4]);
			if (aid < 0) {
				throw new IllegalArgumentException("Angebots-ID ungültig");
			}
			if (data.getAngebotsListe().containsID(aid) == false) {
				throw new IllegalArgumentException(
						"Angebots-ID nicht vorhanden");
			}
		} catch (NumberFormatException nfe) {
			throw new IllegalArgumentException("Angebots-ID ungültig");
		}
		try {
			double preis = Double.valueOf(inhalt[2]);
			if (preis < 0) {
				throw new IllegalArgumentException("Preis ungültig");
			}
		} catch (NumberFormatException nfe) {
			throw new IllegalArgumentException("Preis ungültig");
		}
	}

	public boolean containsID(int id) {
		for (int i = 0; i < rechnungszeilen.size(); i++) {
			if (rechnungszeilen.get(i).getId() == id) {
				return true;
			}
		}
		return false;
	}

	public ArrayList<Rechnungszeile> getRechnungszeilen() {
		return rechnungszeilen;
	}

	public void setRechnungszeilen(ArrayList<Rechnungszeile> rechnungszeilen) {
		this.rechnungszeilen = rechnungszeilen;
	}

}
