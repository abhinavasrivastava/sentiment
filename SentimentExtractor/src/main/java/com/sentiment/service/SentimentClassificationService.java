package com.sentiment.service;

import com.sentiment.classifier.ClassifiedText;
import com.sentiment.request.SentimentRequest;

public interface SentimentClassificationService {
	public ClassifiedText getsentiment(SentimentRequest request);

}
