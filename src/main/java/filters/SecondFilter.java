package filters;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by admin on 2016/11/3.
 */
public class SecondFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println(SecondFilter.class.getName() + " init.");
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		System.out.println(SecondFilter.class.getName() + " doFilter.");
		filterChain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void destroy() {
		System.out.println(SecondFilter.class.getName() + " destroy.");
	}
}
