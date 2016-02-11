/* 
 * Copyright (C) 2014 Vasilis Vryniotis <bbriniotis at datumbox.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package NaiveBayesClassifier;

import java.util.HashMap;
import java.util.Map;

/**
 * The NaiveBayesKnowledgeBase Object stores all the fields that the classifier
 * learns during training.
 * 
 * @author Vasilis Vryniotis <bbriniotis at datumbox.com>
 * @see <a href="http://blog.datumbox.com/developing-a-naive-bayes-text-classifier-in-java/">http://blog.datumbox.com/developing-a-naive-bayes-text-classifier-in-java/</a>
 */
public class NaiveBayesKnowledgeBase {
    /**
     * number of training observations
     */
    public int n=0;
    
    /**
     * number of categories
     */
    public int c=0;
    
    /**
     * number of features
     */
    public int d=0;
    
    /**
     * log priors for log( P(c) )
     */
    public Map<String, Double> logPriors = new HashMap<>();
    
    /**
     * log likelihood for log( P(x|c) ) 
     */
    public Map<String, Map<String, Double>> logLikelihoods = new HashMap<>();
}
