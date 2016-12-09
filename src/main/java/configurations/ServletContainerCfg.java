package configurations;

import filters.CorsFilter;
import filters.FirstFilter;
import filters.SecondFilter;
import listeners.FirstListener;
import listeners.SecondListener;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletListenerRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;
import servlets.FirstServlet;
import servlets.SecondServlet;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

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
	public FilterRegistrationBean setCharacterEncodingFilter() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(new CharacterEncodingFilter());
		filterRegistrationBean.addUrlPatterns("/*");
		filterRegistrationBean.addInitParameter("encoding", "utf-8");
		filterRegistrationBean.addInitParameter("forceEncoding", "true");
		filterRegistrationBean.setOrder(1);

		return filterRegistrationBean;
	}

	@Bean
	public FilterRegistrationBean setCorsFilter() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(new CorsFilter());
		filterRegistrationBean.setName("corsFilter");
		filterRegistrationBean.addUrlPatterns("/*");

		List<String> allowed_origins = new LinkedList<String>();
		allowed_origins.add("http://localhost");
		allowed_origins.add("http://localhost:80");
		allowed_origins.add("http://localhost:8080");
		allowed_origins.add("https://localhost:8443");
		allowed_origins.add("http://127.0.0.1:8080");
		allowed_origins.add("https://127.0.0.1:8443");
		filterRegistrationBean.addInitParameter("cors.allowed.origins", join(allowed_origins, ","));

		List<String> allowed_methods = new LinkedList<String>();
		allowed_methods.add("GET");
		allowed_methods.add("POST");
		allowed_methods.add("PUT");
		allowed_methods.add("DELETE");
		allowed_methods.add("OPTIONS");
		allowed_methods.add("PATCH");
		allowed_methods.add("HEAD");
		filterRegistrationBean.addInitParameter("cors.allowed.methods", join(allowed_methods, ","));

		List<String> allowed_headers = new LinkedList<String>();
		allowed_headers.add("Accept");
		allowed_headers.add("Content-Type");
		allowed_headers.add("Last-Modified");
		allowed_headers.add("Origin");
		allowed_headers.add("X-Requested-With");
		allowed_headers.add("Access-Control-Request-Method");
		allowed_headers.add("Access-Control-Request-Headers");
		allowed_headers.add("x-auth-token");
		filterRegistrationBean.addInitParameter("cors.allowed.headers", join(allowed_headers, ","));

		List<String> exposed_headers = new LinkedList<String>();
		exposed_headers.add("Access-Control-Allow-Origin");
		exposed_headers.add("Access-Control-Allow-Credentials");
		//exposed_headers.add("myToken");
		filterRegistrationBean.addInitParameter("cors.exposed.headers", join(exposed_headers, ","));

		filterRegistrationBean.addInitParameter("cors.support.credentials", "true");

		filterRegistrationBean.addInitParameter("cors.preflight.maxage", "30");

		filterRegistrationBean.setOrder(2);

		return filterRegistrationBean;
	}

	@Bean
	public FilterRegistrationBean setFirstFilter() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(new FirstFilter());
		filterRegistrationBean.setOrder(3);
		return filterRegistrationBean;
	}

	@Bean
	public FilterRegistrationBean setSecondFilter() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(new SecondFilter());
		filterRegistrationBean.setOrder(4);
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

	private String join(final Collection<String> elements, final String joinSeparator) {
		if (elements == null) {
			return null;
		}

		String separator = joinSeparator != null ? joinSeparator : ",";

		StringBuilder buffer = new StringBuilder();
		for (String element : elements) {
			if (element != null && !element.isEmpty()) {
				buffer.append(element).append(separator);
			}
		}
		if (buffer.length() > 0) {
			buffer.delete(buffer.length() - separator.length(), buffer.length());
		}

		return buffer.toString();
	}
}
