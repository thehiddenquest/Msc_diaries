package View.model;

public class userDetails {
	private String userName = new String();
	private String emailId = new String();
	private boolean flag = false;
	
	public void setUserName(String name) {
		userName = name;
	}
	public String getUserName() {
		return userName;
	}
	public void setEmailID(String email) {
		emailId = email;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setFlag(boolean flag) {
		this.flag =flag;
	}
	public boolean getFlag() {
		return flag;
	}
	
	
	
}
