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
import bl.objects.view.reports.OffeneProjekte;
import dal.DALException;
import dal.DBEntity;

public class OffeneProjektePanel extends ReportViewPanel {
	private JButton angebotsReport;

	private JTextField offeneProjekte;

	public OffeneProjektePanel(JFrame owner) {
		super(OffeneProjekte.class, owner);

	}

	@Override
	public void initAdditionalButtons() {
		angebotsReport = new JButton("Als PDF speichern");
		JButton[] buttons = { angebotsReport };
		super.setAdditionalButtons(buttons);
	}

	@Override
	public void initAnalysisPanel() {
		offeneProjekte = new JTextField(20);
		String[] labels = { "Anzahl offener Projekte:" };
		JTextField[] textfields = { offeneProjekte };

		super.setAnalysisPanel(labels, textfields);
	}

	@Override
	public void refreshAnalysis() {
		offeneProjekte.setText(String.valueOf(tModel.getRowCount()));
	}

	@Override
	public void initPopupMenuItems() {

	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

}
