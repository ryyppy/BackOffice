package gui.tabbedViews;

import gui.EntityViewPanel;
import gui.editEntityViews.EditAusgangsrechnungDialog;
import gui.specialViews.RechnungszeilenDialog;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import bl.BL;
import bl.objects.Ausgangsrechnung;
import bl.objects.Kunde;
import dal.DALException;

public class AusgangsrechnungenPanel extends EntityViewPanel {
	private JButton kundenInfo, showRechnungszeilen;

	public AusgangsrechnungenPanel(JFrame owner) {
		super(Ausgangsrechnung.class, EditAusgangsrechnungDialog.class, owner);
	}

	@Override
	public void initAdditionalButtons() {
		kundenInfo = new JButton("Kundeninfo");
		showRechnungszeilen = new JButton("Show Rechnungszeilen");
		JButton[] buttons = { kundenInfo, showRechnungszeilen };
		super.setAdditionalButtons(buttons);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == kundenInfo) {
			int a = table.convertRowIndexToModel(table.getSelectedRow());
			int kIndex = table.getColumn("KundeID").getModelIndex();
			Kunde k;
			try {
				k = BL.getKunde((Integer) tModel.getValueAt(a, kIndex));
				JOptionPane.showMessageDialog(this, k.toString());
			} catch (DALException e1) {
				JOptionPane.showMessageDialog(this, e1.getMessage());
			}
		} else if (e.getSource() == showRechnungszeilen) {
			int a = table.convertRowIndexToModel(table.getSelectedRow());
			int aIndex = table.getColumn("RechnungID").getModelIndex();
			int kIndex = table.getColumn("KundeID").getModelIndex();
			int ausgangsrechnungsID = (Integer) tModel.getValueAt(a, aIndex);
			int kundenID = Integer.valueOf(String.valueOf(tModel.getValueAt(a,
					kIndex)));
			new RechnungszeilenDialog(getOwner(), ausgangsrechnungsID, kundenID);
		}
	}
}
