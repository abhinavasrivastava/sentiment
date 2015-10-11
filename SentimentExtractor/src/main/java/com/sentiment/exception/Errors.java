package com.sentiment.exception;

import java.util.ArrayList;
import java.util.List;

public class Errors {
	private List<ValidationError> error = new ArrayList<ValidationError>();

	public List<ValidationError> getErrors() {
		return error;
	}
	public void add(ValidationError validationError){
		error.add(validationError);
	}
	
	public boolean hasErrors(){
		return error.size() != 0;
	}
	
	public void addAll(Errors errors){
		error.addAll(errors.getErrors());
	}
}

