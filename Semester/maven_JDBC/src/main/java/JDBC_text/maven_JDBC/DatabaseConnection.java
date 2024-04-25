package JDBC_text.maven_JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	private String jdbcUrl = "jdbc:mysql://localhost:3306/rahultest";

	// Database credentials
	private String username = "root";
	private String password = "root";
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
			return null;
		}
	}

	public void stopConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
