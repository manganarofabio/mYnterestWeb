package server;

public class News {
	
	private String url;
	private String topic;
	private String source;
	
	
	public News(String url, String topic) {
		super();
		this.url = url;
		this.topic = topic;
		//this.source = source;
		
		if(url.contains("repubblica"))	{
			this.source="LaRepubblica.it";
		}
		if(url.contains("corriere"))	{
			this.source="Corriere.it";
		}
		if(url.contains("corrieredellosport"))	{
			this.source="CorriereDelloSport.it";
		}
		if(url.contains("lastampa"))	{
			this.source="LaStampa.it";
		}
		if(url.contains("ilsole24ore"))	{
			this.source="IlSole24Ore.com";
		}
		if(url.contains("avvenire"))	{
			this.source="Avvenire.it";
		}
		if(url.contains("ilsecoloxix"))	{
			this.source="IlSecoloXIX.it";
		}
		if(url.contains("ansa"))	{
			this.source="Ansa.it";
		}
		if(url.contains("gazzetta"))	{
			this.source="LaGazzettaDelloSport.it";
		}
		if(url.contains("ilfattoquotidiano"))	{
			this.source="IlFattoQuotidiano.it";
		}
		if(url.contains("huffingtonpost"))	{
			this.source="Huffingtonpost.it";
		}
		if(url.contains("ws3.class.it"))	{
			this.source="ItaliaOggi.it";
		}
	
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getTopic() {
		return topic;
	}


	public void setTopic(String topic) {
		this.topic = topic;
	}


	public String getSource() {
		return source;
	}


	public void setSource(String source) {
		this.source = source;
	};
	
	

}
