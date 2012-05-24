package gui;

import java.awt.BorderLayout;

import gui.tabbedViews.reports.JahresumsatzPanel;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class Report_Tabbed_Panel extends JPanel {
	private JTabbedPane reiter;

	public Report_Tabbed_Panel(JFrame owner) {

		setLayout(new BorderLayout());
		reiter = new JTabbedPane(JTabbedPane.LEFT);
		reiter.addTab("Jahresumsatz", new JahresumsatzPanel(owner));
		reiter.addTab("Ein-/Ausgaben", new JPanel());
		reiter.addTab("Angebotsreport", new JPanel());
		reiter.addTab("Rechnungsreport", new JPanel());

		add(reiter);

		setVisible(true);
	}

	public JTabbedPane getReiter() {
		return reiter;
	}
}
