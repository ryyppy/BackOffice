package gui.tabbedViews.reports;

import gui.ReportViewPanel;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.itextpdf.text.DocumentException;

import bl.filter.PDFFilter;
import bl.objects.view.reports.OffeneProjekte;
import bl.pdf.PDFFile;

public class OffeneProjektePanel extends ReportViewPanel {

	private JTextField offeneProjekte;

	public OffeneProjektePanel(JFrame owner) {
		super(OffeneProjekte.class, owner);

	}

	@Override
	public void initAnalysisPanel() {
		offeneProjekte = new JTextField(20);
		String[] labels = { "Anzahl offener Projekte:" };
		JTextField[] textfields = { offeneProjekte };

		super.setAnalysisPanel(labels, textfields);
	}

	@Override
	public void refreshAnalysis() {
		offeneProjekte.setText(String.valueOf(tModel.getRowCount()));
	}

	@Override
	public void initPopupMenuItems() {

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

					f.createReport("Offene Projekte", tModel);

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
