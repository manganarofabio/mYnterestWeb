
package NaiveBayesClassifier;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe utilizzate per impostare e allenare il classifier
 * 
 */
public class NaiveBayesTool {

	private HashMap<String,File> fileList;


	public NaiveBayesTool(HashMap<String, File> fileList) {
		super();
		this.fileList = fileList;
	}


	/** metodo che legge tutte le linee di un file e le raccoglie in un array di String **/ 
	public static String[] readLines(URL url) throws IOException {

		Reader fileReader = new InputStreamReader(url.openStream(), Charset.forName("UTF-8"));
		List<String> lines;
		try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
			lines = new ArrayList<>();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				lines.add(line);
			}
		}
		return lines.toArray(new String[lines.size()]);
	}



	/** metodo che fornisce la base allenata per il classificatore **/
	public  NaiveBayesKnowledgeBase train() throws IOException {




		Map<String, URL> trainingFiles = new HashMap<>();

		for(String key : fileList.keySet()){

			System.out.println(fileList.get(key).toURI().toURL());

			trainingFiles.put(key, fileList.get(key).toURI().toURL());
		}





		Map<String, String[]> trainingExamples = new HashMap<>();
		for(Map.Entry<String, URL> entry : trainingFiles.entrySet()) {
			trainingExamples.put(entry.getKey(), readLines(entry.getValue()));
		}

		/* allena il classificatore */
		NaiveBayes nb = new NaiveBayes();
		nb.setChisquareCriticalValue(6.63); //0.01 valore di probabilità 
		nb.train(trainingExamples);


		NaiveBayesKnowledgeBase knowledgeBase = nb.getKnowledgeBase();

		nb = null;
		trainingExamples = null;

		return knowledgeBase;
	}

}
