package gui.tabbedViews;

import gui.EntityViewPanel;
import gui.Haupt_Frame;
import gui.editEntityViews.EditKundeDialog;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import bl.objects.Kunde;
import bl.objects.view.KundeView;
import dal.WhereOperator;

public class KundenPanel extends EntityViewPanel {
	private JButton angebote, rechnungen;

	public KundenPanel(JFrame owner) {
		super(Kunde.class, KundeView.class, EditKundeDialog.class, owner);
	}

	@Override
	public void initAdditionalButtons() {
		angebote = new JButton("Show Angebote");
		rechnungen = new JButton("Show Rechnungen");

		JButton[] extra = { angebote, rechnungen };
		super.setAdditionalButtons(extra);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == angebote) {
			KundeView selectedItem = (KundeView) getSelectedItem();
			if (selectedItem != null) {
				JTabbedPane reiter = ((Haupt_Frame) getOwner()).getReiter();
				reiter.setSelectedIndex(reiter.indexOfTab("Angebote"));
				EntityViewPanel evp = (EntityViewPanel) reiter
						.getSelectedComponent();
				evp.getSearchField().setText(selectedItem.getNachname());
				evp.getOperators().setSelectedItem(WhereOperator.EQUALS);
				evp.getFieldnames().setSelectedItem("kunde");
				evp.getSearch().doClick();
			}
		}else if (e.getSource() == rechnungen) {
			KundeView selectedItem = (KundeView) getSelectedItem();
			if (selectedItem != null) {
				JTabbedPane reiter = ((Haupt_Frame) getOwner()).getReiter();
				reiter.setSelectedIndex(reiter.indexOfTab("Ausgangsrechnungen"));
				EntityViewPanel evp = (EntityViewPanel) reiter
						.getSelectedComponent();
				evp.getSearchField().setText(selectedItem.getNachname());
				evp.getOperators().setSelectedItem(WhereOperator.EQUALS);
				evp.getFieldnames().setSelectedItem("kunde");
				evp.getSearch().doClick();
			}
		}
	}
}
