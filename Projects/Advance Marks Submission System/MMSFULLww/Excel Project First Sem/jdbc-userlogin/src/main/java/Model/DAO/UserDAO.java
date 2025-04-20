package Model.DAO;

import Model.repository.MYSQLRepository;
import Model.repository.userRepository;
import Model.transfer_object.UserTO;

public class UserDAO {
	MYSQLRepository<UserTO> mysqlrepository = new MYSQLRepository<>();
	userRepository userrepository = new userRepository(mysqlrepository);

	public boolean createNewUser(String username, String hashedPassword, String salt, String emailID, int operation) {
		UserTO newUser = new UserTO(username, hashedPassword, salt, emailID, operation);
		boolean flag = userrepository.save(newUser);
		return flag;
	}

	public String[] authenticateUser(String username, String password, int operation) {
	    UserTO user = new UserTO(username, password, operation);
	    user = (UserTO) userrepository.load(user);
	    if (operation == 2) {
	        if (user != null) {
	            return new String[] { user.getPassword(), user.getSalt(),user.getEmailID(),user.getUsername() };
	        }
	        return null;
	    }
	    if (operation == 4) {
	        if (user != null) {
	            return new String[] { user.getEmailID()} ;
	        }
	        return null;
	    }
	    return null;
	}
	public boolean deleteUser(String username, int operation) {
		UserTO existingUser = new UserTO(username, null, operation);
		boolean flag = userrepository.delete(existingUser);
		return flag;
	}


}
