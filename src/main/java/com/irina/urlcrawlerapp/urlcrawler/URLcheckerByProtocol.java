package com.irina.urlcrawlerapp.urlcrawler;

import java.io.IOException;

import com.irina.urlcrawlerapp.exception.InputValidationError;

public interface URLcheckerByProtocol {

	int check() throws InputValidationError, IOException;
}
