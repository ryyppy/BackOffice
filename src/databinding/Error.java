package databinding;

/**
 * 
 * @author Armin
 * 
 */
public class Error {
	private String name;
	private String msg;
	private int type;

	/**
	 * GENERIC_FAILURE
	 */
	public final static int GENERIC_FAILURE = 0;
	/**
	 * ISNULL_FAILURE
	 */
	public final static int ISNULL_FAILURE = 1;
	/**
	 * NUMBERFORMAT_FAILURE
	 */
	public final static int NUMBERFORMAT_FAILURE = 2;
	/**
	 * DATEFORMAT_FAILURE
	 */
	public final static int DATEFORMAT_FAILURE = 3;

	public Error(String name, String msg, int type) {
		this.name = name;
		this.msg = msg;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String toString() {
		return getName() + ": " + getMsg();
	}
}
