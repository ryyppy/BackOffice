package gui.models.tablemodels;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import dal.DALException;

import bl.BL;
import bl.objects.Kontakt;
import bl.objects.Kunde;

public class KontaktTableModel extends AbstractTableModel {
	private ArrayList<Kontakt> kontake;
	private String[] columnNames = { "Kontakt-ID", "Firma", "Name", "Telefon" };

	public KontaktTableModel() {
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
		return kontake.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		Kontakt k = kontake.get(row);
		switch (col) {
		case 0:
			return k.getKontaktID();
		case 1:
			return k.getFirma();
		case 2:
			return k.getName();
		case 3:
			return k.getTelefon();
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

	public String[] getColumnNames() {
		return columnNames;
	}

	public void refresh() {
		try {
			kontake = BL.getKontaktListe();
		} catch (DALException e) {
			System.out.println(e.getMessage());
		}
		super.fireTableDataChanged();
	}
}
