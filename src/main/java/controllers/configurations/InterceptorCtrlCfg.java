package controllers.configurations;

import controllers.interceptors.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import utils.SpringBeanUtils;

/**
 * Created by admin on 2016/8/12.
 */
@Configuration
public class InterceptorCtrlCfg extends WebMvcConfigurerAdapter {

	/**
	 * 通过重写WebMvcConfigurerAdapter的addInterceptors方法来自定义拦截器
	 * @param registry
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(SpringBeanUtils.getBean(LoginInterceptor.class)).addPathPatterns(LoginInterceptor.pathPatterns);
	}
}
