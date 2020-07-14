package com.irina.urlcrawlerapp.urlcrawler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMethod;

import com.irina.urlcrawlerapp.exception.InputValidationError;

public class HTTPUrlChecker implements URLcheckerByProtocol{

	private URL url;
	
	public HTTPUrlChecker(URL url) {
		this.url = url;
	}

	public int check() throws InputValidationError {
		int result;
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection) url.openConnection();
	     
	     //TODO provide ability to customize HTTP connection settings from outside 
	     	connection.setConnectTimeout(1000 * 3); 
	     	connection.setReadTimeout(1000 * 2);

	     	connection.setRequestMethod(RequestMethod.HEAD.toString());
			result = connection.getResponseCode();
		 } catch (IOException e) {
			 //map all exceptions of connection to BAD_REQUEST.. for example, bad host in URL
			 //TODO think about more elegant solution...
			result = HttpURLConnection.HTTP_BAD_REQUEST;
		 } finally {
		     connection.disconnect();			
		}

	     return result;
	}
}
