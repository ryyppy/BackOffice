package bl;

import java.io.File;

import extras.MyFileFilter;

/* ImageFilter.java is used by FileChooserDemo2.java. */
public class ImageFilter extends MyFileFilter {

	public ImageFilter() {
		super("Nur Bilder");
	}

	// Accept all directories and all image files.
	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}

		String extension = getExtension(f);
		if (extension != null) {
			if (extension.equals(MyFileFilter.tiff)
					|| extension.equals(MyFileFilter.tif)
					|| extension.equals(MyFileFilter.gif)
					|| extension.equals(MyFileFilter.jpeg)
					|| extension.equals(MyFileFilter.jpg)
					|| extension.equals(MyFileFilter.png)) {
				return true;
			} else {
				return false;
			}
		}

		return false;
	}

}