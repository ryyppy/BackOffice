package gui;

import gui.componentModels.EntityTableModel;
import gui.componentModels.MyTableCellRenderer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import dal.DBEntity;
import dal.WhereChain;
import dal.WhereOperator;

public abstract class ReportViewPanel extends JPanel implements ActionListener {
	public JButton save, refresh;
	protected JButton search;
	protected JPopupMenu popup;
	public JTable table;
	public EntityTableModel tModel;

	private JPanel additionalButtons = null;
	private JPanel analysisPanel = null;

	private Class<? extends DBEntity> entityClass;
	private JFrame owner;

	private JComboBox<String> fieldnames;
	private JComboBox<WhereOperator> operators;
	private JTextField searchField;

	public ReportViewPanel(Class<? extends DBEntity> entityViewClass,
			JFrame owner) {
		setLayout(new BorderLayout());

		this.entityClass = entityViewClass;
		tModel = new EntityTableModel(entityViewClass);
		table = new JTable(tModel);
		popup = new JPopupMenu();

		JScrollPane scrollpane = this.createTablePanel();

		initAdditionalButtons();
		initAnalysisPanel();

		JPanel navi = this.createButtonPanel();
		add(navi, BorderLayout.SOUTH);
		add(scrollpane);

		initPopupMenuItems();

		setVisible(true);
	}

	public JFrame getOwner() {
		return owner;
	}

	public JComboBox<String> getFieldnames() {
		return fieldnames;
	}

	public JButton getSearch() {
		return search;
	}

	public JComboBox<WhereOperator> getOperators() {
		return operators;
	}

	public JTextField getSearchField() {
		return searchField;
	}

	public void initAdditionalButtons() {
		setAdditionalButtons();
	}

	public abstract void initAnalysisPanel();

	public abstract void initPopupMenuItems();

	public Object getSelectedItem() {
		if (table.getSelectedRow() == -1) {
			return null;
		}
		int a = table.convertRowIndexToModel(table.getSelectedRow());

		return tModel.getValueAt(a);
	}

	public ArrayList<Object> getSelectedItems() {
		int[] a = table.getSelectedRows();
		if (a.length == 0) {
			return null;
		}
		ArrayList<Object> ret = new ArrayList<Object>();
		for (int i = 0; i < a.length; i++) {
			int b = table.convertRowIndexToModel(a[i]);
			ret.add(tModel.getValueAt(b));
		}
		return ret;
	}

	public void setAnalysisPanel(String[] labels, JTextField[] textfields) {
		if (labels.length != textfields.length) {

		}
		analysisPanel = new JPanel(new GridLayout(labels.length, 2));
		for (int i = 0; i < labels.length; i++) {
			JPanel flow = new JPanel(new FlowLayout());
			flow.add(new JLabel(labels[i]));
			analysisPanel.add(flow);
			textfields[i].setEnabled(false);
			textfields[i].setEditable(false);
			textfields[i].setHorizontalAlignment(JTextField.CENTER);
			flow = new JPanel(new FlowLayout());
			flow.add(textfields[i]);
			analysisPanel.add(flow);
		}
		refreshAnalysis();
	}

	public abstract void refreshAnalysis();

	public void setAnalysisPanel(JPanel p) {
		this.analysisPanel = p;
	}

	public void setAdditionalButtons() {
		save = new JButton("Als PDF speichern");
		refresh = new JButton("Refresh");
		JButton[] buttons = { save, refresh };
		additionalButtons = new JPanel(new FlowLayout());
		for (JButton b : buttons) {
			if (b == null) {
				additionalButtons.add(new JLabel());
			} else {
				b.addActionListener(this);
				additionalButtons.add(b);
			}
		}
	}

	public void setPopupMenuItems(JMenuItem[] menuitems) {
		for (JMenuItem menuitem : menuitems) {
			popup.add(menuitem);
			menuitem.addActionListener(this);
		}
	}

	private JScrollPane createTablePanel() {
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);

