package com.irina.urlcrawlerapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.irina.urlcrawlerapp.dto.UrlResultStatus;
import com.irina.urlcrawlerapp.exception.ValidationError;
import com.irina.urlcrawlerapp.service.CrawlerService;

@RestController
public class CrawlerController {

	@Autowired
	CrawlerService  service;

	@RequestMapping(value = {"/",  "/hello", "/hello/"})
	public String hello() {
		return "Hello from IriSpringApp :) ";
	}
	
	@RequestMapping(value = {"/crawle",  "/crawle/"})
	public List<UrlResultStatus> crawleUrl(@RequestParam String url, @RequestParam int depth) throws ValidationError {

		return service.crawleUrl(url, depth);
	}

}
