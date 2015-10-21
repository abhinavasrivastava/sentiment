package com.sentiment.processor;

import java.util.ArrayList;
import java.util.HashSet;

public class RemoveRepeatedCharacters
{

	public static HashSet<String> removeRepeated(String tweetWord){
		tweetWord = tweetWord + " ";
		HashSet<String> refactoredWord = new HashSet<String>();
		String refactoredWord1=""; 
		String refactoredWord2 = "";
		char ch1,ch2;

		int count = 0;
		for(int i=0; i<tweetWord.length()-1; i++)
		{
			ch1=tweetWord.charAt(i);
			ch2=tweetWord.charAt(i+1);
			if (ch1 == ch2){
				count++;
				continue;
			}

			if(ch1!=ch2 && count == 0){
				refactoredWord1 = refactoredWord1 + ch1;
				refactoredWord2 = refactoredWord2 + ch1;
			}
			else if (count == 1){
				refactoredWord1 = refactoredWord1 + ch1 + ch1;
				refactoredWord2 = refactoredWord2 + ch1 + ch1;
				count = 0;
			}
			else{
				refactoredWord1 = refactoredWord1 + ch1;
				refactoredWord2 = refactoredWord2 + ch1 + ch1;
				count = 0;
			}
		}
		refactoredWord.add(refactoredWord1);
		refactoredWord.add(refactoredWord2);
		return refactoredWord;
	}

	public static void main(String args[])
	{		
		ArrayList<String> list = new ArrayList<String>(RemoveRepeatedCharacters.removeRepeated("coooool"));
		for (String name: list)
			System.out.println(name);
		list = new ArrayList<String>(RemoveRepeatedCharacters.removeRepeated("baaaad"));
		for (String name: list)
			System.out.println(name);
	}
}