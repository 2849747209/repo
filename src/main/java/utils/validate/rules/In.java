package utils.validate.rules;

import utils.validate.Rule;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by admin on 2016/9/12.
 */
public class In<T> implements Rule<T> {

	private final Set<T> values;

	public In(T... values) {
		if (null != values) {
			this.values = new HashSet<T>(values.length);
			for (T val : values) {
				this.values.add(val);
			}
		}
		else
			throw new NullPointerException("value must be not null.");
	}

	@Override
	public boolean validate(T target) {
		return values.contains(target);
	}
}
