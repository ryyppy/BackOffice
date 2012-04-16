package gui.tabbedViews;

import gui.EntityViewPanel;
import gui.editEntityViews.EditAngebotDialog;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import bl.BL;
import bl.objects.Angebot;
import bl.objects.Kunde;
import bl.objects.Projekt;
import dal.DALException;

public class AngebotePanel extends EntityViewPanel {
	private JButton kunden_info, projekt_info;

	public AngebotePanel(JFrame owner) {
		super(Angebot.class, EditAngebotDialog.class, owner);
	}

	@Override
	public void initAdditionalButtons() {
		kunden_info = new JButton("Kundeninfo");
		projekt_info = new JButton("Projektinfo");
		JButton[] buttons = { kunden_info, projekt_info };
		super.setAdditionalButtons(buttons);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == kunden_info) {
			int a = table.convertRowIndexToModel(table.getSelectedRow());
			Kunde k;
			try {
				k = BL.getKunde((Integer) tModel.getValueAt(a, 5));
				JOptionPane.showMessageDialog(this, k.toString());
			} catch (DALException e1) {
				JOptionPane.showMessageDialog(this, e1.getMessage());
			}
		} else if (e.getSource() == projekt_info) {
			int a = table.convertRowIndexToModel(table.getSelectedRow());
			Projekt p;
			try {
				p = BL.getProjekt((Integer) tModel.getValueAt(a, 6));
				JOptionPane.showMessageDialog(this, p.toString());
			} catch (DALException e1) {
				JOptionPane.showMessageDialog(this, e1.getMessage());
			}
		}
	}
}
