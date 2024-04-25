package DAO;


import respository.MySQLRepository;
import respository.UserRepository;
import transfer_object.UserTO;

public class UserDAO{
	private UserTO userto= new UserTO();
	private UserRepository ur = new UserRepository(new MySQLRepository() );
	public void addNew(String username,String password,String EmailAddress) {
		userto.newUserAdd(username,password,EmailAddress);
		ur.save(userto);
	}
	public boolean authenticate(String username, String password) {
		UserTO dbUser = (UserTO) ur.getuser(username);
		if(dbUser.getUserName() == null) {
			System.out.println("No UserFound");
			return false;
		}
		else {
			userto.authenticate(password,dbUser.getSalt());
			if(userto.GetHashedPassword().trim().equals(dbUser.GetHashedPassword().trim()))
				return true;
			else
				return false;
		}
		
	}

}
