package gui.tabbedViews.reports;

import gui.ReportViewPanel;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.NumberFormat;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.itextpdf.text.DocumentException;

import bl.BL;
import bl.PDFFile;
import bl.filter.PDFFilter;
import bl.objects.Angebot;
import bl.objects.Projekt;
import bl.objects.view.reports.Stundensatz;
import dal.DALException;
import dal.DBEntity;

public class StundensatzPanel extends ReportViewPanel {

	private JMenuItem projektInfo, angebotInfo;
	private JTextField stundensatz;

	public StundensatzPanel(JFrame owner) {
		super(Stundensatz.class, owner);

	}

	@Override
	public void initAnalysisPanel() {
		stundensatz = new JTextField(20);
		String[] labels = { "Stundensatz gesamt:" };
		JTextField[] textfields = { stundensatz };

		super.setAnalysisPanel(labels, textfields);
	}

	@Override
	public void refreshAnalysis() {
		double stundensatz = 0;
		for (DBEntity entry : tModel.getEntries()) {
			Stundensatz eintrag = (Stundensatz) entry;
			stundensatz += eintrag.getStundensatz();
		}
		stundensatz /= tModel.getRowCount();
		String ausgabe = NumberFormat.getCurrencyInstance().format(stundensatz);
		this.stundensatz.setText(ausgabe);
	}

	@Override
	public void initPopupMenuItems() {
		projektInfo = new JMenuItem("Projektinfo");
		angebotInfo = new JMenuItem("Angebotinfo");
		JMenuItem[] menuitems = { projektInfo, angebotInfo };
		super.setPopupMenuItems(menuitems);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == projektInfo) {
			Stundensatz selectedItem = (Stundensatz) getSelectedItem();
			if (selectedItem != null) {
				try {
					Projekt p = BL.getProjekt(selectedItem.getProjektid());
					JOptionPane.showMessageDialog(this, p.toString());
				} catch (DALException e1) {
					JOptionPane.showMessageDialog(this, e1.getMessage());
				}
			}
		} else if (e.getSource() == angebotInfo) {
			Stundensatz selectedItem = (Stundensatz) getSelectedItem();
			if (selectedItem != null) {
				try {
					Angebot p = BL.getAngebot(selectedItem.getAngebotid());
					JOptionPane.showMessageDialog(this, p.toString());
				} catch (DALException e1) {
					JOptionPane.showMessageDialog(this, e1.getMessage());
				}
			}
		} else if (e.getSource() == save) {
			JFileChooser fc = new JFileChooser();
			fc.setAcceptAllFileFilterUsed(false);
			fc.setFileFilter(new PDFFilter());

			int returnVal = fc.showSaveDialog(this);

			if (returnVal == JFileChooser.APPROVE_OPTION) {

				File file = fc.getSelectedFile();

				PDFFile f = null;
				try {
					f = new PDFFile(file);

					f.createReport("Stundensatz", tModel);

					Desktop.getDesktop().open(file);

				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(this, e1.getMessage());
				} catch (DocumentException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} finally {
					if (f != null) {
						f.close();
					}
				}
			}
		} else if (e.getSource() == refresh) {
			tModel.refresh();
			refreshAnalysis();
		}
	}

}
