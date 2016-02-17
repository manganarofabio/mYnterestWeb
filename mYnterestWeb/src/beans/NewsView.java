package beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NewsView {
	
	final int NUMBEROFNEWS = 5;
	
	
	
	public ArrayList<String> getTopics(String email) throws ClassNotFoundException, SQLException{
		
		ArrayList<String> topicsList = new ArrayList<String>();
		String topic = "";
		
		Class.forName("org.sqlite.JDBC");
		Connection con = DriverManager.getConnection("jdbc:sqlite:mynterest.db");

		String templateCheck = "SELECT topicID FROM Follow where email=?";
		PreparedStatement statCheck = con.prepareStatement(templateCheck);
		statCheck.setString(1, email);
		ResultSet rs = statCheck.executeQuery();
		
		while(rs.next()) {

			topic = rs.getString("topicID");
			topicsList.add(topic);
		}
			
			
			statCheck.close();
			con.close();
			return topicsList;
			
	}
		
		
	
	
/*public List<String> getSources(String email) throws ClassNotFoundException, SQLException{
		
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
*/
	
	
	public ArrayList<FeedMessageJSP> getNews(String topic, boolean allNews) throws ClassNotFoundException, SQLException{
		
		
		
		ArrayList<FeedMessageJSP> messList = new ArrayList<FeedMessageJSP>();
		
		
		Class.forName("org.sqlite.JDBC");
		Connection con = DriverManager.getConnection("jdbc:sqlite:mynterest.db");
		ResultSet rs = null;
		String templateGet = null;
		PreparedStatement statGet = null;
		
		if(!allNews){

		templateGet = "SELECT * FROM News where topic=? ORDER BY date DESC LIMIT ?";
		statGet = con.prepareStatement(templateGet);
		
		
		statGet.setString(1, topic);
		statGet.setInt(2, NUMBEROFNEWS);
		rs = statGet.executeQuery();
		
		}
		
		else{
			
			templateGet = "SELECT * FROM News where topic=? ORDER BY date DESC";
			statGet = con.prepareStatement(templateGet);
			
			
			statGet.setString(1, topic);
			
			rs = statGet.executeQuery();
			
		}
		
		
		
		while(rs.next()){
			
			FeedMessageJSP message = new FeedMessageJSP();
			
			message.setTitle(rs.getString("title"));
			System.out.println(message.getTitle());
			
		
			message.setDescription(rs.getString("description"));
			System.out.println(message.getDescription());
			
		
			message.setLink(rs.getString("link"));
			System.out.println(message.getLink());
			
			
			
			message.setSource(rs.getString("sources"));
			System.out.println(message.getSource());
			
			
			
			message.setDate(rs.getTimestamp("date"));
			System.out.println(message.getDate());
			
			System.out.println("stampo il messaggio: " + message.toString() );
			
			messList.add(message);
			
			
			
			
		}
		
		statGet.close();
		con.close();
		
		System.out.println(topic);
		
	
		
		System.out.println(messList.size());
		for(FeedMessageJSP fm : messList)
			System.out.println(fm.toString());
		
		
		return messList;
		
		
		
		
		
	
	}
	
	
	
	
	
	

}
