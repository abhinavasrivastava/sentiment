package com.sentiment.classifier;

public class Word implements Comparable<Word>{
	String word;
	Double mi;

	Word(String word, double mi){
		this.word = word;
		this.mi = mi;
	}

	public String getWord() {
		return word;
	}


	public void setWord(String word) {
		this.word = word;
	}


	public double getMi() {
		return mi;
	}


	public void setMi(double mi) {
		this.mi = mi;
	}


	public int compareTo(Word o) {
		return Double.compare(o.getMi(), this.getMi());
		//return this.getMi().compareTo(o.getMi());
	}

}