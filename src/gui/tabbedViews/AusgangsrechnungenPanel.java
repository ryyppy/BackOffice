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
import javax.swing.JOptionPane;

import bl.BL;
import bl.objects.Ausgangsrechnung;
import bl.objects.Kunde;

import com.itextpdf.text.DocumentException;

import dal.DALException;
import extras.PDFFile;
import extras.PDFFilter;

public class AusgangsrechnungenPanel extends EntityViewPanel {
	private JButton kundenInfo, showRechnungszeilen, print;

	public AusgangsrechnungenPanel(JFrame owner) {
		super(Ausgangsrechnung.class, EditAusgangsrechnungDialog.class, owner);
	}

	@Override
	public void initAdditionalButtons() {
		kundenInfo = new JButton("Kundeninfo");
		showRechnungszeilen = new JButton("Show Rechnungszeilen");
		print = new JButton("Print (to PDF)");
		JButton[] buttons = { kundenInfo, showRechnungszeilen, print };
		super.setAdditionalButtons(buttons);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == kundenInfo) {
			int a = table.convertRowIndexToModel(table.getSelectedRow());
			int kIndex = table.getColumn("KundeID").getModelIndex();
			Kunde k;
			try {
				k = BL.getKunde((Integer) tModel.getValueAt(a, kIndex));
				JOptionPane.showMessageDialog(this, k.toString());
			} catch (DALException e1) {
				JOptionPane.showMessageDialog(this, e1.getMessage());
			}
		} else if (e.getSource() == showRechnungszeilen) {
			int a = table.convertRowIndexToModel(table.getSelectedRow());
			int aIndex = table.getColumn("RechnungID").getModelIndex();
			int kIndex = table.getColumn("KundeID").getModelIndex();
			int ausgangsrechnungsID = (Integer) tModel.getValueAt(a, aIndex);
			int kundenID = Integer.valueOf(String.valueOf(tModel.getValueAt(a,
					kIndex)));
			new RechnungszeilenDialog(getOwner(), ausgangsrechnungsID, kundenID);
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
					Ausgangsrechnung r;

					r = BL.getAusgangsrechnung((Integer) tModel.getValueAt(a,
							aIndex));
					r.setRechnungID((Integer) tModel.getValueAt(a, aIndex));
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
					f.close();
				}

			}
		}
	}
}
