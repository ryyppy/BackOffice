package gui.models.tablemodels;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import bl.BL;
import bl.objects.Buchungszeile;

public class BuchungszeilenTableModel extends AbstractTableModel {
	private ArrayList<Buchungszeile> buchungszeilen;
	private String[] columnNames = { "Buchungszeile-ID", "Kommentar",
			"Steuersatz", "Betrag", "KategorieID" };

	public BuchungszeilenTableModel() {
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
		return buchungszeilen.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		Buchungszeile b = buchungszeilen.get(row);
		switch (col) {
		case 0:
			return b.getBuchungszeileID();
		case 1:
			return b.getKommentar();
		case 2:
			return b.getSteuersatz();
		case 3:
			return b.getBetrag();
		case 4:
			return b.getKategorieID();
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
		buchungszeilen = BL.getBuchungszeilenListe();
		super.fireTableDataChanged();
	}
}
