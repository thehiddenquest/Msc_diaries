package main_package;

import java.util.Scanner;

import DAO.UserDAO;

public class MainTest {
	
	public static void main(String[] args) {
		final encryptPassword epHashed = new encryptPassword();
		UserDAO user = new UserDAO();
		Email mail = null;
		encryptPassword.HashedPasswordWithSalt hashedPasswordWithSalt = null;
		String username, password, salt, hashedPassword, emailID;
		boolean flag = false;
		int ch = 0;
		Scanner sc = new Scanner(System.in);
		do {
			System.out.println("1. Add user");
			System.out.println("2. Authenticate user");
			System.out.println("3. Remove user");
			System.out.println("4. Forget password");
			System.out.println("5. Exit");
			System.out.println("Enter choice : ");
			ch = sc.nextInt();
			sc.nextLine();
			switch(ch) {
				case 1 :	System.out.println("Enter username: ");
							username = sc.nextLine();
							System.out.println("Enter password: ");
							password = sc.nextLine();
							System.out.println("Enter email address: ");
							emailID = sc.nextLine();
							hashedPasswordWithSalt = epHashed.hashPassword(password);
					        hashedPassword = hashedPasswordWithSalt.getHashedPassword();
					        salt = hashedPasswordWithSalt.getSalt();
							flag = user.createNewUser(username, hashedPassword, salt, emailID);
							if(flag)
								System.out.println("Successfully Inserted");
							else
								System.out.println("Successfully failed!");
							break;
							
				case 2 : 	System.out.println("Enter username: ");
							username = sc.nextLine();
							System.out.println("Enter password: ");
							password = sc.nextLine();
							String[] userDetails = user.authenticateUser(username, password); 
							if(userDetails != null) {
								hashedPasswordWithSalt = epHashed.hashPassword(password,userDetails[1]);
						        hashedPassword = hashedPasswordWithSalt.getHashedPassword();
						        if(hashedPassword.equals(userDetails[0]))
						        	System.out.println("Access Granted!");
						        else
						        	System.out.println("Wrong Password!");
							}
							else
								System.out.println("No User Found!");
							break;
				
				case 3 : 	System.out.println("Enter username: ");
							username = sc.nextLine();
							flag = user.deleteUser(username); 
							if(flag)
								System.out.println("User removed!");
							else
								System.out.println("User not removed or exception occur");
							break;
				
				case 4 :	System.out.println("Enter username"); 	
							username = sc.nextLine();
							emailID = user.forgetPassword(username); 
							System.out.println(emailID);
							mail = new Email(emailID);
							String OTP = mail.sendMail();
							System.out.println(OTP);
							break;
			}
		}while(ch != 5);
		sc.close();
	}
}
