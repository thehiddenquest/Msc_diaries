package DAO;

import repository.MYSQLRepository;
import repository.userRepository;
import transfer_object.UserTO;

public class UserDAO {
	MYSQLRepository<UserTO> mysqlrepository = new MYSQLRepository<>();
	userRepository userrepository = new userRepository(mysqlrepository);
	
	public boolean createNewUser(String username, String hashedPassword, String salt, String emailID) {
		UserTO newUser = new UserTO(username, hashedPassword, salt, emailID);
		boolean flag = userrepository.save(newUser);
		return flag;
	}
	
	public String[] authenticateUser(String username, String password) {
		UserTO user = new UserTO(username, password);
		user = (UserTO)userrepository.load(user);
		if (user != null) {
            return new String[]{user.getPassword(), user.getSalt()};
		} 
		return null;
	}
	
	public boolean deleteUser(String username) {
		UserTO nikalLodeUser = new UserTO(username, null);
		boolean flag = userrepository.delete(nikalLodeUser);
		return flag;
	}
	
	public String forgetPassword(String username) {
		UserTO bachaoUser = new UserTO(username, null);
		bachaoUser = (UserTO)userrepository.load(bachaoUser);
		if (bachaoUser != null) {
            return bachaoUser.getEmailID();
		} 
		return null;
	}
	
}
