package gui.projekte;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import bl.BL;

public class AddProjektDialog extends JDialog implements ActionListener {
	private JTextField[] textfeld;
	private JButton add, cancel;

	private BL data;

	private DefaultTableModel tModel;
	private String[] columnNames;

	public AddProjektDialog(JFrame owner, DefaultTableModel tModel,
			String[] columnNames, BL data) {
		super(owner, "Kunde hinzufuegen", true);
		setSize(300, 150);
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

		return panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == add) {
			String[] inhalt = new String[textfeld.length];
			for (int i = 0; i < inhalt.length; i++) {
				inhalt[i] = textfeld[i].getText();
			}
			try {
				data.getProjektListe().validate(inhalt);
				data.getProjektListe().add(inhalt);
				tModel.addRow(inhalt);
				dispose();
			} catch (IllegalArgumentException iae) {
				JOptionPane.showMessageDialog(null, iae.getMessage());
			}
		} else if (e.getSource() == cancel) {
			dispose();
		}
	}
}
