
package NaiveBayesClassifier;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe che rappresenta i testi 
 * utilizzati per il training e per la predizione 
 * 
 */
public class Document {

	public Map<String, Integer> tokens;

	public String category;

	/** costruttore **/
	public Document() {
		tokens = new HashMap<>();
	}
}
