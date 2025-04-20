package Model.repository;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	
	private final String databaseName = "User";
	private String jdbcUrl = "jdbc:mysql://localhost:****/"+databaseName;
	// Database credentials
	private String username = "****";
	private String password = "****";
	private Connection connection = null;
	private static DatabaseConnection DC = null;
	
	private DatabaseConnection() {
		
	}

	public static DatabaseConnection createInstance() {
		if (DC == null) {
			DC = new DatabaseConnection();
		}
		return DC;
	}

	public Connection createConnection() {
		try {
			connection = DriverManager.getConnection(jdbcUrl, username, password);
			System.out.println("Connection Established Successfully");
			return connection;
		} catch (Exception e) {
			System.out.println("Connection failed to establish");
			e.printStackTrace();
			return null;
		}
	}

	public void stopConnection() {
		try {
			connection.close();
			DC = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

