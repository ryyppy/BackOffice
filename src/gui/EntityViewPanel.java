package gui;

import gui.componentModels.MyTableCellRenderer;
import gui.componentModels.EntityTableModel;

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
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import bl.BL;
import dal.DALException;
import dal.DBEntity;

public abstract class EntityViewPanel extends JPanel implements ActionListener {
	protected JButton add, edit, delete;
	public JTable table;
	public EntityTableModel tModel;

	private JPanel additionalButtons = null;

	private Class<? extends JDialog> editClass;
	private Class<? extends DBEntity> entityClass;
	private String className;

	private JFrame owner;

	private EntityViewPanel(Class<? extends DBEntity> c) {
		setLayout(new BorderLayout());

		this.entityClass = c;
		className = entityClass.getName();
		className = className.substring(className.lastIndexOf('.') + 1);

		tModel = new EntityTableModel(c);
		table = new JTable(tModel);

		JScrollPane scrollpane = this.createTablePanel();

		initAdditionalButtons();

		JPanel navi = this.createButtonPanel();
		add(navi, BorderLayout.WEST);
		add(scrollpane);

		setVisible(true);
	}

	public EntityViewPanel(Class<? extends DBEntity> c,
			Class<? extends JDialog> edit, JFrame owner) {
		this(c);
		this.editClass = edit;
		this.owner = owner;
	}

	public JFrame getOwner() {
		return owner;
	}

	public abstract void initAdditionalButtons();

	public void setAdditionalButtons(JButton[] buttons) {
		additionalButtons = new JPanel(new GridLayout(buttons.length, 1));
		for (JButton b : buttons) {
			b.addActionListener(this);
			additionalButtons.add(b);
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

		return new JScrollPane(table);
	}

	private JPanel createButtonPanel() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		gbc.weighty = 0.1;
		panel.add(this.createStandardButtonPanel(), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridy = 2;
		panel.add(this.createSearchPanel(), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridy = 1;
		/** LAST ELEMENT **/
		gbc.anchor = GridBagConstraints.NORTH;
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
		StandardButtonActionListener a = new StandardButtonActionListener();
		add = new JButton("Add");
		edit = new JButton("Edit");
		delete = new JButton("Delete");
		JButton[] buttons = { add, edit, delete };
		JPanel top = new JPanel(new FlowLayout(FlowLayout.CENTER));
		for (JButton b : buttons) {
			b.addActionListener(a);
			top.add(b);
		}
		return top;
	}

	private JPanel createSearchPanel() {
		final JButton search = new JButton("Show Filter");
		final JButton refresh = new JButton("Show All");
		final JTextField searchField = new JTextField();

		ActionListener al = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == search) {
					tModel.setFilter(searchField.getText());
					tModel.refresh();
				} else if (e.getSource() == refresh) {
					searchField.setText("");
					tModel.setFilter("");
					tModel.refresh();
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

		JPanel ret = new JPanel(new GridLayout(4, 1));
		ret.add(new JLabel("<html><body><b> Filter:</b></body></html>"));
		ret.add(searchField);
		ret.add(search);
		ret.add(refresh);
		return ret;
	}

	private class StandardButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == add) {
				try {
					Constructor<? extends JDialog> c = editClass
							.getConstructor(JFrame.class);
					c.newInstance(owner);
					tModel.refresh();
				} catch (InstantiationException e1) {
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				} catch (IllegalArgumentException e1) {
					e1.printStackTrace();
				} catch (InvocationTargetException e1) {
					e1.printStackTrace();
				} catch (NoSuchMethodException e1) {
					e1.printStackTrace();
				} catch (SecurityException e1) {
					e1.printStackTrace();
				}
			} else if (e.getSource() == edit) {
				int a = table.convertRowIndexToModel(table.getSelectedRow());
				int aIndex = 0;
				DBEntity entity = null;

				try {
					String getter = "get" + className;

					Method method = BL.class.getMethod(getter, int.class);
					entity = (DBEntity) method.invoke(BL.class,
							(Integer) tModel.getValueAt(a, aIndex));

					Class<?>[] para = new Class<?>[2];
					para[0] = JFrame.class;
					para[1] = entityClass;

					Object[] args = new Object[2];
					args[0] = owner;
					args[1] = entity;

					Constructor<? extends JDialog> c = editClass
							.getConstructor(para);
					c.newInstance(args);

					tModel.refresh();
				} catch (NoSuchMethodException e1) {
					e1.printStackTrace();
				} catch (SecurityException e1) {
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				} catch (IllegalArgumentException e1) {
					e1.printStackTrace();
				} catch (InvocationTargetException e1) {
					e1.printStackTrace();
				} catch (InstantiationException e1) {
					e1.printStackTrace();
				} catch (Exception e1) {
					e1.printStackTrace();
					if (e1 instanceof DALException) {
						JOptionPane.showMessageDialog(owner, e1.getMessage());
					}
				}
			} else if (e.getSource() == delete) {
				int option = JOptionPane.showConfirmDialog(owner,
						"Sollen die ausgewählten Elemente gelöscht werden?",
						"Löschauftrag", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if (option == JOptionPane.YES_OPTION) {
					int[] a = table.getSelectedRows();
					int aIndex = 0;
					for (int i = 0; i < a.length; i++) {
						int b = table.convertRowIndexToModel(a[i]);
						try {
							String getter = "delete" + className;

							Method method = BL.class.getMethod(getter,
									int.class);

							method.invoke(BL.class,
									(Integer) tModel.getValueAt(b, aIndex));

						} catch (NoSuchMethodException e1) {
							e1.printStackTrace();
						} catch (SecurityException e1) {
							e1.printStackTrace();
						} catch (IllegalAccessException e1) {
							e1.printStackTrace();
						} catch (IllegalArgumentException e1) {
							e1.printStackTrace();
						} catch (InvocationTargetException e1) {
							e1.printStackTrace();
						}
					}
					tModel.refresh();
				}
			}
		}
	}
}
