package com.sentiment.processor;

import java.util.HashMap;

public class TweetSlangSubstitution {
	static HashMap<String, String> slangs = new HashMap<String, String>();

	static{
		slangs.put("b", "be");
		slangs.put("b4", "before");
		slangs.put("2", "to");	
		slangs.put("b/c", "because");
		slangs.put("abt", "about");
		slangs.put("fab", "fabulous");
		slangs.put("k", "ok");
		slangs.put("tbh", "to be honest");
		slangs.put("u", "you");
		slangs.put("biz", "business");
		slangs.put("bff", "best friends forever");
		slangs.put("wtv", "whatever");
		slangs.put("woz", "was");
		slangs.put("fyi", "for your information");
		slangs.put("gf", "girlfriend");
		slangs.put("bf", "boyfriend");
		slangs.put("fr", "for");
		slangs.put("imo", "in my opinion");
		slangs.put("l8r", "later");
		slangs.put("lmao", "laughing my ass off");
		slangs.put("lol", "laughing out loud");
		slangs.put("omg", "oh my god");
		slangs.put("ooc", "out of character");
		slangs.put("pov", "point of view");
		slangs.put("rofl", "rolling on the floor laughing");
		slangs.put("thx", "thanks");
		slangs.put("ty", "thank you");
		slangs.put("tyvm", "thank you very much");
		slangs.put("wtf", "what the fuck");
	}

	public static String replaceSlang(String word){
		String replacedWord = word;
		if(slangs.get(word.replace(".", ""))!=null){
			replacedWord = slangs.get(word);
		}
		return replacedWord;
	}

	public static void main(String args[]){
		System.out.println(TweetSlangSubstitution.replaceSlang("abt"));
	}
}
