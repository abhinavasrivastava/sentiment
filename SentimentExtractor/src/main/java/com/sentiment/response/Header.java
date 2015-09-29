package com.sentiment.response;

import com.sentiment.exception.Errors;

public class Header {
	private String status;
	Errors errors;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Errors getErrors() {
		return errors;
	}
	public void setErrors(Errors errors) {
		this.errors = errors;
	}

}
