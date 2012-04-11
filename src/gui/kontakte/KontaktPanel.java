package gui.kontakte;

import gui.models.tablemodels.KontaktTableModel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import bl.BL;
import bl.objects.Kontakt;

public class KontaktPanel extends JPanel implements ActionListener {
	private JButton add, edit, delete, angebote;
	private JTable table;
	private JScrollPane scrollpane;
	private KontaktTableModel tModel;
	private TableRowSorter<TableModel> tSorter;

	private JFrame owner;

	public KontaktPanel(JFrame owner) {
		// super("EPU - Kunden");
		setSize(600, 300);
		// setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		// setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		this.owner = owner;

		JPanel buttonPanel = initButtons();
		initTable();

		add(buttonPanel, BorderLayout.WEST);
		add(scrollpane);

		setVisible(true);
	}

	public JPanel initButtons() {
		add = new JButton("Add");
		edit = new JButton("Edit");
		delete = new JButton("Delete");
		angebote = new JButton("Show Angebote");

		add.addActionListener(this);
		edit.addActionListener(this);
		delete.addActionListener(this);
		angebote.addActionListener(this);

		JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panel1.add(add);
		panel1.add(edit);
		panel1.add(delete);

		JPanel panel2 = new JPanel(new GridLayout(1, 1));
		panel2.add(angebote);

		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		gbc.weighty = 0.1;
		panel.add(panel1, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridy = 1;
		/** LAST ELEMENT **/
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.weighty = 1.0;
		/** LAST ELEMENT **/
		panel.add(panel2, gbc);

		//
		// JButton button3 = new JButton("3");
		// gbc.fill = GridBagConstraints.HORIZONTAL;
		// gbc.gridy = 2;
		// panel.add(button3, gbc);
		//
		// JButton button4 = new JButton("4");
		// gbc.fill = GridBagConstraints.HORIZONTAL;
		// gbc.gridy = 3;
		// panel.add(button4, gbc);
		//
		// //und so weiter...
		// JButton button5 = new JButton("..");
		// gbc.fill = GridBagConstraints.HORIZONTAL;
		// gbc.gridy = 4;
		// panel.add(button5, gbc);
		//
		// JButton button20 = new JButton("20");
		// gbc.fill = GridBagConstraints.HORIZONTAL;
		// gbc.anchor = GridBagConstraints.NORTH;
		// gbc.weighty = 1.0;
		// gbc.gridy = 6;
		// panel.add(button20, gbc);

		return panel;
	}

	public void initTable() {
		// tModel = new DefaultTableModel(rows, columnNames);
		tModel = new KontaktTableModel();
		table = new JTable(tModel);
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);

		tSorter = new TableRowSorter<TableModel>(table.getModel());
		tSorter.toggleSortOrder(0);
		tSorter.setSortsOnUpdates(true);

		table.setRowSorter(tSorter);

		scrollpane = new JScrollPane(table);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == add) {
			new AddKontaktDialog(owner);
			tModel.refresh();
		} else if (e.getSource() == delete) {
			int[] a = table.getSelectedRows();
			for (int i = 0; i < a.length; i++) {
				int b = table.convertRowIndexToModel(a[i]);
				BL.deleteKontakt(Integer.valueOf((String) (tModel.getValueAt(b
						- i, 0))));
			}
			tModel.refresh();
		} else if (e.getSource() == edit) {
			int a = table.convertRowIndexToModel(table.getSelectedRow());
			Kontakt k = BL.getKontakt(Integer.valueOf((String)tModel.getValueAt(a, 0)));
			new EditKontaktDialog(owner, k);
			tModel.refresh();
		}
	}
}
