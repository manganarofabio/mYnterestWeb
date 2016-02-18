package NaiveBayesClassifier;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * Classe che implementa la forma base del Multinomial Naive Bayes Text Clfiassier come descritto al link: 
 * http://blog.datumbox.com/machine-learning-tutorial-the-naive-bayes-text-classifier/
 * 
 */
public class NaiveBayes {

	/* equivalente al valore di probabilità 0.001 usato nell'algoritmo di selezione delle feature */
	private double chisquareCriticalValue = 10.83;

	private NaiveBayesKnowledgeBase knowledgeBase;

	/** costruttore utilizzato quando carichiamo un classifier già "allenato" **/
	public NaiveBayes(NaiveBayesKnowledgeBase knowledgeBase) {
		this.knowledgeBase = knowledgeBase;
	}

	/** costruttore utilizzato quando dobbiamo allenare un nuovo classifier **/
	public NaiveBayes() {
		this(null);
	}


	public NaiveBayesKnowledgeBase getKnowledgeBase() {

		return knowledgeBase;
	}


	public double getChisquareCriticalValue() {

		return chisquareCriticalValue;
	}

	public void setChisquareCriticalValue(double chisquareCriticalValue) {
		this.chisquareCriticalValue = chisquareCriticalValue;
	}



	/** metodo che preprocessa il dataset originale e lo converte come List<Document> **/
	private List<Document> preprocessDataset(Map<String, String[]> trainingDataset) {
		List<Document> dataset = new ArrayList<>();

		String category;
		String[] examples;

		Document doc;

		Iterator<Map.Entry<String, String[]>> it = trainingDataset.entrySet().iterator();


		while(it.hasNext()) {
			Map.Entry<String, String[]> entry = it.next();
			category = entry.getKey();
			examples = entry.getValue();

			for(int i=0;i<examples.length;++i) {

				doc = TextTokenizer.tokenize(examples[i]);
				doc.category = category;
				dataset.add(doc);


			}


		}

		return dataset;
	}


	/** metodo che raccoglie il numero richiesto di features e svolge la funzione di selezione su tale numero **/
	private FeatureStats selectFeatures(List<Document> dataset) {        
		FeatureExtraction featureExtractor = new FeatureExtraction();

		FeatureStats stats = featureExtractor.extractFeatureStats(dataset); 

		Map<String, Double> selectedFeatures = featureExtractor.chisquare(stats, chisquareCriticalValue);

		Iterator<Map.Entry<String, Map<String, Integer>>> it = stats.featureCategoryJointCount.entrySet().iterator();
		while(it.hasNext()) {
			String feature = it.next().getKey();

			if(selectedFeatures.containsKey(feature)==false) {

				it.remove();
			}
		}

		return stats;
	}


	/** metodo che "allena" il Naive Bayes classifier usando il Multinomial Model passandogli il trainingDataset e le probabilità a priori **/
	public void train(Map<String, String[]> trainingDataset, Map<String, Double> categoryPriors) throws IllegalArgumentException {

		List<Document> dataset = preprocessDataset(trainingDataset);

		FeatureStats featureStats =  selectFeatures(dataset);

		knowledgeBase = new NaiveBayesKnowledgeBase();
		knowledgeBase.n = featureStats.n; 
		knowledgeBase.d = featureStats.featureCategoryJointCount.size();


		if(categoryPriors == null) { 

			knowledgeBase.c = featureStats.categoryCounts.size(); 
			knowledgeBase.logPriors = new HashMap<>();

			String category;
			int count;
			for(Map.Entry<String, Integer> entry : featureStats.categoryCounts.entrySet()) {
				category = entry.getKey();
				count = entry.getValue();

				knowledgeBase.logPriors.put(category, Math.log((double)count/knowledgeBase.n));
			}
		}
		else {

			knowledgeBase.c = categoryPriors.size();

			if(knowledgeBase.c != featureStats.categoryCounts.size()) {
				throw new IllegalArgumentException("Invalid priors Array: Make sure you pass a prior probability for every supported category.");
			}

			String category;
			Double priorProbability;
			for(Map.Entry<String, Double> entry : categoryPriors.entrySet()) {
				category = entry.getKey();
				priorProbability = entry.getValue();
				if(priorProbability == null) {
					throw new IllegalArgumentException("Invalid priors Array: Make sure you pass a prior probability for every supported category.");
				}
				else if(priorProbability<0 || priorProbability>1) {
					throw new IllegalArgumentException("Invalid priors Array: Prior probabilities should be between 0 and 1.");
				}

				knowledgeBase.logPriors.put(category, Math.log(priorProbability));
			}
		}

		Map<String, Double> featureOccurrencesInCategory = new HashMap<>();

		Integer occurrences;
		Double featureOccSum;
		for(String category : knowledgeBase.logPriors.keySet()) {
			featureOccSum = 0.0;
			for(Map<String, Integer> categoryListOccurrences : featureStats.featureCategoryJointCount.values()) {
				occurrences = categoryListOccurrences.get(category);
				if(occurrences != null) {
					featureOccSum+=occurrences;
				}
			}
			featureOccurrencesInCategory.put(category, featureOccSum);
		}


		String feature;
		Integer count;
		Map<String, Integer> featureCategoryCounts;
		double logLikelihood;
		for(String category : knowledgeBase.logPriors.keySet()) {
			for(Map.Entry<String, Map<String, Integer>> entry : featureStats.featureCategoryJointCount.entrySet()) {
				feature = entry.getKey();
				featureCategoryCounts = entry.getValue();

				count = featureCategoryCounts.get(category);
				if(count == null) {
					count = 0;
				}

				logLikelihood = Math.log((count + 1.0)/(featureOccurrencesInCategory.get(category) + knowledgeBase.d));
				if(knowledgeBase.logLikelihoods.containsKey(feature) == false) {
					knowledgeBase.logLikelihoods.put(feature, new HashMap<String, Double>());
				}
				knowledgeBase.logLikelihoods.get(feature).put(category, logLikelihood);
			}
		}
		featureOccurrencesInCategory = null;
	}

	/** metodo che abilita la valutazione delle probabilità a priori basate sul campione **/
	public void train(Map<String, String[]> trainingDataset) {
		train(trainingDataset, null);
	}


	/** metodo che predice la categoria di un testo usando un classificatore già allenato e ritorna la sua categoria **/
	public String predict(String text) throws IllegalArgumentException {
		if(knowledgeBase == null) {
			throw new IllegalArgumentException("Knowledge Bases missing: Make sure you train first a classifier before you use it.");
		}


		Document doc = TextTokenizer.tokenize(text);


		String category;
		String feature;
		Integer occurrences;
		Double logprob;

		String maxScoreCategory = null;
		Double maxScore=Double.NEGATIVE_INFINITY;


		for(Map.Entry<String, Double> entry1 : knowledgeBase.logPriors.entrySet()) {
			category = entry1.getKey();
			logprob = entry1.getValue(); 


			for(Map.Entry<String, Integer> entry2 : doc.tokens.entrySet()) {
				feature = entry2.getKey();

				if(!knowledgeBase.logLikelihoods.containsKey(feature)) {
					continue; 
				}

				occurrences = entry2.getValue(); 

				logprob += occurrences*knowledgeBase.logLikelihoods.get(feature).get(category); 
			}


			if(logprob > maxScore) {
				maxScore=logprob;
				maxScoreCategory=category;
			}
		}

		return maxScoreCategory; 
	}

}
