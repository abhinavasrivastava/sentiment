package com.sentiment.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sentiment.classifier.ClassifiedText;
import com.sentiment.constants.Constants;
import com.sentiment.exception.Errors;
import com.sentiment.exception.ValidationError;
import com.sentiment.model.ApiUser;
import com.sentiment.request.SentimentRequest;
import com.sentiment.service.SentimentClassificationService;
import com.sentiment.util.ApiKeycache;
import com.sentiment.validation.RequestValidator;

@Controller
@RequestMapping(value="/ai")
public class SentimentController extends AbstractController{
	@Autowired
	SentimentClassificationService sentimentClassificationService;

	@Autowired
	RequestValidator requestValidator;
	
	@Autowired
	ApiKeycache apiKeycache;
	
	Logger logger = Logger.getLogger(SentimentController.class);

	@RequestMapping(value="/getresult",method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> getKeywordresults(@ModelAttribute("sentimentRequest")SentimentRequest request,
			BindingResult bindingResult,HttpServletResponse response){

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(Constants.JSON_MEDIA_TYPE);
		String jsonresp = null;
		Errors errors = new Errors();
		requestValidator.validatorResourceData(request,bindingResult,new String[]{"text"});
		if(bindingResult.hasErrors()){
			processValidationErrors(bindingResult.getAllErrors(), errors);
			jsonresp = processJSONResponse(null, errors);
		}
		else{
			ApiUser apiUser = apiKeycache.getApiUser(request.getAuthKey());
			String reqLog = "?logType=apiCount&apiType=1&apiUser=" + apiUser.getUser().getUserId();
			ClassifiedText cText = sentimentClassificationService.getsentiment(request);
			jsonresp = processJSONResponse(cText, errors);
		}
		logger.info("?request="+request.getText()+"&logtype=apicount");

		return new ResponseEntity<String>(jsonresp, responseHeaders, HttpStatus.CREATED);
	}
	protected void processValidationErrors(List<ObjectError> allErrors,	Errors errors) {
		for(ObjectError valError:allErrors){
			ValidationError error=new ValidationError(valError.getCode(), valError.getDefaultMessage());
			errors.add(error);
		}
	}
}