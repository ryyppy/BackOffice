package gui.editEntityViews;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.InvalidObjectException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import bl.BL;
import bl.objects.Kontakt;
import dal.DALException;
import databinding.DataBinder;
import databinding.StandardRule;

public class EditKontaktDialog extends JDialog implements ActionListener,
		KeyListener {
	private JTextField[] textfeld;
	private JButton save, cancel;

	private Kontakt k;

	private String[] columnNames = { "Firma", "Name", "Telefon" };

	public EditKontaktDialog(JFrame owner) {
		super(owner, "Kontakt hinzufuegen", true);
		this.k = null;
		initDialog();

	}

	public EditKontaktDialog(JFrame owner, Kontakt k) {
		super(owner, "Kontakt " + k.getKontaktID() + " bearbeiten", true);
		this.k = k;
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
			textfeld[i].addKeyListener(this);
			textfeld[i].setName(columnNames[i]);
			JLabel l = new JLabel(columnNames[i]);

			JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			p.add(l);
			p.add(textfeld[i]);

			panel.add(p);
		}
		if (k != null) {
			textfeld[0].setText(k.getFirma());
			textfeld[1].setText(k.getName());
			textfeld[2].setText(k.getTelefon());
		}

		return panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource() == save) {

			try {
				DataBinder b = new DataBinder();
				String firma = b.bindFrom_String(textfeld[0],
						new StandardRule());
				String name = b
						.bindFrom_String(textfeld[1], new StandardRule());
				String telefon = b.bindFrom_String(textfeld[2],
						new StandardRule());

				if (!b.hasErrors()) {
					if (k != null) {
						k.setFirma(firma);
						k.setName(name);
						k.setTelefon(telefon);
						BL.updateKontakt(k);
						JOptionPane.showMessageDialog(this,
								"Eintrag wurde erfolgreich bearbeitet");
					} else {
						k = new Kontakt(firma, name, telefon);
						BL.saveKontakt(k);
						JOptionPane.showMessageDialog(this,
								"Eintrag wurde erfolgreich hinzugefügt");
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
				de.printStackTrace();
				JOptionPane.showMessageDialog(this, de.getMessage());
				System.out.println(de.getMessage());
			}
		} else if (e.getSource() == cancel) {
			dispose();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			save.doClick();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}
}
