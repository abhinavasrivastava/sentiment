package com.sentiment.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sentiment.classifier.ClassifiedText;
import com.sentiment.classifier.NaiveBayesClassifier;
import com.sentiment.request.SentimentRequest;

@Service("sentimentClassificationService")
public class SentimentClassificationServiceImpl implements SentimentClassificationService {
	@Autowired
	NaiveBayesClassifier naiveBayesClassifier;
	
	Logger logger = Logger.getLogger(SentimentClassificationServiceImpl.class);

	@Override
	public ClassifiedText getsentiment(SentimentRequest request) {
		ClassifiedText cText = null;
		cText = naiveBayesClassifier.classifyText(request.getText());
		return cText;
	}
}
