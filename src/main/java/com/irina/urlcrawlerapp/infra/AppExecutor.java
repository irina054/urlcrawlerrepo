package com.irina.urlcrawlerapp.infra;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class AppExecutor {
	private static final int CORE_CONCURRENT_THREADS = 3;
	private static final int MAX_CONCURRENT_THREADS = 7;

	
	private static final AppExecutor instance = new AppExecutor();
	
	private ExecutorService executor; 
	private BlockingQueue<Runnable> queue;	
	
	private AppExecutor() {
		queue = new LinkedBlockingQueue<Runnable>();
		setExecutor(new ThreadPoolExecutor(CORE_CONCURRENT_THREADS,
                MAX_CONCURRENT_THREADS,
                0L,
                TimeUnit.MILLISECONDS,
                queue));
	}

	public ExecutorService getExecutor() {
		return executor;
	}

	public void setExecutor(ExecutorService executor) {
		this.executor = executor;
	}

	public static AppExecutor getInstance() {
		return instance;
	}

	public void execute(Runnable command) {
		getExecutor().execute(command);
		
	}
}
