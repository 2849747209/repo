package utils.excel.validate.mappers;

import utils.excel.validate.rules.Rule;

/**
 * Created by admin on 2016/9/12.
 */
public class RuleMapper<K, V> implements Mapper<K, V> {

	private final Rule<K> rule;

	private final V trueVal;

	private final V falseVal;

	public RuleMapper(Rule<K> rule, V trueVal, V falseVal) {
		if (null != rule)
			this.rule = rule;
		else
			throw new NullPointerException("validator must be not null.");

		this.trueVal = trueVal;
		this.falseVal = falseVal;
	}

	@Override
	public V process(K target) {
		if (rule.validate(target)) {
			return trueVal;
		} else {
			return falseVal;
		}
	}
}
