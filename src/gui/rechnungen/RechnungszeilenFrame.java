package gui.rechnungen;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import bl.models.armin.Angebot;

public class RechnungszeilenFrame extends JFrame implements ActionListener {
	private JButton add, edit, delete, angebotInfo;
	private JTable table;
	private JScrollPane scrollpane;
	private DefaultTableModel tModel;
	private TableRowSorter<TableModel> tSorter;

	private BL data;

	private String[] columnNames;
	private Object[][] rows;
	private int ausgangsrechnungsID, kundenID;

	public RechnungszeilenFrame(JFrame owner, BL data, int ausgangsrechnungsID, int kundenID) {
		super("EPU - Rechnungszeilen für AusgangsrechnungsID "+ausgangsrechnungsID);
		setSize(500, 300);
		setLocationRelativeTo(owner);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		this.data = data;
		this.ausgangsrechnungsID= ausgangsrechnungsID;
		this.kundenID=kundenID;

		JPanel buttonPanel = initButtons();
		initTable();

		add(buttonPanel, BorderLayout.SOUTH);
		add(scrollpane);

		setVisible(true);
	}

	public JPanel initButtons() {
		add = new JButton("Add");
		edit = new JButton("Edit");
		delete = new JButton("Delete");
		angebotInfo = new JButton("Angebotinfo");

		add.addActionListener(this);
		edit.addActionListener(this);
		delete.addActionListener(this);
		angebotInfo.addActionListener(this);

		JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panel1.add(add);
		panel1.add(edit);
		panel1.add(delete);

		JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panel2.add(angebotInfo);

		JPanel panel = new JPanel(new GridLayout(2, 1));
		panel.add(panel1);
		panel.add(panel2);

		return panel;
	}

	public void initTable() {
		rows = data.getRechnungszeilenListe().getRows(ausgangsrechnungsID);
		columnNames = data.getRechnungszeilenListe().getColumnNames();

		tModel = new DefaultTableModel(rows, columnNames);

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
			new AddRechnungszeileDialog(this, tModel, columnNames, data, ausgangsrechnungsID,kundenID);
		} else if (e.getSource() == delete) {
			int[] a = table.getSelectedRows();
			for (int i = 0; i < a.length; i++) {
				int b = table.convertRowIndexToModel(a[i]);
				data.getRechnungszeilenListe().delete(
						Integer.valueOf((String) (tModel
								.getValueAt(b - i, 0))));
				tModel.removeRow(b - i);
			}
		} else if (e.getSource() == edit) {

		} else if (e.getSource() == angebotInfo) {
			int a = table.convertRowIndexToModel(table.getSelectedRow());
			Angebot an = data.getAngebotsListe().getObjectById(
					Integer.valueOf((String) tModel.getValueAt(a, 4)));
			JOptionPane.showMessageDialog(null, an.toString());
		} 
	}
}
