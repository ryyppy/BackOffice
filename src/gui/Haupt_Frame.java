package gui;

import gui.angebote.AngebotePanel;
import gui.kunden.KundenPanel;
import gui.projekte.ProjektePanel;
import gui.rechnungen.RechnungenPanel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import bl.BL;
import bl.ProjektListe;

public class Haupt_Frame extends JFrame implements ActionListener {
	private JButton angebote, kunden, projekte, rechnungen;
	private JTabbedPane reiter;
	private BL data = new BL();

	public Haupt_Frame() {
		super("EPU - BackOffice");
		setSize(800, 600);
		// setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		reiter=new JTabbedPane();
		reiter.addTab("Kunden", new KundenPanel(this));
		reiter.addTab("Projekte", new ProjektePanel(this));
		reiter.addTab("Angebote", new AngebotePanel(this));
		reiter.addTab("Rechnungen", new RechnungenPanel(this));

		
		add(reiter);

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
//		if (e.getSource() == angebote) {
//			new AngeboteFrame(data);
//		} else if (e.getSource() == kunden) {
//			new KundenPanel(this, data);
//		} else if (e.getSource() == projekte) {
//			new ProjekteFrame(data);
//		}else if (e.getSource() == rechnungen) {
//			new RechnungenFrame(data);
//		}
	}
}
