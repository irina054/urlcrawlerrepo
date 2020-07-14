package com.irina.urlcrawlerapp.urlcrawler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.irina.urlcrawlerapp.dto.CrawlerResultStatus;

public class URLCheckerExecutorParallel implements URLCheckerExecutor{

	private static final int MAX_NUMBER_OF_PARALLEL_THREADS = 5;
	private ExecutorService executor = Executors.newFixedThreadPool(MAX_NUMBER_OF_PARALLEL_THREADS);	

	public ExecutorService getExecutor() {
		return executor;
	}

	public void setExecutor(ExecutorService executor) {
		this.executor = executor;
	}

	@Override
	public List<CrawlerResultStatus> runChecker(List<String> urlsToScan) {
		List<CrawlerResultStatus> results = new ArrayList<>();
		
		List<Future<List<CrawlerResultStatus>>> taskResults = new ArrayList<>(); 
		for (String urlToScan:urlsToScan) {
			taskResults.add( executor.submit(new URLChecker(urlToScan)));
		}
		
		for (Future<List<CrawlerResultStatus>> taskResult:taskResults) {
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
