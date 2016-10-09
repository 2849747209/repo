package utils.validate;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * Created by admin on 2016/9/12.
 */
public interface Rule<T> {
	public boolean validate(T target);
}