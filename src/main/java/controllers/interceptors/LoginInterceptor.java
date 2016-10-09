package controllers.interceptors;

import beans.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by admin on 2016/8/12.
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

	public static final String[] pathPatterns;

	static {
		pathPatterns = new String[] {"/interceptorCtrl/interceptorMethod", "/user/logout"};
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		System.out.println(LoginInterceptor.class.getSimpleName() + ".preHandle()");
		User user = (User) request.getSession().getAttribute(User.CURRENT_USER_KEY);
		if (null != user)
			return true;
		else {
			response.setContentType("text/html;charset=UTF-8");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write("请登录！");
			return false;
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView modelAndView) throws Exception {
		System.out.println(LoginInterceptor.class.getSimpleName() + ".postHandle()");
	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
		System.out.println(LoginInterceptor.class.getSimpleName() + ".afterCompletion()");
	}
}
