package com.sentiment.analyzer;

public class SentimentAnalyzer {

	/*public static ArrayList<String> getTweets(String topic) throws TwitterException, InterruptedException{
		TweetManager tweetManager = new TweetManager();
		ArrayList<String> tweets = tweetManager.getTweets(topic);
		ArrayList<String> cleanedTweets = tweetManager.cleanTweets(tweets);
		return cleanedTweets;
	}

	public static double[] analyzeSentiment(ArrayList<String> tweets) throws Exception
	{
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

	public static void main(String args[]) throws Exception{
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
