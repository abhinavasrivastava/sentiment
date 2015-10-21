package com.sentiment.service;

import java.util.Date;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogram;
import org.springframework.stereotype.Service;

@Service
public class ApiHitCountServiceImpl {
	
	public Object[][] getApiHitData(int userId, int apiTypeId, Date dateStart, Date dateEnd){
		Object[][] result = null;
		Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "elasticsearch-logging").build();
		Client client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress("212.71.244.245", 9300));
		SearchResponse response = client.prepareSearch("_all")
		        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
		        .setQuery(QueryBuilders.filteredQuery(
		        		QueryBuilders.matchAllQuery(), 
		        		FilterBuilders.boolFilter()
					        .must(FilterBuilders.rangeFilter("@timestamp").from(dateStart.getTime()).to(dateEnd.getTime()))
					        .must(FilterBuilders.termFilter("apiUser", userId))
					        .must(FilterBuilders.termFilter("apiType", apiTypeId))
					        .must(FilterBuilders.termFilter("logType.raw", "apiCount"))
		        ))
		        .addAggregation(AggregationBuilders.dateHistogram("dateAgg").field("@timestamp").interval(DateHistogram.Interval.WEEK))
		        .setFrom(0).setSize(60).setExplain(true)
		        .execute()
		        .actionGet();
		SearchHit [] hits  = response.getHits().getHits();
		//System.out.println(hits[0].getId());
		
		client.close();
		
		DateHistogram histogram = response.getAggregations().get("dateAgg");
		//List<DateHistogram.Bucket>buckets = histogram.getBuckets();
		result = new Object[histogram.getBuckets().size()][];
		int i = 0;
		for(DateHistogram.Bucket bucket : histogram.getBuckets()){
			result[i] = new Object[]{bucket.getKey(), bucket.getDocCount()};
			i++;
		}
		return result;
	}

}
