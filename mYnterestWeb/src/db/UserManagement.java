package db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.jasypt.util.password.StrongPasswordEncryptor;



/**
 * Classe per la gestione degli Utenti
 *
 */
public class UserManagement {

	/** metodo che verifica se un utente è presente nel db **/
	public static boolean checkUser(String email) throws Throwable{

		Class.forName("org.sqlite.JDBC");
		Connection con = DriverManager.getConnection("jdbc:sqlite:mynterest.db");


		String templateCheck = "select * from Users where email = ?";

		PreparedStatement statCheck = con.prepareStatement(templateCheck);

		statCheck.setString(1, email);

		ResultSet rs = statCheck.executeQuery();

		if(rs.next()){

			statCheck.close();
			con.close();
			return true;    

		}
		else{
			statCheck.close();
			con.close();
			return false;
		}
	}


	/** metodo per la creazione dell'Utente **/
	public static boolean createtUser (String email, String password, boolean flagEmail) throws Throwable	{

		Class.forName("org.sqlite.JDBC");
		Connection con = DriverManager.getConnection("jdbc:sqlite:mynterest.db");

		if(!checkUser(email)){

			String templateCreate = "insert into Users (email, password, notification) VALUES (?,?,?)";

			PreparedStatement statCreate = con.prepareStatement(templateCreate);

			statCreate.setString(1,email);
			statCreate.setBoolean(3, flagEmail);

			/* criptazione della password immessa */

			StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
			String encryptedPassword = passwordEncryptor.encryptPassword(password);


			statCreate.setString(2,encryptedPassword);
			statCreate.execute();
			
			System.out.println(email + " creato con successo");

			statCreate.close();
			con.close();
			return true;
		}

		else {

			String templateUpdate = "update Users set notification = ? where email = ?";
			PreparedStatement statUpdate = con.prepareStatement(templateUpdate);

			statUpdate.setBoolean(1, flagEmail);
			statUpdate.setString(2,email);

			statUpdate.execute();
			statUpdate.close();
			con.close();
			return true;
		}

	}



	/** metodo per l'autenticazione dell'Utente **/
	public static boolean logInUser(String email, String password) throws ClassNotFoundException, SQLException{  //da ricordare la gestione degli errori

		ResultSet rs;

		Class.forName("org.sqlite.JDBC");
		Connection con = DriverManager.getConnection("jdbc:sqlite:mynterest.db");


		String templateCheck = "select * from Users where email = ?";
		PreparedStatement statCheck = con.prepareStatement(templateCheck);
		statCheck.setString(1,email);

		rs = statCheck.executeQuery();
		if(rs.next()){

			//System.out.println(rs.getString("password"));

			/* decriptazione della password immessa */
			StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();


			if(passwordEncryptor.checkPassword(password, rs.getString("password"))){ 
				statCheck.close();
				con.close();
				
				return true;  
			}


		}

		statCheck.close();
		con.close();	
		return false;  
	}

	/** metodo per la selezione dei topics preferiti dell'Utente **/
	public static boolean addTopics(String email, ArrayList<String> topicsList) throws SQLException, ClassNotFoundException	{


		Class.forName("org.sqlite.JDBC");
		Connection con = DriverManager.getConnection("jdbc:sqlite:mynterest.db");


		String templateDeleteF = "delete from Follow where email = ?";
		PreparedStatement statDeleteF = con.prepareStatement(templateDeleteF);
		statDeleteF.setString(1, email);

		statDeleteF.executeUpdate();
		statDeleteF.close();


		for(String topic : topicsList){

			String templateInsert = "insert into Follow(email, topicID) values(?,?)";
			PreparedStatement statInsert = con.prepareStatement(templateInsert);


			statInsert.setString(1,email);
			statInsert.setString(2,topic);  

			statInsert.execute();
			statInsert.close();


		}

		con.close();
		return true;

	}

	/** metodo per l'eliminazione di un Utente **/
	public static boolean deleteUser(String email, String password) throws SQLException, IOException{

		try {
			if(logInUser(email,password)){

				Class.forName("org.sqlite.JDBC");
				Connection con = DriverManager.getConnection("jdbc:sqlite:mynterest.db");

				String templateDelete = "delete from Users where email = ?";
				PreparedStatement statDelete = con.prepareStatement(templateDelete);
				statDelete.setString(1,email);

				String templateDeleteF = "delete from Follow where email = ?";
				PreparedStatement statDeleteF = con.prepareStatement(templateDeleteF);
				statDeleteF.setString(1, email);



				if(statDelete.executeUpdate()!= 0){ 

					statDeleteF.executeUpdate();

					statDeleteF.close();
					statDelete.close();
					con.close();
					return true;

				}


				else{  

					statDelete.close();
					con.close();
					return false;
				}

			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return false;

	}


}
