package controllers;

import beans.User;
import exceptions.ParameterException;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by admin on 2016/8/19.
 */
@RestController
@RequestMapping(value = "/validCtrl")
public class ValidationCtrl {

	@RequestMapping(value = "/validUser")
	public String validUser(@Valid User user, BindingResult result) {
		if (result.hasErrors()) {
			throw new ParameterException(result.getAllErrors());
		} else {
			return "ok";
		}
	}

	@RequestMapping(value = "/validString")
	public String validString(@RequestParam(defaultValue = "") String value) {
		if (value.trim().length() == 0)
			return "error";
		else
			return "ok";
	}
}