		for (String columnname : tModel.getColumnNames()) {
			table.getColumn(columnname).setCellRenderer(
					new MyTableCellRenderer());
		}
		table.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_F5) {
					tModel.refresh();
				}
			}
		});

		TableRowSorter<TableModel> tSorter = new TableRowSorter<TableModel>(
				table.getModel());
		tSorter.toggleSortOrder(0);
		tSorter.setSortsOnUpdates(true);

		table.setRowSorter(tSorter);

		table.addMouseListener(new MouseAdapter() {

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					int rowIndex = table.rowAtPoint(table.getMousePosition());
					int columnIndex = table.columnAtPoint(table
							.getMousePosition());
					if (rowIndex != -1) {
						table.changeSelection(rowIndex, columnIndex, false,
								false);

						if (popup.getSubElements().length > 0) {
							popup.show(table, e.getX(), e.getY());
						}
					}
				}
			}
		});

		return new JScrollPane(table);
	}

	private JPanel createButtonPanel() {
		GridBagLayout gbl = new GridBagLayout();
		// gbl.columnWidths = new int[] { 0 };
		// gbl.rowHeights = new int[] { 0 };
		// gbl.columnWeights = new double[] {Double.MIN_VALUE};
		// gbl.rowWeights = new double[] { 1.0 };
		//
		JPanel panel = new JPanel(gbl);
		// int y = 0;
		GridBagConstraints gbc = new GridBagConstraints();
		// gbc.gridx = 0;
		// gbc.gridy = y++;
		// gbc.anchor = GridBagConstraints.NORTH;
		// gbc.fill = GridBagConstraints.HORIZONTAL;
		// createSearchPanel();
		// createStandardButtonPanel();
		//
		// panel.add(new JLabel("asd1"), gbc);
		//
		// if (additionalButtons != null) {
		// GridBagConstraints gbc2 = new GridBagConstraints();
		// gbc2.gridx = 0;
		// gbc2.gridy = y++;
		// gbc2.anchor = GridBagConstraints.NORTH;
		// gbc2.fill = GridBagConstraints.HORIZONTAL;
		// panel.add(new JLabel("asd2"), gbc2);
		// }
		//
		// GridBagConstraints gbc3 = new GridBagConstraints();
		// gbc3.gridx = 0;
		// gbc3.gridy = y++;
		// gbc3.anchor = GridBagConstraints.NORTH;
		// gbc3.fill = GridBagConstraints.HORIZONTAL;
		// panel.add(new JLabel("asd3"), gbc3);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		gbc.weighty = 0.1;
		panel.add(this.createStandardButtonPanel(), gbc);

		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridy = 1;
		if (analysisPanel != null) {
			panel.add(analysisPanel, gbc);
		} else {
			panel.add(new JPanel(new FlowLayout()), gbc);
		}

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridy = 3;
		panel.add(this.createSearchPanel(), gbc);

		gbc.fill = GridBagConstraints.NONE;
		gbc.gridy = 2;
		/** LAST ELEMENT **/
		gbc.anchor = GridBagConstraints.EAST;
		gbc.weighty = 1.0;
		/** LAST ELEMENT **/
		if (additionalButtons != null) {
			panel.add(additionalButtons, gbc);
		} else {
			panel.add(new JPanel(new FlowLayout()), gbc);
		}

		return panel;
	}

	private JPanel createStandardButtonPanel() {
		// StandardButtonActionListener a = new StandardButtonActionListener();
		// add = new JButton("Add");
		// edit = new JButton("Edit");
		// delete = new JButton("Delete");
		// JButton[] buttons = { add, edit, delete };
		// JPanel top = new JPanel(new FlowLayout(FlowLayout.CENTER));
		// for (JButton b : buttons) {
		// b.addActionListener(a);
		// top.add(b);
		// }
		return new JPanel();
	}

	private JPanel createSearchPanel() {
		search = new JButton("Select Filter");
		final JButton refresh = new JButton("Show All");
		fieldnames = new JComboBox<String>(tModel.columnNamesOriginal);
		operators = new JComboBox<WhereOperator>(WhereOperator.values());
		searchField = new JTextField(10);

		ActionListener al = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == search) {
					String fieldname = (String) fieldnames.getSelectedItem();
					WhereOperator operator = (WhereOperator) operators
							.getSelectedItem();
					String value = searchField.getText();
					WhereChain where = new WhereChain(fieldname, operator,
							value);
					tModel.setWhereChain(where);
					// tModel.setFilter(searchField.getText());
					tModel.refresh();
					refreshAnalysis();
				} else if (e.getSource() == refresh) {
					searchField.setText("");
					tModel.setFilter("");
					tModel.setWhereChain(null);
					tModel.refresh();

					refreshAnalysis();
				}
			}
		};
		search.addActionListener(al);
		refresh.addActionListener(al);

		searchField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
					search.doClick();
				}
			}
		});

		JPanel ret = new JPanel(new FlowLayout(FlowLayout.LEFT));

		ret.add(new JLabel("<html><body><b> Filter:</b></body></html>"));
		ret.add(fieldnames);
		ret.add(operators);
		ret.add(searchField);
		ret.add(new JLabel());
		ret.add(search);
		ret.add(refresh);
		return ret;
	}

	// private class StandardButtonActionListener implements ActionListener {
	//
	// @Override
	// public void actionPerformed(ActionEvent e) {
	// if (e.getSource() == add) {
	// try {
	// Constructor<? extends JDialog> c = editClass
	// .getConstructor(JFrame.class);
	// c.newInstance(owner);
	//
	// tModel.refresh();
	// table.changeSelection(table.convertRowIndexToView(tModel
	// .getRowCount() - 1), 0, false, false);
	// } catch (InstantiationException e1) {
	// e1.printStackTrace();
	// } catch (IllegalAccessException e1) {
	// e1.printStackTrace();
	// } catch (IllegalArgumentException e1) {
	// e1.printStackTrace();
	// } catch (InvocationTargetException e1) {
	// e1.printStackTrace();
	// } catch (NoSuchMethodException e1) {
	// e1.printStackTrace();
	// } catch (SecurityException e1) {
	// e1.printStackTrace();
	// }
	// } else if (e.getSource() == edit) {
	// DBEntity entity = (DBEntity) getSelectedItem();
	// if (entity != null) {
	// try {
	// String getter = "get" + className;
	//
	// Method method = BL.class.getMethod(getter, int.class);
	//
	// entity = (DBEntity) method.invoke(BL.class,
	// entity.getID());
	//
	// Class<?>[] para = new Class<?>[2];
	// para[0] = JFrame.class;
	// para[1] = entityClass;
	//
	// Object[] args = new Object[2];
	// args[0] = owner;
	// args[1] = entity;
	//
	// Constructor<? extends JDialog> c = editClass
	// .getConstructor(para);
	// c.newInstance(args);
	//
	// int row = table.convertRowIndexToModel(table
	// .getSelectedRow());
	// tModel.refresh();
	// table.changeSelection(table.convertRowIndexToView(row),
	// 0, false, false);
	// } catch (NoSuchMethodException e1) {
	// e1.printStackTrace();
	// } catch (SecurityException e1) {
	// e1.printStackTrace();
	// } catch (IllegalAccessException e1) {
	// e1.printStackTrace();
	// } catch (IllegalArgumentException e1) {
	// e1.printStackTrace();
	// } catch (InvocationTargetException e1) {
	// e1.printStackTrace();
	// } catch (InstantiationException e1) {
	// e1.printStackTrace();
	// } catch (Exception e1) {
	// e1.printStackTrace();
	// if (e1 instanceof DALException) {
	// JOptionPane.showMessageDialog(owner,
	// e1.getMessage());
	// }
	// }
	// }
	// } else if (e.getSource() == delete) {
	// ArrayList<Object> selected = getSelectedItems();
	// if (selected != null) {
	// int option = JOptionPane
	// .showConfirmDialog(
	// owner,
	// "Sollen die ausgewählten Elemente gelöscht werden?",
	// "Löschauftrag", JOptionPane.YES_NO_OPTION,
	// JOptionPane.QUESTION_MESSAGE);
	// if (option == JOptionPane.YES_OPTION) {
	// for (Object item : selected) {
	// DBEntity entity = (DBEntity) item;
	// try {
	// String getter = "delete" + className;
	//
	// Method method = BL.class.getMethod(getter,
	// int.class);
	//
	// method.invoke(BL.class, entity.getID());
	// } catch (NoSuchMethodException e1) {
	// e1.printStackTrace();
	// } catch (SecurityException e1) {
	// e1.printStackTrace();
	// } catch (IllegalAccessException e1) {
	// e1.printStackTrace();
	// } catch (IllegalArgumentException e1) {
	// e1.printStackTrace();
	// } catch (InvocationTargetException e1) {
	// e1.printStackTrace();
	// } catch (DALException e1) {
	// e1.printStackTrace();
	// }
	// }
	// tModel.refresh();
	// }
	// }
	// }
	// }
	// }
}
