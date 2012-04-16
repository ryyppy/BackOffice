package gui.tabbedViews;

import gui.EntityViewPanel;
import gui.editEntityViews.EditEingangsrechnungDialog;
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

import com.itextpdf.text.DocumentException;

import bl.BL;
import bl.objects.Ausgangsrechnung;
import bl.objects.Eingangsrechnung;
import bl.objects.Kontakt;
import dal.DALException;
import extras.PDFFile;
import extras.PDFFilter;

public class EingangsrechnungenPanel extends EntityViewPanel {
	private JButton kontaktInfo, showRechnungszeilen, print;

	public EingangsrechnungenPanel(JFrame owner) {
		super(Eingangsrechnung.class, EditEingangsrechnungDialog.class, owner);
	}

	@Override
	public void initAdditionalButtons() {
		kontaktInfo = new JButton("Kontaktinfo");
		showRechnungszeilen = new JButton("Show Rechnungszeilen");
		print = new JButton("Print (to PDF)");

		JButton[] buttons = { kontaktInfo, showRechnungszeilen, print };
		super.setAdditionalButtons(buttons);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == kontaktInfo) {
			int a = table.convertRowIndexToModel(table.getSelectedRow());
			Kontakt k;
			try {
				k = BL.getKontakt((Integer) tModel.getValueAt(a, 3));
				JOptionPane.showMessageDialog(this, k.toString());
			} catch (DALException e1) {
				JOptionPane.showMessageDialog(this, e1.getMessage());
			}
		} else if (e.getSource() == showRechnungszeilen) {
			int a = table.convertRowIndexToModel(table.getSelectedRow());
			int aIndex = table.getColumn("RechnungID").getModelIndex();
			int eingangsrechnungsID = (Integer) tModel.getValueAt(a, aIndex);
			new RechnungszeilenDialog(getOwner(), eingangsrechnungsID);
		} else if (e.getSource() == print) {
			JFileChooser fc = new JFileChooser();
			fc.setAcceptAllFileFilterUsed(false);
			// fc.addChoosableFileFilter(new PDFFilter());
			fc.setFileFilter(new PDFFilter());
			int returnVal = fc.showSaveDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				if (!file.exists()) {
					file = new File(file.getPath() + ".pdf");
					try {
						file.createNewFile();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				PDFFile f = null;
				try {
					f = new PDFFile(file);
					int a = table
							.convertRowIndexToModel(table.getSelectedRow());
					int aIndex = table.getColumn("RechnungID").getModelIndex();
					Eingangsrechnung er;

					er = BL.getEingangsrechnung((Integer) tModel.getValueAt(a,
							aIndex));
					er.setRechnungID((Integer) tModel.getValueAt(a, aIndex));
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
					f.close();
				}

			}
		}
	}
}
