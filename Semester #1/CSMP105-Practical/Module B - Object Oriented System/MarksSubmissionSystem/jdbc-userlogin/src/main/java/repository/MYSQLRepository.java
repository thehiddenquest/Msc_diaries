package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.SQLException;
import transfer_object.UserTO;

public class MYSQLRepository<T> extends storageRepository<T> {

	private final DatabaseConnection DC = DatabaseConnection.createInstance();
	private Connection connection = null;

	@Override
	public boolean store(T item) {
		connection = DC.createConnection();
		if (item instanceof UserTO) {
			UserTO user = (UserTO) item;
			boolean flag = insertUser(user.getEmailID(), user.getSalt(), user.getPassword(), user.getUsername());
			DC.stopConnection();
			return flag;
		}
		DC.stopConnection();
		return false;
	}

	@Override
	public T retrive(T item) {
		connection = DC.createConnection();
		if (item instanceof UserTO) {
			UserTO user = (UserTO) item;
			T returnUser = authenticateUser(user.getUsername());
			DC.stopConnection();
			return returnUser;
		}
		DC.stopConnection();
		return null;
	}

	@Override
	public boolean remove(T item) {
		connection = DC.createConnection();
		if (item instanceof UserTO) {
			UserTO user = (UserTO) item;
			boolean flag = deleteUser(user.getUsername());
			DC.stopConnection();
			return flag;
		}
		DC.stopConnection();
		return false;
	}

	private boolean insertUser(String... user) {
		String query = "INSERT INTO userlogin (EmailAddress,Salt, HashedPassword, UserName) VALUES (?, ?, ?, ?)";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, user[0]);
			preparedStatement.setString(2, user[1]);
			preparedStatement.setString(3, user[2]);
			preparedStatement.setString(4, user[3]);

			int rowsAffected = 0;
			try {
				rowsAffected = preparedStatement.executeUpdate();
			} catch (SQLException e) {
				System.out.println("Primary key overlap");
				e.printStackTrace();
			}

			if (rowsAffected > 0) {
				// System.out.println("Data uploaded successfully.");
				return true;
			} else {
				// System.out.println("Failed to upload data.");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private T authenticateUser(String userName) {
		String query = "SELECT * FROM userlogin WHERE UserName = ?";
		UserTO user = null;
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, userName);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				String emailAddress = resultSet.getString("EmailAddress");
				String salt = resultSet.getString("Salt");
				String hashedPassword = resultSet.getString("HashedPassword");
				userName = resultSet.getString("UserName");
				user = new UserTO(userName, hashedPassword, salt, emailAddress);
			}
			return (T) user;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private boolean deleteUser(String userName) {
			String query = "DELETE FROM userlogin WHERE UserName = ?";
			UserTO user = null;
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(query);
		    	preparedStatement.setString(1, userName);
		    	int rowsAffected = 0;
				try {
					rowsAffected = preparedStatement.executeUpdate();
				} catch (SQLException e) {
					System.out.println("Primary key overlap");
					e.printStackTrace();
				}

				if (rowsAffected == 1) {
					// System.out.println("Data uploaded successfully.");
					return true;
				} else {
					// System.out.println("Failed to upload data.");
					return false;
				}
			}catch(Exception e) {
				e.printStackTrace();
				return false;
			}
	}

}
