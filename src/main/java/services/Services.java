package services;

import org.apache.log4j.spi.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by admin on 2016/8/19.
 */
@Service
public class Services {

	private final static Logger logger = org.slf4j.LoggerFactory.getLogger(Services.class);

	public void serviceSayHello() {
		System.out.println("this is Services");
	}

	/**
	 * @Async不能用于有返回值的函数.例如调用这个函数就会报错.
	 */
	@Async
	public int romdomInt(CountDownLatch latch) {
		ThreadLocalRandom random = ThreadLocalRandom.current();
		Thread currentThread = Thread.currentThread();

		int val = random.nextInt(1, 10);
		System.out.println(currentThread.getId() + ":" + val);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			latch.countDown();
		}

		return val;
	}

	@Scheduled(cron = "0/10 * * * * ?") // 每20秒执行一次
	public void scheduler() {
		logger.info("定时器执行任务 scheduled ... ");
	}
}
