package gui.tabbedViews;

import gui.EntityViewPanel;
import gui.Haupt_Frame;
import gui.editEntityViews.EditProjektDialog;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import bl.objects.Projekt;
import bl.objects.view.ProjektView;
import dal.WhereOperator;

public class ProjektePanel extends EntityViewPanel {
	private JButton angebote, zeiterfassung;

	public ProjektePanel(JFrame owner) {
		super(Projekt.class, ProjektView.class, EditProjektDialog.class, owner);
	}

	@Override
	public void initAdditionalButtons() {

		angebote = new JButton("Show Angebote");
		zeiterfassung = new JButton("Zeiterfassung");
		JButton[] buttons = { angebote,null, zeiterfassung };
		super.setAdditionalButtons(buttons);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == angebote) {
			ProjektView selectedItem = (ProjektView) getSelectedItem();
			if (selectedItem != null) {
				JTabbedPane reiter = ((Haupt_Frame) getOwner()).getReiter();
				reiter.setSelectedIndex(reiter.indexOfTab("Angebote"));
				EntityViewPanel evp = (EntityViewPanel) reiter
						.getSelectedComponent();
				evp.getSearchField().setText(selectedItem.getName());
				evp.getOperators().setSelectedItem(WhereOperator.EQUALS);
				evp.getFieldnames().setSelectedItem("projekt");
				evp.getSearch().doClick();
			}
		}
	}
}
