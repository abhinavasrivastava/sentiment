package com.sentiment.classifier;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

@Service("naiveBayesClassifier")
public class NaiveBayesClassifier {
	Logger logger = Logger.getLogger(NaiveBayesClassifier.class);

	ResourceBundle rb = ResourceBundle.getBundle("file");
	NaiveBayesClassificationModel model;

	File posDir = new File(rb.getString("posDir"));
	File negDir = new File(rb.getString("negDir"));
	File testPosDir = new File(rb.getString("testPosDir"));
	File testNegDir = new File(rb.getString("testNegDir"));
	String modelFileName = rb.getString("modelFileName");

	/*NaiveBayesClassifier() throws ClassNotFoundException, IOException{
		loadModel();
	}*/

	@PostConstruct
	private void loadModel() throws IOException, ClassNotFoundException {

		File modelFile = new File(modelFileName);

		if (modelFile.exists()){
			logger.info("model exists");
			InputStream buffer = new BufferedInputStream(new FileInputStream(modelFile));
			ObjectInput input = new ObjectInputStream(buffer);
			model = (NaiveBayesClassificationModel) input.readObject();
			input.close();
		}
		else{
			try {
				logger.info("building new model");
				model = trainModel();
				OutputStream buffer = new BufferedOutputStream(new FileOutputStream(modelFileName));
				ObjectOutput output = new ObjectOutputStream(buffer);
				output.writeObject(model);
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public Set<String> negateSequence(String text){
		/**
		    Detects negations and transforms negated words into "not_" form.
		 **/

		boolean negation = false;
		Set<String>result = new HashSet<String>();
		String[] words = text.split(" ");
		String prev = null;
		String pprev = null;
		for (String word : words){
			String stripped = word.toLowerCase().replaceAll("[?.!:;]","");
			String negated = negation ? "not_" + stripped : stripped;
			result.add(negated);
			if (prev !=null){
				String bigram = prev + " " + negated;
				result.add(bigram);
				if (pprev != null){
					String trigram = pprev + " " + bigram;
					result.add(trigram);
				}
				pprev = prev;
			}
			prev = negated;

			if (word.contains("n't") || word.toLowerCase().equals("no") || word.toLowerCase().equals("not")){
				negation = !negation;
			}
			if (word.contains("?") || word.contains(".") || word.contains("!") || word.contains(":") || word.contains(";")){
				negation = false;
			}
		}
		return result;
	}

	private NaiveBayesClassificationModel trainModel() throws IOException {
		NaiveBayesClassificationModel baseModel = null;
		Map<String, Integer> pos = new HashMap<String, Integer>();
		Map<String, Integer> neg = new HashMap<String, Integer>();
		int[] totals = new int[2];

		if(posDir.isDirectory()){
			int count = 0;
			for(File f : posDir.listFiles()){
				count++;
				if (count%1000==0)
					logger.info(count+" pos files processed");
				BufferedReader br = new BufferedReader(new FileReader(f));
				for(String word : negateSequence(br.readLine())){
					if(pos.get(word) == null){
						pos.put(word, 1);
					}else{
						pos.put(word, pos.get(word) + 1);
					}

					if(neg.get("not_"+word) == null){
						neg.put("not_"+word, 1);
					}else{
						neg.put("not_"+word, neg.get("not_"+word) + 1);
					}

				}
				br.close();
			}
		}

		if(negDir.isDirectory()){
			int count =0;
			for(File f : negDir.listFiles()){
				count++;
				if (count%1000==0)
					logger.info(count+" neg files processed");
				BufferedReader br = new BufferedReader(new FileReader(f));
				for(String word : negateSequence(br.readLine())){
					if(neg.get(word) == null){
						neg.put(word, 1);
					}else{
						neg.put(word, neg.get(word) + 1);
					}

					if(pos.get("not_"+word) == null){
						pos.put("not_"+word, 1);
					}else{
						pos.put("not_"+word, pos.get("not_"+word) + 1);
					}

				}
				br.close();
			}
		}

		pruneFeatures(pos,neg);

		totals[0] = getSum(pos.values());
		totals[1] = getSum(neg.values());

		baseModel = new NaiveBayesClassificationModel(pos, neg, totals);
		logger.info("base model created");

		return featureSelectionTrail(baseModel);
	}

	private int getSum(Collection<Integer> values){
		int sum = 0;
		for(int val : values)
			sum += val;
		return sum;
	}

	private void pruneFeatures(Map<String, Integer>pos, Map<String, Integer>neg) {
		logger.info("Pruning features");
		/*Remove features that appear only once. */
		for(Iterator<Entry<String, Integer>> it = pos.entrySet().iterator(); it.hasNext(); ) {
			Entry<String, Integer> entry = it.next();
			String key = entry.getKey();
			if((pos.get(key) ==null ||  pos.get(key)<=1) && (neg.get(key) ==null  || neg.get(key)<=1)){
				it.remove();
			}
		}

		for(Iterator<Entry<String, Integer>> it = neg.entrySet().iterator(); it.hasNext(); ) {
			Entry<String, Integer> entry = it.next();
			String key = entry.getKey();
			if((pos.get(key) ==null ||  pos.get(key)<=1) && (neg.get(key) ==null  || neg.get(key)<=1)){
				it.remove();
			}
		}
		logger.info("features pruned");	
	}

	private NaiveBayesClassificationModel featureSelectionTrail(NaiveBayesClassificationModel baseModel) throws IOException {
		Set<String> words = new HashSet<String>();
		words.addAll(baseModel.getPos().keySet());
		words.addAll(baseModel.getNeg().keySet());
		logger.info("total length of features in feature selection trial:"+words.size());

		List<Word> wordList = new ArrayList<Word>();
		for(String word:words){
			wordList.add(new Word(word, getMi(word, baseModel)));
		}
		Collections.sort(wordList);
		logger.info("MI calculated and sorted");

		int	step = Integer.parseInt(rb.getString("step"));
		int	start = Integer.parseInt(rb.getString("start"));
		int limit = Integer.parseInt(rb.getString("limit"));

		int bestk = 0;

		double bestAccuracy = 0.0;
		List<String> features = new ArrayList<String>();
		for(int i = 0; i < start; i++){
			features.add(wordList.get(i).getWord());
		}

		for(int i = start; i < limit; i+=step){
			for(int j = i; j< i+step; j++){
				features.add(wordList.get(j).getWord());
			}

			int correct = 0;
			int size = 0;

			Set<String> featuresSet = new HashSet<String>(features);
			for(File f : testPosDir.listFiles()){
				logger.info("test pos file with size:"+size);
				FileReader fR = new FileReader(f);
				BufferedReader bR = new BufferedReader(fR);
				String text = bR.readLine();
				if(classify(text, featuresSet, baseModel)){
					correct++;
				}
				size++;
				bR.close();
				fR.close();			
			}

			for(File f : testNegDir.listFiles()){
				logger.info("test neg file with size:"+size);
				FileReader fR = new FileReader(f);
				BufferedReader bR = new BufferedReader(fR);
				String text = bR.readLine();
				if(!classify(text, featuresSet, baseModel)){
					correct++;
				}
				size++;
				bR.close();
				fR.close();	
			}

			if ((correct*1.0 / size) > bestAccuracy)
				bestk = i;
		}

		features = features.subList(0,bestk);
		System.out.println("best features count:"+features.size());
		System.out.println("bestk:"+bestk);

		return getModelWithRelevantFeatures(baseModel, new HashSet<String>(features));

	}

	private NaiveBayesClassificationModel getModelWithRelevantFeatures(
			NaiveBayesClassificationModel baseModel, Set<String> features) {
		Map<String, Integer> pos = new HashMap<String, Integer>();
		Map<String, Integer> neg = new HashMap<String, Integer>();
		int[] totals = new int[2];
		for(String key : baseModel.getPos().keySet()){
			if(features.contains(key)){
				pos.put(key, baseModel.getPos().get(key));
			}
		}

		for(String key : baseModel.getNeg().keySet()){
			if(features.contains(key)){
				neg.put(key, baseModel.getNeg().get(key));
			}
		}
		totals[0] = getSum(pos.values());
		totals[1] = getSum(neg.values());

		return new NaiveBayesClassificationModel(pos, neg, totals);
	}

	private boolean classify(String text, Set<String> features, NaiveBayesClassificationModel baseModel) {
		Set<String> words = new HashSet<String>();
		for(String word: negateSequence(text)){
			if(features.contains(word)){
				words.add(word);
			}
		}

		if(words.size() == 0) return true;

		double posProb = 0.0;
		double negProb = 0.0;
		for(String word : words){
			if(baseModel.getPos().get(word)!=null){
				posProb += Math.log((baseModel.getPos().get(word) + 1)*1.0 / (2*baseModel.getTotals()[0]));
			}else{
				posProb += Math.log(1.0 / (2*baseModel.getTotals()[0]));
			}

			if(baseModel.getNeg().get(word)!=null){
				negProb += Math.log((baseModel.getNeg().get(word) + 1)*1.0 / (2*baseModel.getTotals()[1]));
			}else{
				negProb += Math.log(1.0 / (2*baseModel.getTotals()[1]));
			}
		}
		return posProb > negProb;
	}


	public ClassifiedText classifyText(String text){
		String sentimentClass = "";

		Set<String> words = new HashSet<String>();
		for(String word: negateSequence(text)){
			if(model.getPos().keySet().contains(word) || model.getNeg().keySet().contains(word)){
				words.add(word);
			}
		}

		if(words.size() == 0) sentimentClass = "Neutral";

		double posProb = 0.0;
		double negProb = 0.0;
		for(String word : words){
			if(model.getPos().get(word) != null)
				posProb += Math.log((model.getPos().get(word) + 1)*1.0 / (2*model.getTotals()[0]));
			else
				posProb += Math.log(1.0 / (2*model.getTotals()[0]));
			if(model.getNeg().get(word) != null)
				negProb += Math.log((model.getNeg().get(word) + 1)*1.0 / (2*model.getTotals()[1]));
			else
				negProb += Math.log(1.0 / (2*model.getTotals()[1]));
		}

		if(posProb > negProb) {
			sentimentClass = "Positive";
		}else if(posProb < negProb) {
			sentimentClass = "Negative";
		}
		else
			sentimentClass = "Neutral";

		return new ClassifiedText(text,posProb,negProb,sentimentClass);
	}

	private double getMi(String word, NaiveBayesClassificationModel model) {
		Map<String, Integer> pos = model.getPos();
		Map<String, Integer> neg = model.getNeg();
		int[] totals = model.getTotals();

		int T = totals[0] + totals[1];
		int W = pos.get(word)!=null?pos.get(word):0 + (neg.get(word)!=null?neg.get(word):0);
		double I = 0.0;
		if(W == 0)
			return 0;

		if(model.getNeg().get(word)!=null && model.getNeg().get(word) > 0){
			// doesn't occur in -ve
			I += (totals[1] - neg.get(word) / T * Math.log((totals[1] - neg.get(word) )* T*1.0 / (T - W) / totals[1]));
			//occurs in -ve
			I += (neg.get(word) / T * Math.log((neg.get(word) )* T*1.0 / (T - W) / totals[1]));
		}

		if(model.getPos().get(word)!=null && model.getPos().get(word) > 0){
			// doesn't occur in +ve
			I += (totals[0] - pos.get(word) / T * Math.log((totals[0] - pos.get(word) )* T*1.0 / (T - W) / totals[0]));
			//occurs in +ve
			I += (pos.get(word) / T * Math.log((pos.get(word) )* T*1.0 / (T - W) / totals[0]));
		}
		return I;
	}

	/*public static void main(String args[]) throws ClassNotFoundException, IOException{
		NaiveBayesClassifier nbClassifier = new NaiveBayesClassifier();
		ClassifiedText cWord = nbClassifier.classifyText(args[0]);
		System.out.println("word:"+cWord.getText());
		System.out.println("pos prob:"+cWord.getPosProbability());
		System.out.println("neg prob:"+cWord.getNegProbability());
		System.out.println("sentiment class:"+cWord.getSentimentClass());
	}*/
}
