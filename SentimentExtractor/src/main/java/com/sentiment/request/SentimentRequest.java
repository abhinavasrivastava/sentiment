package com.sentiment.request;

public class SentimentRequest {
	private String text;
	private String rid;
	private String type;
	private String authKey;

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
	public String getAuthKey() {
		return authKey;
	}
	public void setAuthKey(String authkey) {
		this.authKey = authkey;
	}

}
