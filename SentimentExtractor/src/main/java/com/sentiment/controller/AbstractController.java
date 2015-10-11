package com.sentiment.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.sentiment.classifier.ClassifiedText;
import com.sentiment.exception.Errors;
import com.sentiment.response.Header;
import com.sentiment.response.SentimentResponse;
import com.sentiment.util.GsonUtil;

public abstract class AbstractController {
	@Autowired
	GsonUtil gsonUtil;

	protected String processJSONResponse(ClassifiedText cText, Errors errors){
		String jsonresp = null;
		SentimentResponse resp = new SentimentResponse();
		Header header = new Header();
		resp.setHeader(header);

		if(errors.hasErrors()){
			header.setStatus("0");
			header.setErrors(errors);
		}
		else{		
			header.setStatus("1");
			resp.setResult(cText.getSentimentClass());
		}
		jsonresp = gsonUtil.getGson().toJson(resp);		
		return jsonresp;
	}
}
