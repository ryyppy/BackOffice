package gui.buchungszeilen;

import gui.models.tablemodels.BuchungszeilenTableModel;
import gui.models.tablemodels.CheckBoxCellEditor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import bl.BL;
import bl.objects.Buchungszeile;
import bl.objects.Kategorie;

public class BuchungszeilenPanel extends JPanel implements ActionListener {
	private JButton add, edit, delete, addKategorie, kategorieInfo,
			selectRechnungen;
	private JTable table;
	private JScrollPane scrollpane;
	private BuchungszeilenTableModel tModel;
	private TableRowSorter<TableModel> tSorter;

	private JFrame owner;

	public BuchungszeilenPanel(JFrame owner) {
		// super("EPU - Rechnungen");
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
		addKategorie = new JButton("Add Kategorie");
		kategorieInfo = new JButton("Kategorieinfo");
		selectRechnungen = new JButton("Select Rechnungen");

		add.addActionListener(this);
		edit.addActionListener(this);
		delete.addActionListener(this);
		addKategorie.addActionListener(this);
		kategorieInfo.addActionListener(this);
		selectRechnungen.addActionListener(this);

		JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panel1.add(add);
		panel1.add(edit);
		panel1.add(delete);

		JPanel panel2 = new JPanel(new GridLayout(3, 1));
		panel2.add(addKategorie);
		panel2.add(kategorieInfo);
		panel2.add(selectRechnungen);

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

		tModel = new BuchungszeilenTableModel();

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
			new EditBuchungszeileDialog(owner);
			tModel.refresh();
		} else if (e.getSource() == delete) {
			int[] a = table.getSelectedRows();
			for (int i = 0; i < a.length; i++) {
				int b = table.convertRowIndexToModel(a[i]);
				BL.deleteBuchungszeile(Integer.valueOf(String.valueOf((tModel
						.getValueAt(b - i, 0)))));
			}
			tModel.refresh();
		} else if (e.getSource() == edit) {
			int a = table.convertRowIndexToModel(table.getSelectedRow());
			Buchungszeile b = BL.getBuchungszeile((Integer) tModel.getValueAt(
					a, 0));
			new EditBuchungszeileDialog(owner, b);
			tModel.refresh();
		} else if (e.getSource() == addKategorie) {
			new AddKategorieDialog(owner);
		} else if (e.getSource() == kategorieInfo) {
			int a = table.convertRowIndexToModel(table.getSelectedRow());
			Kategorie k = BL.getKategorie((Integer) tModel.getValueAt(a, 4));
			JOptionPane.showMessageDialog(this, k.toString());
		} else if (e.getSource() == selectRechnungen) {
			int a = table.convertRowIndexToModel(table.getSelectedRow());
			Buchungszeile b = BL.getBuchungszeile((Integer) tModel.getValueAt(
					a, 0));
			new RechnungenDialog(owner, b);
		}
	}
}
