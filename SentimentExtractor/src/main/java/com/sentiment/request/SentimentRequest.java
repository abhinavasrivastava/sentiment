package com.sentiment.request;

public class SentimentRequest {
	private String text;
	private String rid;
	private String type;

	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getRid() {
		return rid;
	}
	public void setRid(String rid) {
		this.rid = rid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
