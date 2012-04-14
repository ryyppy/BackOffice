package gui.buchungszeilen;

import gui.models.tablemodels.MyTableCellRenderer;
import gui.models.tablemodels.RechnungAuswahlTableModel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InvalidObjectException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import bl.objects.Buchungszeile;
import dal.DALException;

public class RechnungenDialog extends JDialog implements ActionListener {
	private JButton save, cancel;
	private JTable table;
	private JScrollPane scrollpane;
	private RechnungAuswahlTableModel tModel;
	private TableRowSorter<TableModel> tSorter;

	private Buchungszeile b;

	public RechnungenDialog(JFrame owner, Buchungszeile b) {
		super(owner, "Rechnungen für Buchungszeile " + b.getBuchungszeileID()
				+ " auswählen", true);
		setSize(500, 300);
		setLocationRelativeTo(owner);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		this.b = b;

		initTable();

		add(initButtons(), BorderLayout.SOUTH);
		add(scrollpane);

		setVisible(true);
	}

	public JPanel initButtons() {
		JPanel ret = new JPanel(new FlowLayout(FlowLayout.CENTER));

		save = new JButton("Save");
		cancel = new JButton("Cancel");

		save.addActionListener(this);
		cancel.addActionListener(this);

		ret.add(save);
		ret.add(cancel);

		return ret;
	}

	public void initTable() {

		tModel = new RechnungAuswahlTableModel(b.getBuchungszeileID());

		table = new JTable(tModel);
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);

		int i = 0;
		for (String columnname : tModel.getColumnNames()) {
			if (tModel.getColumnClass(i++) != Boolean.class) {
				table.getColumn(columnname).setCellRenderer(
						new MyTableCellRenderer());
			}
		}

		tSorter = new TableRowSorter<TableModel>(table.getModel());
		tSorter.toggleSortOrder(0);
		tSorter.setSortsOnUpdates(true);
		table.setRowSorter(tSorter);
		scrollpane = new JScrollPane(table);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == save) {
			try {
				tModel.save();
				dispose();
			} catch (InvalidObjectException e1) {
				JOptionPane.showMessageDialog(this, e1.getMessage());
			} catch (DALException e1) {
				JOptionPane.showMessageDialog(this, e1.getMessage());
			}
		} else if (e.getSource() == cancel) {
			dispose();
		}
	}
}
