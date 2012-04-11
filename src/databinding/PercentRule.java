package databinding;

public class PercentRule extends StandardRule {

	@Override
	public boolean eval(Object val, ErrorControl e, String name) {
		if (!super.eval(val, e, name)) {
			return false;
		}
		if (val.getClass() == Double.class) {
			double a = (Double) val;
			if (a > 100) {
				e.setError(name, "Wert darf nicht über 100% sein",
						Error.NUMBERFORMAT_FAILURE);
				return false;
			}
		}
		return true;
	}

}
