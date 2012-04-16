package gui.specialViews;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InvalidObjectException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import bl.BL;
import bl.objects.Kategorie;
import dal.DALException;
import databinding.DataBinder;
import databinding.StandardRule;

public class AddKategorieDialog extends JDialog implements ActionListener {
	private JTextField[] textfeld;
	private JButton add, cancel;

	private String[] columnNames = { "Kurzbezeichnung", "Beschreibung" };

	public AddKategorieDialog(JFrame owner) {
		super(owner, "Kategorie hinzufuegen", true);
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
		textfeld = new JTextField[columnNames.length];

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

		return panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == add) {

			try {
				DataBinder b = new DataBinder();
				String kbz = b.bindFrom_String(textfeld[0], new StandardRule());
				String beschreibung = b.bindFrom_String(textfeld[1],
						new StandardRule());

				if (!b.hasErrors()) {
					Kategorie k = new Kategorie(kbz, beschreibung);
					BL.saveKategorie(k);
					JOptionPane.showMessageDialog(this,
							"Eintrag wurde erfolgreich hinzugefügt");
					dispose();
				} else {
					JOptionPane.showMessageDialog(this, b.getErrors());
				}
			} catch (NullPointerException npe) {
				JOptionPane.showMessageDialog(this,
						"Kategorie muss ausgewählt sein");
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
