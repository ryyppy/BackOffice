package gui.tabbedViews;

import gui.EntityViewPanel;
import gui.Haupt_Frame;
import gui.editEntityViews.EditKontaktDialog;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import dal.WhereOperator;

import bl.objects.Kontakt;
import bl.objects.view.KontaktView;
import bl.objects.view.KundeView;

public class KontaktPanel extends EntityViewPanel {
	private JButton rechnungen;

	public KontaktPanel(JFrame owner) {
		super(Kontakt.class, KontaktView.class, EditKontaktDialog.class, owner);
	}

	@Override
	public void initAdditionalButtons() {
		rechnungen = new JButton("Show Rechnungen");

		JButton[] extra = { rechnungen };
		super.setAdditionalButtons(extra);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == rechnungen) {
			KontaktView selectedItem = (KontaktView) getSelectedItem();
			if (selectedItem != null) {
				JTabbedPane reiter = ((Haupt_Frame) getOwner()).getReiter();
				reiter.setSelectedIndex(reiter.indexOfTab("Eingangsrechnungen"));
				EntityViewPanel evp = (EntityViewPanel) reiter
						.getSelectedComponent();
				evp.getSearchField().setText(selectedItem.getFirma());
				evp.getOperators().setSelectedItem(WhereOperator.EQUALS);
				evp.getFieldnames().setSelectedItem("kontakt");
				evp.getSearch().doClick();
			}
		}
	}

}
