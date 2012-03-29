package bl;

import java.util.ArrayList;

import bl.models.Projekt;

public class ProjektListe {
	private ArrayList<Projekt> projekte;

	public ProjektListe() {
		projekte = new ArrayList<>();
	}

	public void add(String[] inhalt) {
		projekte.add(new Projekt(Integer.valueOf(inhalt[0]), inhalt[1],
				inhalt[2]));
	}

	public void delete(int id) {
		Projekt p = getObjectById(id);
		if(p!=null){
			projekte.remove(p);
		}
	}

	public Projekt getObjectById(int id) {
		for (int i = 0; i < projekte.size(); i++) {
			if (projekte.get(i).getId() == id) {
				return projekte.get(i);
			}
		}
		return null;
	}

	public String[] getColumnNames() {
		String[] ret = { "Projekt-ID", "Name", "Beschreibung" };
		return ret;
	}

	public Object[][] getRows() {
		Object[][] ret = new Object[projekte.size()][this.getColumnNames().length];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = projekte.get(i).getRow();
		}
		return ret;
	}
	
	public Object[] getIDs(){
		Object[] ret = new Object[projekte.size()];
		for(int i  = 0 ; i< projekte.size();i++){
			ret[i]=projekte.get(i).getId();
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
			if (inhalt[1].length() < 1 || inhalt[2].length() < 1) {
				throw new IllegalArgumentException("Fehlende Eingabe!");
			}
		} catch (NumberFormatException nfe) {
			throw new IllegalArgumentException("ID ungültig");
		}
	}

	public boolean containsID(int id) {
		for (int i = 0; i < projekte.size(); i++) {
			if (projekte.get(i).getId() == id) {
				return true;
			}
		}
		return false;
	}

	public ArrayList<Projekt> getProjekte() {
		return projekte;
	}

	public void setProjekte(ArrayList<Projekt> projekte) {
		this.projekte = projekte;
	}

}
