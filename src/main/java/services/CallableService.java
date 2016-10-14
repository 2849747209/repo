package services;

import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by admin on 2016/10/12.
 */
//@Service
public class CallableService implements Callable<Integer> {

	private CountDownLatch latch;

	public CallableService(CountDownLatch latch) {
		this.latch = latch;
	}

	public Integer call() {
		ThreadLocalRandom random = ThreadLocalRandom.current();
		int returnVal = random.nextInt(1, 10);
		System.out.println(Thread.currentThread().getId() + ":" + returnVal);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			latch.countDown();
		}

		return returnVal;
	}
}
