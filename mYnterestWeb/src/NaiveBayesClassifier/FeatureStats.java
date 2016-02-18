package NaiveBayesClassifier;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe che memorizza tutti i campi generati dalla FeatureExtraction
 * 
 */
public class FeatureStats {

	public int n;


	public Map<String, Map<String, Integer>> featureCategoryJointCount;


	public Map<String, Integer> categoryCounts;

	/** costruttore **/
	public FeatureStats() {
		n = 0;
		featureCategoryJointCount = new HashMap<>();
		categoryCounts = new HashMap<>();
	}
}
