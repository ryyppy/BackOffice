package gui.models.tablemodels;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import bl.objects.armin.Angebot;
import bl.objects.armin.Ausgangsrechnung;

public class AusgangsrechnungTableModel extends AbstractTableModel {
	private ArrayList<Ausgangsrechnung> ausgangsrechnungen;
	private String[] columnNames = { "Rechnung-ID", "Status", "Kunden-ID" };

	public AusgangsrechnungTableModel(
			ArrayList<Ausgangsrechnung> ausgangsrechnungen) {
		this.ausgangsrechnungen = ausgangsrechnungen;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return ausgangsrechnungen.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		Ausgangsrechnung a = ausgangsrechnungen.get(row);
		switch (col) {
		case 0:
			return a.getId();
		case 1:
			return a.getStatus();
		case 2:
			return a.getKundenID();
		default:
			return "";
		}
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	public String[] getColumnNames() {
		return columnNames;
	}

	public void refresh() {
		super.fireTableDataChanged();
	}
}
