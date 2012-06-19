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

		kunden = new JComboBox<Kunde>();
		kunden.setName(columnNames[2]);
		kunden.setRenderer(new MyListCellRenderer("nachname"));
		p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		l = new JLabel(columnNames[2]);
		p.add(l);
		panel.add(p);

		p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p.add(kunden);
		panel.add(p);

		DataBinder d = new DataBinder();
		try {
			d.bindTo_int(kunden,
					new EntityComboBoxModel<Kunde>(BL.getKundeListe()),
					ar == null ? -2 : ar.getKundeID());
		} catch (DALException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
		if (ar != null) {
			d.bindTo_String2(status, ar.getStatus());
			d.bindTo_Date(datum, ar.getDatum());
		} else {
			d.bindTo_Date(datum, new Date());
		}

		return panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
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
						// JOptionPane.showMessageDialog(this,
						// "Eintrag wurde erfolgreich bearbeitet");
					} else {
						ar = new Ausgangsrechnung(status, datum, kundenID);
						BL.saveAusgangsrechnung(ar);
						// JOptionPane.showMessageDialog(this,
						// "Eintrag wurde erfolgreich hinzugefügt");
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
