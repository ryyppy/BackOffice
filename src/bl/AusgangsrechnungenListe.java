package bl;

import java.util.ArrayList;

import bl.models.Angebot;
import bl.models.Ausgangsrechnung;

public class AusgangsrechnungenListe {
	private ArrayList<Ausgangsrechnung> ausgangsrechnungen;
	private BL data;

	public AusgangsrechnungenListe(BL data) {
		ausgangsrechnungen = new ArrayList<>();
		this.data = data;
	}

	public void add(String[] inhalt) {
		ausgangsrechnungen.add(new Ausgangsrechnung(Integer.valueOf(inhalt[0]),
				inhalt[1], Integer.valueOf(inhalt[2])));
	}

	public void delete(int id) {
		Ausgangsrechnung p = getObjectById(id);
		if (p != null) {
			ausgangsrechnungen.remove(p);
		}

	}

	public Ausgangsrechnung getObjectById(int id) {
		for (int i = 0; i < ausgangsrechnungen.size(); i++) {
			if (ausgangsrechnungen.get(i).getId() == id) {
				return ausgangsrechnungen.get(i);
			}
		}
		return null;
	}

	public String[] getColumnNames() {
		String[] ret = { "Ausgangsrechnung-ID", "Status", "Kunden-ID" };
		return ret;
	}

	public Object[][] getRows() {
		Object[][] ret = new Object[ausgangsrechnungen.size()][this
				.getColumnNames().length];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = ausgangsrechnungen.get(i).getRow();
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
			int kid = Integer.valueOf(inhalt[2]);
			if (kid < 0) {
				throw new IllegalArgumentException("Kunden-ID ungültig");
			}
			if (data.getKundenListe().containsID(kid) == false) {
				throw new IllegalArgumentException("Kunden-ID nicht vorhanden");
			}
		} catch (NumberFormatException nfe) {
			throw new IllegalArgumentException("Kunden-ID ungültig");
		}
	}

	public boolean containsID(int id) {
		for (int i = 0; i < ausgangsrechnungen.size(); i++) {
			if (ausgangsrechnungen.get(i).getId() == id) {
				return true;
			}
		}
		return false;
	}

	public ArrayList<Ausgangsrechnung> getAusgangsrechnungen() {
		return ausgangsrechnungen;
	}

	public void setAusgangsrechnungen(
			ArrayList<Ausgangsrechnung> ausgangsrechnungen) {
		this.ausgangsrechnungen = ausgangsrechnungen;
	}

}
