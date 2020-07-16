package com.irina.urlcrawlerapp.service;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.irina.urlcrawlerapp.dto.UrlResultStatus;
import com.irina.urlcrawlerapp.infra.AppExecutor;
import com.irina.urlcrawlerapp.urlcrawler.RequestCrawlerResults;

public class ParseContentToExtractUrlsTask implements Runnable{

	private String url;
	private int depth;
	private RequestCrawlerResults results;
	private StringBuffer urlContent;
	
	private Set<String> urls;
	private int nextLevel;

	public ParseContentToExtractUrlsTask(String url, int depth, RequestCrawlerResults results, 	StringBuffer urlContent) {
				this.url = url;
				this.depth = depth;
				this.results = results;
				this.urlContent = urlContent;
				this.urls = new HashSet<>();
				this.nextLevel = depth -1;
		
	}

	public Set<String> getUrls() {
		return urls;
	}

	public void setUrls(Set<String> urls) {
		this.urls = urls;
	}

	@Override
	public void run() {
		parseHTMLContent();
		updateResults();
	}

	private void placeNextTask(String url) {
		GetURLHTTPstatusTask task = new GetURLHTTPstatusTask(url, nextLevel, results);
		AppExecutor.getInstance().execute(task);		
	}

	private void updateResults() {
		List<String> uniqueUrls  = new ArrayList<>();
		for (String url:urls) {
			Object o = results.getResults().putIfAbsent(url, new UrlResultStatus(url, HttpURLConnection.HTTP_BAD_REQUEST, nextLevel));
			if (o == null) {
				//create GET Task to perform crawle for this URL
				placeNextTask(url);
				uniqueUrls.add(url);
			}
			//otherwise this URL is already in a list to scan
		}
		results.getResults().get(url).setRefUrls(uniqueUrls);
		results.getResults().get(url).setParsingDone(true);
		results.notifyUponFinish(url);

	}

	private void parseHTMLContent() {
        // Regular expression for a URL 
        String regex 
            = "<a\\s+(?:[^>]*?\\s+)?href=([\"'])(.*?)\\1"; 

        // To store the pattern of the 
        // URL formed by regex 
        Pattern pattern 
            = Pattern.compile(regex); 

        // To extract all the URL that 
        // matches the pattern in raw 
        Matcher matcher 
            = pattern.matcher(urlContent); 

        // It will loop until all the URLs 
        // in the current website get stored 
        // in the queue 
        while (matcher.find()) { 

            // To store the next URL in raw 
            String actual = matcher.group(2); 

            if (actual.contains("http:")) {
            // It will check whether this URL is 
            // visited or not
            	urls.add(actual);            
            }
        } 
		
	}

}
