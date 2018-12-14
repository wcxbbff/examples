package org.demos.cores;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * wait notify
 * 
 * @Description TODO
 * @Date Dec 14, 2018 2:46:33 PM
 * @Author allen
 */
public class WaitNotifyExample {
 public synchronized void before() {
        System.out.println("before");
        notifyAll();
    }

    public synchronized void after() {
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("after");
    }
	 

	public static void main(String[] arg) {
		 ExecutorService executorService = Executors.newCachedThreadPool();
		    WaitNotifyExample example = new WaitNotifyExample();
		    executorService.execute(() -> example.after());
		    try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		    executorService.execute(() -> example.before());
		    executorService.shutdown();
		 
	}
}
