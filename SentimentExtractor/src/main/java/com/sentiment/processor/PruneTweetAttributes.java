package com.sentiment.processor;

import java.util.ArrayList;

import com.twitter.Extractor;

public class PruneTweetAttributes {

	public static boolean pruneTwitterAttributes(String tweetWord){
		Extractor extractor = new Extractor();
		ArrayList<String> names,hashtags,urls;
		urls = (ArrayList<String>) extractor.extractURLs(tweetWord);
		hashtags = (ArrayList<String>) extractor.extractHashtags(tweetWord);
		names = (ArrayList<String>) extractor.extractMentionedScreennames(tweetWord);

		if(!urls.isEmpty())
			return true;
		else if(!hashtags.isEmpty())
			return true;
		else if(!names.isEmpty())
			return true;
		else
			return false;
	}

	public static void main(String args[]){
		//http://t.co/YQkJNHtL
		System.out.println(PruneTweetAttributes.pruneTwitterAttributes("#shrey"));
	}
}
