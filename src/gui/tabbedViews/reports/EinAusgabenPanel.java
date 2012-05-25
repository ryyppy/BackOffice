package gui.tabbedViews.reports;

import gui.ReportViewPanel;

import java.awt.event.ActionEvent;
import java.text.NumberFormat;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import bl.BL;
import bl.objects.Projekt;
import bl.objects.view.reports.Ausgaben;
import bl.objects.view.reports.Einnahmen;
import bl.objects.view.reports.Jahresumsatz;
import dal.DALException;
import dal.DBEntity;

public class EinAusgabenPanel extends ReportViewPanel {
	private JMenuItem projektInfo;

	private JTextField einnahmen, ausgaben, gv;

	public EinAusgabenPanel(JFrame owner) {
		super(Einnahmen.class, Ausgaben.class, owner);

	}

	@Override
	public void initAnalysisPanel() {
		einnahmen = new JTextField(20);
		ausgaben = new JTextField(20);
		gv = new JTextField(20);
		String[] labels = { "Einnahmen:", "Ausgaben:", "Gewinn/Verlust:" };
		JTextField[] textfields = { einnahmen, ausgaben, gv };

		super.setAnalysisPanel(labels, textfields);
	}

	@Override
	public void refreshAnalysis() {
		double einnahmen = 0;
		for (DBEntity entry : tModel.getEntries()) {
			Einnahmen eintrag = (Einnahmen) entry;
			einnahmen += eintrag.getBetrag();
		}
		String ausgabe = NumberFormat.getCurrencyInstance().format(einnahmen);
		this.einnahmen.setText(ausgabe);

		double ausgaben = 0;
		for (DBEntity entry : tModel2.getEntries()) {
			Ausgaben eintrag = (Ausgaben) entry;
			ausgaben += eintrag.getBetrag();
		}
		ausgabe = NumberFormat.getCurrencyInstance().format(ausgaben);
		this.ausgaben.setText(ausgabe);
		
		ausgabe = NumberFormat.getCurrencyInstance().format(einnahmen-ausgaben);
		this.gv.setText(ausgabe);
	}

	@Override
	public void initPopupMenuItems() {
		projektInfo = new JMenuItem("Projektinfo");
		JMenuItem[] menuitems = { projektInfo };
		super.setPopupMenuItems(menuitems);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == projektInfo) {
			Jahresumsatz selectedItem = (Jahresumsatz) getSelectedItem();
			if (selectedItem != null) {
				try {
					Projekt p = BL.getProjekt(selectedItem.getProjektid());
					JOptionPane.showMessageDialog(this, p.toString());
				} catch (DALException e1) {
					JOptionPane.showMessageDialog(this, e1.getMessage());
				}
			}
		} else if (e.getSource() == save) {

		} else if (e.getSource() == refresh) {
			tModel.refresh();
			tModel2.refresh();
			refreshAnalysis();
		}
	}

}
