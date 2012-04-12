package databinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StandardRule implements Rule {

	@Override
	public boolean eval(Object val, ErrorControl e, String name) {
		if (val.getClass() == String.class) {
			if (val == null || String.valueOf(val).isEmpty()) {
				e.setError(name, "Fehlende Eingabe", Error.ISNULL_FAILURE);
				return false;
			}
		} else if (val.getClass() == Integer.class) {
			int a = (Integer) val;
			if (a < 0) {
				e.setError(name, "Wert darf nicht negativ sein",
						Error.NUMBERFORMAT_FAILURE);
				return false;
			}
		} else if (val.getClass() == Double.class) {
			double a = (Double) val;
			if (a < 0) {
				e.setError(name, "Wert darf nicht negativ sein",
						Error.NUMBERFORMAT_FAILURE);
				return false;
			}
		} else if (val.getClass() == Date.class) {

			try {
				Date d = (Date) val;
				if (d.before(new SimpleDateFormat("dd.MM.yyyy")
						.parse("01.01.1900"))) {
					e.setError(name,
							"Datum befindet sich weit in der Vergangenheit",
							Error.DATEFORMAT_FAILURE);
					return false;
				}
			} catch (ParseException e1) {
				return true;
			}
		}
		return true;
	}

}
