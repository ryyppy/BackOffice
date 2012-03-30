package gui.models.comboboxmodels;

import java.util.ArrayList;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

import bl.objects.Kunde;
import bl.objects.Projekt;

public class ProjekteComboBoxModel implements ComboBoxModel<Projekt> {
	private ArrayList<Projekt> projekte;
	private Projekt selectedItem;

	public ProjekteComboBoxModel(ArrayList<Projekt> projekte) {
		this.projekte = projekte;
	}

	@Override
	public Object getSelectedItem() {
		// TODO Auto-generated method stub
		return selectedItem;
	}

	@Override
	public void setSelectedItem(Object arg0) {
		// TODO Auto-generated method stub
		selectedItem = (Projekt) arg0;
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub

	}

	@Override
	public Projekt getElementAt(int index) {
		// TODO Auto-generated method stub
		return projekte.get(index);
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return projekte.size();
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub

	}

}
