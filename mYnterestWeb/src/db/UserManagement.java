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



public class UserManagement {
	
	
	
	public static boolean createtUser (String email, String password) throws ClassNotFoundException, SQLException	{
		
		Class.forName("org.sqlite.JDBC");
		Connection con = DriverManager.getConnection("jdbc:sqlite:mynterest.db");
		
		
		String templateCheck = "select * from Users where email=?";//modello di query
		String templateCreate = "insert into Users (email, password) VALUES (?,?)";

		PreparedStatement statCheck = con.prepareStatement(templateCheck);
		PreparedStatement statCreate = con.prepareStatement(templateCreate);
		statCheck.setString(1, email);
		
		
		
		ResultSet rs = statCheck.executeQuery();
		
		 if(rs.next()){
			 
			 statCheck.close();
			 con.close();
			 return false;    //ritorna falso se l'utente esiste già altrimenti lo crea e torna true
			 
		 }
		 else {
			 statCreate.setString(1,email);
			 statCreate.setString(2,password);
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
		
			
			//l'utente esiste quindi controllo la password
			if(rs.getString("password").equals(password)){
				
				con.close();
				return true;  //utente verificato
			}
			    
			
		}
		con.close();	
		return false;  //utente non esitente oppure password sbagliata
	}
	
	public static boolean addTopics(String email, String topics) throws SQLException, ClassNotFoundException	{
		
		
		Class.forName("org.sqlite.JDBC");
		Connection con = DriverManager.getConnection("jdbc:sqlite:mynterest.db");
		
		
		
		String templateCheck = "update Users set topic=? where email=?";
		PreparedStatement statCheck = con.prepareStatement(templateCheck);
		statCheck.setString(1,topics);
		statCheck.setString(2,email);
		
		
		if(statCheck.executeUpdate()!=0)	{
			return true;
		}
		
		else 
			return false;
		
	}
	
	
	
	/*public boolean deleteUserDb(User u) throws SQLException, IOException{
		
		//System.out.println("delete ok");
		int rs;
		Connection con = MyConnection.connectToUtenti();
		
		String templateDelete = "delete from Users where name=?";
		PreparedStatement statDelete= con.prepareStatement(templateDelete);
		statDelete.setString(1,u.getName());
		rs = statDelete.executeUpdate();
		
		
		if(rs == 1){ //utente cancellato dal db ->elimino la cartella
			
			//System.out.println("delete ok");
			
			
		
			File[] roots = File.listRoots();  //valido per tutti gli os
			
			File dir = new File(roots[0] + "InterestOf" + u.getName());
			
			Path path = Paths.get(roots[0] + "InterestOf" + u.getName());
			
			if(Files.exists(path, LinkOption.NOFOLLOW_LINKS)){
				
							FileUtils.forceDelete(dir);
							System.out.println("cartella eliminata");
							
							con.close();
							return true;
							
			}
			
			
			else{   //cartella già eliminata
				con.close();
				return true;
			}
			
		}
		
		con.close();
		return false;
	}
	
	
	

    */


}
