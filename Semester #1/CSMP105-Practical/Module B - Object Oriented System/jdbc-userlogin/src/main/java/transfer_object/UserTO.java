package transfer_object;

public class UserTO {
	private int operation = 0;
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
	
	public UserTO(String username, String password, String salt, String emailID, int op) {
		this.username = username;
		this.password = password;
		this.salt = salt;
		this.emailID = emailID;
		this.operation = op;
	}
	
	public UserTO(String username, String password,int op) {
		this.username = username;
		this.password = password;
		this.salt = null;
		this.emailID = null;
		this.operation = op;
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
	public int getOperation() {
		return operation;
	}
}
