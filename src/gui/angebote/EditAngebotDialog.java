package gui.angebote;

import gui.models.comboboxmodels.KundenComboBoxModel;
import gui.models.comboboxmodels.MyListCellRenderer;
import gui.models.comboboxmodels.ProjekteComboBoxModel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InvalidObjectException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import dal.DALException;

import bl.BL;
import bl.objects.Angebot;
import bl.objects.Kunde;
import bl.objects.Projekt;

public class EditAngebotDialog extends JDialog implements ActionListener {
	private JTextField[] textfeld;
	private JComboBox<Kunde> kunden;
	private JComboBox<Projekt> projekte;
	private JButton save, cancel;

	private Angebot a;

	private String[] columnNames = { "Summe", "Dauer", "Chance", "Kunde-ID",
			"Projekt-ID" };

	public EditAngebotDialog(JFrame owner, Angebot a) {
		super(owner, "Angebot hinzufuegen", true);
		setSize(300, 250);
		setLocationRelativeTo(owner);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());

		this.a = a;

		JPanel buttonPanel = initButtons();
		JPanel fields = initTextFields();

		add(buttonPanel, BorderLayout.SOUTH);
		add(fields);

		setVisible(true);
	}

	public JPanel initButtons() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		save = new JButton("Save");
		cancel = new JButton("Cancel");

		save.addActionListener(this);
		cancel.addActionListener(this);

		panel.add(save);
		panel.add(cancel);

		return panel;
	}

	public JPanel initTextFields() {
		textfeld = new JTextField[columnNames.length - 2];

		JPanel panel = new JPanel(new GridLayout(columnNames.length, 2));

		for (int i = 0; i < textfeld.length; i++) {
			textfeld[i] = new JTextField(15);
			JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			JLabel l = new JLabel(columnNames[i]);
			p.add(l);
			panel.add(p);

			p = new JPanel(new FlowLayout(FlowLayout.LEFT));
			p.add(textfeld[i]);

			panel.add(p);

		}
		textfeld[0].setText(String.valueOf(a.getSumme()));
		textfeld[1].setText(String.valueOf(a.getDauer()));
		textfeld[2].setText(String.valueOf(a.getChance()));

		kunden = new JComboBox<Kunde>(new KundenComboBoxModel(
				BL.getKundenListe()));
		kunden.setSelectedItem(BL.getKunde(a.getKundenID()));
		kunden.setRenderer(new MyListCellRenderer("nachname"));

		JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JLabel l = new JLabel(columnNames[columnNames.length - 2]);
		p.add(l);
		panel.add(p);

		p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p.add(kunden);
		panel.add(p);

		projekte = new JComboBox<Projekt>(new ProjekteComboBoxModel(
				BL.getProjektListe()));
		projekte.setSelectedItem(BL.getProjekt(a.getProjektID()));
		projekte.setRenderer(new MyListCellRenderer("name"));
		p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		l = new JLabel(columnNames[columnNames.length - 1]);
		p.add(l);
		panel.add(p);

		p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p.add(projekte);
		panel.add(p);

		return panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == save) {
			String[] inhalt = new String[columnNames.length];
			for (int i = 0; i < textfeld.length; i++) {
				inhalt[i] = textfeld[i].getText();
			}
			try {
				inhalt[columnNames.length - 2] = String
						.valueOf(((Kunde) (kunden.getSelectedItem())).getKundenID());
				inhalt[columnNames.length - 1] = String
						.valueOf(((Projekt) (projekte.getSelectedItem()))
								.getProjektID());
				a.setInhalt(inhalt);
				BL.updateAngebot(a);
				dispose();
			} catch (NullPointerException npe) {
				JOptionPane.showMessageDialog(this,
						"Kunde bzw. Projekt müssen ausgewählt sein");
			} catch (IllegalArgumentException iae) {
				JOptionPane.showMessageDialog(this, iae.getMessage());
			} catch (InvalidObjectException ioe) {
				JOptionPane.showMessageDialog(this, ioe.getMessage());
			} catch (DALException de) {
				JOptionPane.showMessageDialog(this, de.getMessage());
			}
		} else if (e.getSource() == cancel) {
			dispose();
		}
	}
}
