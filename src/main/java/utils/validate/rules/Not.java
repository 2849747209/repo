package utils.validate.rules;

import utils.validate.Rule;
import utils.validate.Validator;

/**
 * Created by admin on 2016/9/12.
 */
public class Not<T> implements Rule<T> {
	private final Rule<T> rule;

	public Not(Rule<T> rule) {
		if (null != rule)
			this.rule = rule;
		else
			throw new NullPointerException("validator must be not null.");
	}

	@Override
	public boolean validate(T target) {
		return !rule.validate(target);
	}
}
