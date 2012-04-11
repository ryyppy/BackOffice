package gui.rechnungen;

import gui.models.tablemodels.EingangsrechnungTableModel;

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
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import bl.BL;
import bl.objects.Eingangsrechnung;
import bl.objects.Kontakt;

public class EingangsrechnungenPanel extends JPanel implements ActionListener {
	private JButton add, edit, delete, kontaktInfo, showRechnungszeilen;
	private JTable table;
	private JScrollPane scrollpane;
	private EingangsrechnungTableModel tModel;
	private TableRowSorter<TableModel> tSorter;

	private JFrame owner;

	public EingangsrechnungenPanel(JFrame owner) {
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
		kontaktInfo = new JButton("Kontaktinfo");
		showRechnungszeilen = new JButton("Show Rechnungszeilen");

		add.addActionListener(this);
		edit.addActionListener(this);
		delete.addActionListener(this);
		kontaktInfo.addActionListener(this);
		showRechnungszeilen.addActionListener(this);

		JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panel1.add(add);
		panel1.add(edit);
		panel1.add(delete);

		JPanel panel2 = new JPanel(new GridLayout(3, 1));
		panel2.add(kontaktInfo);
		panel2.add(showRechnungszeilen);

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

		tModel = new EingangsrechnungTableModel();

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
			new EditEingangsrechnungDialog(owner);
			tModel.refresh();
		} else if (e.getSource() == delete) {
			int[] a = table.getSelectedRows();
			for (int i = 0; i < a.length; i++) {
				int b = table.convertRowIndexToModel(a[i]);
				BL.deleteEingangsrechnung(Integer.valueOf((String) (tModel
						.getValueAt(b - i, 0))));
			}
			tModel.refresh();
		} else if (e.getSource() == edit) {
			int a = table.convertRowIndexToModel(table.getSelectedRow());
			Eingangsrechnung er = BL.getEingangsrechnung((Integer) tModel
					.getValueAt(a, 0));
			new EditEingangsrechnungDialog(owner, er);
			tModel.refresh();
		} else if (e.getSource() == kontaktInfo) {
			int a = table.convertRowIndexToModel(table.getSelectedRow());
			Kontakt k = BL.getKontakt((Integer) tModel.getValueAt(a, 3));
			JOptionPane.showMessageDialog(this, k.toString());
		} else if (e.getSource() == showRechnungszeilen) {
			int a = table.convertRowIndexToModel(table.getSelectedRow());
			int eingangsrechnungsID = (Integer) tModel.getValueAt(a, 0);
			int kontaktID = (Integer) tModel.getValueAt(a, 3);
			new RechnungszeilenFrame(owner, eingangsrechnungsID, kontaktID);
		}
	}
}
