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
import bl.filter.PDFFilter;
import bl.objects.Eingangsrechnung;
import bl.objects.Projekt;
import bl.objects.view.reports.Jahresumsatz;
import bl.pdf.PDFFile;
import dal.DALException;
import dal.DBEntity;

public class JahresumsatzPanel extends ReportViewPanel {
	private JMenuItem projektInfo;

	private JTextField jahresUmsatz;

	public JahresumsatzPanel(JFrame owner) {
		super(Jahresumsatz.class, owner);

	}

	@Override
	public void initAnalysisPanel() {
		jahresUmsatz = new JTextField(20);
		String[] labels = { "Prognostizierter Jahresumsatz:" };
		JTextField[] textfields = { jahresUmsatz };

		super.setAnalysisPanel(labels, textfields);
	}

	@Override
	public void refreshAnalysis() {
		double prognose = 0;
		for (DBEntity entry : tModel.getEntries()) {
			Jahresumsatz eintrag = (Jahresumsatz) entry;
			prognose += eintrag.getAvgAngebote();
		}
		String ausgabe = NumberFormat.getCurrencyInstance().format(prognose);
		jahresUmsatz.setText(ausgabe);
	}

	@Override
	public void initPopupMenuItems() {
		projektInfo = new JMenuItem("Projektinfo");
		JMenuItem[] menuitems = { projektInfo };
		super.setPopupMenuItems(menuitems);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == projektInfo) {
			Jahresumsatz selectedItem = (Jahresumsatz) getSelectedItem();
			if (selectedItem != null) {
				try {
					Projekt p = BL.getProjekt(selectedItem.getProjektid());
					JOptionPane.showMessageDialog(this, p.toString());
				} catch (DALException e1) {
					JOptionPane.showMessageDialog(this, e1.getMessage());
				}
			}
		} else if (e.getSource() == save) {
//			try {
				JFileChooser fc = new JFileChooser();
				fc.setAcceptAllFileFilterUsed(false);
				fc.setFileFilter(new PDFFilter());

				int returnVal = fc.showSaveDialog(this);

				if (returnVal == JFileChooser.APPROVE_OPTION) {

					File file = fc.getSelectedFile();

					PDFFile f = null;
					try {
						f = new PDFFile(file);

						f.createReport("Jahresumsatz", tModel);

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
				
//				File file = new File("report.pdf");
//				file.createNewFile();
//				PDFFile f = new PDFFile(file);
//				f.createReport("Jahresumsatz", tModel);
//
//				Desktop.getDesktop().open(file);
//			} catch (DocumentException e1) {
//				e1.printStackTrace();
//			} catch (IOException e1) {
//				e1.printStackTrace();
//			}
		} else if (e.getSource() == refresh) {
			tModel.refresh();
			refreshAnalysis();
		}
	}

}
