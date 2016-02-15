package server;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class DB {
	
	
	public static Connection createDB () throws SQLException, ClassNotFoundException	{
		
		//File f = new File("mynterest.db");
	
		Class.forName("org.sqlite.JDBC");
		Connection con = DriverManager.getConnection("jdbc:sqlite:mynterest.db");
		
		Statement stat = con.createStatement();
		stat.executeUpdate("create table Users (email varchar PRIMARY KEY,"
												+ "password varchar,"
												+ "notification BOOLEAN)");
		
		stat.executeUpdate("create table News (title varchar, description varchar, link varchar PRIMARY KEY, topic varchar, sources varchar, date timestamp)");
		
		stat.executeUpdate("create table Topics(topicID varchar PRIMARY KEY)");
		
		stat.executeUpdate("create table Follow(email varchar REFERENCES Users(email), topicID varchar REFERENCES Topics(topicID), PRIMARY KEY(email, topicID))");
		
		stat.execute("insert into Topics (topicID) values('Sport')");
		
		stat.execute("insert into Topics (topicID) values('Politica')");
		
		stat.execute("insert into Topics (topicID) values('Cronaca')");
		
		stat.execute("insert into Topics (topicID) values('Economia')");
		
		stat.execute("insert into Topics (topicID) values('Scienze')");
		
		stat.execute("insert into Topics (topicID) values('Esteri')");
		
		stat.close();
		
		return con;
	
	}

}
