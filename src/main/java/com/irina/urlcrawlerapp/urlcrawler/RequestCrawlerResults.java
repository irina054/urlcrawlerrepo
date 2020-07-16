package com.irina.urlcrawlerapp.urlcrawler;

import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;

import com.irina.urlcrawlerapp.dto.UrlResultStatus;

public class RequestCrawlerResults {
	private ConcurrentMap<String, UrlResultStatus>  results;
	private BlockingQueue<String> finishNotifications;

	public RequestCrawlerResults() {
		super();
		results = new ConcurrentHashMap<String, UrlResultStatus>();
		setFinishNotifications(new LinkedBlockingQueue<String>());
	}

	public ConcurrentMap<String, UrlResultStatus> getResults() {
		return results;
	}
	
	public void setResults(ConcurrentMap<String, UrlResultStatus> results) {
		this.results = results;
	}

	public Collection<UrlResultStatus> getAllUrlResultStatuses() {
		return results.values();
	}

	public BlockingQueue<String> getFinishNotifications() {
		return finishNotifications;
	}

	public void setFinishNotifications(BlockingQueue<String> finishNotifications) {
		this.finishNotifications = finishNotifications;
	}

	public void notifyUponFinish(String url) {
		finishNotifications.add(url);
		
	}

	
}
