package gui.tabbedViews;

import gui.EntityViewPanel;
import gui.editEntityViews.EditKontaktDialog;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

import bl.objects.Kontakt;

public class KontaktPanel extends EntityViewPanel {
	private JButton angebote;

	public KontaktPanel(JFrame owner) {
		super(Kontakt.class, EditKontaktDialog.class, owner);
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
