package Model.main_package;

import org.mindrot.jbcrypt.BCrypt;

public class encryptPassword {

	public HashedPasswordWithSalt hashPassword(String password) {
		String salt = BCrypt.gensalt();
		String hashedPassword = BCrypt.hashpw(password, salt);
		return new HashedPasswordWithSalt(hashedPassword, salt);
	}
	
	public HashedPasswordWithSalt hashPassword(String password, String salt) {
		String hashedPassword = BCrypt.hashpw(password, salt);
		return new HashedPasswordWithSalt(hashedPassword, salt);
	}
	public boolean authenticate(String hashedPassword, String newPassword, String salt) {
	    // Hash the new password with the provided salt
	    String hashedNewPassword = BCrypt.hashpw(newPassword, salt);
	    
	    // Compare the hashed new password with the provided hashed password
	    return hashedPassword.equals(hashedNewPassword);
	}
	public boolean authenticate(String sendPassword, String recievedPassword) {
	    // Hash the new password with the provided salt
		String salt = BCrypt.gensalt();
	    String hashedSendPassword = BCrypt.hashpw(sendPassword, salt);
	    String hashedRecievedPassword = BCrypt.hashpw(recievedPassword, salt);
	    // Compare the hashed new password with the provided hashed password
	    return hashedSendPassword.equals(hashedRecievedPassword);
	}

	public static class HashedPasswordWithSalt {
		private final String hashedPassword;
		private final String salt;

		public HashedPasswordWithSalt(String hashedPassword, String salt) {
			this.hashedPassword = hashedPassword;
			this.salt = salt;
		}

		public String getHashedPassword() {
			return hashedPassword;
		}

		public String getSalt() {
			return salt;
		}
	}
}
