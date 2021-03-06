package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import services.CallableService;
import services.Services;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by admin on 2016/10/12.
 */
@RestController
@RequestMapping(value = "/threadCtrl")
public class ThreadCtrl {

	@Autowired
	private Services services;

	//@Autowired
	//private CallableService callable;

	@Autowired
	private ThreadPoolTaskExecutor springThreadPool;

	@Autowired
	private ThreadPoolExecutor threadPool;

	@RequestMapping(value = "/multiThreads")
	public Object validAsync() {
		int[] array = new int[10];

		Thread currentThread = Thread.currentThread();
		System.out.println(currentThread.getId() + " start");
		CountDownLatch latch = new CountDownLatch(10);
		/*for (int i = 0; i < array.length; i++) {
			array[i] = services.romdomInt(latch);
		}*/
		try {
			for (int i = 0; i < array.length; i++) {
				//RunnableFuture<Integer> future = new FutureTask<Integer>(callableImpl);
				//threadPool.execute(future);
				//array[i] = future.get();

				CallableService callable = new CallableService(latch);
				Future<Integer> future = threadPool.submit(callable);
				array[i] = future.get();
			}
			latch.await();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(currentThread.getId() + " end");

		return array;
	}

	@RequestMapping(value = "/lifecycle")
	public void lifecycle() {
		Thread currentThread = Thread.currentThread();
		System.out.println(currentThread.getId() + " start");
		CountDownLatch latch = new CountDownLatch(10);

		Runnable target = new Runnable() {
			private ThreadLocal<Integer> lifecycle = new ThreadLocal<Integer>();

			public void run() {
				Integer val = lifecycle.get();
				if (null == val) {
					ThreadLocalRandom random = ThreadLocalRandom.current();
					Integer var = random.nextInt();
					System.out.println(Thread.currentThread().getId() + " set val:" + var);
					lifecycle.set(var);
				} else {
					System.out.println(Thread.currentThread().getId() + " find val not null");
				}

				latch.countDown();
			}
		};

		for (int i=0; i<10; i++)
			threadPool.execute(target);

		try {
			latch.await();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(currentThread.getId() + " end");
	}
}
