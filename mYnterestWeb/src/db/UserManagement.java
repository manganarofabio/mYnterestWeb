package db;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.jasypt.util.password.StrongPasswordEncryptor;



public class UserManagement {
	
	public static boolean checkUser(String email) throws Throwable{
		
		Class.forName("org.sqlite.JDBC");
		Connection con = DriverManager.getConnection("jdbc:sqlite:mynterest.db");
		
		
		String templateCheck = "select * from Users where email=?";//modello di query
		

		PreparedStatement statCheck = con.prepareStatement(templateCheck);
		
		statCheck.setString(1, email);
		
		
		
		ResultSet rs = statCheck.executeQuery();
		
		 if(rs.next()){
			 
			 statCheck.close();
			 con.close();
			 return true;    //ritorna true se l'utente esiste già altrimenti torna true
			 
		 }
		 else{
			 statCheck.close();
			 con.close();
		 	 return false;
		 }
	}
	
	
	
	public static boolean createtUser (String email, String password) throws Throwable	{
		
		Class.forName("org.sqlite.JDBC");
		Connection con = DriverManager.getConnection("jdbc:sqlite:mynterest.db");
		
		
		
		String templateCreate = "insert into Users (email, password) VALUES (?,?)";

		
		PreparedStatement statCreate = con.prepareStatement(templateCreate);
		
		
		
		
		
		
		 if(UserManagement.checkUser(email)){
			 
			 
			 con.close();
			 return false;    //ritorna falso se l'utente esiste già altrimenti lo crea e torna true
			 
		 }
		 else {
			 statCreate.setString(1,email);
			 
			 //encrypt password
			 
			 StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
			 String encryptedPassword = passwordEncryptor.encryptPassword(password);
			 
			 System.out.println(encryptedPassword);
			 
			 statCreate.setString(2,encryptedPassword);
			 statCreate.execute();
			 	  	
			 statCreate.close();
			 con.close();
			 return true;
		 }
	    
	}
	
	
	
	public static boolean logInUser(String email, String password) throws ClassNotFoundException, SQLException{  //da ricordare la gestione degli errori
		
		ResultSet rs;
		
		Class.forName("org.sqlite.JDBC");
		Connection con = DriverManager.getConnection("jdbc:sqlite:mynterest.db");
		
		
		
		String templateCheck = "select * from Users where email=?";
		PreparedStatement statCheck = con.prepareStatement(templateCheck);
		statCheck.setString(1,email);
		
		rs = statCheck.executeQuery();
		if(rs.next()){
		
			System.out.println(rs.getString("password"));
			//decrypt password
			
			StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
			
			
			//if(rs.getString("password").equals(password))
		 
			//l'utente esiste quindi controllo la password
			
			if(passwordEncryptor.checkPassword(password, rs.getString("password"))){ //ritorna true se le passwords coincidono
				statCheck.close();
				con.close();
				return true;  //utente verificato
			}
			    
			
		}
		
		statCheck.close();
		con.close();	
		return false;  //utente non esitente oppure password sbagliata
	}
	
	public static boolean addTopics(String email, String topics, boolean flagEmail) throws SQLException, ClassNotFoundException	{
		
		
		Class.forName("org.sqlite.JDBC");
		Connection con = DriverManager.getConnection("jdbc:sqlite:mynterest.db");
		
		System.out.println(flagEmail);
		
		
		
		String templateCheck = "update Users set topic=?, notification=? where email=?";
		PreparedStatement statCheck = con.prepareStatement(templateCheck);
		statCheck.setString(1,topics);
		statCheck.setBoolean(2,flagEmail);   //in realtà nel db mette 1 se == true else 0
		statCheck.setString(3,email);
		
		
		
		if(statCheck.executeUpdate()!=0)	{
			//con.close();
			statCheck.close();
			return true;
		}
		
		else{
			con.close();
		
			return false;
		}
		
	}
	
public static boolean addSources(String email, String sources) throws SQLException, ClassNotFoundException	{
		
		
		Class.forName("org.sqlite.JDBC");
		Connection con = DriverManager.getConnection("jdbc:sqlite:mynterest.db");
		
		
		
		
		
		String templateCheck = "update Users set sources=? where email=?";
		PreparedStatement statCheck = con.prepareStatement(templateCheck);
		statCheck.setString(1,sources);
		statCheck.setString(2,email);
		
		
		
		if(statCheck.executeUpdate()!=0)	{
			//con.close();       non so perchè ma se lo chiudiamo non va
			statCheck.close();
			return true;
		}
		
		else {
			con.close();
		
			return false;
		}
		
	}
	
	
	public static boolean deleteUser(String email, String password) throws SQLException, IOException{
		
		try {
			if(logInUser(email,password)){
			
			

				Class.forName("org.sqlite.JDBC");
				Connection con = DriverManager.getConnection("jdbc:sqlite:mynterest.db");
					
				String templateDelete = "delete from Users where email=?";
				PreparedStatement statDelete= con.prepareStatement(templateDelete);
				statDelete.setString(1,email);
				
			
				if(statDelete.executeUpdate()!= 0){ //utente cancellato dal db 
					
					
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
