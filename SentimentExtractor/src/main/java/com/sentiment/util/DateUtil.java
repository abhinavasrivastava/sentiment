package com.sentiment.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	private static SimpleDateFormat DATE_FORMATTER_YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");
	
	public static String getFormattedDate(Date date){
		return DATE_FORMATTER_YYYY_MM_DD.format(date);
	}

}
