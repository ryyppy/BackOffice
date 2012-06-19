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
import bl.objects.Buchungszeile;
import bl.objects.Kategorie;
import bl.objects.Kunde;
import dal.DALException;
import databinding.DataBinder;
import databinding.StandardRule;

public class EditBuchungszeileDialog extends JDialog implements ActionListener {
	private JTextField[] textfeld;
	private JComboBox<Kategorie> kategorie;
	private JButton add, cancel;

	private String[] columnNames = { "Datum", "Kommentar", "Steuersatz",
			"Betrag", "KategorieID" };

	private Buchungszeile b;

	public EditBuchungszeileDialog(JFrame owner) {
		super(owner, "Buchungszeile hinzufuegen", true);
		this.b = null;
		initDialog();
	}

	public EditBuchungszeileDialog(JFrame owner, Buchungszeile b) {
		super(owner, "Buchungszeile " + b.getBuchungszeileID() + " bearbeiten",
				true);
		this.b = b;
		initDialog();
	}

	public void initDialog() {
		setSize(300, 200);
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

		JPanel panel = new JPanel(new GridLayout(columnNames.length, 2));

		for (int i = 0; i < textfeld.length; i++) {
			textfeld[i] = new JTextField(15);
			textfeld[i].setName(columnNames[i]);
			JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			JLabel l = new JLabel(columnNames[i]);
			p.add(l);
			panel.add(p);

			p = new JPanel(new FlowLayout(FlowLayout.LEFT));
			p.add(textfeld[i]);

			panel.add(p);

		}
		kategorie = new JComboBox<Kategorie>();
		kategorie.setName(columnNames[columnNames.length - 1]);
		kategorie.setRenderer(new MyListCellRenderer());

		JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JLabel l = new JLabel(columnNames[columnNames.length - 1]);
		p.add(l);
		panel.add(p);

		p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p.add(kategorie);
		panel.add(p);

		DataBinder d = new DataBinder();
		try {
			d.bindTo_String(kategorie,
					new EntityComboBoxModel<Kategorie>(BL.getKategorieListe()),
					b == null ? null : b.getKKbz());
		} catch (DALException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}

		if (b != null) {
			d.bindTo_Date(textfeld[0], b.getDatum());
			d.bindTo_String(textfeld[1], b.getKommentar());
			d.bindTo_double(textfeld[2], b.getSteuersatz());
			d.bindTo_double(textfeld[3], b.getBetrag());

		} else {
			d.bindTo_Date(textfeld[0], new Date());
		}

		return panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == add) {

			try {
				DataBinder b = new DataBinder();
				Date datum = b.bindFrom_Date(textfeld[0], new StandardRule());
				String kommentar = b.bindFrom_String(textfeld[1],
						new StandardRule());
				double steuersatz = b.bindFrom_double(textfeld[2],
						new StandardRule());
				double betrag = b.bindFrom_double(textfeld[3],
						new StandardRule());
				String kategorieKbz = b.bindFrom_String(kategorie, null);
				JOptionPane.showMessageDialog(this, kategorieKbz);
				if (!b.hasErrors()) {
					if (this.b != null) {
						this.b.setKommentar(kommentar);
						this.b.setSteuersatz(steuersatz);
						this.b.setBetrag(betrag);
						this.b.setKKbz(kategorieKbz);
						BL.updateBuchungszeile(this.b);
						// JOptionPane.showMessageDialog(this,
						// "Eintrag wurde erfolgreich bearbeitet");
					} else {
						this.b = new Buchungszeile(datum, kommentar,
								steuersatz, betrag, kategorieKbz);
						BL.saveBuchungszeile(this.b);
						// JOptionPane.showMessageDialog(this,
						// "Eintrag wurde erfolgreich hinzugefügt");
					}
					dispose();
				} else {
					JOptionPane.showMessageDialog(this, b.getErrors());
				}
			} catch (NullPointerException npe) {
				JOptionPane.showMessageDialog(this,
						"Kategorie muss ausgewählt sein", "Error",
						JOptionPane.ERROR_MESSAGE);
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
