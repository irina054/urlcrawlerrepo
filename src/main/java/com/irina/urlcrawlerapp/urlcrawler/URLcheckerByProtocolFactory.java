package com.irina.urlcrawlerapp.urlcrawler;

import java.net.URL;

import com.irina.urlcrawlerapp.exception.InputValidationError;

public class URLcheckerByProtocolFactory {

	public static URLcheckerByProtocol getUrlChecker(URL url) throws InputValidationError {
		if (url.getProtocol().equals("http")) return new HTTPUrlChecker(url);
		
		throw new InputValidationError(String.format("Unsupported URL protocol: %s ", url.getProtocol()));
		// implement for https , etc...
	}
}
