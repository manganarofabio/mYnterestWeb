package beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import server.FeedMessage;

public class NewsView {
	
	final int NUMBEROFNEWS = 5;
	
	
	
	public List<String> getTopics(String email) throws ClassNotFoundException, SQLException{
		
		List<String> topics = new ArrayList<String>();
		String topic = "";
		
		Class.forName("org.sqlite.JDBC");
		Connection con = DriverManager.getConnection("jdbc:sqlite:mynterest.db");

		String templateCheck = "SELECT topic FROM Users where email=?";
		PreparedStatement statCheck = con.prepareStatement(templateCheck);
		statCheck.setString(1, email);
		ResultSet rs = statCheck.executeQuery();
		
		if(rs.next()) {

			topic = rs.getString("topic");
			topics= Arrays.asList(topic.split("\\s*,\\s*"));
			
			con.close();
			
			return topics;
			
		}
		
		else{
			con.close();
		
			return null;
		}
	}
	
public List<String> getSources(String email) throws ClassNotFoundException, SQLException{
		
		List<String> sources = new ArrayList<String>();
		String source = "";
		
		Class.forName("org.sqlite.JDBC");
		Connection con = DriverManager.getConnection("jdbc:sqlite:mynterest.db");

		String templateCheck = "SELECT sources FROM Users where email=?";
		PreparedStatement statCheck = con.prepareStatement(templateCheck);
		statCheck.setString(1, email);
		ResultSet rs = statCheck.executeQuery();
		
		if(rs.next()) {

			source = rs.getString("sources");
			sources= Arrays.asList(source.split("\\s*,\\s*"));
			
			con.close();
			
			return sources;
			
		}
		
		else{
			con.close();
		
			return null;
		}
	}

	
	
	public ArrayList<FeedMessageJSP> getNews(String topic, String source) throws ClassNotFoundException, SQLException{
		
		
		
		ArrayList<FeedMessageJSP> messList = new ArrayList<FeedMessageJSP>();
		
		
		Class.forName("org.sqlite.JDBC");
		Connection con = DriverManager.getConnection("jdbc:sqlite:mynterest.db");

		String templateGet = "SELECT * FROM News where topic=? AND sources=? ORDER BY date DESC LIMIT ?";
		PreparedStatement statGet = con.prepareStatement(templateGet);
		statGet.setString(1, topic);
		statGet.setString(2, source);
		statGet.setInt(3, NUMBEROFNEWS);
		ResultSet rs = statGet.executeQuery();
		
		
		
		while(rs.next()){
			
			FeedMessageJSP message = new FeedMessageJSP();
			
			message.setTitle(rs.getString("title"));
			System.out.println(message.getTitle());
			
		
			message.setDescription(rs.getString("description"));
			System.out.println(message.getDescription());
			
		
			message.setLink(rs.getString("link"));
			System.out.println(message.getLink());
			
			
			
			//message.setSource(rs.getString("source"));
			//System.out.println(message.getSource());
			
			
			
			message.setDate(rs.getTimestamp("date"));
			System.out.println(message.getDate());
			
			System.out.println("stampo il messaggio: " + message.toString() );
			
			messList.add(message);
			
			
			
			
		}
		
		con.close();
		
		System.out.println(topic);
		
	
		
		System.out.println(messList.size());
		for(FeedMessageJSP fm : messList)
			System.out.println(fm.toString());
		
		
		return messList;
		
		
		
		
		
	
	}
	
	
	
	

}
