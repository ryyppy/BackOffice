package gui.models.tablemodels;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import bl.objects.armin.Angebot;
import bl.objects.armin.Ausgangsrechnung;
import bl.objects.armin.Rechnungszeile;

public class RechnungszeilenTableModel extends AbstractTableModel {
	private ArrayList<Rechnungszeile> rechnungszeilen;
	private String[] columnNames = { "Rechnungszeile-ID", "Rechnung-ID", "Kommentar", "Steuersatz", "Betrag", "Angebot-ID" };

	public RechnungszeilenTableModel(
			ArrayList<Rechnungszeile> rechnungszeilen) {
		this.rechnungszeilen = rechnungszeilen;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return rechnungszeilen.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		Rechnungszeile r = rechnungszeilen.get(row);
		switch (col) {
		case 0:
			return r.getId();
		case 1:
			return r.getRechnungID();
		case 2:
			return r.getKommentar();
		case 3:
			return r.getSteuersatz();
		case 4:
			return r.getBetrag();
		case 5:
			return r.getAngebotsID();
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
