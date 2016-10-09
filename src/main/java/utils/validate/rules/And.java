package utils.validate.rules;

import utils.validate.Rule;

/**
 * Created by admin on 2016/9/12.
 */
public class And<T> implements Rule<T> {
	private final Rule<T>[] rules;

	public And(Rule<T>... rules) {
		if (null != rules)
			this.rules = rules;
		else
			throw new NullPointerException("validator must be not null.");
	}

	@Override
	public boolean validate(T target) {
		for (Rule<T> rule : rules) {
			if (!rule.validate(target))
				return false;
		}

		return true;
	}
}
