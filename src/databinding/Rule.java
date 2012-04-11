package databinding;


public interface Rule {
	public boolean eval(Object val, ErrorControl e, String name);
}
