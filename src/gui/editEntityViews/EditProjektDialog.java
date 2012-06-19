package gui.editEntityViews;

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
import bl.objects.Projekt;
import dal.DALException;
import databinding.DataBinder;
import databinding.StandardRule;

public class EditProjektDialog extends JDialog implements ActionListener {
	private JTextField[] textfeld;
	private JButton save, cancel;

	private Projekt p;

	private String[] columnNames = { "Name", "Beschreibung",
			"Verbrauchte Stunden" };

	public EditProjektDialog(JFrame owner) {
		super(owner, "Projekt hinzufuegen", true);
		this.p = null;
		initDialog();
	}

	public EditProjektDialog(JFrame owner, Projekt p) {
		super(owner, "Projekt " + p.getProjektID() + " bearbeiten", true);
		this.p = p;
		initDialog();
	}

	public void initDialog() {
		setSize(300, 150);
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

		save = new JButton("Save");
		cancel = new JButton("Cancel");

		save.addActionListener(this);
		cancel.addActionListener(this);

		panel.add(save);
		panel.add(cancel);

		return panel;
	}

	public JPanel initTextFields() {
		textfeld = new JTextField[columnNames.length];

		JPanel panel = new JPanel(new GridLayout(textfeld.length, 1));

		for (int i = 0; i < textfeld.length; i++) {
			textfeld[i] = new JTextField(20);
			textfeld[i].setName(columnNames[i]);
			JLabel l = new JLabel(columnNames[i]);

			JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			p.add(l);
			p.add(textfeld[i]);

			panel.add(p);
		}

		if (p != null) {
			DataBinder d = new DataBinder();
			d.bindTo_String(textfeld[0], p.getName());
			d.bindTo_String(textfeld[1], p.getBeschreibung());
			d.bindTo_double(textfeld[2], p.getVerbrauchteStunden());
		}

		return panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == save) {
			try {
				DataBinder b = new DataBinder();
				String name = b
						.bindFrom_String(textfeld[0], new StandardRule());
				String beschreibung = b.bindFrom_String(textfeld[1],
						new StandardRule());
				Double verbrauchteStunden = b.bindFrom_double(textfeld[2],
						new StandardRule());
				if (!b.hasErrors()) {
					if (p != null) {
						p.setName(name);
						p.setBeschreibung(beschreibung);
						p.setVerbrauchteStunden(verbrauchteStunden);
						BL.updateProjekt(p);
						// JOptionPane.showMessageDialog(this,
						// "Eintrag wurde erfolgreich bearbeitet");
					} else {
						p = new Projekt(name, beschreibung, verbrauchteStunden);
						BL.saveProjekt(p);
						// JOptionPane.showMessageDialog(this,
						// "Eintrag wurde erfolgreich hinzugefügt");
					}
					dispose();
				} else {
					JOptionPane.showMessageDialog(this, b.getErrors());
				}
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
