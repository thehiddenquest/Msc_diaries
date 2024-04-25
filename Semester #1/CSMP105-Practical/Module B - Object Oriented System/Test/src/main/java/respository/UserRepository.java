package respository;

import javax.swing.JOptionPane;

import transfer_object.BaseTO;


public class UserRepository implements BaseRepository {

	private StorageRepository repository;
	MySQLRepository msql = new MySQLRepository();

	public UserRepository(StorageRepository repository) {
		this.repository = repository;
	}
	@Override
	public StorageRepository getRepository() {
		return repository;
	}
	@Override
	public void save(BaseTO BD) {
		msql.store(BD,"Rahultest");
	}
	public Object getuser(String username){
		Object obj =msql.restore(username,"Rahultest");
		return obj;
	}
	
	public void resetPass(String userName) {
		String OTP = null;
		String Password = null;
		Email mail = new Email(msql.getEmail(userName),"123456");
		mail.sendMail();
		OTP = JOptionPane.showInputDialog("Enter OTP");
		if(OTP.equals("123456")) {
			JOptionPane.showInputDialog("Enter new Password", Password);
			msql.reset(userName,Password);
		}
	}
	public void removeUser(String userName) {
		msql.remove(userName);
	}

}
