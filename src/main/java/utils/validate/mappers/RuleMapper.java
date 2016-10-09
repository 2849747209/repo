package utils.validate.mappers;

import utils.validate.Mapper;
import utils.validate.Rule;

/**
 * Created by admin on 2016/9/12.
 */
public class RuleMapper implements Mapper {

	private final Rule rule;

	private final Object trueVal;

	private final Object falseVal;

	public RuleMapper(Rule rule, Object trueVal, Object falseVal) {
		if (null != rule)
			this.rule = rule;
		else
			throw new NullPointerException("validator must be not null.");

		this.trueVal = trueVal;
		this.falseVal = falseVal;
	}

	@Override
	public Object process(Object target) {
		if (rule.validate(target)) {
			return trueVal;
		} else {
			return falseVal;
		}
	}
}
