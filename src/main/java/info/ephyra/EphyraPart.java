package info.ephyra;

import info.ephyra.io.MsgPrinter;
import info.ephyra.nlp.LingPipe;
import info.ephyra.nlp.NETagger;
import info.ephyra.nlp.OpenNLP;
import info.ephyra.nlp.SnowballStemmer;
import info.ephyra.nlp.StanfordNeTagger;
import info.ephyra.nlp.StanfordParser;
import info.ephyra.nlp.indices.FunctionWords;
import info.ephyra.nlp.indices.IrregularVerbs;
import info.ephyra.nlp.indices.Prepositions;
import info.ephyra.nlp.indices.WordFrequencies;
import info.ephyra.nlp.semantics.ontologies.WordNet;
import info.ephyra.questionanalysis.QuestionInterpreter;

/**
 * <code>OpenEphyra</code> is an open framework for question answering (QA).
 * Here, we use only some of the original OpenEphyra functionality.
 * 
 * @author Nico Schlaefer
 *         Leonid Boytsov extracted some question analysis code.
 */
public class EphyraPart {
	/**
	 * <p>Initializes some OpenEphyra components.</p>
	 * 
	 * <p>For use as an API.</p>
	 * 
	 * @param dir directory of Ephyra
	 */
	public static void initBasic(String dir) {		
		MsgPrinter.printInitializing();
		
		// create tokenizer
		MsgPrinter.printStatusMsg("Creating tokenizer...");
		if (!OpenNLP.createTokenizer(dir +
				"res/nlp/tokenizer/opennlp/EnglishTok.bin.gz"))
			MsgPrinter.printErrorMsg("Could not create tokenizer.");
		
		// create sentence detector
		MsgPrinter.printStatusMsg("Creating sentence detector...");
		if (!OpenNLP.createSentenceDetector(dir +
				"res/nlp/sentencedetector/opennlp/EnglishSD.bin.gz"))
			MsgPrinter.printErrorMsg("Could not create sentence detector.");
		LingPipe.createSentenceDetector();
		
		// create stemmer
		MsgPrinter.printStatusMsg("Creating stemmer...");
		SnowballStemmer.create();
		
		// create part of speech tagger
		MsgPrinter.printStatusMsg("Creating POS tagger...");
		if (!OpenNLP.createPosTagger(
				dir + "res/nlp/postagger/opennlp/tag.bin.gz",
				dir + "res/nlp/postagger/opennlp/tagdict"))
			MsgPrinter.printErrorMsg("Could not create OpenNLP POS tagger.");
		
		// create chunker
		MsgPrinter.printStatusMsg("Creating chunker...");
		if (!OpenNLP.createChunker(dir +
				"res/nlp/phrasechunker/opennlp/EnglishChunk.bin.gz"))
			MsgPrinter.printErrorMsg("Could not create chunker.");
		
		// create syntactic parser
		MsgPrinter.printStatusMsg("Creating Stanford syntactic parser...");

		try {
			StanfordParser.initialize();
		} catch (Exception e) {
			MsgPrinter.printErrorMsg("Could not create Stanford parser.");
		}
		
		// create named entity taggers
		MsgPrinter.printStatusMsg("Creating NE taggers...");
		NETagger.loadListTaggers(dir + "res/nlp/netagger/lists/");
		NETagger.loadRegExTaggers(dir + "res/nlp/netagger/patterns.lst");
		MsgPrinter.printStatusMsg("  ...loading models");

		if (!StanfordNeTagger.isInitialized() && !StanfordNeTagger.init())
			MsgPrinter.printErrorMsg("Could not create Stanford NE tagger.");
		MsgPrinter.printStatusMsg("  ...done");
				
		// create WordNet dictionary
		MsgPrinter.printStatusMsg("Creating WordNet dictionary...");
		if (!WordNet.initialize(dir +
				"res/ontologies/wordnet/file_properties.xml"))
			MsgPrinter.printErrorMsg("Could not create WordNet dictionary.");
		
		// load function words (numbers are excluded)
		MsgPrinter.printStatusMsg("Loading function verbs...");
		if (!FunctionWords.loadIndex(dir +
				"res/indices/functionwords_nonumbers"))
			MsgPrinter.printErrorMsg("Could not load function words.");
		
		// load prepositions
		MsgPrinter.printStatusMsg("Loading prepositions...");
		if (!Prepositions.loadIndex(dir +
				"res/indices/prepositions"))
			MsgPrinter.printErrorMsg("Could not load prepositions.");
		
		// load irregular verbs
		MsgPrinter.printStatusMsg("Loading irregular verbs...");
		if (!IrregularVerbs.loadVerbs(dir + "res/indices/irregularverbs"))
			MsgPrinter.printErrorMsg("Could not load irregular verbs.");
		
		// load word frequencies
		MsgPrinter.printStatusMsg("Loading word frequencies...");
		if (!WordFrequencies.loadIndex(dir + "res/indices/wordfrequencies"))
			MsgPrinter.printErrorMsg("Could not load word frequencies.");
		
		// load question patterns
		MsgPrinter.printStatusMsg("Loading question patterns...");
		if (!QuestionInterpreter.loadPatterns(dir +
				"res/patternlearning/questionpatterns/"))
			MsgPrinter.printErrorMsg("Could not load question patterns.");
	}	
}
