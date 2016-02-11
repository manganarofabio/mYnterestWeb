package server;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.stream.XMLStreamException;

import NaiveBayesClassifier.NaiveBayes;
import NaiveBayesClassifier.NaiveBayesTool;
import NaiveBayesClassifier.TrainingFile;




public class ReadMain implements Runnable {
	
	final static int TIMER =20000;  //tempo che il server aspetta prima di aggiornare le notizie
	
	static NewsCollector nc;

	static Connection con;

	static ArrayList<News> myNews = new ArrayList<News>();

	
	
	//public static void main(String[] args) throws ClassNotFoundException, SQLException, ParseException, InterruptedException, XMLStreamException {
	
	public void run(){
		
		myNews = UrlSetter.setUrl();
		
		File f = new File ("mynterest.db");
		
		TopicArray newTopics = new TopicArray();

		String curTopic;
		
		while(true)	{  //ciclo 

			newTopics.clear();
			
			//System.out.println(newTopics.toString());
			
			curTopic = null;
			
			//connessione db
			
			if(!f.exists()){
				try {
					con = DB.createDB();
				} catch (ClassNotFoundException e) {
					
					e.printStackTrace();
					
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			}	  
			else {
				try {
					Class.forName("org.sqlite.JDBC");
				} catch (ClassNotFoundException e1) {
					
					e1.printStackTrace();
				}
				try {
					con = DriverManager.getConnection("jdbc:sqlite:mynterest.db");
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			}

	
			
			
			
			for( News news : myNews){  //scorriamo tutti gli url dei feed

				curTopic = null;
				
				//System.out.println("siamo qui" + curTopic);
				nc = new NewsCollector(con,news);
				

				try {
					curTopic = nc.newsCollect();
				} catch (SQLException e) {
					
					e.printStackTrace();
				} catch (ParseException e) {
					
					e.printStackTrace();
					
									
				} catch (XMLStreamException e) {
					
					e.printStackTrace();
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
				
				if(curTopic != null && !newTopics.contains(curTopic))	{
					
					//System.out.println("siamo qui" + curTopic);
					
					newTopics.add(curTopic);
				}
				
				
			} //fine for
			
			//CREAZIONE TRAINING FILE
			
			HashMap<String,File> fileList = new HashMap<String,File>();
			try {
				fileList = TrainingFile.Create();
			} catch (ClassNotFoundException | SQLException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			//INDIVIDUAZIONE TOPIC
			
			//select delle notizie con topic null
			
			NaiveBayesTool nbT = new NaiveBayesTool(fileList);
			NaiveBayes nb = null;
			try {
				nb = new  NaiveBayes(nbT.train());
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			

			String templateSelect = "select title, description, link from News where topic is null";//modello di query
			String templateUpdate = "update News set topic = ? where link = ?";
			
			PreparedStatement statUpdate = null;
			
			PreparedStatement statSelect = null;
			try {
			 statSelect = con.prepareStatement(templateSelect);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			ResultSet rs = null;
			try {
				 rs = statSelect.executeQuery();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String topic;
			 try {
				statUpdate = con.prepareStatement(templateUpdate);
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			try {
				while(rs.next()){
					
					topic = nb.predict(rs.getString("title")+ " " + rs.getString("description"));
					
					statUpdate.setString(1,topic);
					statUpdate.setString(2, rs.getString("link"));
					
					statUpdate.execute();
					
				}
			} catch (IllegalArgumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			//chiusura prepared statement
			
			try {
				statSelect.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				statUpdate.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			
			
			//in newTopics abbiamo tutti i topic per i quali, nell'iterazione corrente, è stata aggiunta almeno una notiza
			String templateCheck = "Select email, topic from Users where notification=1";
			PreparedStatement statCheck = null;
			try {
				statCheck = con.prepareStatement(templateCheck);
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
			ResultSet rs1 = null;
			try {
				rs1 = statCheck.executeQuery();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
			if (!newTopics.isEmpty()){
				try {
					while(rs1.next())	{
						System.out.println(rs1.getString(1));
						
											
							//SendEmail.send(rs.getString(1), rs.getString(2), newTopics);
					}
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			}
			
		  //nonostante noi prendiamo solo notizie recenti di 2 giorni, quelle vecchie rimangono nel db, e vanno rimosse
			
			try {
				nc.deleteOldNews();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
			
			try {
				con.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
			
			
	      	try {
				Thread.sleep(TIMER);
			} catch (InterruptedException e) {
			
				e.printStackTrace();
			}
		}

	/*
    String templateInsert= "select date from News where topic='politica'";
	PreparedStatement statInsert=con.prepareStatement(templateInsert);
	ResultSet rs = statInsert.executeQuery();
		
	while(rs.next())	{

		System.out.println(rs.getTimestamp("date"));
	}

		
	 String templateInsert1= "SELECT title, date FROM news ORDER BY date DESC";
		PreparedStatement statInsert1=con.prepareStatement(templateInsert1);
		ResultSet rs1 = statInsert1.executeQuery();


		while(rs1.next())	{

			//System.out.println(rs1.getString("title"));
			System.out.println(rs1.getTimestamp("date"));	
		}
		*/
	}
		 
		
	
	
} 
