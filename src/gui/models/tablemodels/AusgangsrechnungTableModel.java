package gui.models.tablemodels;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import com.sun.org.apache.bcel.internal.generic.GETSTATIC;

import bl.BL;
import bl.objects.Angebot;
import bl.objects.Ausgangsrechnung;

public class AusgangsrechnungTableModel extends AbstractTableModel {
	private ArrayList<Ausgangsrechnung> ausgangsrechnungen;
	private String[] columnNames = { "Rechnung-ID", "Status", "Kunden-ID" };

	public AusgangsrechnungTableModel() {
		this.refresh();
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
		ausgangsrechnungen = BL.getAusgangsrechnungenListe();
		super.fireTableDataChanged();
	}
}
