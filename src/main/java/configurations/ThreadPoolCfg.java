package configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.context.WebApplicationContext;

import java.util.concurrent.*;

/**
 * Created by admin on 2016/10/12.
 */
@Configuration
@EnableAsync //启用Spring异步方法执行功能,如果没有此注解@Async注解将失效
public class ThreadPoolCfg {
	/*
	 @Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE)这个是说在每次注入的时候回自动创建一个新的bean实例
	 @Scope(value=ConfigurableBeanFactory.SCOPE_SINGLETON)单例模式，在整个应用中只能创建一个实例
	 @Scope(value=WebApplicationContext.SCOPE_GLOBAL_SESSION)全局session中的一般不常用
	 @Scope(value=WebApplicationContext.SCOPE_APPLICATION)在一个web应用中只创建一个实例
	 @Scope(value=WebApplicationContext.SCOPE_REQUEST)在一个请求中创建一个实例
	 @Scope(value=WebApplicationContext.SCOPE_SESSION)每次创建一个会话中创建一个实例

	 @Scope(proxyMode=ScopedProxyMode.INTERFACES)创建一个JDK代理模式
	 @Scope(proxyMode=ScopedProxyMode.TARGET_CLASS)基于类的代理模式
	 @Scope(proxyMode=ScopedProxyMode.NO)(默认)不进行代理
	*/
	@Bean(name = "springThreadPool")
	@Scope(value = WebApplicationContext.SCOPE_APPLICATION)
	public ThreadPoolTaskExecutor getThreadPoolTaskExecutor() {
		ThreadPoolTaskExecutor poolTaskExecutor = new ThreadPoolTaskExecutor();
		//线程池所使用的缓冲队列
		poolTaskExecutor.setQueueCapacity(200);
		//线程池维护线程的最少数量
		poolTaskExecutor.setCorePoolSize(5);
		//线程池维护线程的最大数量
		poolTaskExecutor.setMaxPoolSize(1000);
		//线程池维护线程所允许的空闲时间
		poolTaskExecutor.setKeepAliveSeconds(30000);
		poolTaskExecutor.initialize();

		return poolTaskExecutor;
	}

	@Bean(name = "threadPool")
	@Scope(value = WebApplicationContext.SCOPE_APPLICATION)
	public ThreadPoolExecutor getThreadPoolExecutor() {
		int coreSize = 10;
		int maxSize = 256;
		return new ThreadPoolExecutor(coreSize, maxSize, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), new ThreadPoolExecutor.CallerRunsPolicy());
	}
}
