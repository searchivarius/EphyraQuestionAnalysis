package info.ephyra;

import info.ephyra.io.MsgPrinter;
import info.ephyra.nlp.StanfordNeTagger;
import info.ephyra.nlp.StanfordParser;
import info.ephyra.nlp.semantics.ontologies.WordNet;

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
		
		// create syntactic parser
		MsgPrinter.printStatusMsg("Creating Stanford syntactic parser...");

		try {
			StanfordParser.initialize();
		} catch (Exception e) {
			MsgPrinter.printErrorMsg("Could not create Stanford parser.");
		}
		
		if (!StanfordNeTagger.isInitialized() && !StanfordNeTagger.init())
			MsgPrinter.printErrorMsg("Could not create Stanford NE tagger.");
		MsgPrinter.printStatusMsg("  ...done");
				
		// create WordNet dictionary
		MsgPrinter.printStatusMsg("Creating WordNet dictionary...");
		if (!WordNet.initialize(dir +
				"ontologies/wordnet/file_properties.xml"))
			MsgPrinter.printErrorMsg("Could not create WordNet dictionary.");
		
	}	
}
