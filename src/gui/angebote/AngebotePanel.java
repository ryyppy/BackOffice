package gui.angebote;

import gui.tablemodels.AngebotTableModel;

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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import bl.BL;
import bl.models.armin.Kunde;
import bl.models.armin.Projekt;

public class AngebotePanel extends JPanel implements ActionListener {
	private JButton add, edit, delete, kunden_info, projekt_info;
	private JTable table;
	private JScrollPane scrollpane;
	private AngebotTableModel tModel;
	private TableRowSorter<TableModel> tSorter;

	private JFrame owner;

	public AngebotePanel(JFrame owner) {
		// super("EPU - Angebote");
		setSize(500, 300);
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
		kunden_info = new JButton("Kundeninfo");
		projekt_info = new JButton("Projektinfo");

		add.addActionListener(this);
		edit.addActionListener(this);
		delete.addActionListener(this);
		kunden_info.addActionListener(this);
		projekt_info.addActionListener(this);

		JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panel1.add(add);
		panel1.add(edit);
		panel1.add(delete);

		JPanel panel2 = new JPanel(new GridLayout(2, 1));
		panel2.add(kunden_info);
		panel2.add(projekt_info);

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

		return panel;
	}

	public void initTable() {

		tModel = new AngebotTableModel(BL.getAngebotsListe());

		table = new JTable(tModel);
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);

		tSorter = new TableRowSorter<>(table.getModel());
		tSorter.toggleSortOrder(0);
		tSorter.setSortsOnUpdates(true);

		table.setRowSorter(tSorter);

		scrollpane = new JScrollPane(table);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == add) {
			new AddAngebotDialog(owner);
			tModel.refresh();
		} else if (e.getSource() == delete) {
			int[] a = table.getSelectedRows();
			for (int i = 0; i < a.length; i++) {
				int b = table.convertRowIndexToModel(a[i]);
				BL.deleteAngebot(Integer.valueOf((String) (tModel.getValueAt(b
						- i, 0))));
				tModel.refresh();
			}

		} else if (e.getSource() == edit) {

		} else if (e.getSource() == kunden_info) {
			int a = table.convertRowIndexToModel(table.getSelectedRow());
			Kunde k = BL.getKunde((Integer) tModel.getValueAt(a, 5));
			JOptionPane.showMessageDialog(this, k.toString());
		} else if (e.getSource() == projekt_info) {
			int a = table.convertRowIndexToModel(table.getSelectedRow());
			Projekt p = BL.getProjekt((Integer) tModel.getValueAt(a, 6));
			JOptionPane.showMessageDialog(this, p.toString());
		}
	}
}
