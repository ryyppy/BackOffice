package gui.editEntityViews;

import gui.componentModels.EntityComboBoxModel;
import gui.componentModels.MyListCellRenderer;

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

import bl.BL;
import bl.objects.Angebot;
import bl.objects.Rechnungszeile;
import dal.DALException;
import databinding.DataBinder;
import databinding.StandardRule;

public class EditRechnungszeileDialog extends JDialog implements
		ActionListener {
	private JTextField[] textfeld;
	private JButton add, cancel;
	private JComboBox<Angebot> angebote;

	private String[] columnNames = { "Rechnungs-ID", "Kommentar", "Steuersatz",
			"Betrag", "Angebot-ID" };
	private int rechnungID, kundeID;

	private Rechnungszeile r;

	public EditRechnungszeileDialog(JFrame owner, int rechnungID, int kundenID) {
		super(owner, "Rechnungszeile zur Rechnung " + rechnungID
				+ " hinzufuegen", true);
		this.r = null;
		this.rechnungID = rechnungID;
		this.kundeID = kundenID;

		initDialog();
	}

	public EditRechnungszeileDialog(JFrame owner, int rechnungID,
			int kundenID, Rechnungszeile r) {
		super(owner, "Rechnungszeile der Rechnung " + rechnungID
				+ " bearbeiten", true);
		this.r = r;
		this.rechnungID = rechnungID;
		this.kundeID = kundenID;
		initDialog();
	}

	public void initDialog() {
		setSize(320, 200);
		setLocationRelativeTo(getOwner());
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

		add = new JButton("Save");
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
			textfeld[i].setName(columnNames[i]);
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

		if (kundeID != -1) {
			try {
				angebote = new JComboBox<Angebot>(
						new EntityComboBoxModel<Angebot>(
								BL.getAngebotListe(kundeID)));

			} catch (DALException e) {
				JOptionPane.showMessageDialog(this, e.getMessage());
				System.exit(0);
			}
			angebote.setName(columnNames[columnNames.length - 1]);
			angebote.setRenderer(new MyListCellRenderer("beschreibung"));

			JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			JLabel l = new JLabel(columnNames[columnNames.length - 1]);
			p.add(l);
			panel.add(p);

			p = new JPanel(new FlowLayout(FlowLayout.LEFT));
			p.add(angebote);
			panel.add(p);
		}
		if (r != null) {
			textfeld[1].setText(r.getKommentar());
			textfeld[2].setText(String.valueOf(r.getSteuersatz()));
			textfeld[3].setText(String.valueOf(r.getBetrag()));
			if (kundeID != -1) {
				for (int i = 0; i < angebote.getItemCount(); i++) {
					if (angebote.getItemAt(i).getAngebotID() == r
							.getAngebotID()) {
						angebote.setSelectedIndex(i);
						break;
					}
				}
			}
		}

		return panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == add) {

			try {
				DataBinder b = new DataBinder();

				String kommentar = b.bindFrom_String(textfeld[1],
						new StandardRule());
				double steuersatz = b.bindFrom_double(textfeld[2],
						new StandardRule());
				double betrag = b.bindFrom_double(textfeld[3],
						new StandardRule());
				Integer angebotsID = null;
				if (kundeID != -1) {
					angebotsID = b.bindFrom_int(angebote, null);
				}

				if (!b.hasErrors()) {
					if (r != null) {
						r.setKommentar(kommentar);
						r.setSteuersatz(steuersatz);
						r.setBetrag(betrag);
						r.setAngebotID(angebotsID);
						r.setRechnungID(rechnungID);
						BL.updateRechnungszeile(r);
						JOptionPane.showMessageDialog(this,
								"Eintrag wurde erfolgreich bearbeitet");
					} else {
						r = new Rechnungszeile(kommentar, steuersatz, betrag,
								rechnungID, angebotsID);
						BL.saveRechnungszeile(r);
						JOptionPane.showMessageDialog(this,
								"Eintrag wurde erfolgreich hinzugefügt");
					}
					dispose();
				} else {
					JOptionPane.showMessageDialog(this, b.getErrors());
				}
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
