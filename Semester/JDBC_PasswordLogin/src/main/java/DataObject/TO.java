package DataObject;

public class TO {
	private String username;
	private String hashedPassword;
	private String salt;
	private String EmailId;
	private PasswordHashRepository passwordHashRepository = new PasswordHashRepository();
	public TO(String EmailId,String hashedPassword,String salt,String username) {
		this.username = username;
		this.hashedPassword = hashedPassword;
		this.salt = salt;
		this.EmailId = EmailId;
	}
	public TO() {
		// TODO Auto-generated constructor stub
	}
	protected TO newUserAdd(String username,String password,String EmailID) {
		this.username = username;
		PasswordHashRepository.HashedPasswordWithSalt hashedPasswordWithSalt = passwordHashRepository.hashPassword(password);

        // Retrieve the hashed password and salt
        hashedPassword = hashedPasswordWithSalt.getHashedPassword();
        salt = hashedPasswordWithSalt.getSalt();
        this.EmailId = EmailID;
        return this;
	}
	protected void authenticate(String password,String salt) {
		PasswordHashRepository.HashedPasswordWithSalt hashedPasswordWithSalt = passwordHashRepository.hashPassword(password,salt);

        // Retrieve the hashed password and salt
        hashedPassword = hashedPasswordWithSalt.getHashedPassword();
	}
	public String getUserName() {
		return username;
	}
	public String GetHashedPassword() {
		return hashedPassword;
	}
	public String getSalt() {
		return salt;
	}
	public String getEmailID() {
		return EmailId;
	}
	
}
