package com.sentiment.exception;

import java.util.ArrayList;
import java.util.List;

public class Errors {
	private List<Error> errors = new ArrayList<Error>();

	public List<Error> getErrors() {
		return errors;
	}
	public void add(Error error){
		errors.add(error);
	}
	
	public boolean hasErrors(){
		return errors.size() != 0;
	}
	
	public void addAll(Errors error){
		errors.addAll(error.getErrors());
	}
}

