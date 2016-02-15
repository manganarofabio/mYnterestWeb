package NaiveBayesClassifier;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class TrainingFile {
	
	
	
	public static HashMap<String,File> Create () throws ClassNotFoundException, SQLException, IOException	{
	
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
		
	
		
		Class.forName("org.sqlite.JDBC");
		Connection con = DriverManager.getConnection("jdbc:sqlite:mynterest.db");
		
		String templateSelect = "select title, description, link from News where topic = ?";//modello di query
		PreparedStatement statSelect = con.prepareStatement(templateSelect);
		
		for(String key : fileList.keySet()){
			
			System.out.println(key);
			
			statSelect.setString(1, key);
			ResultSet rs = statSelect.executeQuery();
			
			 File f = fileList.get(key);
			 
				// if file doesnt exists, then create it
			 if (!f.exists()) {
				 f.createNewFile();


				 fw = new FileWriter(f, true);
				 bw = new BufferedWriter(fw);
				 pw = new PrintWriter(bw);

				 while(rs.next()){

					 //System.out.println(rs.getString("title") + " " + rs.getString("description") + " ");
					 //System.out.println("---------------------------------------------------------------\n");
					 pw.println(rs.getString("title") + " " + rs.getString("description") + " " + rs.getString("link"));



				 }

				 pw.close();
				 bw.close();
				 fw.close();
			 }
			 //fw.close();
			 //bw.close();
			 //pw.close();
			 


		}

		return fileList;
	}


}
	
