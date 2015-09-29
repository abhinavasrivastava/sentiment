package com.sentiment.constants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.http.MediaType;

public class Constants {

	public static final MediaType JAVA_SCRIPT_MEDIA_TYPE = MediaType.valueOf("application/javascript");
	public static final MediaType JSON_MEDIA_TYPE = MediaType.valueOf("application/json;charset=UTF-8");      

	public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	public static final String SPECIAL_CHAR_REGEX = "\\band\\b|\\bor\\b|[+\\-,]";
}
