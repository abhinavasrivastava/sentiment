package com.sentiment.response;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GraphApiResponseUtil {
	static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");;
	
	/*public static Map<String, Map<Long, Integer>> getDataSegregatedByDate(Object[][] data){
		Map<String, Map<Long, Integer>>points = new LinkedHashMap<String,Map<Long,Integer>>();
		for(Object[] point: data){
			String date = getFormattedDate((Long)point[0]);
			if(points.containsKey(date)){
				points.get(date).put((Long)point[0], (Integer)point[1]);
			}else{
				Map<Long, Integer>dataMap = new LinkedHashMap<Long, Integer>();
				dataMap.put((Long)point[0], (Integer)point[1]);
				points.put(date, dataMap);
			}
		}
		return points;
	}
	
	public static Map<String, Map<Long, Integer>> getDataSegregatedByDate(List<Object[]> data){
		Map<String, Map<Long, Integer>>points = new LinkedHashMap<String,Map<Long,Integer>>();
		for(Object[] point: data){
			String date = getFormattedDate((Long)point[0]);
			if(points.containsKey(date)){
				points.get(date).put((Long)point[0], (Integer)point[1]);
			}else{
				Map<Long, Integer>dataMap = new LinkedHashMap<Long, Integer>();
				dataMap.put((Long)point[0], (Integer)point[1]);
				points.put(date, dataMap);
			}
		}
		return points;
	}
	
	public static Map<Integer,Map<String, Map<Long, Integer>>> getDataSegregatedByDate(Map<Integer, List<Object[]>> data){
		Map<Integer,Map<String, Map<Long, Integer>>>moviePointsMap = new LinkedHashMap<Integer, Map<String,Map<Long,Integer>>>();
		for(Integer movId : data.keySet()){
			moviePointsMap.put(movId, getDataSegregatedByDate(data.get(movId)));
		}
		return moviePointsMap;
	}*/
	
	public static List<Map<Object, Object>>getDataForAmChart(Object[][] data){
		List<Map<Object, Object>>points = new ArrayList<Map<Object,Object>>();
		for(Object[] point : data){
			Map<Object, Object>pointMap = new LinkedHashMap<Object, Object>();
			pointMap.put("date", point[0]);
			pointMap.put("value", point[1]);
			points.add(pointMap);
		}
		return points;
	}
	
	public static List<Map<Object, Object>>getDataForAmChart(Map<Long, Map<Integer, Integer>> data){
		List<Map<Object, Object>>points = new ArrayList<Map<Object,Object>>();
		for(Long time : data.keySet()){
			Map<Object, Object>pointMap = new LinkedHashMap<Object, Object>();
			pointMap.put("date", time);
			for(int movieId : data.get(time).keySet()){
				pointMap.put(movieId, data.get(time).get(movieId));
			}
			points.add(pointMap);
		}
		return points;
	}

	/*private static String getFormattedDate(Long timestamp) {
		String formattedDate = dateFormat.format(timestamp);
		return formattedDate;
	}*/
}
