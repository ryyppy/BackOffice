package gui;

import gui.angebote.AngebotePanel;
import gui.kunden.KundenPanel;
import gui.projekte.ProjektePanel;
import gui.rechnungen.AusgangsrechnungenPanel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import bl.BL;

public class Haupt_Frame extends JFrame {
	private JTabbedPane reiter;

	public Haupt_Frame() {
		super("EPU - BackOffice");
		setSize(800, 600);
		// setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		reiter = new JTabbedPane();
		reiter.addTab("Kunden", new KundenPanel(this));
		reiter.addTab("Projekte", new ProjektePanel(this));
		reiter.addTab("Angebote", new AngebotePanel(this));
		reiter.addTab("Rechnungen", new AusgangsrechnungenPanel(this));

		add(reiter);

		setVisible(true);
	}

}
