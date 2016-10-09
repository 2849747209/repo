package controllers.advice;

import exceptions.ParameterException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

/**
 * Created by admin on 2016/8/19.
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

	@ExceptionHandler(value = NullPointerException.class)
	@ResponseStatus(HttpStatus.OK)
	public String nullPointerException(NullPointerException exception) {
		System.out.println(exception);
		return exception.getMessage();
	}

	@ExceptionHandler(value = ParameterException.class)
	@ResponseStatus(HttpStatus.OK)
	public String parameterException(ParameterException exception) {
		List<ObjectError> errors = exception.getErrors();
		for(ObjectError error : errors) {
			if (FieldError.class.isInstance(error)) {
				System.out.println(((FieldError)error).getField() + ":" + error.getDefaultMessage());
			} else {
				System.out.println(error.getCode() + ":" + error.getDefaultMessage());
			}
		}
		return "error";
	}
}
