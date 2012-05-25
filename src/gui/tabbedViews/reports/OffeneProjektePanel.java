package gui.tabbedViews.reports;

import gui.ReportViewPanel;

import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JTextField;

import bl.objects.view.reports.OffeneProjekte;

public class OffeneProjektePanel extends ReportViewPanel {

	private JTextField offeneProjekte;

	public OffeneProjektePanel(JFrame owner) {
		super(OffeneProjekte.class, owner);

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
		if (e.getSource() == save) {

		} else if (e.getSource() == refresh) {
			tModel.refresh();
			refreshAnalysis();
		}
	}

}
