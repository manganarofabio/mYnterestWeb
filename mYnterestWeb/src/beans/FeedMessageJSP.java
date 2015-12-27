package beans;

import java.sql.Timestamp;

public class FeedMessageJSP {
	
	String Title;
	String Description;
	String Link;
	String source;
	Timestamp date;
	
	
	

	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getLink() {
		return Link;
	}
	public void setLink(String link) {
		Link = link;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	
	
	public String toString(){
		
		return Title;
	}
	
	

}
