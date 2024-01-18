package dev.bonus.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtil {


	public static Connection getConnection(String url, String database, String user, String password) {
		// 코드 작성 후 App.java에서 호출 가능하도록
		Properties prop = new Properties();
		
		try {

//			final String DB_URL = prop.getProperty(url);
//			final String DATABASE_NAME = prop.getProperty(database);
//			final String USER = prop.getProperty(user);
//			final String PASSWORD = prop.getProperty(password);
			
			return DriverManager.getConnection(url + database, user, password);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return null;
	}
}
