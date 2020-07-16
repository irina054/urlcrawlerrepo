package com.irina.urlcrawlerapp.urlcrawler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import com.irina.urlcrawlerapp.dto.UrlResultStatus;
import com.irina.urlcrawlerapp.exception.InputValidationError;

public class URLChecker implements Callable<List<UrlResultStatus>> {
	
	private List<String> urls = new ArrayList<String>();

	public URLChecker(List<String> urls) {
		super();
		this.urls = urls;
	}

	public URLChecker(String url) {
		this.urls.add(url);
	}

	@Override
	public List<UrlResultStatus> call() throws Exception {
		List<UrlResultStatus> result = new ArrayList<>();
		for (String urlInput:urls) {
				//malformed URL string will cause to runtime exception
				//TODO Currently URL is tested at endPOINT of rest API ... consider refactor if required .. no need to duplicate ...
		    	 URL url = validateURLpattern(urlInput);

		    	 URLcheckerByProtocol urlChecker = URLcheckerByProtocolFactory.getUrlChecker(url);
			     result.add(new UrlResultStatus(urlInput, urlChecker.check(), 17));			     		    
		}
		return result;
	}

	public static URL  validateURLpattern(String url) throws InputValidationError {
		URL u = null;
		try {
			u = new URL(url);
		}catch (MalformedURLException e) {
			throw new InputValidationError(String.format("Wrong URL format: %s. Should be like : <scheme>://<authority><path>?<query>#<fragment>. Detailed Error is : %s", 
										url, e.getLocalizedMessage()));
		} 
		return u;
		
	}	

}
