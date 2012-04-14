package gui.models.tablemodels;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import bl.BL;
import bl.objects.Buchungszeile;
import dal.DALException;

public class BuchungszeilenTableModel extends AbstractTableModel {
	private ArrayList<Buchungszeile> buchungszeilen;
	private String[] columnNames = { "Buchungszeile-ID", "Datum", "Kommentar",
			"Steuersatz", "Betrag", "Kategorie" };

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
			return b.getDatumString();
		case 2:
			return b.getKommentar();
		case 3:
			return b.getSteuersatz();
		case 4:
			return b.getBetrag();
		case 5:
			return b.getKKbz();
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
			buchungszeilen = BL.getBuchungszeilenListe();
		} catch (DALException e) {
			System.out.println(e.getMessage());
		}
		super.fireTableDataChanged();
	}
}
