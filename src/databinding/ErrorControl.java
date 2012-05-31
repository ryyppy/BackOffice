package databinding;

import java.util.ArrayList;

public class ErrorControl extends ArrayList<Error> {

	public void setError(String name, String msg, int type) {
		this.add(new Error(name, msg, type));
	}

}
