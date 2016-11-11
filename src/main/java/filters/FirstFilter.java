package filters;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by admin on 2016/11/3.
 */
public class FirstFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println(FirstFilter.class.getName() + " init.");
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		System.out.println(FirstFilter.class.getName() + " doFilter.");
		filterChain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void destroy() {
		System.out.println(FirstFilter.class.getName() + " destroy.");
	}
}
