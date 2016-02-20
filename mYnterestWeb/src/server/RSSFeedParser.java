package server;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;

import org.jsoup.Jsoup;



/**
 * Classe per l'analisi dei feed Rss
 *
 */
public class RSSFeedParser {

	static final String TITLE = "title";
	static final String DESCRIPTION = "description";
	static final String CHANNEL = "channel";
	static final String LANGUAGE = "language";
	static final String COPYRIGHT = "copyright";
	static final String LINK = "link";
	static final String AUTHOR = "author";
	static final String ITEM = "item";
	static final String PUB_DATE = "pubDate";
	static final String GUID = "guid";

	private String topic = "";
	private String source = "";

	private URL url;

	/** costruttore **/
	public RSSFeedParser(String feedUrl, String topic, String source) {
		try {
			this.topic=topic;
			this.source=source;
			this.url = new URL(feedUrl);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}
	/** metodo che legge e analizza il feed **/
	public Feed readFeed() throws XMLStreamException, InterruptedException, IOException {
		Feed feed = null;

		boolean isFeedHeader = true;

		String description = "";
		String title = "";
		String link = "";
		String language = "";
		String copyright = "";
		String pubdate = "";

		XMLInputFactory inputFactory = XMLInputFactory.newInstance();

		InputStream in = read();
		XMLEventReader eventReader = inputFactory.createXMLEventReader(in);

		while (eventReader.hasNext()) {
			XMLEvent event = eventReader.nextEvent();


			if (event.isStartElement()) {



				String localPart = event.asStartElement().getName().getLocalPart();

				switch (localPart) {
				case ITEM:
					if (isFeedHeader) {
						isFeedHeader = false;
						feed = new Feed(title, link, description, language,
								copyright, pubdate);
						System.out.println(feed.toString());
					}

					break;
				case TITLE:
					title = getCharacterData(event, eventReader);
					break;
				case DESCRIPTION:
					description = getCharacterData(event, eventReader);
					break;
				case LINK:
					link = getCharacterData(event, eventReader);
					break;
				case GUID:
					break;
				case LANGUAGE:
					language = getCharacterData(event, eventReader);
					break;
				case AUTHOR:
					break;
				case PUB_DATE:
					pubdate = getCharacterData(event, eventReader);
					break;
				case COPYRIGHT:
					copyright = getCharacterData(event, eventReader);
					break;
				}
			} else if (event.isEndElement()) {


				if (event.asEndElement().getName().getLocalPart() == (ITEM)) {

					FeedMessage message = new FeedMessage();

					message.setDescription(description);

					message.setLink(link);
					message.setTitle(title);

					message.setPubDate(pubdate);
					message.setTopic(topic);
					message.setSource(source);
					feed.getMessages().add(message);
					event = eventReader.nextEvent();
					continue;
				}
			}

		}

		return feed;
	}
	/** metodo che estrae il contenuto del XMLEvent **/
	private String getCharacterData(XMLEvent event, XMLEventReader eventReader)
			throws XMLStreamException {
		String result = "";
		event = eventReader.nextEvent();
		if (event instanceof Characters) {
			result = event.asCharacters().getData();
		}


		return cleanXMLEvent(result);
	}

	/** metodo che apre un InputStream sull' URL **/
	private InputStream read() throws InterruptedException, IOException {
		try {
			return url.openStream();
		} catch (IOException e) {
			System.out.println("exception sleep");
			// Thread.sleep(10000);

			return url.openStream();
		}
	}

	/** metodo per la "pulizia" degli XMLEvent **/
	public String cleanXMLEvent (String result)	{
		String clean = new String();

		clean = Jsoup.parse(result.toString()).text();


		return clean;
	}

} 
