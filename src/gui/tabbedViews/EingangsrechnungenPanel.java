package gui.tabbedViews;

import gui.EntityViewPanel;
import gui.editEntityViews.EditEingangsrechnungDialog;
import gui.specialViews.RechnungszeilenDialog;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import bl.BL;
import bl.objects.Eingangsrechnung;
import bl.objects.Kontakt;
import dal.DALException;

public class EingangsrechnungenPanel extends EntityViewPanel {
	private JButton kontaktInfo, showRechnungszeilen;

	public EingangsrechnungenPanel(JFrame owner) {
		super(Eingangsrechnung.class, EditEingangsrechnungDialog.class, owner);
	}

	@Override
	public void initAdditionalButtons() {
		kontaktInfo = new JButton("Kontaktinfo");
		showRechnungszeilen = new JButton("Show Rechnungszeilen");

		JButton[] buttons = { kontaktInfo, showRechnungszeilen };
		super.setAdditionalButtons(buttons);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == kontaktInfo) {
			int a = table.convertRowIndexToModel(table.getSelectedRow());
			Kontakt k;
			try {
				k = BL.getKontakt((Integer) tModel.getValueAt(a, 3));
				JOptionPane.showMessageDialog(this, k.toString());
			} catch (DALException e1) {
				JOptionPane.showMessageDialog(this, e1.getMessage());
			}
		} else if (e.getSource() == showRechnungszeilen) {
			int a = table.convertRowIndexToModel(table.getSelectedRow());
			int aIndex = table.getColumn("RechnungID").getModelIndex();
			int eingangsrechnungsID = (Integer) tModel.getValueAt(a, aIndex);
			new RechnungszeilenDialog(getOwner(), eingangsrechnungsID, -1);
		}
	}
}
