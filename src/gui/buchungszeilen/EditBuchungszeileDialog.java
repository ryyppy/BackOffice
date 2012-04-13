package gui.buchungszeilen;

import gui.models.comboboxmodels.KategorieComboBoxModel;
import gui.models.comboboxmodels.MyListCellRenderer;

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
import bl.objects.Angebot;
import bl.objects.Ausgangsrechnung;
import bl.objects.Buchungszeile;
import bl.objects.Kategorie;
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
		super(owner, "Buchungszeile bearbeiten", true);
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
		try {
			kategorie = new JComboBox<Kategorie>(new KategorieComboBoxModel(
					BL.getKategorieListe()));
		} catch (DALException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
			System.exit(0);
		}
		kategorie.setName(columnNames[columnNames.length - 1]);
		kategorie.setRenderer(new MyListCellRenderer());

		JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JLabel l = new JLabel(columnNames[columnNames.length - 1]);
		p.add(l);
		panel.add(p);

		p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p.add(kategorie);
		panel.add(p);

		if (b != null) {
			textfeld[0].setText(b.getDatumString());
			textfeld[1].setText(b.getKommentar());
			textfeld[2].setText(String.valueOf(b.getSteuersatz()));
			textfeld[3].setText(String.valueOf(b.getBetrag()));
			try {
				kategorie.setSelectedItem(BL.getKategorie(b.getKategorieKbz()));
			} catch (DALException e) {
				JOptionPane.showMessageDialog(this, e.getMessage());
				System.exit(0);
			}
		} else {
			textfeld[0].setText(new StringBuilder(new SimpleDateFormat(
					"dd.MM.yyyy").format(new Date())).toString());
		}

		return panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
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
						this.b.setKategorieKbz(kategorieKbz);
						BL.updateBuchungszeile(this.b);
					} else {
						this.b = new Buchungszeile(datum, kommentar,
								steuersatz, betrag, kategorieKbz);
						BL.saveBuchungszeile(this.b);
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
