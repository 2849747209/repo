package utils.excel.validate.rules;

/**
 * Created by admin on 2016/9/12.
 */
public class Or<T> implements Rule<T> {
	private final Rule<T>[] rules;

	public Or(Rule<T>... rules) {
		if (null != rules)
			this.rules = rules;
		else
			throw new NullPointerException("validator must be not null.");
	}

	@Override
	public boolean validate(T target) {
		for (Rule<T> rule : rules) {
			if (rule.validate(target))
				return true;
		}

		return false;
	}
}
