package server;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



/**
 * Classe per l'invio delle email di notifica
 *
 */
public class SendEmail {

	/** metodo che invia le email contenenti i topic aggiornati **/
	public static void send(String to, TopicArray newTopics, Connection con) throws SQLException {

		ArrayList<String> myTopics = new ArrayList<String>();

		String templateGet = "Select topicID from Follow where email=?";
		PreparedStatement statGet = con.prepareStatement(templateGet);

		statGet.setString(1, to);

		ResultSet rs = statGet.executeQuery();

		while(rs.next()){
			myTopics.add(rs.getString("topicID"));
		}
		statGet.close();

		TopicArray toSend = new TopicArray();

		for (String t : newTopics)	{
			if(myTopics.contains(t))	{
				toSend.add(t);
			}
		}


		if (!toSend.isEmpty())	{
			final String username = "mynterestwebapp@gmail.com";
			final String password = "matteofabiomynterest";

			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");

			Session session = Session.getInstance(props,
					new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});

			try {

				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress("mynterestwebapp@gmail.com"));  	
				message.setRecipients(Message.RecipientType.TO,                    
						InternetAddress.parse(to));				
				message.setSubject("Ci sono nuove notizie per te!");   
				message.setText("Sono state aggiunte le ultime notizie nei topic:\n"
						+ toSend.toString());

				Transport.send(message);

				System.out.println("Email inviata");

			} catch (MessagingException e) {

				throw new RuntimeException(e);
			}
		}
	}

}

