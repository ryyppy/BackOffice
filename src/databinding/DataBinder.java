package databinding;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

import dal.DBEntity;

public class DataBinder {
	private ErrorControl errorCtrl;

	public DataBinder() {
		errorCtrl = new ErrorControl();
	}

	public String bindFrom_String(JTextField f, Rule r) {
		setComponentBorder(f, Color.GREEN);
		String ret = f.getText();

		// Execute Rule
		if (r != null) {
			if (!r.eval(ret, errorCtrl, f.getName())) {
				setComponentBorder(f, Color.RED);
			}
		}

		return ret;
	}

	public String bindFrom_String(JComboBox f, Rule r) {
		setComponentBorder(f, Color.GREEN);
		String ret = "";
		// Convert
		try {
			ret = String.valueOf(f.getSelectedItem());
		} catch (Exception e) {
			errorCtrl.setError(f.getName(), "Fehlende Auswahl",
					Error.NUMBERFORMAT_FAILURE);
			setComponentBorder(f, Color.RED);
			return ret;
		}

		// Execute Rule
		if (r != null) {
			if (!r.eval(ret, errorCtrl, f.getName())) {
				setComponentBorder(f, Color.RED);
			}
		}

		return ret;
	}

	public int bindFrom_int(JTextField f, Rule r) {
		setComponentBorder(f, Color.GREEN);
		int ret = -1;
		// Convert
		try {
			ret = Integer.parseInt(f.getText());
		} catch (Exception e) {
			errorCtrl.setError(f.getName(), "Ungültige Eingabe",
					Error.NUMBERFORMAT_FAILURE);
			setComponentBorder(f, Color.RED);
			return ret;
		}
		// Execute Rule
		if (r != null) {
			if (!r.eval(ret, errorCtrl, f.getName())) {
				setComponentBorder(f, Color.RED);
			}
		}
		return ret;
	}

	public int bindFrom_int(JComboBox f, Rule r) {
		setComponentBorder(f, Color.GREEN);
		int ret = -1;
		// Convert
		try {
			DBEntity d = (DBEntity) f.getSelectedItem();
			ret = Integer.parseInt(String.valueOf(d.getID()));
		} catch (Exception e) {
			errorCtrl.setError(f.getName(), "Fehlende Auswahl",
					Error.NUMBERFORMAT_FAILURE);
			setComponentBorder(f, Color.RED);
			return ret;
		}
		// Execute Rule
		if (r != null) {
			if (!r.eval(ret, errorCtrl, f.getName())) {
				setComponentBorder(f, Color.RED);
			}
		}
		return ret;
	}

	public double bindFrom_double(JTextField f, Rule r) {
		setComponentBorder(f, Color.GREEN);
		double ret = -1;
		// Convert
		try {
			ret = Double.parseDouble(f.getText());
		} catch (Exception e) {
			errorCtrl.setError(f.getName(), "Ungültige Eingabe",
					Error.NUMBERFORMAT_FAILURE);
			setComponentBorder(f, Color.RED);
			return ret;
		}
		// Execute Rule
		if (r != null) {
			if (!r.eval(ret, errorCtrl, f.getName())) {
				setComponentBorder(f, Color.RED);
			}
		}
		return ret;
	}

	public Date bindFrom_Date(JTextField f, Rule r) {
		setComponentBorder(f, Color.GREEN);
		Date ret = null;
		// Convert
		try {
			ret = new SimpleDateFormat("dd.MM.yyyy").parse(f.getText());

		} catch (Exception e) {
			errorCtrl.setError(f.getName(),
					"Datumsformat ungültig - (TT.MM.JJJJ)",
					Error.DATEFORMAT_FAILURE);
			setComponentBorder(f, Color.RED);
			return ret;
		}
		// Execute Rule
		if (r != null) {
			if (!r.eval(ret, errorCtrl, f.getName())) {
				setComponentBorder(f, Color.RED);
			}
		}
		return ret;
	}

	public boolean hasErrors() {
		if (errorCtrl.isEmpty()) {
			return false;
		}
		return true;
	}

	public String getErrors() {
		String ret = "";
		for (Error e : this.errorCtrl) {
			ret += e.toString() + "\n";
		}
		return ret;
	}

	public ErrorControl getErrorControl() {
		return errorCtrl;
	}

	private void setComponentBorder(JComponent comp, Color c) {
		comp.setBorder(BorderFactory.createLineBorder(c));
	}

	public static void main(String[] args) {
		JTextField f = new JTextField("17.05.2013");
		f.setName("Vorname");

		DataBinder b = new DataBinder();

		b.bindFrom_String(f, new StandardRule());
		b.bindFrom_int(f, new StandardRule());
		b.bindFrom_Date(f, new StandardRule());

		if (b.hasErrors()) {
			System.out.println(b.getErrors());
		}
	}
}
