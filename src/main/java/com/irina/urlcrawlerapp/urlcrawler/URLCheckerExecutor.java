package com.irina.urlcrawlerapp.urlcrawler;

import java.util.List;
import java.util.concurrent.ExecutorService;

import com.irina.urlcrawlerapp.dto.UrlResultStatus;

public interface URLCheckerExecutor {

	ExecutorService getExecutor() ;
	void setExecutor(ExecutorService executor);

	List<UrlResultStatus> runChecker(List<String> urlsToScan) ;
}
