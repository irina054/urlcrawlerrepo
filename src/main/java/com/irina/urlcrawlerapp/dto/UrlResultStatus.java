package com.irina.urlcrawlerapp.dto;

import java.util.ArrayList;
import java.util.List;

public class UrlResultStatus implements Cloneable{
	private String url;
	private int  httpRequestStatusCode;
	
	private transient int depthLevel;
	private transient List<String> refUrls;
	private transient boolean isAvailabilityTestDone; 
	private transient boolean isParsingRequired; 
	private transient boolean isParsingDone; 

	public UrlResultStatus(String url2, int responseCode, int depthLevel) {
		this.url = url2;
		this.httpRequestStatusCode = responseCode;
		this.depthLevel = depthLevel;
		this.refUrls  = new ArrayList<String>();
		this.setParsingRequired(true);
	}


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

	public boolean isParsingDone() {
		return isParsingDone;
	}

	public void setParsingDone(boolean isParsingDone) {
		this.isParsingDone = isParsingDone;
	}

	public boolean isAvailabilityTestDone() {
		return isAvailabilityTestDone;
	}

	public void setAvailabilityTestDone(boolean isAvailabilityTestDone) {
		this.isAvailabilityTestDone = isAvailabilityTestDone;
	}

	public List<String> getRefUrls() {
		return refUrls;
	}

	public void setRefUrls(List<String> refUrls) {
		this.refUrls = refUrls;
	}

	public int getDepthLevel() {
		return depthLevel;
	}

	public void setDepthLevel(int depthLevel) {
		this.depthLevel = depthLevel;
	}


	public boolean isParsingRequired() {
		return isParsingRequired;
	}


	public void setParsingRequired(boolean isParsingRequired) {
		this.isParsingRequired = isParsingRequired;
	}

//	public void copy(UrlResultStatus urlResultStatus) {		
//		BeanUtils.copyProperties(this, urlResultStatus);
//	}
}
