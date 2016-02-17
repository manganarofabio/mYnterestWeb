package server;

import java.util.ArrayList;



public class UrlSetter {
	
	public static ArrayList<News> setUrl ()	{
		
		ArrayList<String> topicNull = new ArrayList<String>();
		topicNull.add("http://www.repubblica.it/rss/homepage/rss2.0.xml?ref=HRF-1");
		topicNull.add("http://www.ilsole24ore.com/rss/notizie/attualita.xml");
		topicNull.add("http://www.avvenire.it/_layouts/Avvenire/feed.aspx?");
		topicNull.add("http://www.ilfattoquotidiano.it/feed/");
		topicNull.add("http://www.ilsecoloxix.it/homepage/rss/homepage.xml");
		topicNull.add("http://www.huffingtonpost.it/feeds/verticals/italy/news.xml");
		
		ArrayList<String> politica = new ArrayList<String>();
		politica.add("http://www.repubblica.it/rss/politica/rss2.0.xml?ref=HRF-1");
		politica.add("http://xml.corriereobjects.it/rss/politica.xml");
		politica.add("http://www.lastampa.it/italia/politica/rss.xml");
		politica.add("http://www.ilsole24ore.com/rss/notizie/politica.xml");
		
		ArrayList<String> cronaca = new ArrayList<String>();
		cronaca.add("http://www.repubblica.it/rss/cronaca/rss2.0.xml?ref=HRF-1");
		cronaca.add("http://xml.corriereobjects.it/rss/cronache.xml");
		cronaca.add("http://www.lastampa.it/italia/cronache/rss.xml");
		//cronaca.add("http://www.ilmattino.it/rss/societa.xml");
		cronaca.add("http://www.ansa.it/sito/notizie/cronaca/cronaca_rss.xml");
		
		
		ArrayList<String> economia = new ArrayList<String>();
		economia.add("http://www.repubblica.it/rss/economia/rss2.0.xml?ref=HRF-1");
		economia.add("http://xml.corriereobjects.it/rss/economia.xml");
		economia.add("http://www.lastampa.it/economia/rss.xml");
		economia.add("http://www.ilsole24ore.com/rss/notizie/politica-economia.xml");
		economia.add("http://ws3.class.it/rssfeed/rss.asmx/RssFeed?tipo=io_artep");
		
		ArrayList<String> sport = new ArrayList<String>();
		sport.add("http://www.repubblica.it/rss/sport/rss2.0.xml?ref=HRF-1");
		sport.add("http://xml.corriereobjects.it/rss/sport.xml");
		sport.add("http://www.lastampa.it/sport/rss.xml");
		sport.add("http://www.corrieredellosport.it/rss/");
		sport.add("http://www.gazzetta.it/rss/home.xml");
		
		ArrayList<String> esteri = new ArrayList<String>();
		esteri.add("http://www.repubblica.it/rss/esteri/rss2.0.xml?ref=HRF-1");
		esteri.add("http://xml.corriereobjects.it/rss/esteri.xml");
		esteri.add("http://www.lastampa.it/esteri/rss.xml");
		esteri.add("http://www.ansa.it/sito/notizie/mondo/mondo_rss.xml");
		
		ArrayList<String> scienze = new ArrayList<String>();
		scienze.add("http://www.repubblica.it/rss/scienze/rss2.0.xml?ref=HRF-1");
		scienze.add("http://xml.corriereobjects.it/rss/scienze.xml");
		scienze.add("http://www.repubblica.it/rss/tecnologia/rss2.0.xml?ref=HRF-1");
		scienze.add("http://www.lastampa.it/tecnologia/rss.xml");
		scienze.add("http://www.ansa.it/sito/notizie/tecnologia/tecnologia_rss.xml");
	
		ArrayList<News> myNews = new ArrayList<News>();
		
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		
		list.add(topicNull);
		list.add(politica);
		list.add(cronaca);
		list.add(economia);
		list.add(sport);
		list.add(scienze);
		list.add(esteri);
		
		for(ArrayList<String> i : list)	{
			for(String j : i)	{
				if (i==politica)	{
					myNews.add(new News(j, "politica"));
					continue;
				}
				if (i==cronaca)	{
					myNews.add(new News(j, "cronaca"));	
					continue;
				}
				if (i==economia)	{
					myNews.add(new News(j, "economia"));	
					continue;
				}
				if (i==sport)	{
					myNews.add(new News(j, "sport"));	
					continue;	
				}
				/*if (i==ambiente)	{
					myNews.add(new News(j, "ambiente"));	
					continue;
				}*/
				/*if (i==animali)	{
					myNews.add(new News(j, "animali"));
					continue;
				}*/
				/*if (i==spettacoli)	{
					myNews.add(new News(j, "spettacoli"));	
					continue;
				}*/
				/*if (i==cultura)	{
					myNews.add(new News(j, "cultura"));	
					continue;
				}*/
				/*if (i==motori)	{
					myNews.add(new News(j, "motori"));
					continue;
				}*/
				if (i==scienze)	{
					myNews.add(new News(j, "scienze"));	
					continue;
				}
				if (i==esteri)	{
					myNews.add(new News(j, "esteri"));	
					continue;
				}
				if(i==topicNull)	{
					myNews.add(new News(j, null));
				}
				
			}
	}
		
		
		return myNews;
	}
	
	
}
