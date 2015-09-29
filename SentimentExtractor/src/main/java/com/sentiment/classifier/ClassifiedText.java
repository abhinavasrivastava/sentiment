package com.sentiment.classifier;

public class ClassifiedText {
	private String text;
	private double posProbability;
	private double negProbability;
	private String sentimentClass;

	public ClassifiedText(String text, double posProbability,double negProbability, String sentimentClass) {
		this.setText(text);
		this.posProbability = posProbability;
		this.negProbability = negProbability;
		this.sentimentClass = sentimentClass;
	}

	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public double getPosProbability() {
		return posProbability;
	}
	public void setPosProbability(double posProbability) {
		this.posProbability = posProbability;
	}
	public double getNegProbability() {
		return negProbability;
	}
	public void setNegProbability(double negProbability) {
		this.negProbability = negProbability;
	}
	public String getSentimentClass() {
		return sentimentClass;
	}
	public void setSentimentClass(String sentimentClass) {
		this.sentimentClass = sentimentClass;
	}

}
