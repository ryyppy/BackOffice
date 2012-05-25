package gui.tabbedViews.reports;

import gui.ReportViewPanel;

import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JTextField;

import bl.objects.view.reports.OffeneRechnungen;

public class OffeneRechnungenPanel extends ReportViewPanel {

	private JTextField offeneRechnungen;

	public OffeneRechnungenPanel(JFrame owner) {
		super(OffeneRechnungen.class, owner);

	}

	@Override
	public void initAnalysisPanel() {
		offeneRechnungen = new JTextField(20);
		String[] labels = { "Anzahl offener Rechnungen:" };
		JTextField[] textfields = { offeneRechnungen };

		super.setAnalysisPanel(labels, textfields);
	}

	@Override
	public void refreshAnalysis() {
		offeneRechnungen.setText(String.valueOf(tModel.getRowCount()));
	}

	@Override
	public void initPopupMenuItems() {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == save) {

		} else if (e.getSource() == refresh) {
			tModel.refresh();
			refreshAnalysis();
		}
	}

}
