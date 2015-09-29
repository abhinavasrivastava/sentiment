package com.sentiment.util;

import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Utility Class which is used to convert String object into JSON format, which is returned in RESPONSE Body.
 */
@Component("gsonUtil")
public class GsonUtil {
	
	private Gson gson;
	
	private GsonUtil(){
		gson = new GsonBuilder().serializeNulls().create();
	}
	
	public Gson getGson(){
		return gson;
	}
	@PreDestroy
	public void destroy(){
		gson = null;
	}
}
