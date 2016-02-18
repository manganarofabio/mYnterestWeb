package beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import server.FeedMessage;


/**
 * Classe per la visualizzazione delle news nei file .jsp
 *
 */
public class NewsView {

	/* variabile che rappresenta il limite di news da visualizzare */
	final int NUMBEROFNEWS = 5;


	/** metodo che ritorna i topic preferiti dell'utente **/ 
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


	/** metodo che ritorna le news di un topic **/
	public ArrayList<FeedMessage> getNews(String topic, boolean allNews) throws ClassNotFoundException, SQLException{



		ArrayList<FeedMessage> messList = new ArrayList<FeedMessage>();


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

			FeedMessage message = new FeedMessage();

			message.setTitle(rs.getString("title"));

			message.setDescription(rs.getString("description"));

			message.setLink(rs.getString("link"));

			message.setSource(rs.getString("sources"));

			message.setPubDate(rs.getTimestamp("date").toString());

			messList.add(message);


		}

		statGet.close();
		con.close();


		return messList;


	}


}
