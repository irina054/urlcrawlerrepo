package com.irina.urlcrawlerapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.irina.urlcrawlerapp.dto.CrawlerResultStatus;
import com.irina.urlcrawlerapp.exception.ValidationError;
import com.irina.urlcrawlerapp.urlcrawler.CrawlerPatternGenerator;
import com.irina.urlcrawlerapp.urlcrawler.CrawlerPatternGeneratorTwoSubNodes;
import com.irina.urlcrawlerapp.urlcrawler.URLChecker;
import com.irina.urlcrawlerapp.urlcrawler.URLCheckerExecutor;
import com.irina.urlcrawlerapp.urlcrawler.URLCheckerExecutorParallel;


@Service
public class CrawlerService {

	private static final String DEFAULT_URL_SUFFIX = ".com";

	private URLCheckerExecutor executor = new URLCheckerExecutorParallel();
	private CrawlerPatternGenerator generator = new CrawlerPatternGeneratorTwoSubNodes();

	public CrawlerPatternGenerator getGenerator() {
		return generator;
	}

	public void setGenerator(CrawlerPatternGeneratorTwoSubNodes generator) {
		this.generator = generator;
	}

	public URLCheckerExecutor getExecutor() {
		return executor;
	}

	public void setExecutor(URLCheckerExecutor executor) {
		this.executor = executor;
	}

	public List<CrawlerResultStatus> crawleUrl(String url, int depth) throws ValidationError {
		//check URL format in general, throw exception otherwise
		URLChecker.validateURLpattern(url +  DEFAULT_URL_SUFFIX);		
		List<String> urlsToScan = generator.getURLsToCrawler(url, depth);		
		return executor.runChecker(urlsToScan);
	}



}
