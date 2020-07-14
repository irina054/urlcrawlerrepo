package com.irina.urlcrawlerapp.urlcrawler;

import java.util.List;

import com.irina.urlcrawlerapp.dto.CrawlerResultStatus;

public interface URLCheckerExecutor {

	List<CrawlerResultStatus> runChecker(List<String> urlsToScan) ;
}
