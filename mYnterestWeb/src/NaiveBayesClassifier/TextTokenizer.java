
package NaiveBayesClassifier;


import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Classe usata per ottenere i token e memorizzarli come Document objects.
 * 
 * 
 **/
public class TextTokenizer {

	/** 
	 * metodo per preprocessare il testo rimuovendo punteggiatura, spazi duplicati e renderlo minuscolo.
	 * 
	 **/ 
	public static String preprocess(String text) {
		return text.replaceAll("\\p{P}", " ").replaceAll("\\s+", " ").toLowerCase(Locale.getDefault());
	}

	/**
	 * metodo per estrarre le parole chiave dal testo.
	 * 
	 **/
	public static String[] extractKeywords(String text) {
		return text.split(" ");
	}

	/**
	 * metodo per contare il numero di occorrenze delle parole chiave all'interno del testo
	 *  
	 */
	public static Map<String, Integer> getKeywordCounts(String[] keywordArray) {
		Map<String, Integer> counts = new HashMap<>();

		Integer counter;
		for(int i=0;i<keywordArray.length;++i) {
			counter = counts.get(keywordArray[i]);
			if(counter==null) {
				counter=0;
			}
			counts.put(keywordArray[i], ++counter); 
		}

		return counts;
	}

	/**
	 * 
	 * metodo per ottenere i token e ritorna un Document Object
	 * 
	 */
	public static Document tokenize(String text) {
		String preprocessedText = preprocess(text);
		String[] keywordArray = extractKeywords(preprocessedText);

		Document doc = new Document();
		doc.tokens = getKeywordCounts(keywordArray);
		return doc;
	}
}
