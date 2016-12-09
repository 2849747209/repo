package controllers;

import beans.User;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utils.NumberUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.concurrent.ThreadLocalRandom;

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

	@RequestMapping("/params")
	public String getParams(HttpServletRequest request, HttpServletResponse response) {
		StringBuilder builder = new StringBuilder();
		HttpSession session = request.getSession();
		if (null != session) {
			System.out.println("sessionId:" + session.getId());
			builder.append("sessionId:").append(session.getId()).append(",");
		}

		String token = request.getHeader("x-auth-token");
		if (null != token) {
			System.out.println("x-auth-token:" + token);
			builder.append("x-auth-token:").append(token);
		}

		ThreadLocalRandom random = ThreadLocalRandom.current();
		String number = String.valueOf(random.nextLong());
		response.setHeader("myToken", number);

		return builder.toString();
	}
}
