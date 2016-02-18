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




/**
 * Classe che implementa l'avvio del server che si occupa del download
 * dei feed Rss e della loro gestione
 *
 */
public class ReadMain implements Runnable {


	/* tempo che il server aspetta prima di aggiornare le notizie == 1 giorno */
	final static int TIMER = 24*60*60*1000;  

	static NewsCollector nc;

	static Connection con;

	static ArrayList<News> myNews = new ArrayList<News>();



	/* metodo che avvia il server */
	public void run(){

		myNews = UrlSetter.setUrl();

		File f = new File ("mynterest.db");

		TopicArray newTopics = new TopicArray();

		String curTopic;

		/* ciclo di aggiornamento delle notizie */
		while(true)	{  

			newTopics.clear();


			curTopic = null;

			/* connessione al db per aggiornamento/inserimento notizie */

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




			/* ciclo per l'estrazione delle news dagli URL */
			for( News news : myNews){  

				curTopic = null;


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
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if(curTopic != null && !newTopics.contains(curTopic))	{

					newTopics.add(curTopic);
				}


			} 

			/* creazione training files per il Naive Bayes Classifier */

			HashMap<String,File> fileList = new HashMap<String,File>();
			try {
				fileList = TrainingFile.Create(con);
			} catch (ClassNotFoundException | SQLException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			/* individuazione topic delle notizie senza topic */

			NaiveBayesTool nbT = new NaiveBayesTool(fileList);
			NaiveBayes nb = null;
			try {
				nb = new  NaiveBayes(nbT.train());
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}



			String templateSelect = "select title, description, link from News where topic is null";


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

				/* aggiornamento news con topic predetto */
				while(rs.next()){

					String templateUpdate = "update News set topic = ? where link = ?";

					PreparedStatement statUpdate = con.prepareStatement(templateUpdate);
					topic = nb.predict(rs.getString("title")+ " " + rs.getString("description")+ " " + rs.getString("link"));

					statUpdate.setString(1,topic);
					statUpdate.setString(2, rs.getString("link"));


					statUpdate.execute();
					statUpdate.close();

				}
			} catch (IllegalArgumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}



			try {
				statSelect.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}



			/* selezione degli utenti a cui inviare email per notifica di nuove notizie nei topic preferiti */
			String templateCheck = "Select email from Users where notification=1";


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
						SendEmail.send(rs1.getString(1), newTopics, con);
					}
				} catch (SQLException e) {

					e.printStackTrace();
				}
			}
			try {
				statCheck.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			/* rimozione notizie meno recenti */

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

				/* attesa del server prima di un nuovo aggiornamento delle notizie */
				Thread.sleep(TIMER);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}


	}




} 
