package com.sentiment.validation;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.sentiment.exception.ErrorCode;
import com.sentiment.model.ApiUser;
import com.sentiment.request.SentimentRequest;
import com.sentiment.util.ApiKeycache;

@Component(value="requestValidator")
public class RequestValidator extends AbstractRequestValidator {
	Logger logger = Logger.getLogger(RequestValidator.class);
	
	@Autowired
	ApiKeycache apiKeycache;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return SentimentRequest.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		SentimentRequest req = (SentimentRequest) target; 
		ApiUser apiUser = apiKeycache.getApiUser(req.getAuthKey());
		if(apiUser == null || apiUser.getApiId() != 1){
			errors.reject(ErrorCode.INVALID_API_TOKEN_ERROR, ErrorCode.INVALID_API_TOKEN_ERROR_VALUE);
		}
		validateReq(target,errors);		
	}
}