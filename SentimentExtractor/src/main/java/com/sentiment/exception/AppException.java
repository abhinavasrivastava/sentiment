package com.sentiment.exception;

public class AppException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1565759252191771192L;
	private Integer errorCode;


	public AppException() {
		super();
	}

	public AppException(String message) {
		super(message);
	}
	
	public AppException(String message, Integer errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public AppException(String message, Throwable cause) {
		super(message, cause);
	}

	public AppException(Throwable cause) {
		super(cause);
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
	

}
