package gui.angebote;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import bl.BL;

public class AddAngebotDialog extends JDialog implements ActionListener {
	private JTextField[] textfeld;
	private JComboBox kunden, projekte;
	private JButton add, cancel;

	private BL data;

	private DefaultTableModel tModel;
	private String[] columnNames;

	public AddAngebotDialog(JFrame owner, DefaultTableModel tModel,
			String[] columnNames, BL data) {
		super(owner, "Angebot hinzufuegen", true);
		setSize(300, 250);
		setLocationRelativeTo(owner);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());

		this.tModel = tModel;
		this.columnNames = columnNames;
		this.data = data;

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
		textfeld = new JTextField[columnNames.length - 2];

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

		kunden = new JComboBox(data.getKundenListe().getIDs());
		JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JLabel l = new JLabel(columnNames[columnNames.length-2]);
		p.add(l);
		panel.add(p);

		p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p.add(kunden);
		panel.add(p);

		projekte = new JComboBox(data.getProjektListe().getIDs());

		p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		l = new JLabel(columnNames[columnNames.length-1]);
		p.add(l);
		panel.add(p);

		p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p.add(projekte);
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
			inhalt[columnNames.length-2]=String.valueOf(kunden.getSelectedItem());
			inhalt[columnNames.length-1]=String.valueOf(projekte.getSelectedItem());
			try {
				data.getAngebotsListe().validate(inhalt);
				data.getAngebotsListe().add(inhalt);
				tModel.addRow(inhalt);
				dispose();
			} catch (IllegalArgumentException iae) {
				JOptionPane.showMessageDialog(this, iae.getMessage());
			}
		} else if (e.getSource() == cancel) {
			dispose();
		}
	}
}