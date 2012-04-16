package extras;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public abstract class MyFileFilter extends FileFilter {

	public final static String XML = "xml";
	public final static String PDF = "pdf";

	private String description;

	public MyFileFilter(String description) {
		this.description = description;
	}

	public String getExtension(File f) {
		String ext = null;
		String s = f.getName();
		int i = s.lastIndexOf('.');

		if (i > 0 && i < s.length() - 1) {
			ext = s.substring(i + 1).toLowerCase();
		}
		return ext;
	}

	@Override
	public String getDescription() {
		return description;
	}

}
