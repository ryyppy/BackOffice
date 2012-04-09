package gui.kunden;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InvalidObjectException;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dal.DALException;

import bl.BL;
import bl.objects.Kunde;

public class EditKundeDialog extends JDialog implements ActionListener {
	private JTextField[] textfeld;
	private JButton save, cancel;
	
	private Kunde k;

	private String[] columnNames = { "Vorname", "Nachname", "Geburtsdatum" };

	public EditKundeDialog(JFrame owner, Kunde k ) {
		super(owner, "Kunde bearbeiten", true);
		setSize(300, 200);
		setLocationRelativeTo(owner);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		
		this.k=k;

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
			JLabel l = new JLabel(columnNames[i]);

			JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			p.add(l);
			p.add(textfeld[i]);

			panel.add(p);
		}
		textfeld[0].setText(k.getVorname());
		textfeld[1].setText(k.getNachname());
		textfeld[2].setText(k.getGeburtsdatumString());

		return panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == save) {
			
			try {
				k.setVorname(textfeld[0].getText());
				k.setNachname(textfeld[1].getText());
				k.setGeburtsdatum(textfeld[2].getText());
				BL.updateKunde(k);
				dispose();
			} catch (IllegalArgumentException iae) {
				JOptionPane.showMessageDialog(this, iae.getMessage());
			} catch (InvalidObjectException ioe) {
				JOptionPane.showMessageDialog(this, ioe.getMessage());
			} catch (ParseException pe) {
				JOptionPane.showMessageDialog(this, pe.getMessage());
			} catch (DALException de) {
				JOptionPane.showMessageDialog(this, de.getMessage());
			}
		} else if (e.getSource() == cancel) {
			dispose();
		}
	}
}
