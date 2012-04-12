package databinding;

import java.util.Date;

public class BirthdayRule extends StandardRule {

	@Override
	public boolean eval(Object val, ErrorControl e, String name) {
		if (!super.eval(val, e, name)) {
			return false;
		}
		if (val.getClass() == Date.class) {
			Date d = (Date) val;
			if (d.after(new Date())) {
				e.setError(name, "Datum befindet sich in der Zukunft",
						Error.DATEFORMAT_FAILURE);
				return false;
			}
		}
		return true;
	}

}
