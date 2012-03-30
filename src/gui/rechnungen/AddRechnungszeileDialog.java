package gui.rechnungen;

import gui.models.comboboxmodels.AngebotComboBoxModel;
import gui.models.comboboxmodels.MyListCellRenderer;

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
import bl.objects.armin.Angebot;
import bl.objects.armin.Projekt;
import bl.objects.armin.Rechnungszeile;

public class AddRechnungszeileDialog extends JDialog implements ActionListener {
	private JTextField[] textfeld;
	private JButton add, cancel;
	private JComboBox<Angebot> angebote;

	private BL data;

	private DefaultTableModel tModel;
	private String[] columnNames = { "Rechnungs-ID", "Kommentar", "Steuersatz",
			"Betrag", "Angebot-ID" };
	private int rechnungID, kundenID;

	public AddRechnungszeileDialog(JFrame owner, int rechnungID, int kundenID) {
		super(owner, "Rechnungszeile zur Ausgangsrechnung " + rechnungID
				+ " hinzufuegen", true);
		setSize(320, 200);
		setLocationRelativeTo(owner);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());

		this.rechnungID = rechnungID;
		this.kundenID = kundenID;

		JPanel buttonPanel = initButtons();
		JPanel fields = initTextFields();

		add(buttonPanel, BorderLayout.SOUTH);
		add(fields);

		setVisible(true);
	}

	public JPanel initButtons() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		add = new JButton("Add");
		cancel = new JButton("Cancel");

		add.addActionListener(this);
		cancel.addActionListener(this);

		panel.add(add);
		panel.add(cancel);

		return panel;
	}

	public JPanel initTextFields() {
		textfeld = new JTextField[columnNames.length - 1];

		JPanel panel = new JPanel(new GridLayout(columnNames.length, 1));

		for (int i = 0; i < textfeld.length; i++) {
			textfeld[i] = new JTextField(15);
			if (columnNames[i].equals("Rechnungs-ID")) {
				textfeld[i].setText(String.valueOf(rechnungID));
				textfeld[i].setEditable(false);
			}
			JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			JLabel l = new JLabel(columnNames[i]);
			p.add(l);
			panel.add(p);

			p = new JPanel(new FlowLayout(FlowLayout.LEFT));
			p.add(textfeld[i]);

			panel.add(p);
		}

		angebote = new JComboBox<Angebot>(new AngebotComboBoxModel(
				BL.getAngebotsListe(kundenID)));
		angebote.setRenderer(new MyListCellRenderer("projektID"));

		JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JLabel l = new JLabel(columnNames[columnNames.length - 1]);
		p.add(l);
		panel.add(p);

		p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p.add(angebote);
		panel.add(p);

		return panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == add) {
			String[] inhalt = new String[columnNames.length];
			for (int i = 0; i < textfeld.length; i++) {
				inhalt[i] = textfeld[i].getText();
			}

			try {
				inhalt[columnNames.length - 1] = String
						.valueOf(((Angebot) (angebote.getSelectedItem()))
								.getId());
				Rechnungszeile r = new Rechnungszeile(inhalt);
				BL.saveRechnungszeile(r);
				dispose();
			} catch (NullPointerException npe) {
				JOptionPane.showMessageDialog(this,
						"Angebot muss ausgewählt sein");
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
