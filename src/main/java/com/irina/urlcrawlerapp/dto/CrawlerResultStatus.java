package com.irina.urlcrawlerapp.dto;

public class CrawlerResultStatus {
	private String url;
	private int  httpRequestStatusCode;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getHttpRequestStatusCode() {
		return httpRequestStatusCode;
	}

	public void setHttpRequestStatusCode(int httpRequestStatusCode) {
		this.httpRequestStatusCode = httpRequestStatusCode;
	}

	public CrawlerResultStatus(String url2, int responseCode) {
		this.url = url2;
		this.httpRequestStatusCode = responseCode;
	}
}
