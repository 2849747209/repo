package controllers;

import beans.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created by admin on 2016/8/19.
 */
@RestController
@RequestMapping("/user")
public class UserCtrl {

	@RequestMapping("/login")
	public String login(HttpSession session) {
		System.out.println(session.getId());
		session.setAttribute(User.CURRENT_USER_KEY, new User());
		return "login()";
	}

	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		System.out.println(session.getId());
		session.removeAttribute(User.CURRENT_USER_KEY);
		return "logout()";
	}

	@RequestMapping("/null")
	public String NullPointer() {
		throw new NullPointerException("这是测试用的空指针异常");
	}
}
