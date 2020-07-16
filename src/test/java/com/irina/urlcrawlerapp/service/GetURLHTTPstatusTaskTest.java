package com.irina.urlcrawlerapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.HttpURLConnection;

import org.junit.jupiter.api.Test;

class GetURLHTTPstatusTaskTest extends ChainResponsibilityTasksTest{

	@Test
	void testRun() {
		String url = "http://www.example.com";
		GetURLHTTPstatusTask task = new GetURLHTTPstatusTask(url, 2, results);

		task.run();
		//check 1st level HTTP request status
		assertEquals(HttpURLConnection.HTTP_OK, results.getResults().get(url).getHttpRequestStatusCode());
		
		//check that only 1 parse task was generated and placed in a queueTasks
		//assertEquals(1, queueTasks.size());
		//assertEquals(queueTasks.poll().getClass(), ParseContentToExtractUrlsTask.class);

	}

}
