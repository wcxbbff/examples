package org.demos.cores;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * condition
 * 
 * @Description TODO
 * @Date Dec 14, 2018 2:46:33 PM
 * @Author allen
 */
public class ConditionWaitNotifyExample {
	Lock lock = new ReentrantLock();
	Condition condition = lock.newCondition();

	public void before() {
		lock.lock();
		try {
			System.out.println("1before !");
			condition.signalAll();
			System.out.println("2before !");
		} finally {
			lock.unlock();
		}

	}

	public void after() {
		lock.lock();
		try {
			System.out.println("1after !");
			condition.await();
			System.out.println("2after !");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	public static void main(String[] arg) {
		ExecutorService executorService = Executors.newCachedThreadPool();
		ConditionWaitNotifyExample example = new ConditionWaitNotifyExample();
		executorService.execute(() -> example.after());
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		executorService.execute(() -> example.before());
		executorService.shutdown();
		try {
			while(!executorService.awaitTermination(1, TimeUnit.SECONDS)){
				int threadCount = ((ThreadPoolExecutor)executorService).getActiveCount(); 
				System.out.println(threadCount+"个线程还在执行 !");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("线程执行完成 !");
	}
}
