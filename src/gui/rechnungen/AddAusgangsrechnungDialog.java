package gui.rechnungen;

import gui.comboboxmodels.KundenComboBoxModel;
import gui.comboboxmodels.MyListCellRenderer;

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
import bl.models.armin.Ausgangsrechnung;
import bl.models.armin.Kunde;

public class AddAusgangsrechnungDialog extends JDialog implements
		ActionListener {
	private JTextField[] textfeld;
	private JComboBox<Kunde> kunden;
	private JButton add, cancel;

	private String[] columnNames = { "Status", "Kunde" };

	public AddAusgangsrechnungDialog(JFrame owner) {
		super(owner, "Ausgangsrechnung hinzufuegen", true);
		setSize(300, 150);
		setLocationRelativeTo(owner);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());

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
			JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			JLabel l = new JLabel(columnNames[i]);
			p.add(l);
			panel.add(p);

			p = new JPanel(new FlowLayout(FlowLayout.LEFT));
			p.add(textfeld[i]);

			panel.add(p);
		}

		kunden = new JComboBox<Kunde>(new KundenComboBoxModel(
				BL.getKundenListe()));
		kunden.setRenderer(new MyListCellRenderer("nachname"));
		JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JLabel l = new JLabel(columnNames[columnNames.length - 1]);
		p.add(l);
		panel.add(p);

		p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p.add(kunden);
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
						.valueOf(((Kunde) (kunden.getSelectedItem())).getId());
				Ausgangsrechnung a = new Ausgangsrechnung(inhalt);
				BL.saveAusgangsrechnung(a);
				dispose();
			}catch (NullPointerException npe){
				JOptionPane.showMessageDialog(this, "Kunde muss ausgewählt sein");
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
