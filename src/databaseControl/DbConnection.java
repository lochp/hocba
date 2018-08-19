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
    
    private static DbConnection dbConnection;
    
    public DbConnection() {
    	
    }
    
    public static DbConnection getSingleInstance() {
    	if (dbConnection == null) {
    		dbConnection = new DbConnection();
    	}
    	return dbConnection;
    }
    
    public Connection getConnection() throws SQLException, ClassNotFoundException{
    	Properties properties = Properties.getInstance();
        Class.forName(JDBC_DRIVER_CLASS);
        Connection conn = (Connection) DriverManager.getConnection(properties.getProperty(JDBC_URL), properties.getProperty(JDBC_USERNAME), properties.getProperty(JDBC_PASSWORD));
        return conn;
    }
    
    public Connection getConnection(Boolean autoCommit) throws SQLException, ClassNotFoundException {
    	Properties properties = Properties.getInstance();
        Class.forName(JDBC_DRIVER_CLASS);
        Connection conn = (Connection) DriverManager.getConnection(properties.getProperty(JDBC_URL), properties.getProperty(JDBC_USERNAME), properties.getProperty(JDBC_PASSWORD));
        conn.setAutoCommit(autoCommit);
        return conn;
    }
    
}
