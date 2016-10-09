package utils.validate;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by admin on 2016/9/12.
 */
public interface Validator<T> {
	public boolean validate(T target);
}