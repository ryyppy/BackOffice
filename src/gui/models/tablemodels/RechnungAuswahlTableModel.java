package gui.models.tablemodels;

import java.io.InvalidObjectException;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import bl.BL;
import bl.objects.Ausgangsrechnung;
import bl.objects.Eingangsrechnung;
import bl.objects.Rechnung;
import bl.objects.Rechnung_Buchungszeile;
import dal.DALException;

public class RechnungAuswahlTableModel extends AbstractTableModel {
	private ArrayList<Rechnung> rechnungen;
	private String[] columnNames = { "Rechnung-ID", "Status", "Datum",
			"Kunde/Kontakt", "Auswahl" };
	private int buchungszeileID;
	private ArrayList<Rechnung_Buchungszeile> rechnungen_buchungszeilen;

	public RechnungAuswahlTableModel(int buchungszeileID) {
		this.buchungszeileID = buchungszeileID;
		this.refresh();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return rechnungen.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		Rechnung r = rechnungen.get(row);
		switch (col) {
		case 0:
			return r.getRechnungID();
		case 1:
			return r.getStatus();
		case 2:
			return r.getDatumString();
		case 3:
			if (r instanceof Ausgangsrechnung) {
				int kundenID = ((Ausgangsrechnung) r).getKundeID();
				try {
					return BL.getKunde(kundenID).getNachname() + " (Kunde: "
							+ kundenID + ")";
				} catch (DALException e) {
					System.out.println(e.getMessage());
					return "";
				}
			} else if (r instanceof Eingangsrechnung) {
				int kontaktID = ((Eingangsrechnung) r).getKontaktID();
				try {
					return BL.getKontakt(kontaktID).getFirma() + " (Kontakt: "
							+ kontaktID + ")";
				} catch (DALException e) {
					System.out.println(e.getMessage());
					return "";
				}
			} else {
				return "";
			}
		case 4:
			int c = contains(r.getRechnungID());
			if (c != -1) {
				return true;
			}
			return false;
		default:
			return "";
		}
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Rechnung r = rechnungen.get(rowIndex);
		if (columnIndex == 4) {
			// falls verknüpfung bereits vorhanden --> Loeschen
			int c = contains(r.getRechnungID());

			// wenn checkbox deselected WAR und jz selected wird --> Hinzufuegen
			if (((Boolean) getValueAt(rowIndex, columnIndex)) == false) {
				Rechnung_Buchungszeile rb = new Rechnung_Buchungszeile(
						r.getRechnungID(), buchungszeileID);
				rechnungen_buchungszeilen.add(rb);
			}

			// falls verknüpfung bereits vorhanden --> Loeschen
			if (c != -1) {
				rechnungen_buchungszeilen.remove(c);
			}
		}
		super.setValueAt(aValue, rowIndex, columnIndex);

	}

	@Override
	public Class<?> getColumnClass(int column) {
		if (column == 4)
			return Boolean.class;
		return super.getColumnClass(column);
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		if (column == 4) {
			return true;
		}
		return false;
	}

	public Object[] getColumnNames() {
		return columnNames;
	}

	public void refresh() {
		try {
			rechnungen = BL.getRechnungsListe();
			rechnungen_buchungszeilen = BL
					.getRechnung_BuchungszeileListe(buchungszeileID);
		} catch (DALException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		super.fireTableDataChanged();
	}

	public void save() throws InvalidObjectException, DALException {
		BL.deleteRechnung_Buchungszeile(buchungszeileID);
		BL.saveRechnung_Buchungszeile(rechnungen_buchungszeilen);
	}

	private int contains(int rechnungsID) {
		int ret = -1;
		for (Rechnung_Buchungszeile r : rechnungen_buchungszeilen) {
			ret++;
			if (r.getRechnungID() == rechnungsID
					&& r.getBuchungszeileID() == buchungszeileID) {
				return ret;
			}
		}
		return -1;
	}

}
