package com.sentiment.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.sentiment.service.MovieSentimentStatsServiceImpl;

@Controller
@RequestMapping(value="/movie/stats")
public class MovieSentimentController {
	
	@Autowired
	MovieSentimentStatsServiceImpl movieSentimentStatsServiceImpl;

	@RequestMapping(value="/getSentimentData",method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> getSentimentData(HttpServletRequest request,
			BindingResult bindingResult,HttpServletResponse response){
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/json; charset=utf-8");
		int movieId = Integer.parseInt(request.getParameter(""));
		String startDate = request.getParameter("sdate");
		String endDate = request.getParameter("edate");
		Object[][] result =  movieSentimentStatsServiceImpl.getTweetSentimentTimeSeriesData(movieId, startDate, endDate);
		Gson gson = new Gson();
		String data = "NDF";
		if(result.length > 0) data = gson.toJson(result);
		return new ResponseEntity<String>(data, responseHeaders, HttpStatus.CREATED);
		
	}
	
	@RequestMapping(value="/getStrengthData",method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> getStrengthData(HttpServletRequest request,
			BindingResult bindingResult,HttpServletResponse response){
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/json; charset=utf-8");
		int movieId = Integer.parseInt(request.getParameter(""));
		String startDate = request.getParameter("sdate");
		String endDate = request.getParameter("edate");
		Object[][] result =  movieSentimentStatsServiceImpl.getTweetStrngthTimeSeriesData(movieId, startDate, endDate);
		Gson gson = new Gson();
		String data = "NDF";
		if(result.length > 0) data = gson.toJson(result);
		return new ResponseEntity<String>(data, responseHeaders, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/getTagCloudData",method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> getTagCloudData(HttpServletRequest request,
			BindingResult bindingResult,HttpServletResponse response){
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/json; charset=utf-8");
		int movieId = Integer.parseInt(request.getParameter(""));
		String startDate = request.getParameter("sdate");
		String endDate = request.getParameter("edate");
		Map<String, Double> result =  movieSentimentStatsServiceImpl.getMovieTagCloudData(movieId, startDate, endDate);
		Gson gson = new Gson();
		String data = "NDF";
		if(result.size() > 0) data = gson.toJson(result);
		return new ResponseEntity<String>(data, responseHeaders, HttpStatus.CREATED);
	}
}
