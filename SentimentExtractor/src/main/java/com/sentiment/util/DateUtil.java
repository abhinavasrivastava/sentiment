package com.sentiment.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	private static SimpleDateFormat DATE_FORMATTER_YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");
	
	public static String getFormattedDate(Date date){
		return DATE_FORMATTER_YYYY_MM_DD.format(date);
	}
	
	public static long getDateWithoutTime(long milis){
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		Date date = new Date(milis);
		System.out.println(date);
		Date todayWithZeroTime = null;
		try {
			todayWithZeroTime = formatter.parse(formatter.format(date));
			System.out.println(todayWithZeroTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return todayWithZeroTime.getTime();
	}
	
	public static void main(String[] args) {
		System.out.println(getDateWithoutTime(1448672402000l));
		//1448649000000
		//1448649000000
	}

}
