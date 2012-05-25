package gui.tabbedViews.reports;

import gui.ReportViewPanel;

import java.awt.event.ActionEvent;
import java.text.NumberFormat;

import javax.swing.JFrame;
import javax.swing.JTextField;

import dal.DBEntity;

import bl.objects.view.reports.Jahresumsatz;
import bl.objects.view.reports.OffeneProjekte;
import bl.objects.view.reports.Stundensatz;

public class StundensatzPanel extends ReportViewPanel {

	private JTextField stundensatz;

	public StundensatzPanel(JFrame owner) {
		super(Stundensatz.class, owner);

	}

	@Override
	public void initAnalysisPanel() {
		stundensatz = new JTextField(20);
		String[] labels = { "Stundensatz gesamt:" };
		JTextField[] textfields = { stundensatz };

		super.setAnalysisPanel(labels, textfields);
	}

	@Override
	public void refreshAnalysis() {
		double stundensatz = 0;
		for (DBEntity entry : tModel.getEntries()) {
			Stundensatz eintrag = (Stundensatz) entry;
			stundensatz += eintrag.getStundensatz();
		}
		stundensatz /= tModel.getRowCount();
		String ausgabe = NumberFormat.getCurrencyInstance().format(stundensatz);
		this.stundensatz.setText(ausgabe);
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
