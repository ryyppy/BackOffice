package bl;

import java.util.ArrayList;

import bl.models.Kunde;

public class KundenListe {
	private ArrayList<Kunde> kunden;

	public KundenListe() {
		kunden = new ArrayList<>();
	}

	public void add(String[] inhalt) {
		kunden.add(new Kunde(Integer.valueOf(inhalt[0]), inhalt[1], inhalt[2],
				inhalt[3]));
	}

	public void delete(int id) {
		Kunde k = getObjectById(id);
		if (k != null) {
			kunden.remove(k);
		}
	}

	public Kunde getObjectById(int id) {
		for (int i = 0; i < kunden.size(); i++) {
			if (kunden.get(i).getId() == id) {
				return kunden.get(i);
			}
		}
		return null;
	}

	public String[] getColumnNames() {
		String[] ret = { "Kunden-ID", "Vorname", "Nachname", "Geburtsdatum" };
		return ret;
	}

	public Object[][] getRows() {
		Object[][] ret = new Object[kunden.size()][this.getColumnNames().length];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = kunden.get(i).getRow();
		}
		return ret;
	}
	
	public Object[] getIDs(){
		Object[] ret = new Object[kunden.size()];
		for(int i  = 0 ; i< kunden.size();i++){
			ret[i]=kunden.get(i).getId();
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
			if (inhalt[1].length() < 1 || inhalt[2].length() < 1
					|| inhalt[2].length() < 1) {
				throw new IllegalArgumentException("Fehlende Eingabe!");
			}
		} catch (NumberFormatException nfe) {
			throw new IllegalArgumentException("ID ungültig");
		}
	}

	public boolean containsID(int id) {
		for (int i = 0; i < kunden.size(); i++) {
			if (kunden.get(i).getId() == id) {
				return true;
			}
		}
		return false;
	}

	public ArrayList<Kunde> getKunden() {
		return kunden;
	}

	public void setKunden(ArrayList<Kunde> kunden) {
		this.kunden = kunden;
	}

}
