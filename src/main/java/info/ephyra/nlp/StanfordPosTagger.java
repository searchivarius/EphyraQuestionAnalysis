package info.ephyra.nlp;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

/**
 * This class provides an interface to the 
 * <a href="http://nlp.stanford.edu/software/tagger.shtml">Stanford part of speech tagger</a>.
 * 
 * @author Manas Pathak, Leonid Boytsov
 * @version 2014-07
 */
public class StanfordPosTagger  {
  static MaxentTagger model;

  public StanfordPosTagger() throws Exception {
    model = new MaxentTagger(MaxentTagger.DEFAULT_JAR_PATH);
    if (model == null) throw new Exception("Cannot init a model from the default location");
	}
	
	/**
	 * Splits the sentence into individual tokens.
	 * 
	 * @param sentence Input sentence
	 * @return Array of tokens
	 */
	public String[] tokenize(String sentence) {
	  List<List<HasWord>> t = MaxentTagger.tokenizeText(new StringReader(sentence));
		
		List<String> tokens = new ArrayList<String>();
		
		for (int j = 0; j < t.size(); j++) {
			for (HasWord w: t.get(j)) {
				tokens.add(w.word());
			}
		}
		
		return (String[]) tokens.toArray(new String[tokens.size()]);
	}
	
	/**
	 * Tags the tokens with part of speech
	 * 
	 * @param tokens Array of token strings
	 * @return Part of speech tags
	 */
	public String[] tagPos(String[] tokens) {
		List<HasWord> untagged = createSentence(tokens);
		List<TaggedWord> tagged = model.tagSentence(untagged);
		
		String[] pos = new String[tagged.size()];
		for (int i = 0; i < tagged.size(); i++) {
			HasWord w = (HasWord) tagged.get(i);
			String[] s = w.toString().split("/");
			if (s.length > 1)
				pos[i] = s[s.length - 1];
			else
				pos[i] = "";
		}
		
		return pos;
	}
	

	private static List<HasWord> createSentence(String[] tokens) {
		ArrayList<HasWord> wordList = new ArrayList<HasWord>();
		
		for (String s : tokens) {
			HasWord w = new Word(s);
			wordList.add(w);
		}
		
		return wordList;
	}
	
	/**
	 * Tags a String with part of speech
	 * 
	 * @param text Input String
	 * @return String tagged with part of speech in "word/tag" notation
	 */
	public String tagPos(String text) {
		String[] tokens = tokenize(text);
		String[] pos = tagPos(tokens);
		
		String output = "";
		
		for (int i = 0; i < pos.length; i++) {
			output += tokens[i] + "/" + pos[i] + " ";
		}
		
		return output.trim();
	}
	
	public static void main(String[] args) throws Exception {
		String s1 = "\"Everything but the kitchen sink\" is an English phrase used to denote wildly exaggerated inclusion. It is used in phrases such as He brought everything but the kitchen sink. See for example this humorous bug report that claims that the web browser Mozilla has everything but a kitchen sink.";
		
		StanfordPosTagger tagger = null;
		try {
		  tagger = new StanfordPosTagger();
		} catch (Exception e) {
		  e.printStackTrace();
		  return;
		}
		
		System.out.println(tagger.tagPos(s1));
		
		String[] tokens = tagger.tokenize(s1);
		String[] pos = tagger.tagPos(tokens);
		for (int i = 0; i < pos.length; i++) {
			System.out.println(tokens[i] + " <- " + pos[i]);
		}
	}
}
