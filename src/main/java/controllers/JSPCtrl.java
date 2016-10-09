package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by admin on 2016/9/27.
 */
@Controller
@RequestMapping("/jspCtrl")
public class JSPCtrl {

	@RequestMapping("/hello")
	public ModelAndView helloJsp(HttpServletRequest request) {
		System.out.println(request.getSession().getId());
		ModelAndView mv = new ModelAndView("index.jsp");
		return mv;
	}
}
