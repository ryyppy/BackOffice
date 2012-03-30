package gui.comboboxmodels;

import java.util.ArrayList;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

import bl.models.armin.Angebot;
import bl.models.armin.Kunde;

public class AngebotComboBoxModel implements ComboBoxModel<Angebot> {
	private ArrayList<Angebot> angebote;
	private Angebot selectedItem;

	public AngebotComboBoxModel(ArrayList<Angebot> angebote) {
		this.angebote = angebote;
	}

	@Override
	public Object getSelectedItem() {
		// TODO Auto-generated method stub
		return selectedItem;
	}

	@Override
	public void setSelectedItem(Object arg0) {
		// TODO Auto-generated method stub
		selectedItem = (Angebot) arg0;
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub

	}

	@Override
	public Angebot getElementAt(int index) {
		// TODO Auto-generated method stub
		return angebote.get(index);
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return angebote.size();
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub

	}

}
