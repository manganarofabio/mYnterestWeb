package server;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.xml.stream.XMLStreamException;




public class ReadMain implements Runnable {
	
	final static int TIMER = 10*1000;  //tempo che il server aspetta prima di aggiornare le notizie
	
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
				
				
			}
			
			//in newTopics abbiamo tutti i topic per i quali, nell'iterazione corrente, è stata aggiunta almeno una notiza
			String templateCheck = "Select email, topic from Users where notification=1";
			PreparedStatement statCheck = null;
			try {
				statCheck = con.prepareStatement(templateCheck);
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
			ResultSet rs = null;
			try {
				rs = statCheck.executeQuery();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
			if (!newTopics.isEmpty()){
				try {
					while(rs.next())	{
						System.out.println(rs.getString(1));
						
											
							SendEmail.send(rs.getString(1), rs.getString(2), newTopics);
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
