package gui;

import gui.tabbedViews.reports.EinAusgabenPanel;
import gui.tabbedViews.reports.JahresumsatzPanel;
import gui.tabbedViews.reports.OffeneProjektePanel;
import gui.tabbedViews.reports.OffeneRechnungenPanel;
import gui.tabbedViews.reports.StundensatzPanel;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class Report_Tabbed_Panel extends JPanel {
	private JTabbedPane reiter;

	public Report_Tabbed_Panel(JFrame owner) {

		setLayout(new BorderLayout());
		reiter = new JTabbedPane(JTabbedPane.LEFT);
		reiter.addTab("Jahresumsatz", new JahresumsatzPanel(owner));
		reiter.addTab("Offene Projekte", new OffeneProjektePanel(owner));
		reiter.addTab("Offene Rechnungen", new OffeneRechnungenPanel(owner));
		reiter.addTab("Stundensatz", new StundensatzPanel(owner));
		reiter.addTab("Ein-/Ausgaben", new EinAusgabenPanel(owner));
		reiter.addTab("Angebotsreport", new JPanel());
		reiter.addTab("Rechnungsreport", new JPanel());

		add(reiter);

		setVisible(true);
	}

	public JTabbedPane getReiter() {
		return reiter;
	}
}
