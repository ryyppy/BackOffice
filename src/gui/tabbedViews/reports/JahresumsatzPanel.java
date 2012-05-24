package gui.tabbedViews.reports;

import gui.ReportViewPanel;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import bl.BL;
import bl.objects.Angebot;
import bl.objects.Kunde;
import bl.objects.Projekt;
import bl.objects.view.reports.Jahresumsatz;
import dal.DALException;
import dal.DBEntity;

public class JahresumsatzPanel extends ReportViewPanel {
	private JMenuItem kundenInfo, projektInfo;
	private JButton angebotsReport;

	private JTextField jahresUmsatz;

	public JahresumsatzPanel(JFrame owner) {
		super(Jahresumsatz.class, owner);
		
	}

	@Override
	public void initAdditionalButtons() {
		angebotsReport = new JButton("Als PDF speichern");
		JButton[] buttons = { angebotsReport };
		super.setAdditionalButtons(buttons);
	}

	@Override
	public void initAnalysisPanel() {
		jahresUmsatz = new JTextField(20);
		String[] labels = { "Prognostizierter Jahresumsatz:" };
		JTextField[] textfields = { jahresUmsatz };

		super.setAnalysisPanel(labels, textfields);
	}

	@Override
	public void refreshAnalysis() {
		double prognose = 0;
		for (DBEntity entry : tModel.getEntries()) {
			Jahresumsatz eintrag = (Jahresumsatz) entry;
			prognose += eintrag.getAvgAngebote();
		}
		jahresUmsatz.setText(String.valueOf(prognose));
	}

	@Override
	public void initPopupMenuItems() {
		kundenInfo = new JMenuItem("Kundeninfo");
		projektInfo = new JMenuItem("Projektinfo");
		JMenuItem[] menuitems = { kundenInfo, projektInfo };
		super.setPopupMenuItems(menuitems);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == kundenInfo) {
			Angebot selectedItem = (Angebot) getSelectedDBEntity();
			if (selectedItem != null) {
				try {
					Kunde k = BL.getKunde(selectedItem.getKundeID());
					JOptionPane.showMessageDialog(this, k.toString());
				} catch (DALException e1) {
					JOptionPane.showMessageDialog(this, e1.getMessage());
				}
			}
		} else if (e.getSource() == projektInfo) {
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
