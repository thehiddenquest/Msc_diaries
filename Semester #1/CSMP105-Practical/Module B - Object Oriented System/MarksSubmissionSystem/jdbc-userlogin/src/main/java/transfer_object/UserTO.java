package transfer_object;

public class UserTO {
	private final String ID = "user";
	private String username;
	private String password;
	private String salt;
	private String emailID;
	
	public UserTO(String username, String password, String salt, String emailID) {
		this.username = username;
		this.password = password;
		this.salt = salt;
		this.emailID = emailID;
	}
	
	public UserTO(String username, String password) {
		this.username = username;
		this.password = password;
		this.salt = null;
		this.emailID = null;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getSalt() {
		return salt;
	}
	
	public String getEmailID() {
		return emailID;
	}
	public String getID() {
		return ID;
	}
}
