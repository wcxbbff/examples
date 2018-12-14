package org.demos.cores;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *  
 * @Description TODO  
 * @Date        Dec 14, 2018 4:22:33 PM  
 * @Author      allen
 */
public class TwoThread {
	

	public static void main(String[] arg) {
		TwoThread twoThread = new TwoThread();
		Thread thread1=new Thread(twoThread.new Ji());
		Thread thread2=new Thread(twoThread.new Ou());
		thread1.start();
		thread2.start();
	}
	private volatile  AtomicInteger num = new AtomicInteger(0);
	class Ji implements Runnable {

		@Override
		public void run() {
			while(num.get()<=1000){
				
				if ((num.get() & 1) == 1) {
					System.out.println("JI --"+num.get());
					num.incrementAndGet();
				}
			}
		}

	}

	class Ou implements Runnable {
		@Override
		public void run() {
			while(num.get()<=1000){
				
				if ((num.get() & 1) == 0) {
					System.out.println("Ou --" + num.get());
					num.incrementAndGet();
				}
			}
		}
	}
}
