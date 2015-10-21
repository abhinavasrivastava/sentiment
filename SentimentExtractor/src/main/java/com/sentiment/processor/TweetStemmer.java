package com.sentiment.processor;

import org.tartarus.snowball.ext.EnglishStemmer;

public class TweetStemmer {

	public static String stemTweet(String tweetWord){
		return tweetWord;
		/*EnglishStemmer stemmer = new EnglishStemmer();
		String temp = tweetWord.replace(".", "");
		stemmer.setCurrent(temp);
		stemmer.stem();
		if(tweetWord.contains(".")){
			return stemmer.getCurrent()+".";
		}
		else
			return stemmer.getCurrent();*/
	}

	public static void main(String args[]){
		System.out.println(TweetStemmer.stemTweet("escalates."));
		System.out.println(TweetStemmer.stemTweet("escalate"));
	}
}
