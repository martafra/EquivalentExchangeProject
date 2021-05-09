package logic.database;

import java.sql.*;

public class MyConnection {
	
	private static MyConnection instance = null;
	private Connection con;
	private String nomeDB = "db";
	private String portaDB = "3307";
	private String usernameDB = "root";
	private String passwordDB = "passsql";
	private String driver = "com.mysql.cj.jdbc.Driver";

	
	private MyConnection() {
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection("jdbc:mysql://localhost:" + portaDB + "/" + nomeDB, usernameDB,
					passwordDB);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static MyConnection getInstance() {
		
		if(instance == null) {
			instance = new MyConnection();

			
		}
		return instance;	
	}
	
	public Connection getConnection() {
		return con;
		
	}
}