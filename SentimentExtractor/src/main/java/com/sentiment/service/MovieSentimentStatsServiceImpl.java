package com.sentiment.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sentiment.dao.MovieSentimentStatsDaoImpl;

@Service
public class MovieSentimentStatsServiceImpl {
	
	@Autowired
	MovieSentimentStatsDaoImpl movieSentimentStatsDaoImpl;

	public Object[][] getTweetSentimentTimeSeriesData(int movieId, String startDate, String endDate){
		Object[][] tweetSentimentTimeSeriesData = null;
		tweetSentimentTimeSeriesData = movieSentimentStatsDaoImpl.getTweetSentimentTimeSeriesData(movieId, startDate, endDate);
		return tweetSentimentTimeSeriesData;
	}
	
	public Map<Long, Map<Integer, Integer>> getTweetSentimentTimeSeriesDataForMovies(List<Integer> movieIds, String startDate, String endDate) {
		return movieSentimentStatsDaoImpl.getTweetSentimentTimeSeriesDataForMovies(movieIds, startDate, endDate);
	}
	
	public Map<Long, Map<Integer, Integer>> getTweetStrengthTimeSeriesDataForMovies(List<Integer> movieIds, String startDate, String endDate) {
		return movieSentimentStatsDaoImpl.getTweetStrengthTimeSeriesDataForMovies(movieIds, startDate, endDate);
	}
	
	public Object[][] getTweetStrengthTimeSeriesData(int movieId, String startDate, String endDate){
		Object[][] tweetStrngthTimeSeriesData = null;
		tweetStrngthTimeSeriesData = movieSentimentStatsDaoImpl.getTweetStrengthTimeSeriesData(movieId, startDate, endDate);
		return tweetStrngthTimeSeriesData;
	}
	
	public Map<String, Double>getMovieTagCloudData(int movieId, String startDate, String endDate){
		Map<String, Double>movieTagCloudData = new HashMap<String, Double>();
		List<Map<String, Double>> listMap = movieSentimentStatsDaoImpl.getMovieTagCloudData(movieId, startDate, endDate);
		for(Map<String, Double> map : listMap){
			for(String tag : map.keySet()){
				if(movieTagCloudData.containsKey(tag)){
					movieTagCloudData.put(tag, map.get(tag) + movieTagCloudData.get(tag));
				}else{
					movieTagCloudData.put(tag, map.get(tag));
				}
			}
		}
		return movieTagCloudData;
	}
	
	public Map<String, Object>getTotalTweetsCollectedTillDate(int movieId){
		return movieSentimentStatsDaoImpl.getTotalTweetsCollectedTillDate(movieId);
	}
}
