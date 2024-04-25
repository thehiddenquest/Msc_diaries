package respository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import transfer_object.UserTO;

public class MySQLRepository implements StorageRepository {
	UserTO objto = null;
	private DatabaseConnection DC = DatabaseConnection.createInstance();
	Connection connection = null;
	public void store(Object obj) {
			connection = DC.createConnection();
			objto = (UserTO)obj;
			setDataBase();
			DC.stopConnection();
	}
	public UserTO restore(String username) {
		connection = DC.createConnection();
		getData(username);
		DC.stopConnection();
		return objto;

	}
	private void getData(String username) {
		String query = "SELECT * FROM userlogin WHERE UserName = \""+username+"\"";
		
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				// Retrieve data from the result set
				String emailID = resultSet.getString("EmailAddress");
				String salt = resultSet.getString("Salt");
				String hashedpassword = resultSet.getString("HashedPassword");
				String usernameget = resultSet.getString("UserName");
				objto = new UserTO(emailID,hashedpassword,salt,usernameget);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception in Query processing");
		}
	}
	private void setDataBase() {
		String query = "INSERT INTO userlogin (EmailAddress,Salt, HashedPassword, UserName) VALUES (?, ?, ?, ?)";
		try {
			// Create a PreparedStatement
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, objto.getEmailID());
			preparedStatement.setString(2, objto.getSalt());
			preparedStatement.setString(3, objto.GetHashedPassword());
			preparedStatement.setString(4, objto.getUserName());
			// Execute the INSERT statement
			int rowsAffected = 0;
			try {
				rowsAffected = preparedStatement.executeUpdate();
			} catch (SQLException e) {
				System.out.println("Primary key overlap");
				e.printStackTrace();

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
	@Override
	public Object getConnection(String filename) {
		// TODO Auto-generated method stub
		return null;
	}

}
