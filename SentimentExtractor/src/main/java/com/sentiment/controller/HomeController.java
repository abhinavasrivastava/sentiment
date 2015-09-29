package com.sentiment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	@RequestMapping(value = "/ping", method = RequestMethod.GET)
	public @ResponseBody String ping() {
		return "{\"responseHeader\":{\"Name\":\"SentimentAnalysisApi\"},\"status\":\"OK\"}";
	}
}
