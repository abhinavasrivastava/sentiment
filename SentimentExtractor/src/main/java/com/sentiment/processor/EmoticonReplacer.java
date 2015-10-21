package com.sentiment.processor;

import java.util.HashMap;
import java.util.regex.Pattern;

public class EmoticonReplacer {
	private static final String SPACE_EXCEPTIONS = "\\n\\r";
	public static final String PUNCTUATION_CHAR_CLASS = "\\p{P}\\p{M}\\p{S}" + SPACE_EXCEPTIONS;
	public static final String SPACE_CHAR_CLASS = "\\p{C}\\p{Z}&&[^" + SPACE_EXCEPTIONS + "\\p{Cs}]";
	public static final String SPACE_REGEX = "[" + SPACE_CHAR_CLASS + "]";
	public static final String PUNCTUATION_REGEX = "[" + PUNCTUATION_CHAR_CLASS + "]";
	private static final String EMOTICON_DELIMITER =
			SPACE_REGEX + "|" + PUNCTUATION_REGEX;
	public static final Pattern SMILEY_REGEX_PATTERN = Pattern.compile(":[)DdpP]|:[ -]\\)|<3");
	public static final Pattern FROWNY_REGEX_PATTERN = Pattern.compile(":[(<]|:[ -]\\(");
	
	public static final Pattern EMOTICON_REGEX_PATTERN =
			Pattern.compile("(?<=^|" + EMOTICON_DELIMITER + ")("
					+ SMILEY_REGEX_PATTERN.pattern() + "|" + FROWNY_REGEX_PATTERN.pattern()
					+ ")+(?=$|" + EMOTICON_DELIMITER + ")");
	
	private Pattern regexPattern = EMOTICON_REGEX_PATTERN;

	static HashMap<String, String> smileys = new HashMap<String, String>();

	static {
		smileys.put("&:)", "sad");
		smileys.put("&:O", "sad");
		smileys.put("&:(", "sad");	
		smileys.put(":-)", "happy");
		smileys.put(":)", "happy");
		smileys.put(":D", "happy");
		smileys.put(":o)", "happy");
		smileys.put("8)", "happy");
		smileys.put(";(", "sad");
		smileys.put(":-D", "laugh");
		smileys.put(":'-(", "cry");
		smileys.put(":'(", "cry");
		smileys.put(":-P", "cheeky");
		smileys.put(":P", "cheeky");
		smileys.put(":$", "embarrassed");
		smileys.put("|-O", "bored");
		smileys.put("|;-)", "cool");
	}

	public static String replaceEmoticon(String word){
		String replacedWord = word;
		if(smileys.get(word)!=null){
			replacedWord = smileys.get(word);
		}
		return replacedWord;
	}

	public static void main(String args[]){
		System.out.println(EmoticonReplacer.replaceEmoticon(":)"));
	}

}
