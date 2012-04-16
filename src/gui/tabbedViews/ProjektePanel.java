package gui.tabbedViews;

import gui.EntityViewPanel;
import gui.editEntityViews.EditProjektDialog;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

import bl.objects.Projekt;

public class ProjektePanel extends EntityViewPanel {
	private JButton angebote;

	public ProjektePanel(JFrame owner) {
		super(Projekt.class, EditProjektDialog.class, owner);
	}

	@Override
	public void initAdditionalButtons() {

		angebote = new JButton("Show Angebote");
		JButton[] buttons = { angebote };
		super.setAdditionalButtons(buttons);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}
}
