package gui.models.tablemodels;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import bl.objects.Kunde;

public class KundenTableModel extends AbstractTableModel {
	private ArrayList<Kunde> kunden;
	private String[] columnNames = { "Kunden-ID", "Vorname", "Nachname", "Geburtsdatum" };

	public KundenTableModel(ArrayList<Kunde> kunden) {
		this.kunden = kunden;
	}
	
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return kunden.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		Kunde k = kunden.get(row);
		switch (col) {
		case 0:
			return String.valueOf(k.getId());
		case 1:
			return k.getVorname();
		case 2:
			return k.getNachname();
		case 3:
			return k.getGeburtsdatumString();
		default:
			return "";
		}
	}
	@Override
	public String getColumnName(int column) {
		return columnNames[column];
		// switch (column) {
		// case 0:
		// return "Kunden-ID";
		// case 1:
		// return "Vorname";
		// case 2:
		// return "Nachname";
		// case 3:
		// return "Geburtsdatum";
		// default:
		// return "";
		// }
	}
	
	public String[] getColumnNames(){
		return columnNames;
	}
	
	public void refresh(){
		super.fireTableDataChanged();
	}
}
