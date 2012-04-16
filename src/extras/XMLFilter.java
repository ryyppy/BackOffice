package extras;

import java.io.File;

/* ImageFilter.java is used by FileChooserDemo2.java. */
public class XMLFilter extends MyFileFilter {

	public XMLFilter() {
		super("*.xml");
	}

	// Accept all directories and all xml files.
	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}

		String extension = getExtension(f);
		if (extension != null) {
			if (extension.equals(MyFileFilter.XML)) {
				return true;
			} else {
				return false;
			}
		}

		return false;
	}

}