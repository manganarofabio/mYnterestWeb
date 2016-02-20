package server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Calendar;

import javax.xml.stream.XMLStreamException;


/**
 * Classe per la raccolta delle notizie dai relativi URL 
 *
 */
public class NewsCollector {

	final static int DELETEBEFORE = -2;
	final static long TWODAYSMILLIS = 1000*60*60*24*2;
	private Connection con;
	private News news;



	/** costruttore **/
	public NewsCollector(Connection con, News news) {
		super();
		this.con = con;
		this.news = news;
	}



	/** metodo che raccoglie le notizie **/
	public String newsCollect() throws SQLException, XMLStreamException, InterruptedException, ParseException, IOException{

		Feed feed;

		try	{
			RSSFeedParser parser = new RSSFeedParser(news.getUrl(), news.getTopic(), news.getSource());
			feed = parser.readFeed();

		}
		catch (XMLStreamException e)	{
			/* in caso di eccezione attendiamo 10s e ripetiamo */
			Thread.sleep(1000*10);
			RSSFeedParser parser = new RSSFeedParser(news.getUrl(), news.getTopic(), news.getSource());
			feed = parser.readFeed();

		}
		String curTopic = null;

		StoreFeed sf = new StoreFeed(con);

		/* scorriamo tutte le notizie all'interno di un feed */	    
		if(feed != null)	{
			for (FeedMessage message : feed.getMessages()) { 
				
				Timestamp tmp = new Timestamp(System.currentTimeMillis() - TWODAYSMILLIS);
				
				/* se la notizia è più vecchia di 2 giorni la salviamo ma non aggiorniamo curTopic */
				if (sf.storeNews(message) && Conversion.dateConvert(message.getPubDate()).after(tmp))	{
					curTopic = news.getTopic(); 

				}
			}
		}
		System.out.println(curTopic);  

		return curTopic;

	}

	/** metodo che elimina le notizie meno recenti secondo la variabile DELETEBEFORE **/
	public void deleteOldNews () throws SQLException	{



		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, DELETEBEFORE);
		long x = cal.getTimeInMillis();



		String templateCheck = "Delete from News where date < ?";
		PreparedStatement statCheck = con.prepareStatement(templateCheck);
		statCheck.setLong(1, x);

		statCheck.executeUpdate();
		statCheck.close();

	}

}
