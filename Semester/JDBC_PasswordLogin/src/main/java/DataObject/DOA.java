package DataObject;

import Repository.UserRepository;

public class DOA {
	private TO userto= new TO();
	private UserRepository ur = new UserRepository();
	public void addNew(String username,String password,String EmailAddress) {
		userto.newUserAdd(username,password,EmailAddress);
		ur.save(userto);
	}
	public boolean authenticate(String username, String password) {
		TO dbUser = (TO) ur.getuser(username);
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
