package com.sentiment.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MovieSentimentStatsDaoImpl {
	
	Logger LOGGER = Logger.getLogger(MovieSentimentStatsDaoImpl.class);
	protected JdbcTemplate jdbcTemplate;


	@Autowired
	public void setDataSource(DataSource dataSource) {
		if(jdbcTemplate == null) {
			this.jdbcTemplate = new JdbcTemplate(dataSource);
		}
	}

	public void saveStats(int movieId, String collectionDate, int numTweets, int sentimentScore, String tagCloud){
		String sql = "insert into movie_sentiment_stats ("
				+ "movie_id,"
				+ "collection_date,"
				+ "num_tweets,"
				+ "sentiment_score,"
				+ "tag_cloud,"
				+ "created_tx_stamp,"
				+ "last_updated_tx_stamp) values(?,?,?,?,?,?,?)";
		
		jdbcTemplate.update(sql, 
				movieId,
				collectionDate,
				numTweets,
				sentimentScore,
				tagCloud,
				new Date(),
				new Date());
	}

	public Object[][] getTweetSentimentTimeSeriesData(int movieId, String startDate, String endDate) {
		Object[][] tweetSentimentTimeSeriesData = null;
		String sql = "SELECT created_tx_stamp,sentiment_score FROM movie_sentiment_stats WHERE movie_id=? AND collection_date >=? AND collection_date <= ?";
		List<Map<String, Object>>listMap = jdbcTemplate.queryForList(sql, movieId, startDate, endDate);
		if(listMap != null && listMap.size() > 0){
			tweetSentimentTimeSeriesData = new Object[listMap.size()][];
			int idx = 0;
			for(Map<String, Object>map : listMap){
				Date time = (Date)map.get("created_tx_stamp");
				Integer sentimentScore = (Integer)map.get("sentiment_score");
				tweetSentimentTimeSeriesData[idx] = new Object[]{time.getTime(), sentimentScore};
				idx++;
			}
		}
		return tweetSentimentTimeSeriesData;
	}
	
	public Map<Integer, List<Object[]>> getTweetSentimentTimeSeriesDataForMovies(List<Integer> movieIds, String startDate, String endDate) {
		Map<Integer, List<Object[]>> tweetSentimentTimeSeriesData = null;
		String sql = "SELECT movie_id, created_tx_stamp,sentiment_score FROM movie_sentiment_stats WHERE movie_id in (" + StringUtils.join(movieIds, ",") + ") AND collection_date >=? AND collection_date <= ?";
		List<Map<String, Object>>listMap = jdbcTemplate.queryForList(sql, startDate, endDate);
		if(listMap != null && listMap.size() > 0){
			tweetSentimentTimeSeriesData = new HashMap<Integer, List<Object[]>>();
			for(Map<String, Object>map : listMap){
				Integer movieId = (Integer)map.get("movie_id");
				Date time = (Date)map.get("created_tx_stamp");
				Integer sentimentScore = (Integer)map.get("sentiment_score");
				
				if(tweetSentimentTimeSeriesData.containsKey(movieId)){
					tweetSentimentTimeSeriesData.get(movieId).add(new Object[]{time.getTime(), sentimentScore});
				}else{
					List<Object[]>dataPts =  new ArrayList<Object[]>();
					dataPts.add(new Object[]{time.getTime(), sentimentScore});
					tweetSentimentTimeSeriesData.put(movieId, dataPts);
				}
			}
		}
		return tweetSentimentTimeSeriesData;
	}
	
	public Object[][] getTweetStrengthTimeSeriesData(int movieId, String startDate, String endDate) {
		Object[][] tweetStrengthTimeSeriesData = null;
		String sql = "SELECT created_tx_stamp,num_tweets FROM movie_sentiment_stats WHERE movie_id=? AND collection_date >=? AND collection_date <= ?";
		List<Map<String, Object>>listMap = jdbcTemplate.queryForList(sql, movieId, startDate, endDate);
		if(listMap != null && listMap.size() > 0){
			tweetStrengthTimeSeriesData = new Object[listMap.size()][];
			int idx = 0;
			for(Map<String, Object>map : listMap){
				Date time = (Date)map.get("created_tx_stamp");
				Integer numTweets = (Integer)map.get("num_tweets");
				tweetStrengthTimeSeriesData[idx] = new Object[]{time.getTime(), numTweets};
				idx++;
			}
		}
		return tweetStrengthTimeSeriesData;
	}
	
	public Map<Integer, List<Object[]>> getTweetStrengthTimeSeriesDataForMovies(List<Integer> movieIds, String startDate, String endDate) {
		Map<Integer, List<Object[]>> tweetStrengthTimeSeriesData = null;
		String sql = "SELECT movie_id, created_tx_stamp,num_tweets FROM movie_sentiment_stats "
				+ "WHERE movie_id in (" + StringUtils.join(movieIds, ",") + ") AND collection_date >=? AND collection_date <= ?";
		List<Map<String, Object>>listMap = jdbcTemplate.queryForList(sql, startDate, endDate);
		if(listMap != null && listMap.size() > 0){
			tweetStrengthTimeSeriesData = new HashMap<Integer, List<Object[]>>();
			for(Map<String, Object>map : listMap){
				Integer movieId = (Integer)map.get("movie_id");
				Date time = (Date)map.get("created_tx_stamp");
				Integer numTweets = (Integer)map.get("num_tweets");
				
				if(tweetStrengthTimeSeriesData.containsKey(movieId)){
					tweetStrengthTimeSeriesData.get(movieId).add(new Object[]{time.getTime(), numTweets});
				}else{
					List<Object[]>dataPts =  new ArrayList<Object[]>();
					dataPts.add(new Object[]{time.getTime(), numTweets});
					tweetStrengthTimeSeriesData.put(movieId, dataPts);
				}
			}
		}
		return tweetStrengthTimeSeriesData;
	}
	
	
	public List<Map<String, Double>>getMovieTagCloudData(int movieId, String startDate, String endDate){
		List<Map<String, Double>>movieTagCloudData = new ArrayList<Map<String,Double>>();
		String sql = "select tag_cloud FROM movie_sentiment_stats WHERE movie_id=? AND collection_date >=? AND collection_date <= ?";
		List<Map<String, Object>>listMap = jdbcTemplate.queryForList(sql, movieId, startDate, endDate);
		if(listMap != null && listMap.size() > 0){
			for(Map<String, Object>map : listMap){
				String tgCloud =  (String)map.get("tag_cloud");
				Map<String, Double>tgMap = new HashMap<String, Double>();
				String[]tags = tgCloud.split("\\$\\$\\$");
				for(String tag: tags){
					String[] tokens = tag.split("@@@");
					tgMap.put(tokens[0], Double.parseDouble(tokens[1]));
				}
				movieTagCloudData.add(tgMap);
			}
		}
		return movieTagCloudData;
	}
	
	public Map<String, Object>getTotalTweetsCollectedTillDate(int movieId){
		Map<String, Object> result = new HashMap<String, Object>();
		String sql  = "SELECT mss.movie_id as movieId, mtm.start_date as startDate, mtm.end_date as endDate, SUM(num_tweets) as totalTweets FROM movies_tweet_master mtm, movie_sentiment_stats mss "
				+ "WHERE mtm.mov_id = mss.movie_id and mtm.mov_id=? GROUP BY mtm.mov_id";
		List<Map<String, Object>>listMap = jdbcTemplate.queryForList(sql, movieId);
		if(listMap != null && listMap.size() > 0){
			result =  listMap.get(0);
		}
		return result;
	}
}
