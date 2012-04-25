package gui.tabbedViews;

import gui.EntityViewPanel;
import gui.editEntityViews.EditEingangsrechnungDialog;
import gui.specialViews.LogView;
import gui.specialViews.RechnungszeilenDialog;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import bl.BL;
import bl.PDFFile;
import bl.PDFFilter;
import bl.XMLFile;
import bl.XMLFilter;
import bl.objects.Eingangsrechnung;
import bl.objects.Kontakt;
import bl.objects.view.EingangsrechnungView;

import com.itextpdf.text.DocumentException;

import dal.DALException;

public class EingangsrechnungenPanel extends EntityViewPanel {
	private JButton kontaktInfo, showRechnungszeilen, print, importRechnung;

	public EingangsrechnungenPanel(JFrame owner) {
		super(Eingangsrechnung.class, EingangsrechnungView.class,
				EditEingangsrechnungDialog.class, owner);
	}

	@Override
	public void initAdditionalButtons() {
		kontaktInfo = new JButton("Kontaktinfo");
		showRechnungszeilen = new JButton("Show Rechnungszeilen");
		print = new JButton("Print (to PDF)");
		importRechnung = new JButton("Import");

		JButton[] buttons = { kontaktInfo, showRechnungszeilen, print, null,
				importRechnung };
		super.setAdditionalButtons(buttons);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == kontaktInfo) {
			Eingangsrechnung selectedItem = (Eingangsrechnung) getSelectedDBEntity();
			if (selectedItem != null) {
				try {
					Kontakt k = BL.getKontakt(selectedItem.getKontaktID());
					JOptionPane.showMessageDialog(this, k.toString());
				} catch (DALException e1) {
					JOptionPane.showMessageDialog(this, e1.getMessage());
				}
			}
		} else if (e.getSource() == showRechnungszeilen) {
			Eingangsrechnung selectedItem = (Eingangsrechnung) getSelectedDBEntity();
			if (selectedItem != null) {
				new RechnungszeilenDialog(getOwner(),
						selectedItem.getRechnungID());
			}
		} else if (e.getSource() == print) {
			Eingangsrechnung selectedItem = (Eingangsrechnung) getSelectedDBEntity();
			if (selectedItem != null) {

				JFileChooser fc = new JFileChooser();
				fc.setAcceptAllFileFilterUsed(false);
				fc.setFileFilter(new PDFFilter());

				int returnVal = fc.showSaveDialog(this);

				if (returnVal == JFileChooser.APPROVE_OPTION) {

					File file = fc.getSelectedFile();

					PDFFile f = null;
					try {
						f = new PDFFile(file);

						Eingangsrechnung er = BL
								.getEingangsrechnung(selectedItem
										.getRechnungID());
						er.setRechnungID(selectedItem.getRechnungID());
						f.createRechnung(er);

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
		} else if (e.getSource() == importRechnung) {
			JFileChooser fc = new JFileChooser();
			fc.setAcceptAllFileFilterUsed(false);
			fc.setFileFilter(new XMLFilter());

			int returnVal = fc.showOpenDialog(this);

			if (returnVal == JFileChooser.APPROVE_OPTION) {

				File file = fc.getSelectedFile();

//				XMLFile f = null;

				try {
					if (file.exists()) {
//						f = new XMLFile(file);
//						f.importRechnungen();
						String log = BL.importEingangsrechnung(file);
						new LogView(getOwner(), log);
						tModel.refresh();
					} else {
						JOptionPane.showMessageDialog(this,
								"Datei konnte nicht gefunden werden");
					}
				} catch (ParserConfigurationException e1) {
					e1.printStackTrace();
				} catch (SAXException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (DALException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

	}
}
