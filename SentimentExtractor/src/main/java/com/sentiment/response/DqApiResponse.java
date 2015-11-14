package com.sentiment.response;

public class DqApiResponse<T> {
	
	private Header header;
	private T body;
	
	public DqApiResponse(){
		
	}
	
	public DqApiResponse(Header header, T body){
		this.header = header;
		this.body = body;
	}
	
	public Header getHeader() {
		return header;
	}
	public void setHeader(Header header) {
		this.header = header;
	}
	public T getBody() {
		return body;
	}
	public void setBody(T body) {
		this.body = body;
	}
	
	

}
