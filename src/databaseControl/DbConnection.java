package databaseControl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import properties.Properties;

public class DbConnection {
	
	private static final String JDBC_URL = "JDBC_URL";
    
    private static final String JDBC_DRIVER_CLASS = "com.mysql.jdbc.Driver";
    
    private static final String JDBC_USERNAME = "JDBC_USERNAME";
    
    private static final String JDBC_PASSWORD = "JDBC_PASSWORD";
    
    public Connection getConnection() throws SQLException, ClassNotFoundException{
    	Properties properties = Properties.getInstance();
        Class.forName(JDBC_DRIVER_CLASS);
        Connection conn = (Connection) DriverManager.getConnection(properties.getProperty(JDBC_URL), properties.getProperty(JDBC_USERNAME), properties.getProperty(JDBC_PASSWORD));
        return conn;
    }
    
}
