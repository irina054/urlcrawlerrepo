package com.irina.urlcrawlerapp.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

import com.irina.urlcrawlerapp.dto.UrlResultStatus;
import com.irina.urlcrawlerapp.infra.AppExecutor;
import com.irina.urlcrawlerapp.urlcrawler.RequestCrawlerResults;

/*
 * check retrieved URL 
 * update results structure
 * if node depth is not last then read content and initiate Parse resource task to find next level URLs
 */
public class GetURLHTTPstatusTask implements Runnable{
	private String url;
	private int depth; 
	private RequestCrawlerResults results;
	private int responseCode;
	private StringBuffer urlContent;
	boolean isParsingRequired = false;
	
	public GetURLHTTPstatusTask(String url, int depth, RequestCrawlerResults results) {
		this.setUrl(url);
		this.depth = depth;
		this.results = results;
		this.urlContent = new StringBuffer();
		if (depth != CrawlerService.LAST_LEVEL_NO_PARSING) {
			isParsingRequired = true;
		}
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public RequestCrawlerResults getResults() {
		return results;
	}

	public void setResults(RequestCrawlerResults results) {
		this.results = results;
	}

	public StringBuffer getUrlContent() {
		return urlContent;
	}

	public void setUrlContent(StringBuffer urlContent) {
		this.urlContent = urlContent;
	}


	@Override
	public void run() {		
		readUrl();
		updateResults();
	}

	private void updateResults() {
		if (results.getResults().get(url) != null) {
			results.getResults().get(url).setHttpRequestStatusCode(responseCode);
		}else {
			results.getResults().putIfAbsent(url, new UrlResultStatus(url, responseCode, depth));
		}
		results.getResults().get(url).setAvailabilityTestDone(true);
		if (responseCode != HttpURLConnection.HTTP_OK) {
			isParsingRequired= false;
			//results.getResults().get(url).setParsingDone(true);			
		}
		results.getResults().get(url).setParsingRequired(isParsingRequired);
		placeNextTask();
		results.notifyUponFinish(url);
	}

	private void placeNextTask() {
		if (isParsingRequired) {
			ParseContentToExtractUrlsTask parseTask = new ParseContentToExtractUrlsTask( url,  depth,  results,  urlContent);
			AppExecutor.getInstance().execute(parseTask);
		}
		
	}

	private void readUrl() {
		URLConnection connection = null;
		BufferedReader br = null; 
        try { 
            
            URL urlLink = new URL(url);          
  
            connection = urlLink.openConnection();   
           // connection.setConnectTimeout(3000);
            if (connection instanceof HttpsURLConnection) {
            	HttpsURLConnection httpsConnection = (HttpsURLConnection)connection;
                responseCode = httpsConnection.getResponseCode();
            }else {
            	HttpURLConnection httpConnection = (HttpURLConnection)connection;                
                responseCode = httpConnection.getResponseCode();
            	
            }
            
            // Read the HTML from website 
            if ((responseCode == HttpURLConnection.HTTP_OK) && (isParsingRequired)) {
	            br = new BufferedReader(new InputStreamReader(urlLink.openStream())); 
	  
	            // To store the input 
	            // from the website 
	            String input = ""; 
	  
	            // Read the HTML line by line 
	            // and append it to raw 
	            while ((input 
	                    = br.readLine()) 
	                   != null) { 
	            	urlContent.append(input);
	                //raw += input; 
	            } 
	  
	            // Close BufferedReader 
	            br.close(); 
	      
            }
        }catch (SocketTimeoutException e1) {
        	responseCode = HttpURLConnection.HTTP_CLIENT_TIMEOUT;        	
        }catch (Exception ex) { 
        	responseCode = HttpURLConnection.HTTP_BAD_REQUEST;
        }finally {
			//TODO close ....
        	if (br != null)
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	if (connection != null) {
        		if (connection instanceof HttpsURLConnection) {        	
        			((HttpsURLConnection) connection).disconnect();
        		}else {
        			((HttpURLConnection) connection).disconnect();
        		}  	
            }
		} 
   
	}
}
