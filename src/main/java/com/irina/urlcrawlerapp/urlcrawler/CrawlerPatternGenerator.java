package com.irina.urlcrawlerapp.urlcrawler;

import java.util.List;

import com.irina.urlcrawlerapp.exception.ValidationError;

public interface CrawlerPatternGenerator {
	List<String> getURLsToCrawler(String url, int depth) throws ValidationError;
}
