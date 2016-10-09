package utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import sun.rmi.server.InactiveGroupException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2016/8/19.
 */
@Component
public class SpringBeanUtils implements BeanFactoryAware {

	private static BeanFactory beanFactory;

	@Override
	public void setBeanFactory(BeanFactory factory) throws BeansException {
		beanFactory = factory;
	}

	public static <T> T getBean(Class<T> cls) {
		return beanFactory.getBean(cls);
	}

	public static <T> T getBean(String id, Class<T> cls) {
		return beanFactory.getBean(id, cls);
	}
}
