package com.irina.urlcrawlerapp.service;

import java.net.HttpURLConnection;
import java.util.List;

import org.springframework.stereotype.Service;

import com.irina.urlcrawlerapp.dto.UrlResultStatus;
import com.irina.urlcrawlerapp.exception.ValidationError;
import com.irina.urlcrawlerapp.infra.AppExecutor;
import com.irina.urlcrawlerapp.urlcrawler.RequestCrawlerResults;


@Service
public class CrawlerService {

	public static final int LAST_LEVEL_NO_PARSING = 1;

	public List<UrlResultStatus> crawleUrl(String url, int depth) throws ValidationError {
		//check URL format in general, throw exception otherwise
		//URLChecker.validateURLpattern(url +  DEFAULT_URL_SUFFIX);		
		//List<String> urlsToScan = generator.getURLsToCrawler(url, depth);		
		//return executor.runChecker(urlsToScan);
		
		RequestCrawlerResults results = new RequestCrawlerResults();
		
		//prepare root URL to start crawling... each task will update results structure upon finishing
		GetURLHTTPstatusTask getUrlHTTPstatusTask = new GetURLHTTPstatusTask(url, depth, results);
		results.getResults().putIfAbsent(url, new UrlResultStatus(url, HttpURLConnection.HTTP_BAD_REQUEST, depth));
		AppExecutor.getInstance().execute(getUrlHTTPstatusTask);
		
		CollectCrawlerResults collectResults  = new  CollectCrawlerResults(url, depth, results);
		return collectResults.get();
		
	}



}
