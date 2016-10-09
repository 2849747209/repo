package utils.validate.rules;

import utils.validate.Rule;
import utils.validate.Validator;

/**
 * Created by admin on 2016/9/12.
 */
public class Ne<T> implements Rule<T> {

	private final Comparable<T> value;

	public Ne(Comparable<T> value) {
		if (null != value)
			this.value = value;
		else
			throw new NullPointerException("value must be not null.");
	}

	@Override
	public boolean validate(T target) {
		int result = value.compareTo(target);
		if (result != 0)
			return true;
		else
			return false;
	}
}
