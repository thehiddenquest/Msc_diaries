package JDBC_text.maven_JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class sqlQuery {
	private DatabaseConnection DC = DatabaseConnection.createInstance();
	private Connection connection = DC.createConnection();
	private int ID = 0; // Initial value

	private ArrayList<TOA> toaList = null;
	public ArrayList<TOA> getData() {
		toaList = new ArrayList<>();
		String query = "SELECT * FROM testt";

		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				// Retrieve data from the result set
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String gender = resultSet.getString("gender");
				TOA toa = new TOA(id, name, gender);
				toaList.add(toa);
				ID = id;

			}
			return toaList;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception in Query processing");
			return null;
		}
	}
	public void setDataBase(String name, String gender) {
		String query = "INSERT INTO testt (id,name, gender) VALUES (?, ?, ?)";
		try {
			// Create a PreparedStatement
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, ++ID);
			preparedStatement.setString(2, name);
			preparedStatement.setString(3, gender);
			// Execute the INSERT statement
			int rowsAffected = 0;
			try {
				rowsAffected = preparedStatement.executeUpdate();
			} catch (SQLException e) {
				System.out.println("Primary key overlap");

			}

			// Check if insertion was successful
			if (rowsAffected > 0) {
				System.out.println("Data uploaded successfully.");
			} else {
				System.out.println("Failed to upload data.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public void closeConnection() {
		DC.stopConnection();
	}
}
