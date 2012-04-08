package gui.models.comboboxmodels;

import java.util.ArrayList;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

import bl.objects.Kategorie;

public class KategorieComboBoxModel implements ComboBoxModel<Kategorie> {
	private ArrayList<Kategorie> kategorien;
	private Kategorie selectedItem;

	public KategorieComboBoxModel(ArrayList<Kategorie> kategorien) {
		this.kategorien = kategorien;
	}

	@Override
	public Object getSelectedItem() {
		// TODO Auto-generated method stub
		return selectedItem;
	}

	@Override
	public void setSelectedItem(Object arg0) {
		// TODO Auto-generated method stub
		selectedItem = (Kategorie) arg0;
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub

	}

	@Override
	public Kategorie getElementAt(int index) {
		// TODO Auto-generated method stub
		return kategorien.get(index);
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return kategorien.size();
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub

	}

}
