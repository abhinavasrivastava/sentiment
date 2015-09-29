package com.sentiment.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.sentiment.exception.ErrorCode;
import com.sentiment.request.SentimentRequest;

public abstract class AbstractRequestValidator implements Validator{
	private  String[] params;

	public void validatorResourceData(Object target, Errors errors, String ... params){
		this.params=params;
		validate(target, errors);
	}

	public void checkIfEmpty(Errors errors){
		for(String parameter : params){										
			ValidationUtils.rejectIfEmpty(errors, parameter, ErrorCode.MISSING_PARAMETER_ERROR,parameter);
		}
	}
	public void validateReq(Object target,Errors errors){
		SentimentRequest req = (SentimentRequest) target; 
		if(req.getText()==null || req.getText().isEmpty())
			errors.reject(ErrorCode.MISSING_PARAMETER_ERROR, ErrorCode.MISSING_PARAMETER_ERROR_VALUE);
	}
}