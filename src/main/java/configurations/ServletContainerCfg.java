package configurations;

import filters.FirstFilter;
import filters.SecondFilter;
import listeners.FirstListener;
import listeners.SecondListener;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletListenerRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import servlets.FirstServlet;
import servlets.SecondServlet;

/**
 * Created by admin on 2016/11/3.
 */
@Configuration
public class ServletContainerCfg {

	@Bean
	public ServletListenerRegistrationBean<FirstListener> setFirstListener() {
		return new ServletListenerRegistrationBean<FirstListener>(new FirstListener());
	}

	@Bean
	public ServletListenerRegistrationBean<SecondListener> setSecondListener() {
		return new ServletListenerRegistrationBean<SecondListener>(new SecondListener());
	}

	@Bean
	public FilterRegistrationBean setFirstFilter() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(new FirstFilter());
		filterRegistrationBean.setOrder(1);
		return filterRegistrationBean;
	}

	@Bean
	public FilterRegistrationBean setSecondFilter() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(new SecondFilter());
		filterRegistrationBean.setOrder(2);
		return filterRegistrationBean;
	}

	@Bean
	public ServletRegistrationBean setFirstServlet() {
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
		servletRegistrationBean.setServlet(new FirstServlet());
		servletRegistrationBean.addUrlMappings("/first/*");
		servletRegistrationBean.setOrder(1);
		return servletRegistrationBean;
	}

	@Bean
	public ServletRegistrationBean setSecondServlet() {
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
		servletRegistrationBean.setServlet(new SecondServlet());
		servletRegistrationBean.addUrlMappings("/second/*");
		servletRegistrationBean.setOrder(1);
		return servletRegistrationBean;
	}
}
