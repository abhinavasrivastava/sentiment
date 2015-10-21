package com.sentiment.analyzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import twitter4j.TwitterException;

import com.sentiment.classifier.ClassifiedText;
import com.sentiment.classifier.NaiveBayesClassifier;
import com.sentiment.processor.EmoticonReplacer;
import com.sentiment.processor.PruneTweetAttributes;
import com.sentiment.processor.RemoveRepeatedCharacters;
import com.sentiment.processor.TweetSlangSubstitution;
import com.sentiment.processor.TweetStemmer;

@Component
public class SentimentAnalyzer {

	public List<String> cleanTweets(List<String> tweets){
		List<String>cleanTweets = new ArrayList<String>();
		for(String tweet: tweets){
			String cleanTweet="";
			List<String> tweetWords = Arrays.asList(tweet.split("\\s+"));
			for(String word : tweetWords){
				if(!PruneTweetAttributes.pruneTwitterAttributes(word)){
					//remove exclamation marks etc
					word = EmoticonReplacer.replaceEmoticon(word);
					word = TweetSlangSubstitution.replaceSlang(word);
					List<String> wordList = new ArrayList<String>(RemoveRepeatedCharacters.removeRepeated(word));
					for(String repeatWord : wordList){
						cleanTweet = cleanTweet+ " " + TweetStemmer.stemTweet(repeatWord); 
					}
				}
			}
			cleanTweets.add(cleanTweet);
		}
		return cleanTweets;
	}

	public double[] analyzeSentiment(List<String> tweets) 
	{
		tweets = cleanTweets(tweets);
		NaiveBayesClassifier nbClassifier = new NaiveBayesClassifier();
		double pos=0,neg=0,sentimentScore[] = new double[2];

		for(String tweet : tweets){
			ClassifiedText cTweet = nbClassifier.classifyText(tweet);
			if(cTweet.getSentimentClass().equals("Positive"))
				pos++;
			else if (cTweet.getSentimentClass().equals("Negative"))
				neg++;
		}
		sentimentScore[0] = pos;
		sentimentScore[1] = neg;
		return sentimentScore;
	}

	/*public static void main(String args[]) throws Exception{
		ResourceBundle rb = ResourceBundle.getBundle("topics");
		String[] topics = rb.getString("tweet.topics").split(",");
		for (String topic : topics){
			ArrayList<String> tweets = SentimentAnalyzer.getTweets(topic);
			double [] score = SentimentAnalyzer.analyzeSentiment(tweets);
			double allScore = score[0]/(score[0]+score[1])*100;
			System.out.println(topic+" sentiment:"+allScore);
			System.out.println("all tweets count:"+tweets.size());

			HashSet<String> tweetSet = new HashSet<String>(tweets);
			score = SentimentAnalyzer.analyzeSentiment(new ArrayList<String>(tweetSet));
			double uniqScore = score[0]/(score[0]+score[1])*100;
			System.out.println(topic+" sentiment:"+uniqScore);
			System.out.println("set tweets count:"+tweetSet.size());
			System.out.println("average:"+(allScore+uniqScore)/2);
		}
	}*/
}
