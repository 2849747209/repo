package controllers;

import beans.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created by admin on 2016/8/12.
 */
@RestController
@RequestMapping("/interceptorCtrl")
public class InterceptorCtrl {

	@RequestMapping("/interceptorMethod")
	public String interceptorMethod() {
		return "this is InterceptorCtrl interceptorMethod()";
	}
}
