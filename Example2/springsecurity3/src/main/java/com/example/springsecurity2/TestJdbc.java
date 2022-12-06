package it.beije.onlusbackend;

import java.sql.Connection;
import java.sql.DriverManager;
// Connection Tester
public class TestJdbc {

	public static void main(String[] args) {
		 
		String jdbcUrl = "jdbc:mysql://localhost:3306/onlus?useSSL=false";
		//String jdbcUrl = "jdbc:mysql://localhost:3306/<insert_schema>?useSSL=false";
		String user ="root";
		String pass = "beije";
		try {
			System.out.println("Connecting to database: " + jdbcUrl);
			Connection myConn = 
					DriverManager.getConnection(jdbcUrl, user, pass);
			System.out.println("Connection successful!");



			
		}catch(Exception exc) {
			exc.printStackTrace();
		}
		

	}

}
