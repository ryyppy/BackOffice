package gui.models.tablemodels;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import bl.BL;
import bl.objects.Kunde;
import dal.DALException;

public class KundenTableModel extends AbstractTableModel {
	private ArrayList<Kunde> kunden;
	private String[] columnNames = { "Kunden-ID", "Vorname", "Nachname",
			"Geburtsdatum" };

	public KundenTableModel() {
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
		return kunden.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		Kunde k = kunden.get(row);
		switch (col) {
		case 0:
			return k.getKundeID();
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
	}

	public String[] getColumnNames() {
		return columnNames;
	}

	public void refresh() {
		try {
			kunden = BL.getKundenListe();
		} catch (DALException e) {
			System.out.println(e.getMessage());
		}
		super.fireTableDataChanged();
	}
}
