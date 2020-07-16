package com.irina.urlcrawlerapp.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

import com.irina.urlcrawlerapp.dto.UrlResultStatus;
import com.irina.urlcrawlerapp.urlcrawler.RequestCrawlerResults;

public class CollectCrawlerResults {
 
	private String url;
	private int depth; 
	private RequestCrawlerResults results;
	
	public CollectCrawlerResults(String url, int depth, RequestCrawlerResults results) {
		super();
		this.setUrl(url);
		this.depth = depth;
		this.results = results;
	}

	public List<UrlResultStatus> get() {
		boolean isFoundAllResults = false;
		Queue<String> expectedResults = new LinkedList<>();
		expectedResults.add(url);
	//	int currentDepth = 1;
		while (!isFoundAllResults) {
			try {
				String notification = results.getFinishNotifications().take();
				
				//look for all results, if missing then stop and continue next time when some results will arrive
				while (!expectedResults.isEmpty()) {
					String currentUrl = expectedResults.peek();
					
					UrlResultStatus urlResultStatus = results.getResults().get(currentUrl);
					
					//start handling next level
//					if (currentDepth < urlResultStatus.getDepthLevel()) {
//						++currentDepth;
//					}

					if ((urlResultStatus != null) && (urlResultStatus.isAvailabilityTestDone())) {
						if (!urlResultStatus.isParsingRequired()) {
							expectedResults.poll();							
						}else  {
							if (urlResultStatus.isParsingDone()) {				
								expectedResults.addAll(urlResultStatus.getRefUrls());
								expectedResults.poll();
							}else {
								break; //the parse results for this elements still not there, wait for next notification to check it.
							}
						}						
					}else {
						break; //the get availability results for this elements still not there, wait for next notification to check it
					}
				}
				if (expectedResults.isEmpty()) {
					isFoundAllResults = true;
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return results.getAllUrlResultStatuses().stream().collect(Collectors.toList());
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
