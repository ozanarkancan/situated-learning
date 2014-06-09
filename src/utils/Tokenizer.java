package utils;

import java.io.StringReader;
import java.util.List;

import edu.berkeley.nlp.tokenizer.PTBTokenizer;

//Penn Treebank Tokenizer

public class Tokenizer {
	public static List<String> tokenize(String raw){
		PTBTokenizer tokenizer = new edu.berkeley.nlp.tokenizer.PTBTokenizer();
		tokenizer.setSource(new StringReader(raw));
		return tokenizer.tokenize();
	}
	
	public static String clean(String raw){
		return list2String(tokenize(raw));
	}
	
	private static String list2String(List<String> tokens){
		String str = "";
		for(String token : tokens)
			str += token + " ";
		return str.trim();
	}
}
