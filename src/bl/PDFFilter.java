package bl;

import java.io.File;

import extras.MyFileFilter;


/* ImageFilter.java is used by FileChooserDemo2.java. */
public class PDFFilter extends MyFileFilter {

	public PDFFilter() {
		super("*.pdf");
	}

	// Accept all directories and all pdf files
	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}

		String extension = getExtension(f);
		if (extension != null) {
			if (extension.equals(MyFileFilter.PDF)) {
				return true;
			} else {
				return false;
			}
		}

		return false;
	}

}