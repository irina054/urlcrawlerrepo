package com.irina.urlcrawlerapp.service;

import org.junit.jupiter.api.Test;

import com.irina.urlcrawlerapp.exception.ValidationError;

class CrawlerServiceTest {

	CrawlerService service  = new CrawlerService();
	
	@Test
	void test() throws ValidationError {
		String url = "www.example.com"; 
		service.crawleUrl(url, 3);
	}

}
