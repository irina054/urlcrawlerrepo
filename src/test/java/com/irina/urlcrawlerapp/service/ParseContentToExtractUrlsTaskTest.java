package com.irina.urlcrawlerapp.service;

import static org.junit.jupiter.api.Assertions.*;

import java.net.HttpURLConnection;

import org.junit.jupiter.api.Test;

class ParseContentToExtractUrlsTaskTest extends ChainResponsibilityTasksTest{

	@Test
	void testRun() {
		/*
		 * TODO: obvious that google can't be used as a test data because it changing over the time...
		 * 		refactor to work with local /unchanged resources !!!   
		 */
		String url = "http://www.google.com";
		GetURLHTTPstatusTask getTask = new GetURLHTTPstatusTask(url, 2, results);
		getTask.run();
		//check 1st level HTTP request status
		assertEquals(HttpURLConnection.HTTP_OK, results.getResults().get(url).getHttpRequestStatusCode());
		

		ParseContentToExtractUrlsTask parseTask = new ParseContentToExtractUrlsTask(url, 2, results, getTask.getUrlContent());
		parseTask.run();
		assertEquals(10, parseTask.getUrls().size());

		//TODO: check that only 1 parse task was generated and placed in a queueTasks
	}

}
