package gui.tabbedViews;

import gui.EntityViewPanel;
import gui.HauptFrame;
import gui.editEntityViews.EditKundeDialog;

import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;

import bl.objects.Kunde;
import bl.objects.view.KundeView;
import dal.WhereOperator;

public class KundenPanel extends EntityViewPanel {
	private JMenuItem angebote, rechnungen;

	public KundenPanel(JFrame owner) {
		super(Kunde.class, KundeView.class, EditKundeDialog.class, owner);
	}

	@Override
	public void initAdditionalButtons() {

	}

	@Override
	public void initAnalysisPanel() {

	}

	@Override
	public void initPopupMenuItems() {
		angebote = new JMenuItem("Angebote anzeigen");
		rechnungen = new JMenuItem("Rechnungen anzeigen");

		JMenuItem[] menuitems = { angebote, rechnungen };
		super.setPopupMenuItems(menuitems);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == angebote) {
			KundeView selectedItem = (KundeView) getSelectedItem();
			if (selectedItem != null) {
				JTabbedPane reiter = ((HauptFrame) getOwner()).getReiter();
				reiter.setSelectedIndex(reiter.indexOfTab("Angebote"));
				EntityViewPanel evp = (EntityViewPanel) reiter
						.getSelectedComponent();
				evp.getSearchField().setText(selectedItem.getNachname());
				evp.getOperators().setSelectedItem(WhereOperator.EQUALS);
				evp.getFieldnames().setSelectedItem("kunde");
				evp.getSearch().doClick();
			}
		} else if (e.getSource() == rechnungen) {
			KundeView selectedItem = (KundeView) getSelectedItem();
			if (selectedItem != null) {
				JTabbedPane reiter = ((HauptFrame) getOwner()).getReiter();
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
