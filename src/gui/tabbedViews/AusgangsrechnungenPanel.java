package gui.tabbedViews;

import gui.EntityViewPanel;
import gui.editEntityViews.EditAusgangsrechnungDialog;
import gui.specialViews.RechnungszeilenDialog;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import bl.BL;
import bl.PDFFile;
import bl.PDFFilter;
import bl.objects.Ausgangsrechnung;
import bl.objects.Kunde;
import bl.objects.view.AusgangsrechnungView;

import com.itextpdf.text.DocumentException;

import dal.DALException;

public class AusgangsrechnungenPanel extends EntityViewPanel {
	private JMenuItem kundenInfo, showRechnungszeilen, print;

	public AusgangsrechnungenPanel(JFrame owner) {
		super(Ausgangsrechnung.class, AusgangsrechnungView.class,
				EditAusgangsrechnungDialog.class, owner);
	}

	@Override
	public void initAdditionalButtons() {

	}

	@Override
	public void initAnalysisPanel() {

	}

	@Override
	public void initPopupMenuItems() {
		print = new JMenuItem("Als PDF drucken");
		kundenInfo = new JMenuItem("Kundeninfo");
		showRechnungszeilen = new JMenuItem("Show Rechnungszeilen");
		JMenuItem[] menuitems = { showRechnungszeilen, print, kundenInfo };
		super.setPopupMenuItems(menuitems);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == kundenInfo) {
			Ausgangsrechnung selectedItem = (Ausgangsrechnung) getSelectedDBEntity();
			if (selectedItem != null) {
				try {
					Kunde k = BL.getKunde(selectedItem.getKundeID());
					JOptionPane.showMessageDialog(this, k.toString());
				} catch (DALException e1) {
					JOptionPane.showMessageDialog(this, e1.getMessage());
				}
			}
		} else if (e.getSource() == showRechnungszeilen) {
			Ausgangsrechnung selectedItem = (Ausgangsrechnung) getSelectedDBEntity();
			if (selectedItem != null) {
				new RechnungszeilenDialog(getOwner(),
						selectedItem.getRechnungID(), selectedItem.getKundeID());
			}
		} else if (e.getSource() == print) {
			Ausgangsrechnung selectedItem = (Ausgangsrechnung) getSelectedDBEntity();
			if (selectedItem != null) {

				JFileChooser fc = new JFileChooser();
				fc.setAcceptAllFileFilterUsed(false);
				fc.setFileFilter(new PDFFilter()); // addchoosablefilefilter
				int returnVal = fc.showSaveDialog(this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();

					PDFFile f = null;
					try {
						f = new PDFFile(file);

						Ausgangsrechnung r;

						r = BL.getAusgangsrechnung(selectedItem.getRechnungID());
						r.setRechnungID(selectedItem.getRechnungID());
						f.createRechnung(r);
						Desktop.getDesktop().open(file);
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(this, e1.getMessage());
					} catch (DocumentException e1) {
						e1.printStackTrace();
					} catch (DALException e1) {
						JOptionPane.showMessageDialog(this, e1.getMessage());
					} catch (IOException e1) {
						e1.printStackTrace();
					} finally {
						if (f != null) {
							f.close();
						}
					}

				}
			}
		}
	}

}
