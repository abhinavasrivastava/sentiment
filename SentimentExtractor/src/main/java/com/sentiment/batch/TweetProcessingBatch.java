package com.sentiment.batch;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

import com.sentiment.analyzer.SentimentAnalyzer;
import com.sentiment.cache.MoviesCache;
import com.sentiment.dao.CacheMasterDaoImpl;
import com.sentiment.dao.MovieSentimentStatsDaoImpl;
import com.sentiment.exception.AppException;
import com.sentiment.model.MovieTwSearchDetail;
import com.sentiment.processor.TopicClusterGenerator;

@Component
public class TweetProcessingBatch {
	
	Logger logger = Logger.getLogger(TweetProcessingBatch.class);

	@Autowired
	MoviesCache moviesCache;

	@Autowired
	SentimentAnalyzer sentimentAnalyzer;

	@Autowired
	TopicClusterGenerator topicClusterGenerator;

	@Autowired
	MovieSentimentStatsDaoImpl movieSentimentStatsDaoImpl;
	
	@Autowired
	CacheMasterDaoImpl cacheMasterDaoImpl;
	
	//private static int COLLECTION_TIME_DURAION_MIN = 1;
	ResourceBundle rb = ResourceBundle.getBundle("app");

	public void execute(){
		logger.info("Tweet coletion started.");
		Date date = new Date();
		DateFormat outputFormatter = new SimpleDateFormat("yyyy/MM/dd");
		String collectionDate = outputFormatter.format(date); 
		//List<MovieTwSearchDetail>movies = moviesCache.getAllMovies();
		List<MovieTwSearchDetail> movies = null;
		try {
			movies = cacheMasterDaoImpl.getAllMovies();
		} catch (AppException e1) {
			logger.error("Batch could not run due to DB failure.");
			logger.error(e1.getMessage(), e1);
		}
		if(movies == null || movies.size() == 0) return;
		
		int collectionWindowMin = Integer.parseInt(rb.getString("tweet.collection.duration.min"));
		for(MovieTwSearchDetail movie : movies){
			if(movie.getStartDate().compareTo(new Date()) <= 0 && movie.getEndDate().compareTo(new Date()) >= 0){
				//Open Stream & collect tweets for 14 minutes
				String[] keywords = movie.getKeywords().split(",");
				try {
					List<String>tweets = collect(keywords, collectionWindowMin);
					if(tweets.size() > 0){
						//Process & compute
						int count = tweets.size();
						double[] scores = sentimentAnalyzer.analyzeSentiment(tweets);
						int sentimentScore = (int)((scores[0]*100) / (scores[0] + scores[1]));
						Map<String, Double>clusters = topicClusterGenerator.getTopicClusters(tweets);
						String tagCloud = getTagCloud(clusters);
						movieSentimentStatsDaoImpl.saveStats(movie.getId(), collectionDate, count, collectionWindowMin, sentimentScore, tagCloud, date);
					}else{
						logger.info("No tweets for movieId - " + movie.getId());
						movieSentimentStatsDaoImpl.saveStats(movie.getId(), collectionDate, 0, collectionWindowMin, 0, null, date);
					}

				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		}
		Date endTime = new Date();
		long timeTaken = (endTime.getTime() - date.getTime())/(1000*60);
		logger.info("Tweet coletion finished. Time taken - " + timeTaken + " minutes");
	}
	
	/*public void execute(){
		Calendar cal = Calendar.getInstance();
		cal.set(2015, 10, 12, 10, 0);

		for(int i=0; i<26; i++){
			Date date = cal.getTime();
			DateFormat outputFormatter = new SimpleDateFormat("yyyy/MM/dd");
			String collectionDate = outputFormatter.format(date);
			logger.info("Collection time - " + collectionDate);
			List<MovieTwSearchDetail>movies = moviesCache.getAllMovies();
			for(MovieTwSearchDetail movie : movies){
				if(movie.getStartDate().compareTo(new Date()) <= 0 && movie.getEndDate().compareTo(new Date()) >= 0){
					logger.info("start collecting tweets for movie - " + movie.getId());
					//Open Stream & collect tweets for 14 minutes
					String[] keywords = movie.getKeywords().split(",");
					try {
						List<String>tweets = collect(keywords);
						if(tweets.size() > 0){
							//Process & compute
							int count = tweets.size();
							double[] scores = sentimentAnalyzer.analyzeSentiment(tweets);
							int sentimentScore = (int)((scores[0]*100) / (scores[0] + scores[1]));
							Map<String, Double>clusters = topicClusterGenerator.getTopicClusters(tweets);
							String tagCloud = getTagCloud(clusters);
							movieSentimentStatsDaoImpl.saveStats(movie.getId(), collectionDate, count, sentimentScore, tagCloud, date);
						}else{
							logger.info("No tweets for movieId - " + movie.getId());
							movieSentimentStatsDaoImpl.saveStats(movie.getId(), collectionDate, 0, 0, null, date);
						}

					} catch (Exception e) {
						e.printStackTrace();
					} 
				}
			}
			cal.add(Calendar.HOUR, 12);
		}
	}*/

	private String getTagCloud(Map<String, Double> clusters) {
		StringBuilder sb = new StringBuilder();
		for(String topic : clusters.keySet()){
			sb.append(topic).append("@@@").append(clusters.get(topic)).append("$$$");
		}
		String tagCloud = sb.substring(0, sb.lastIndexOf("$$$"));
		return tagCloud;
	}

	
	
	
	
	private StatusListener getListener(final BlockingQueue<Status> tweets){
		StatusListener listener = new StatusListener(){
			public void onStatus(Status status) {
				System.out.println(status.getUser().getName() + " : " + status.getText());
				tweets.offer(status);
			}
			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}
			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}
			public void onException(Exception ex) {
				ex.printStackTrace();
			}
			@Override
			public void onScrubGeo(long arg0, long arg1) {
				// TODO Auto-generated method stub

			}
			@Override
			public void onStallWarning(StallWarning arg0) {
				// TODO Auto-generated method stub

			}
		};
		
		return listener;
	}
	TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
	
