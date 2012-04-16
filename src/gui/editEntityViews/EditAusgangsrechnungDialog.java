package gui.editEntityViews;

import gui.componentModels.EntityComboBoxModel;
import gui.componentModels.MyListCellRenderer;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InvalidObjectException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import bl.BL;
import bl.objects.Ausgangsrechnung;
import bl.objects.Kunde;
import dal.DALException;
import databinding.DataBinder;
import databinding.StandardRule;

public class EditAusgangsrechnungDialog extends JDialog implements
		ActionListener {
	private JComboBox<Kunde> kunden;
	private JComboBox<String> status;
	private JTextField datum;
	private JButton save, cancel;

	private Ausgangsrechnung ar;

	private String[] columnNames = { "Status", "Datum", "Kunde" };
	private String[] statusValues = { "offen", "bezahlt" };

	public EditAusgangsrechnungDialog(JFrame owner) {
		super(owner, "Ausgangsrechnung hinzufuegen", true);
		this.ar = null;
		initDialog();
	}

	public EditAusgangsrechnungDialog(JFrame owner, Ausgangsrechnung ar) {
		super(owner, "Ausgangsrechnung " + ar.getRechnungID() + " bearbeiten",
				true);
		this.ar = ar;
		initDialog();
	}

	public void initDialog() {
		setSize(300, 170);
		setLocationRelativeTo(getOwner());
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());

		JPanel buttonPanel = initButtons();
		JPanel fields = initTextFields();

		add(buttonPanel, BorderLayout.SOUTH);
		add(fields, BorderLayout.NORTH);
		// add(new RechnungszeilenPanel(owner, ar.getRechnungID(),
		// ar.getKundenID()));

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
		status.setName(columnNames[0]);
		JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JLabel l = new JLabel(columnNames[0]);
		p.add(l);
		panel.add(p);

		p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p.add(status);
		panel.add(p);

		datum = new JTextField(15);
		datum.setName(columnNames[1]);
		p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		l = new JLabel(columnNames[1]);
		p.add(l);
		panel.add(p);

		p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p.add(datum);
		panel.add(p);

		try {
			kunden = new JComboBox<Kunde>(new EntityComboBoxModel<Kunde>(
					BL.getKundeListe()));
		} catch (DALException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
			System.exit(0);
		}
		kunden.setName(columnNames[2]);
		kunden.setRenderer(new MyListCellRenderer("nachname"));
		p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		l = new JLabel(columnNames[2]);
		p.add(l);
		panel.add(p);

		p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p.add(kunden);
		panel.add(p);

		if (ar != null) {
			status.setSelectedItem(ar.getStatus());
			for (int i = 0; i < kunden.getItemCount(); i++) {
				if (kunden.getItemAt(i).getKundeID() == ar.getKundeID()) {
					kunden.setSelectedIndex(i);
					break;
				}
			}
			datum.setText(ar.getDatumString());
		} else {
			datum.setText(new StringBuilder(new SimpleDateFormat("dd.MM.yyyy")
					.format(new Date())).toString());
		}

		return panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == save) {
			try {
				DataBinder b = new DataBinder();
				String status = b.bindFrom_String2(this.status, null);
				Date datum = b.bindFrom_Date(this.datum, new StandardRule());
				int kundenID = b.bindFrom_int(kunden, null);

				if (!b.hasErrors()) {
					if (ar != null) {
						ar.setStatus(status);
						ar.setDatum(datum);
						ar.setKundeID(kundenID);
						BL.updateAusgangsrechnung(ar);
						JOptionPane.showMessageDialog(this,
								"Eintrag wurde erfolgreich bearbeitet");
					} else {
						ar = new Ausgangsrechnung(status, datum, kundenID);
						BL.saveAusgangsrechnung(ar);
						JOptionPane.showMessageDialog(this,
								"Eintrag wurde erfolgreich hinzugefügt");
					}
					dispose();
				} else {
					JOptionPane.showMessageDialog(this, b.getErrors());
				}
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
