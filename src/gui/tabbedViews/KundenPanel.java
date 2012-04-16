package gui.tabbedViews;

import gui.EntityViewPanel;
import gui.editEntityViews.EditKundeDialog;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

import bl.objects.Kunde;

public class KundenPanel extends EntityViewPanel {
	private JButton angebote;

	public KundenPanel(JFrame owner) {
		super(Kunde.class, EditKundeDialog.class, owner);
	}

	@Override
	public void initAdditionalButtons() {
		angebote = new JButton("Show Angebote");

		JButton[] extra = { angebote };
		super.setAdditionalButtons(extra);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}
}
