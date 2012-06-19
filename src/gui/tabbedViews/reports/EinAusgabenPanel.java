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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.itextpdf.text.DocumentException;

import bl.PDFFile;
import bl.filter.PDFFilter;
import bl.objects.view.reports.Ausgaben;
import bl.objects.view.reports.Einnahmen;
import dal.DBEntity;

public class EinAusgabenPanel extends ReportViewPanel {

	private JTextField einnahmen, ausgaben, gv;

	public EinAusgabenPanel(JFrame owner) {
		super(Einnahmen.class, Ausgaben.class, owner);
		search.doClick();
	}

	@Override
	public void initAnalysisPanel() {
		einnahmen = new JTextField(20);
		ausgaben = new JTextField(20);
		gv = new JTextField(20);
		String[] labels = { "Einnahmen:", "Ausgaben:", "Gewinn/Verlust:" };
		JTextField[] textfields = { einnahmen, ausgaben, gv };

		super.setAnalysisPanel(labels, textfields);
	}

	@Override
	public void refreshAnalysis() {
		double einnahmen = 0;
		for (DBEntity entry : tModel.getEntries()) {
			Einnahmen eintrag = (Einnahmen) entry;
			einnahmen += eintrag.getBetrag();
		}
		String ausgabe = NumberFormat.getCurrencyInstance().format(einnahmen);
		this.einnahmen.setText(ausgabe);

		double ausgaben = 0;
		for (DBEntity entry : tModel2.getEntries()) {
			Ausgaben eintrag = (Ausgaben) entry;
			ausgaben += eintrag.getBetrag();
		}
		ausgabe = NumberFormat.getCurrencyInstance().format(ausgaben);
		this.ausgaben.setText(ausgabe);

		ausgabe = NumberFormat.getCurrencyInstance().format(
				einnahmen - ausgaben);
		this.gv.setText(ausgabe);
	}

	@Override
	public void initPopupMenuItems() {

	}

	@Override
	public JPanel createSearchPanel() {
		return createFilterDatePanel();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == save) {
			JFileChooser fc = new JFileChooser();
			fc.setAcceptAllFileFilterUsed(false);
			fc.setFileFilter(new PDFFilter());

			int returnVal = fc.showSaveDialog(this);

			if (returnVal == JFileChooser.APPROVE_OPTION) {

				File file = fc.getSelectedFile();

				PDFFile f = null;
				try {
					f = new PDFFile(file);

					f.createReport("Ein Ausgaben", tModel);

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
			tModel2.refresh();
			refreshAnalysis();
		}
	}

}
