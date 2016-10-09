package exceptions;

import org.springframework.validation.ObjectError;

import java.util.List;

/**
 * Created by admin on 2016/8/19.
 */
public class ParameterException extends RuntimeException {

	private List<ObjectError> errors;

	public ParameterException(List<ObjectError> errors) {
		this.errors = errors;
	}

	public List<ObjectError> getErrors() {
		return errors;
	}
}
