package utils.excel.validate.rules;

/**
 * Created by admin on 2016/9/12.
 */
public interface Rule<T> {
	public boolean validate(T target);
}