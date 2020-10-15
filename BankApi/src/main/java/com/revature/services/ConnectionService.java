package com.revature.services;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionService {
	private static Connection connection;
	
	public static Connection getConnection() {
		if (connection == null) {
			try {
				FileInputStream fis = new FileInputStream("connection.properties");
				Properties prop = new Properties();
				prop.load(fis);
				
				connection = DriverManager.getConnection(prop.getProperty("url"), 
						prop.getProperty("username"), prop.getProperty("password"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return connection;
	}
	
	public static void closeConnection() {
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
