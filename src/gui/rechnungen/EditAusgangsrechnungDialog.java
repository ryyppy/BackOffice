package gui.rechnungen;

import gui.models.comboboxmodels.KundenComboBoxModel;
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
import bl.objects.Ausgangsrechnung;
import bl.objects.Kunde;

public class EditAusgangsrechnungDialog extends JDialog implements
		ActionListener {
	private JComboBox<Kunde> kunden;
	private JComboBox<String> status;
	private JButton save, cancel;

	private Ausgangsrechnung ar;

	private String[] columnNames = { "Status", "Kunde" };
	private String[] statusValues = { "offen", "bezahlt" };

	public EditAusgangsrechnungDialog(JFrame owner, Ausgangsrechnung ar) {
		super(owner, "Ausgangsrechnung bearbeiten", true);
		setSize(500, 450);
		setLocationRelativeTo(owner);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());

		this.ar = ar;

		JPanel buttonPanel = initButtons();
		JPanel fields = initTextFields();

		add(buttonPanel, BorderLayout.SOUTH);
		add(fields, BorderLayout.NORTH);
		add(new RechnungszeilenPanel(owner, ar.getId(), ar.getKundenID()));

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

		JPanel panel = new JPanel(new GridLayout(columnNames.length, 1));

		status = new JComboBox<String>(statusValues);
		status.setSelectedItem(ar.getStatus());
		JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JLabel l = new JLabel(columnNames[0]);
		p.add(l);
		panel.add(p);

		p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p.add(status);
		panel.add(p);

		kunden = new JComboBox<Kunde>(new KundenComboBoxModel(
				BL.getKundenListe()));
		kunden.setSelectedItem(BL.getKunde(ar.getKundenID()));
		kunden.setRenderer(new MyListCellRenderer("nachname"));
		p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		l = new JLabel(columnNames[1]);
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
		if (e.getSource() == save) {
			String[] inhalt = new String[columnNames.length];
			try {
				ar.setStatus((String)status.getSelectedItem());
				ar.setKundenID(((Kunde) (kunden.getSelectedItem())).getId());
				BL.updateAusgangsrechnung(ar);
				dispose();
			} catch (NullPointerException npe) {
				JOptionPane.showMessageDialog(this,
						"Kunde muss ausgewählt sein");
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
