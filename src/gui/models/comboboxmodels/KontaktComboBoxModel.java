package gui.models.comboboxmodels;

import java.util.ArrayList;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

import bl.objects.Kontakt;
import bl.objects.Kunde;

public class KontaktComboBoxModel implements ComboBoxModel<Kontakt> {
	private ArrayList<Kontakt> kontakte;
	private Kontakt selectedItem;

	public KontaktComboBoxModel(ArrayList<Kontakt> kontakte) {
		this.kontakte = kontakte;
	}

	@Override
	public Object getSelectedItem() {
		// TODO Auto-generated method stub
		return selectedItem;
	}

	@Override
	public void setSelectedItem(Object arg0) {
		// TODO Auto-generated method stub
		selectedItem = (Kontakt) arg0;
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub

	}

	@Override
	public Kontakt getElementAt(int index) {
		// TODO Auto-generated method stub
		return kontakte.get(index);
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return kontakte.size();
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub

	}

}
