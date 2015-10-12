package com.sentiment.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sentiment.constants.Constants;
import com.sentiment.exception.AppException;
import com.sentiment.util.ApiKeycache;

@Controller
@RequestMapping(value="/cache")
public class CacheController {
	
	Logger logger = Logger.getLogger(CacheController.class);
	
	@Autowired
	ApiKeycache apiKeycache;

	@RequestMapping(value="/refreshAuthKeyCache",method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> refreshAuthTokenCache(HttpServletRequest req){
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(Constants.JSON_MEDIA_TYPE);
		try {
			apiKeycache.loadCache();
		} catch (AppException e) {
			logger.error(e.getMessage(), e);
		}
		return new ResponseEntity<String>("OK", responseHeaders, HttpStatus.CREATED);
	}
}
