package com.sentiment.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

import com.google.gson.Gson;
import com.sentiment.response.DqApiResponse;
import com.sentiment.response.GraphApiResponseUtil;
import com.sentiment.response.Header;
import com.sentiment.service.MovieSentimentStatsServiceImpl;
import com.sentiment.util.APIsConstant;

@Controller
@RequestMapping(value="/movie/stats")
public class MovieSentimentController {
	
	@Autowired
	MovieSentimentStatsServiceImpl movieSentimentStatsServiceImpl;

	@RequestMapping(value="/getMovieSentimentData",method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> getMovieSentimentData(HttpServletRequest request, HttpServletResponse response){
		int movieId = Integer.parseInt(request.getParameter("movieId"));
		String startDate = request.getParameter("sdate");
		String endDate = request.getParameter("edate");
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/json; charset=utf-8");
		responseHeaders.add("Access-Control-Allow-Origin", "*");
		responseHeaders.add("Access-Control-Allow-Headers", "*");
		String jsonResp = "";
		//DqApiResponse<Object[][]> resp = new DqApiResponse<Object[][]>();
		DqApiResponse<List<Map<Object, Object>>> resp = new DqApiResponse<List<Map<Object, Object>>>();
		Header header = new Header();
		resp.setHeader(header);
		
		Object[][] result =  movieSentimentStatsServiceImpl.getTweetSentimentTimeSeriesData(movieId, startDate, endDate);
		if(result != null && result.length > 0){
			List<Map<Object, Object>> points =  GraphApiResponseUtil.getDataForAmChart(result);
			resp.setBody(points);
			header.setStatus(APIsConstant.RESPONSE_STATUS_OK);
		}else{
			header.setStatus(APIsConstant.RESPONSE_STATUS_ERROR);
		}
		Gson gson = new Gson();
		jsonResp = gson.toJson(resp);
		return new ResponseEntity<String>(jsonResp, responseHeaders, HttpStatus.CREATED);
		
	}
	
	@RequestMapping(value="/getMoviesSentimentData",method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> getMoviesSentimentData(HttpServletRequest request, HttpServletResponse response){
		String[] ids = request.getParameter("movieIds").split(",");
		List<Integer>movieIds = new ArrayList<Integer>();
		for(String id:ids){
			movieIds.add(Integer.parseInt(id));
		}
		String startDate = request.getParameter("sdate");
		String endDate = request.getParameter("edate");
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/json; charset=utf-8");
		responseHeaders.add("Access-Control-Allow-Origin", "*");
		responseHeaders.add("Access-Control-Allow-Headers", "*");
		String jsonResp = "";
		//DqApiResponse<Map<Integer, List<Object[]>>> resp = new DqApiResponse<Map<Integer, List<Object[]>>>();
		DqApiResponse<List<Map<Object, Object>>> resp = new DqApiResponse<List<Map<Object, Object>>>();
		Header header = new Header();
		resp.setHeader(header);
		
		Map<Long, Map<Integer, Integer>> result =  movieSentimentStatsServiceImpl.getTweetSentimentTimeSeriesDataForMovies(movieIds, startDate, endDate);
		if(result != null && result.size() > 0){
			List<Map<Object, Object>> points = GraphApiResponseUtil.getDataForAmChart(result);
			resp.setBody(points);
			header.setStatus(APIsConstant.RESPONSE_STATUS_OK);
		}else{
			header.setStatus(APIsConstant.RESPONSE_STATUS_ERROR);
		}
		Gson gson = new Gson();
		jsonResp = gson.toJson(resp);
		return new ResponseEntity<String>(jsonResp, responseHeaders, HttpStatus.CREATED);
		
	}
	
	@RequestMapping(value="/getMovieStrengthData",method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> getMovieStrengthData(HttpServletRequest request, HttpServletResponse response){
		int movieId = Integer.parseInt(request.getParameter("movieId"));
		String startDate = request.getParameter("sdate");
		String endDate = request.getParameter("edate");
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/json; charset=utf-8");
		responseHeaders.add("Access-Control-Allow-Origin", "*");
		responseHeaders.add("Access-Control-Allow-Headers", "*");
		String jsonResp = "";
		//DqApiResponse<Object[][]> resp = new DqApiResponse<Object[][]>();
		DqApiResponse<List<Map<Object, Object>>> resp = new DqApiResponse<List<Map<Object, Object>>>();
		Header header = new Header();
		resp.setHeader(header);
		
		Object[][] result =  movieSentimentStatsServiceImpl.getTweetStrengthTimeSeriesData(movieId, startDate, endDate);
		if(result != null && result.length > 0){
			List<Map<Object, Object>>points =  GraphApiResponseUtil.getDataForAmChart(result);
			resp.setBody(points);
			header.setStatus(APIsConstant.RESPONSE_STATUS_OK);
		}else{
			header.setStatus(APIsConstant.RESPONSE_STATUS_ERROR);
		}
		Gson gson = new Gson();
		jsonResp = gson.toJson(resp);
		return new ResponseEntity<String>(jsonResp, responseHeaders, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/getMoviesStrengthData",method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> getMoviesStrengthDataForMovies(HttpServletRequest request, HttpServletResponse response){
		String[] ids = request.getParameter("movieIds").split(",");
		List<Integer>movieIds = new ArrayList<Integer>();
		for(String id:ids){
			movieIds.add(Integer.parseInt(id));
		}
		String startDate = request.getParameter("sdate");
		String endDate = request.getParameter("edate");
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/json; charset=utf-8");
		responseHeaders.add("Access-Control-Allow-Origin", "*");
		responseHeaders.add("Access-Control-Allow-Headers", "*");
		String jsonResp = "";
		//DqApiResponse<Map<Integer, List<Object[]>>> resp = new DqApiResponse<Map<Integer, List<Object[]>>>();
		DqApiResponse<List<Map<Object, Object>>> resp = new DqApiResponse<List<Map<Object, Object>>>();
		Header header = new Header();
		resp.setHeader(header);
		
		Map<Long, Map<Integer, Integer>> result =  movieSentimentStatsServiceImpl.getTweetStrengthTimeSeriesDataForMovies(movieIds, startDate, endDate);
		if(result != null && result.size() > 0){
			List<Map<Object, Object>> points = GraphApiResponseUtil.getDataForAmChart(result);
			resp.setBody(points);
			header.setStatus(APIsConstant.RESPONSE_STATUS_OK);
		}else{
			header.setStatus(APIsConstant.RESPONSE_STATUS_ERROR);
		}
		Gson gson = new Gson();
		jsonResp = gson.toJson(resp);
		return new ResponseEntity<String>(jsonResp, responseHeaders, HttpStatus.CREATED);
	}
	
	
	@RequestMapping(value="/getTagCloudData",method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> getTagCloudData(HttpServletRequest request, HttpServletResponse response){
		int movieId = Integer.parseInt(request.getParameter("movieId"));
		String startDate = request.getParameter("sdate");
		String endDate = request.getParameter("edate");
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/json; charset=utf-8");
		responseHeaders.add("Access-Control-Allow-Origin", "*");
		responseHeaders.add("Access-Control-Allow-Headers", "*");
		String jsonResp = "";
		DqApiResponse<Map<String, Double>> resp = new DqApiResponse<Map<String, Double>>();
		Header header = new Header();
		resp.setHeader(header);
		
		Map<String, Double> result =  movieSentimentStatsServiceImpl.getMovieTagCloudData(movieId, startDate, endDate);
		if(result != null && result.size() > 0){
			resp.setBody(result);
			header.setStatus(APIsConstant.RESPONSE_STATUS_OK);
		}else{
			header.setStatus(APIsConstant.RESPONSE_STATUS_ERROR);
		}
		Gson gson = new Gson();
		jsonResp = gson.toJson(resp);
		return new ResponseEntity<String>(jsonResp, responseHeaders, HttpStatus.CREATED);
	}
	
	
	@RequestMapping(value="/getTotalTweetsCollectedTillDate",method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> getTotalTweetsCollectedTillDate(HttpServletRequest request, HttpServletResponse response){
		int movieId = Integer.parseInt(request.getParameter("movieId"));
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/json; charset=utf-8");
		responseHeaders.add("Access-Control-Allow-Origin", "*");
		responseHeaders.add("Access-Control-Allow-Headers", "*");
		String jsonResp = "";
		DqApiResponse<Map<String, Object>> resp = new DqApiResponse<Map<String, Object>>();
		Header header = new Header();
		resp.setHeader(header);
		
		Map<String, Object>result = movieSentimentStatsServiceImpl.getTotalTweetsCollectedTillDate(movieId);
		
		if(result != null && result.size() > 0){
			resp.setBody(result);
			header.setStatus(APIsConstant.RESPONSE_STATUS_OK);
		}else{
			header.setStatus(APIsConstant.RESPONSE_STATUS_ERROR);
		}
		Gson gson = new Gson();
		jsonResp = gson.toJson(resp);
		return new ResponseEntity<String>(jsonResp, responseHeaders, HttpStatus.CREATED);
	}
}
