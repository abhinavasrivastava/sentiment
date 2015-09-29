package com.sentiment.response;

import com.sentiment.classifier.ClassifiedText;

public class SentimentResponse {
	private Header header;
	private ClassifiedText result;

	public Header getHeader() {
		return header;
	}
	public void setHeader(Header header) {
		this.header = header;
	}
	public ClassifiedText getResult() {
		return result;
	}
	public void setResult(ClassifiedText result) {
		this.result = result;
	}

}
