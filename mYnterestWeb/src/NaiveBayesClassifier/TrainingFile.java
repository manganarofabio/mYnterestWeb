package NaiveBayesClassifier;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;


/**
 * Classe che imposta i file
 *  per il training del classificatore
 *
 */
public class TrainingFile {

	/** metodo che crea i file di training all'avvio del server **/
	public static HashMap<String,File> Create (Connection con) throws ClassNotFoundException, SQLException, IOException	{

		BufferedWriter bw = null;
		FileWriter fw = null;
		PrintWriter pw = null;

		HashMap<String,File> fileList = new HashMap<String,File>();

		File fileSport = new File("trainingSport.txt");
		File fileCronaca = new File("trainingCronaca.txt");
		File fileEconomia = new File("trainingEconomia.txt");
		File filePolitica = new File("trainingPolitica.txt");
		File fileScienze = new File("trainingScienze.txt");
		File fileEsteri = new File("trainingEsteri.txt");

		fileList.put("sport", fileSport);
		fileList.put("cronaca", fileCronaca);
		fileList.put("economia", fileEconomia);
		fileList.put("politica", filePolitica);
		fileList.put("scienze", fileScienze);
		fileList.put("esteri", fileEsteri);




		for(String key : fileList.keySet()){

			File f = fileList.get(key);
			if (!f.exists()) {
				f.createNewFile();



				String templateSelect = "select title, description, link from News where topic = ?";
				PreparedStatement statSelect = con.prepareStatement(templateSelect);

				statSelect.setString(1, key);
				ResultSet rs = statSelect.executeQuery();

				fw = new FileWriter(f, true);
				bw = new BufferedWriter(fw);
				pw = new PrintWriter(bw);

				while(rs.next()){

					pw.println(rs.getString("title") + " " + rs.getString("description") + " " + rs.getString("link"));

				}

				pw.close();
				bw.close();
				fw.close();

				statSelect.close();
			}


		}

		return fileList;
	}


}
	

