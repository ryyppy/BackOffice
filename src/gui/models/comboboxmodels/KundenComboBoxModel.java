package gui.models.comboboxmodels;

import java.util.ArrayList;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

import bl.objects.Kunde;

public class KundenComboBoxModel implements ComboBoxModel<Kunde> {
	private ArrayList<Kunde> kunden;
	private Kunde selectedItem;

	public KundenComboBoxModel(ArrayList<Kunde> kunden) {
		this.kunden = kunden;
	}

	@Override
	public Object getSelectedItem() {
		// TODO Auto-generated method stub
		return selectedItem;
	}

	@Override
	public void setSelectedItem(Object arg0) {
		// TODO Auto-generated method stub
		selectedItem = (Kunde) arg0;
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub

	}

	@Override
	public Kunde getElementAt(int index) {
		// TODO Auto-generated method stub
		return kunden.get(index);
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return kunden.size();
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub

	}

}
