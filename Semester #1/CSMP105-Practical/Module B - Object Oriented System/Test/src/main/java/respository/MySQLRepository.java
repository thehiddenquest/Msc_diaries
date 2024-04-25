package respository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import transfer_object.PasswordHashRepository;
import transfer_object.UserTO;

public class MySQLRepository implements StorageRepository {
	UserTO objto = null;
	private DatabaseConnection DC = DatabaseConnection.createInstance();
	Connection connection = null;

	public void store(Object obj,String DatabaseName) {
		connection = DC.createConnection(DatabaseName);
		objto = (UserTO) obj;
		setDataBase();
		DC.stopConnection();
	}

	public UserTO restore(String username,String DatabaseName) {
		connection = DC.createConnection(DatabaseName);
		getData(username);
		DC.stopConnection();
		return objto;

	}

	private void getData(String username) {
		String query = "SELECT * FROM userlogin WHERE UserName = \"" + username + "\"";

		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				// Retrieve data from the result set
				String emailID = resultSet.getString("EmailAddress");
				String salt = resultSet.getString("Salt");
				String hashedpassword = resultSet.getString("HashedPassword");
				String usernameget = resultSet.getString("UserName");
				objto = new UserTO(emailID, hashedpassword, salt, usernameget);
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

	public void remove(String userName) {
		connection = DC.createConnection("rahultest");
		if (userName.equalsIgnoreCase("ADMIN")) {
			System.out.println("Operation Not Allowed !");
		} else {
			removeUser(userName);
		}
	}

	private void removeUser(String username) {
		String query = "DELETE FROM userlogin WHERE UserName = \"" + username + "\"";

		try {
			if(connection == null) {
				connection = DC.createConnection("rahultest"); 
			}
			Statement statement = connection.createStatement();
			statement.executeUpdate(query);
			System.out.println("Operation successfull");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception in Query processing");
		}
	}

	@Override
	public Object getConnection(String filename) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getEmail(String userName) {
		String query = "SELECT EmailAddress FROM userlogin WHERE UserName = \"" + userName + "\"";
		Statement statement;
		try {
			if(connection == null) {
				connection = DC.createConnection("rahultest"); 
			}
			statement = connection.createStatement();
			ResultSet rst = statement.executeQuery(query);
			String mail = rst.toString();
			return mail;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public void reset(String userName, String Password) {
		doReset(userName, Password);
	}
	private void doReset(String UserName,String Password) {
		PasswordHashRepository passwordHashRepository = new PasswordHashRepository();
		PasswordHashRepository.HashedPasswordWithSalt hashedPasswordWithSalt = passwordHashRepository.hashPassword(Password);
		String hashedPassword = hashedPasswordWithSalt.getHashedPassword();
        String salt = hashedPasswordWithSalt.getSalt();
        String query = "UPDATE userlogin SET Salt = \"" + salt + "\", HashedPassword = \"" + hashedPassword + "\" WHERE UserName = \"" + UserName + "\"";
		String query2 = "SELECT * FROM userlogin WHERE UserName = \"" + UserName + "\"";
		Statement statement;
		try {
			statement = connection.createStatement();
			int affectedrow = statement.executeUpdate(query);
			System.out.print(affectedrow);
			ResultSet rst = statement.executeQuery(query2);
			while(rst.next()) {
				String emailID = rst.getString("EmailAddress");
				String saltcode = rst.getString("Salt");
				String hashedpassword = rst.getString("HashedPassword");
				String usernameget = rst.getString("UserName");
				System.out.println(emailID + saltcode + hashedpassword + usernameget);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
