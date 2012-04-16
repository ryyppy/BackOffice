package gui.componentModels;

import java.util.ArrayList;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

public class EntityComboBoxModel<T> implements ComboBoxModel<T> {
	private ArrayList<T> entities;
	private Object selectedItem;

	public EntityComboBoxModel(ArrayList<T> entities) {
		this.entities = entities;
	}

	@Override
	public void addListDataListener(ListDataListener arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public T getElementAt(int arg0) {
		return entities.get(arg0);
	}

	@Override
	public int getSize() {
		return entities.size();
	}

	@Override
	public void removeListDataListener(ListDataListener arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object getSelectedItem() {
		return selectedItem;
	}

	@Override
	public void setSelectedItem(Object anItem) {
		selectedItem = anItem;
	}

}
