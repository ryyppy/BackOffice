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
import bl.objects.view.BuchungszeileView;
import dal.DALException;

public class BuchungszeilenPanel extends EntityViewPanel {
	private JButton addKategorie, kategorieInfo, selectRechnungen;

	public BuchungszeilenPanel(JFrame owner) {
		super(Buchungszeile.class, BuchungszeileView.class,
				EditBuchungszeileDialog.class, owner);
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
			BuchungszeileView selectedItem = (BuchungszeileView) getSelectedItem();
			if (selectedItem != null) {
				try {
					Kategorie k = BL.getKategorie(selectedItem.getKKbz());
					JOptionPane.showMessageDialog(this, k.toString());
				} catch (DALException e1) {
					JOptionPane.showMessageDialog(this, e1.getMessage());
				}
			}
		} else if (e.getSource() == selectRechnungen) {
			BuchungszeileView selectedItem = (BuchungszeileView) getSelectedItem();
			if (selectedItem != null) {
				try {
					Buchungszeile b = BL.getBuchungszeile(selectedItem
							.getBuchungszeileID());
					new SelectRechnungenDialog(getOwner(), b);
				} catch (DALException e1) {
					JOptionPane.showMessageDialog(this, e1.getMessage());
				}
			}
		}
	}
}
