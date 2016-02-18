package NaiveBayesClassifier;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe che memorizza tutti i campi che
 *  il classificatore ha imparato durante l'allenamento 
 */
public class NaiveBayesKnowledgeBase {

	/* numero di osservazioni training */
	public int n=0;

	/* numero di categorie*/
	public int c=0;

	/* numero di features */
	public int d=0;

	public Map<String, Double> logPriors = new HashMap<>();

	public Map<String, Map<String, Double>> logLikelihoods = new HashMap<>();
}
