package com.sentiment.validation;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.sentiment.request.SentimentRequest;

@Component(value="requestValidator")
public class RequestValidator extends AbstractRequestValidator {
	Logger logger = Logger.getLogger(RequestValidator.class);
	@Override
	public boolean supports(Class<?> clazz) {
		return SentimentRequest.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		//checkIfEmpty(errors);
		validateReq(target,errors);		
		}
}