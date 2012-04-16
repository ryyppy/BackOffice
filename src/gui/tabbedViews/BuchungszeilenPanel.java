package gui.tabbedViews;

import gui.EntityViewPanel;
import gui.editEntityViews.EditBuchungszeileDialog;
import gui.specialViews.AddKategorieDialog;
import gui.specialViews.SelectRechnungenDialog;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import bl.BL;
import bl.objects.Buchungszeile;
import bl.objects.Kategorie;
import dal.DALException;

public class BuchungszeilenPanel extends EntityViewPanel {
	private JButton addKategorie, kategorieInfo, selectRechnungen;

	public BuchungszeilenPanel(JFrame owner) {
		super(Buchungszeile.class, EditBuchungszeileDialog.class, owner);
	}

	@Override
	public void initAdditionalButtons() {
		addKategorie = new JButton("Add Kategorie");
		kategorieInfo = new JButton("Kategorieinfo");
		selectRechnungen = new JButton("Select Rechnungen");
		JButton[] buttons = { addKategorie, kategorieInfo, selectRechnungen };
		super.setAdditionalButtons(buttons);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addKategorie) {
			new AddKategorieDialog(getOwner());
		} else if (e.getSource() == kategorieInfo) {
			int a = table.convertRowIndexToModel(table.getSelectedRow());
			Kategorie k;
			try {
				k = BL.getKategorie(String.valueOf(tModel.getValueAt(a, 5)));
				JOptionPane.showMessageDialog(this, k.toString());
			} catch (DALException e1) {
				JOptionPane.showMessageDialog(this, e1.getMessage());
			}
		} else if (e.getSource() == selectRechnungen) {
			int a = table.convertRowIndexToModel(table.getSelectedRow());
			Buchungszeile b;
			try {
				b = BL.getBuchungszeile((Integer) tModel.getValueAt(a, 0));
				new SelectRechnungenDialog(getOwner(), b);
			} catch (DALException e1) {
				JOptionPane.showMessageDialog(this, e1.getMessage());
			}
		}
	}
}
