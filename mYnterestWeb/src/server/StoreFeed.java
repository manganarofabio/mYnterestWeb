package server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;



/**
 * Classe per la memorizzazione dei Feed
 *
 */
public class StoreFeed {


	private FeedMessage message;
	private Connection con;
	private ResultSet rs;

	/** costruttore **/
	public StoreFeed (Connection con)	{
		this.con=con;
	}

	/** metodo che memorizza nel db una notizia **/
	public boolean storeNews (FeedMessage message) throws SQLException, ParseException	{

		String templateCheck = "Select link from News where link = ?";
		PreparedStatement statCheck = con.prepareStatement(templateCheck);
		statCheck.setString(1, message.getLink());

		rs = statCheck.executeQuery();

		/* memorizziamo la notizia solo se non è già presente nel db */
		if(!rs.next())	{ 

			if( message.isValid())	{  

				String templateInsert = "insert into News VALUES (?,?,?,?,?,?)";   
				PreparedStatement statInsert=con.prepareStatement(templateInsert);
				statInsert.setString(1,message.getTitle());
				statInsert.setString(2,message.getDescription());
				statInsert.setString(3,message.getLink());
				/* getTopic() torna null se la notizia è senza topic (deve essere analizzata con il NaiveBayesClassifier) */
				statInsert.setString(4,message.getTopic()); 
				statInsert.setString(5,message.getSource());
				statInsert.setTimestamp(6, Conversion.dateConvert(message.getPubDate()));

				statInsert.executeUpdate();

				statInsert.close();
				statCheck.close();

				return true;  
			}

		}
		statCheck.close();
		return false;

	}

}