	public List<String> collect(final String[] keywords, int collectionWindowMin) throws TwitterException, IOException{
		long start = System.currentTimeMillis();
		long end = start + collectionWindowMin*60*1000; // 60 seconds * 1000 ms/sec

		final BlockingQueue<Status> tweets = new LinkedBlockingQueue<Status>(); 
		/*StatusListener listener = new StatusListener(){
			public void onStatus(Status status) {
				System.out.println(status.getUser().getName() + " : " + status.getText());
				tweets.offer(status);
			}
			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}
			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}
			public void onException(Exception ex) {
				ex.printStackTrace();
			}
			@Override
			public void onScrubGeo(long arg0, long arg1) {
				// TODO Auto-generated method stub

			}
			@Override
			public void onStallWarning(StallWarning arg0) {
				// TODO Auto-generated method stub

			}
		};*/
		
		StatusListener listener = getListener(tweets);
		FilterQuery fq = new FilterQuery();        
		//String keywords[] = {"#talvar"};
		fq.track(keywords); 
		fq.language("en");

		//TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
		twitterStream.addListener(listener);
		twitterStream.filter(fq);
		
		final List<String> collected = new ArrayList<String>();
		while (System.currentTimeMillis() < end) {
			Status status = null;
			try{
				status = tweets.poll(10, TimeUnit.SECONDS); 
			}catch(InterruptedException ex){

			}

			if (status == null) {
				// TODO: Consider hitting this too often could indicate no further Tweets
				continue;
			}
			collected.add(status.getText());
		}
		//twitterStream.shutdown();
		twitterStream.clearListeners();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return collected;
	}

	public static void main(String[] args) {/*
		try {
			new TweetProcessingBatch().collect(new String[]{"bihar"});
		} catch (TwitterException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	*/}

}
