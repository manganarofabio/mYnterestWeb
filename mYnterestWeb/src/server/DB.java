package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * Classe per la creazione del db in cui memorizzare notizie e utenti
 *
 */
public class DB {

	/** metodo che crea il db e le tabelle **/
	public static Connection createDB () throws SQLException, ClassNotFoundException	{

		

		Class.forName("org.sqlite.JDBC");
		Connection con = DriverManager.getConnection("jdbc:sqlite:mynterest.db");

		Statement stat = con.createStatement();
		stat.executeUpdate("create table Users (email varchar PRIMARY KEY,"
				+ "password varchar,"
				+ "notification BOOLEAN)");

		stat.executeUpdate("create table News (title varchar, description varchar, link varchar PRIMARY KEY, topic varchar, sources varchar, date timestamp)");

		stat.executeUpdate("create table Topics(topicID varchar PRIMARY KEY)");

		stat.executeUpdate("create table Follow(email varchar REFERENCES Users(email), topicID varchar REFERENCES Topics(topicID), PRIMARY KEY(email, topicID))");

		stat.execute("insert into Topics (topicID) values('sport')");

		stat.execute("insert into Topics (topicID) values('politica')");

		stat.execute("insert into Topics (topicID) values('cronaca')");

		stat.execute("insert into Topics (topicID) values('economia')");

		stat.execute("insert into Topics (topicID) values('scienze')");

		stat.execute("insert into Topics (topicID) values('esteri')");

		stat.close();

		return con;



	}

}
