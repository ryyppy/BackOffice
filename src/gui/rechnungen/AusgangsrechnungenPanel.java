package gui.rechnungen;

import gui.models.tablemodels.AusgangsrechnungTableModel;

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
import bl.objects.Ausgangsrechnung;
import bl.objects.Kunde;

public class AusgangsrechnungenPanel extends JPanel implements ActionListener {
	private JButton add, edit, delete, kundenInfo, showRechnungszeilen;
	private JTable table;
	private JScrollPane scrollpane;
	private AusgangsrechnungTableModel tModel;
	private TableRowSorter<TableModel> tSorter;

	private JFrame owner;

	public AusgangsrechnungenPanel(JFrame owner) {
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
		kundenInfo = new JButton("Kundeninfo");
		showRechnungszeilen = new JButton("Show Rechnungszeilen");

		add.addActionListener(this);
		edit.addActionListener(this);
		delete.addActionListener(this);
		kundenInfo.addActionListener(this);
		showRechnungszeilen.addActionListener(this);

		JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panel1.add(add);
		panel1.add(edit);
		panel1.add(delete);

		JPanel panel2 = new JPanel(new GridLayout(2, 1));
		panel2.add(kundenInfo);
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

		tModel = new AusgangsrechnungTableModel(BL.getAusgangsrechnungenListe());

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
			new AddAusgangsrechnungDialog(owner);
			tModel.refresh();
		} else if (e.getSource() == delete) {
			int[] a = table.getSelectedRows();
			for (int i = 0; i < a.length; i++) {
				int b = table.convertRowIndexToModel(a[i]);
				BL.deleteAusgangsrechnung(Integer.valueOf((String) (tModel
						.getValueAt(b - i, 0))));
			}
			tModel.refresh();
		} else if (e.getSource() == edit) {
			int a = table.convertRowIndexToModel(table.getSelectedRow());
			Ausgangsrechnung ar = BL.getAusgangsrechnung((Integer)tModel.getValueAt(a, 0));
			new EditAusgangsrechnungDialog(owner, ar);
			tModel.refresh();
		} else if (e.getSource() == kundenInfo) {
			int a = table.convertRowIndexToModel(table.getSelectedRow());
			Kunde k = BL.getKunde((Integer) tModel.getValueAt(a, 2));
			JOptionPane.showMessageDialog(this, k.toString());
		} else if (e.getSource() == showRechnungszeilen) {
			int a = table.convertRowIndexToModel(table.getSelectedRow());
			int ausgangsrechnungsID = (Integer) tModel.getValueAt(a, 0);
			int kundenID = (Integer) tModel.getValueAt(a, 2);
			new RechnungszeilenFrame(owner, ausgangsrechnungsID, kundenID);
		}
	}
}
