package com.sentiment.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.carrot2.clustering.lingo.LingoClusteringAlgorithm;
import org.carrot2.core.Cluster;
import org.carrot2.core.Controller;
import org.carrot2.core.ControllerFactory;
import org.carrot2.core.Document;
import org.carrot2.core.ProcessingResult;
import org.springframework.stereotype.Component;

@Component
public class TopicClusterGenerator {

	public Map<String, Double> getTopicClusters(List<String>texts) {
		Map<String, Double> clusterMap = new HashMap<String, Double>();

		/* Prepare Carrot2 documents */
		final ArrayList<Document> documents = new ArrayList<Document>();
		for (String text : texts)
		{
			documents.add(new Document(text));
		}

		/* A controller to manage the processing pipeline. */
		final Controller controller = ControllerFactory.createSimple();

		/*
		 * Perform clustering by topic using the Lingo algorithm. Lingo can
		 * take advantage of the original query, so we provide it along with the documents.
		 */
		final ProcessingResult byTopicClusters = controller.process(documents, null,
				LingoClusteringAlgorithm.class);
		final List<Cluster> clustersByTopic = byTopicClusters.getClusters();
		
		for(Cluster cluster : clustersByTopic){
			System.out.println(cluster.getLabel() + "-" + cluster.getScore());
			clusterMap.put(cluster.getLabel(), cluster.getScore());
		}
		
		return clusterMap;
	}
}
