package NaiveBayesClassifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * Classe utilizzata per generare i FeatureStats Object dal dataset e per
 * svolgere funzioni di selezione utilizzando il Chisquare test
 * 
 */
public class FeatureExtraction {

	/**
	 * metodo che genera FeatureStats Object  con le metriche che riguardano le occorrenze
	 * delle parole chiave nelle categorie, il numero delle categorie e il numero totale delle osservazioni.
	 * Queste statistiche sono utilizzate dall'algoritmo di selezione.
	 * 
	 *
	 */
	public FeatureStats extractFeatureStats(List<Document> dataset) {
		FeatureStats stats = new FeatureStats();

		Integer categoryCount;
		String category;
		Integer featureCategoryCount;
		String feature;
		Map<String, Integer> featureCategoryCounts;
		for(Document doc : dataset) {
			/* incrementa il numero di osservazioni */
			++stats.n;
			category = doc.category;


			/* incrementa ilnumero di categorie */
			categoryCount = stats.categoryCounts.get(category);
			if(categoryCount==null) {
				stats.categoryCounts.put(category, 1);
			}
			else {
				stats.categoryCounts.put(category, categoryCount+1);
			}

			for(Map.Entry<String, Integer> entry : doc.tokens.entrySet()) {
				feature = entry.getKey();

				/* ritorna il numero di feature nelle categorie */
				featureCategoryCounts = stats.featureCategoryJointCount.get(feature);
				if(featureCategoryCounts==null) { 
					/* lo inizzializza se non esiste */
					stats.featureCategoryJointCount.put(feature, new HashMap<String, Integer>());
				}

				featureCategoryCount=stats.featureCategoryJointCount.get(feature).get(category);
				if(featureCategoryCount==null) {
					featureCategoryCount=0;
				}

				/* incrementa il numero di occorrenze delle feature nella categoria */
				stats.featureCategoryJointCount.get(feature).put(category, ++featureCategoryCount);
			}
		}

		return stats;
	}

	/**
	 * metode che svolge la selezione di feaure utilizzando il test statistico non parametrico Chisquare
	 * 
	 * 
	 */
	public Map<String, Double> chisquare(FeatureStats stats, double criticalLevel) {
		Map<String, Double> selectedFeatures = new HashMap<>();

		String feature;
		String category;
		Map<String, Integer> categoryList;

		int N1dot, N0dot, N00, N01, N10, N11;
		double chisquareScore;
		Double previousScore;
		for(Map.Entry<String, Map<String, Integer>> entry1 : stats.featureCategoryJointCount.entrySet()) {
			feature = entry1.getKey();
			categoryList = entry1.getValue();


			N1dot = 0;
			for(Integer count : categoryList.values()) {
				N1dot+=count;
			}


			N0dot = stats.n - N1dot;

			for(Map.Entry<String, Integer> entry2 : categoryList.entrySet()) {
				category = entry2.getKey();
				N11 = entry2.getValue(); 
				N01 = stats.categoryCounts.get(category)-N11;                 
				N00 = N0dot - N01; 
				N10 = N1dot - N11; 

				/* calcola il punteggio Chisquare sulla base delle statistiche rilevate */
				chisquareScore = stats.n*Math.pow(N11*N00-N10*N01, 2)/((N11+N01)*(N11+N10)*(N10+N00)*(N01+N00));

				/* se il punteggio è maggiore del valore critico allora lo aggiunge alla lista */
				if(chisquareScore>=criticalLevel) {
					previousScore = selectedFeatures.get(feature);
					if(previousScore==null || chisquareScore>previousScore) {
						selectedFeatures.put(feature, chisquareScore);
					}
				}
			}
		}

		return selectedFeatures;
	}
}

