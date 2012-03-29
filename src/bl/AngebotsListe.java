package bl;

import java.util.ArrayList;

import bl.models.Angebot;
import bl.models.Rechnungszeile;

public class AngebotsListe {
	private ArrayList<Angebot> angebote;
	private BL data;

	public AngebotsListe(BL data) {
		angebote = new ArrayList<>();
		this.data = data;
	}

	public void add(String[] inhalt) {
		angebote.add(new Angebot(Integer.valueOf(inhalt[0]), Double
				.valueOf(inhalt[1]), Double.valueOf(inhalt[2]), Double
				.valueOf(inhalt[3]), inhalt[4], Integer.valueOf(inhalt[5]),
				Integer.valueOf(inhalt[6])));
	}

	public void delete(int id) {
		Angebot p = getObjectById(id);
		if (p != null) {
			angebote.remove(p);
		}

	}

	public Angebot getObjectById(int id) {
		for (int i = 0; i < angebote.size(); i++) {
			if (angebote.get(i).getId() == id) {
				return angebote.get(i);
			}
		}
		return null;
	}

	public String[] getColumnNames() {
		String[] ret = { "Angebot-ID", "Summe", "Dauer", "Chance", "Datum",
				"Kunden-ID", "Projekt-ID" };
		return ret;
	}

	public Object[][] getRows() {
		Object[][] ret = new Object[angebote.size()][this.getColumnNames().length];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = angebote.get(i).getRow();
		}
		return ret;
	}

	public Object[] getIDs(int kundenID) {
		ArrayList<Object> retList = new ArrayList<>();
		for (Angebot a : angebote) {
			if (a.getKundenID() == kundenID) {
				retList.add(a.getId());
			}
		}

		Object[] ret = new Object[retList.size()];
		for (int i = 0; i < retList.size(); i++) {
			ret[i] = retList.get(i);
		}
		return ret;
	}

	public int countAngebote(int projekID) {
		int ret = 0;
		for (Angebot a : angebote) {
			if (a.getProjektID() == projekID) {
				ret++;
			}
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
			double summe = Double.valueOf(inhalt[1]);
			if (summe < 0) {
				throw new IllegalArgumentException("Summe ungültig");
			}
		} catch (NumberFormatException nfe) {
			throw new IllegalArgumentException("Summe ungültig");
		}
		try {
			double dauer = Double.valueOf(inhalt[2]);
			if (dauer < 0) {
				throw new IllegalArgumentException("Dauer ungültig");
			}
		} catch (NumberFormatException nfe) {
			throw new IllegalArgumentException("Dauer ungültig");
		}
		try {
			double chance = Double.valueOf(inhalt[3]);
			if (chance < 0 || chance > 100) {
				throw new IllegalArgumentException("Chance ungültig (0-100%)");
			}
		} catch (NumberFormatException nfe) {
			throw new IllegalArgumentException("Chance ungültig");
		}
		if (inhalt[4].length() < 1) {
			throw new IllegalArgumentException("Datum ungültig!");
		}
		try {
			int kid = Integer.valueOf(inhalt[5]);
			if (kid < 0) {
				throw new IllegalArgumentException("Kunden-ID ungültig");
			}
			if (data.getKundenListe().containsID(kid) == false) {
				throw new IllegalArgumentException("Kunden-ID nicht vorhanden");
			}
		} catch (NumberFormatException nfe) {
			throw new IllegalArgumentException("Kunden-ID ungültig");
		}
		try {
			int pid = Integer.valueOf(inhalt[6]);
			if (pid < 0) {
				throw new IllegalArgumentException("Projekt-ID ungültig");
			}
			if (data.getProjektListe().containsID(pid) == false) {
				throw new IllegalArgumentException("Projekt-ID nicht vorhanden");
			}
		} catch (NumberFormatException nfe) {
			throw new IllegalArgumentException("Projekt-ID ungültig");
		}
	}

	public boolean containsID(int id) {
		for (int i = 0; i < angebote.size(); i++) {
			if (angebote.get(i).getId() == id) {
				return true;
			}
		}
		return false;
	}

	public ArrayList<Angebot> getAngebote() {
		return angebote;
	}

	public void setAngebot(ArrayList<Angebot> angebote) {
		this.angebote = angebote;
	}

}
