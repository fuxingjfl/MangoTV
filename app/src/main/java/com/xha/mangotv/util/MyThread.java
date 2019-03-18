package com.xha.mangotv.util;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.util.Log;
/**
 * 
 * 线程�?
 * 
 * @author dell-dell
 *
 */
public class MyThread {

	private static ThreadPool threadPool;

	private MyThread() {
	}

	public static ThreadPool getInstance() {
		if (threadPool == null) {// ��һ ����ͬ��������ִ�д���
			synchronized (MyThread.class) {// ����
				// ���������̲߳���ȫ������
				if (threadPool == null) {
					// cpu�ĸ���
					int availableProcessors = Runtime.getRuntime()
							.availableProcessors();
					int num = availableProcessors * 2 + 1;
					System.out.println("num=" + num);
					threadPool = new ThreadPool(num, num, 0L);
				}
			}
		}
		return threadPool;
	}

	public static class ThreadPool {

		private int corePoolSize;// �����߳���
		private int maximumPoolSize;// ����߳���?
		private long keepAliveTime;// ���ֻ�Ծʱ��
		private ThreadPoolExecutor executor;

		// private TimeUnit unit;//���ֻ�Ծ��ʱ�䵥λ
		private ThreadPool(int corePoolSize, int maximumPoolSize,
				long keepAliveTime) {
			this.corePoolSize = corePoolSize;
			this.maximumPoolSize = maximumPoolSize;
			this.keepAliveTime = keepAliveTime;
		}

		public void execute(Runnable r) {
			if (r == null) {
				return;
			}
			if (executor == null) {
				executor = new ThreadPoolExecutor(corePoolSize,
						maximumPoolSize, keepAliveTime, TimeUnit.SECONDS,
						new LinkedBlockingQueue<Runnable>(),
						Executors.defaultThreadFactory(),
						new RejectedExecutionHandler() {

							@Override
							public void rejectedExecution(Runnable r,
									ThreadPoolExecutor executor) {
								// TODO Auto-generated method stub

							}
						});
			}
			executor.execute(r);
		}
	}

}
