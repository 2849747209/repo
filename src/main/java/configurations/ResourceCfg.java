package configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 * Created by admin on 2016/9/27.
 */
//@EnableWebMvc //开启SpringMVC支持,若无此句,重写WebMvcConfigurerAdapter方法无效
@Configuration
public class ResourceCfg extends WebMvcConfigurerAdapter {

	/**
	 * 若不想在这里配就在application.properties里面配置
	 * @return
	 */
	/*@Bean
	public InternalResourceViewResolver getJspViewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/jsp/");
		//viewResolver.setSuffix(".jsp");

		return viewResolver;
	}*/

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		ResourceHandlerRegistration registration = registry.addResourceHandler("/js/**");//指的是暴露在外的访问路径
		registration.addResourceLocations("classpath:/js/");//指的是文件的放置目录
	}
}
