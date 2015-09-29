package com.sentiment.classifier;

import java.io.Serializable;
import java.util.Map;

public class NaiveBayesClassificationModel implements Serializable{
	private static final long serialVersionUID = -3246407437325320516L;
	
	private Map<String, Integer> pos;
	private Map<String, Integer> neg;
	private int[] totals;
	
	public NaiveBayesClassificationModel(){
	}
	
	public NaiveBayesClassificationModel(Map<String, Integer> pos, Map<String, Integer> neg, int[] totals){
		this.pos = pos;
		this.neg = neg;
		this.totals = totals;
	}

	public Map<String, Integer> getPos() {
		return pos;
	}

	public void setPos(Map<String, Integer> pos) {
		this.pos = pos;
	}

	public Map<String, Integer> getNeg() {
		return neg;
	}

	public void setNeg(Map<String, Integer> neg) {
		this.neg = neg;
	}

	public int[] getTotals() {
		return totals;
	}

	public void setTotals(int[] totals) {
		this.totals = totals;
	}
}
