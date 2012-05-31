package gui.tabbedViews;

import gui.EntityViewPanel;
import gui.HauptFrame;
import gui.editEntityViews.EditProjektDialog;
import gui.specialViews.LogView;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import bl.BL;
import bl.filter.XMLFilter;
import bl.objects.Projekt;
import bl.objects.view.ProjektView;
import dal.DALException;
import dal.WhereOperator;

public class ProjektePanel extends EntityViewPanel {
	private JMenuItem angebote;
	private JButton zeiterfassung;

	public ProjektePanel(JFrame owner) {
		super(Projekt.class, ProjektView.class, EditProjektDialog.class, owner);
	}

	@Override
	public void initAdditionalButtons() {
		zeiterfassung = new JButton("Zeiterfassung");
		JButton[] buttons = { zeiterfassung };
		super.setAdditionalButtons(buttons);
	}

	@Override
	public void initAnalysisPanel() {
		//
		// super.setAnalysisPanel(new String[] { "Offene Rechnungen",
		// "Noch was cooles" },
		// new String[] { "12", "13" });
	}

	@Override
	public void initPopupMenuItems() {
		angebote = new JMenuItem("Angebote anzeigen");

		JMenuItem[] menuitems = { angebote };
		super.setPopupMenuItems(menuitems);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == angebote) {
			ProjektView selectedItem = (ProjektView) getSelectedItem();
			if (selectedItem != null) {
				JTabbedPane reiter = ((HauptFrame) getOwner()).getReiter();
				reiter.setSelectedIndex(reiter.indexOfTab("Angebote"));
				EntityViewPanel evp = (EntityViewPanel) reiter
						.getSelectedComponent();
				evp.getSearchField().setText(selectedItem.getName());
				evp.getOperators().setSelectedItem(WhereOperator.EQUALS);
				evp.getFieldnames().setSelectedItem("projekt");
				evp.getSearch().doClick();
			}
		} else if (e.getSource() == zeiterfassung) {
			JFileChooser fc = new JFileChooser();
			fc.setAcceptAllFileFilterUsed(false);
			fc.setFileFilter(new XMLFilter());

			int returnVal = fc.showOpenDialog(this);

			if (returnVal == JFileChooser.APPROVE_OPTION) {

				File file = fc.getSelectedFile();

				try {
					if (file.exists()) {
						String log = BL.importZeit(file);
						if (!log.isEmpty()) {
							new LogView(getOwner(), log);
						}
						tModel.refresh();
					} else {
						JOptionPane.showMessageDialog(this,
								"Datei konnte nicht gefunden werden");
					}
				} catch (ParserConfigurationException e1) {
					e1.printStackTrace();
				} catch (SAXException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (DALException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(this,
							"Fehler beim importieren der verbrauchten Stunden");
				}
			}
		}
	}
}
