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
import bl.objects.view.AngebotView;
import dal.DALException;

public class AngebotePanel extends EntityViewPanel {
	private JButton kunden_info, projekt_info;

	public AngebotePanel(JFrame owner) {
		super(Angebot.class, AngebotView.class, EditAngebotDialog.class, owner);
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
			Angebot selectedItem = (Angebot) getSelectedDBEntity();
			if (selectedItem != null) {
				try {
					Kunde k = BL.getKunde(selectedItem.getKundeID());
					JOptionPane.showMessageDialog(this, k.toString());
				} catch (DALException e1) {
					JOptionPane.showMessageDialog(this, e1.getMessage());
				}
			}
		} else if (e.getSource() == projekt_info) {
			Angebot selectedItem = (Angebot) getSelectedDBEntity();
			if (selectedItem != null) {
				try {
					Projekt p = BL.getProjekt(selectedItem.getProjektID());
					JOptionPane.showMessageDialog(this, p.toString());
				} catch (DALException e1) {
					JOptionPane.showMessageDialog(this, e1.getMessage());
				}
			}
		}
	}
}
