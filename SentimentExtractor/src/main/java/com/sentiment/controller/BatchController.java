package com.sentiment.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sentiment.batch.TweetProcessingBatch;

@Controller
@RequestMapping(value="/batch")
public class BatchController {

	@Autowired
	TweetProcessingBatch tweetProcessingBatch;
	
	@RequestMapping(value="/collectAndProcessTweets",method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> collectAndProcessTweets(HttpServletRequest request,HttpServletResponse response){
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/json; charset=utf-8");
		tweetProcessingBatch.execute();
		return new ResponseEntity<String>("OK", responseHeaders, HttpStatus.CREATED);
	}
}
