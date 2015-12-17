package server;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;



public class ReadMain implements Runnable{
	
	final static int TIMER = 4*1000;  //tempo che il server aspetta prima di aggiornare le notizie
	
	static NewsCollector nc;

	static Connection con;

	static ArrayList<News> myNews = new ArrayList<News>();

	
	
	//-----MAIN----
	//public static void main(String[] args) throws ClassNotFoundException, SQLException, ParseException, InterruptedException {
		

		
		
	
	
	@Override
	public void run() {
		
		
	

		
		myNews = UrlSetter.setUrl();
		
		File f = new File ("mynterest.db");

		while(true)	{  //ciclo 


			//connessione db
			if(!f.exists()){
				try {
					con = DB.createDB();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}	  
			else {
				try {
					Class.forName("org.sqlite.JDBC");
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					con = DriverManager.getConnection("jdbc:sqlite:mynterest.db");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			/*String templateDel= "Delete from News";

			PreparedStatement statDel=con.prepareStatement(templateDel);

			statDel.executeUpdate();*/


			for( News news : myNews){

				nc = new NewsCollector(con,news);

				try {
					nc.newsCollect();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}  


			StoreFeed sf = new StoreFeed(con);
			
			try {
				sf.deleteOldNews();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				Thread.sleep(TIMER);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
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
