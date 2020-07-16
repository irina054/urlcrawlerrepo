package com.irina.urlcrawlerapp.urlcrawler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.irina.urlcrawlerapp.dto.UrlResultStatus;

public class URLCheckerExecutorParallel implements URLCheckerExecutor{

	private static final int CORE_CONCURRENT_THREADS = 3;
	private static final int MAX_CONCURRENT_THREADS = 7;
	private ExecutorService executor ;
	private BlockingQueue<Runnable> queue;	

	public URLCheckerExecutorParallel(BlockingQueue<Runnable> queue) {
		super();
		this.queue = queue;
		this.executor = new ThreadPoolExecutor(CORE_CONCURRENT_THREADS,
                MAX_CONCURRENT_THREADS,
                0L,
                TimeUnit.MILLISECONDS,
                queue);
	}

	public ExecutorService getExecutor() {
		return executor;
	}

	public void setExecutor(ExecutorService executor) {
		this.executor = executor;
	}

	@Override
	public List<UrlResultStatus> runChecker(List<String> urlsToScan) {
		List<UrlResultStatus> results = new ArrayList<>();
		
		List<Future<List<UrlResultStatus>>> taskResults = new ArrayList<>(); 
		for (String urlToScan:urlsToScan) {
			taskResults.add( executor.submit(new URLChecker(urlToScan)));
		}
		
		for (Future<List<UrlResultStatus>> taskResult:taskResults) {
			try {
				results.addAll(taskResult.get());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return results;
	}

	//TODO handle shutdown of ExecutorService on shutting down the application
}
