package gui;

import gui.angebote.AngebotePanel;
import gui.buchungszeilen.BuchungszeilenPanel;
import gui.kontakte.KontaktPanel;
import gui.kunden.KundenPanel;
import gui.projekte.ProjektePanel;
import gui.rechnungen.AusgangsrechnungenPanel;
import gui.rechnungen.EingangsrechnungenPanel;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class Haupt_Frame extends JFrame {
	private JTabbedPane reiter;

	public Haupt_Frame() {
		super("EPU - BackOffice");
		setSize(800, 600);
		// setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		reiter = new JTabbedPane();
		reiter.addTab("Kontakt", new KontaktPanel(this));
		reiter.addTab("Kunden", new KundenPanel(this));
		reiter.addTab("Projekte", new ProjektePanel(this));
		reiter.addTab("Angebote", new AngebotePanel(this));
		reiter.addTab("Ausgangsrechnungen", new AusgangsrechnungenPanel(this));
		reiter.addTab("Eingangsrechnungen", new EingangsrechnungenPanel(this));
		reiter.addTab("Buchungszeilen", new BuchungszeilenPanel(this));

		add(reiter);

		setVisible(true);
	}

}
