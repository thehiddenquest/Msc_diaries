package ems_package;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Random;

import javax.swing.JOptionPane;

import myproject.*;

public class User extends User_info {
	HashMap<String, String> user = new HashMap<String, String>();
	HashMap<String, String> userlist = new HashMap<String, String>();

	public User(String username, String Password, String emailid) {
		super.userid = username;
		super.password = Password;
		super.emailid = emailid;
	}

	@Override
	public void UserAuthenticate(User_info u) {
		User_Repository fileRepositroy = new User_Repository(new FileRepository());
		User us = (User) fileRepositroy.load();

		if (u.userid.equals(us.userid)) {
			try {

				MessageDigest m = MessageDigest.getInstance("MD5");

				m.update(u.password.getBytes());

				byte[] bytes = m.digest();

				StringBuilder s = new StringBuilder();
				for (int i = 0; i < bytes.length; i++) {
					s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
				}

				String temp = s.toString();
				if (us.password.equals(temp)) {
					System.out.println("Access Granted");
					Employee l = new leader("n", "n", "n", 1, 1);
					new EmployeeSystemGui(l);
				} else {
					System.out.println("Access not Granted");
				}

			} catch (NoSuchAlgorithmException ex) {
				ex.printStackTrace();
			}
		} else {
			System.out.println("2Access not Granted");
		}
	}

	@Override
	public void addUser(User_info u) {
		user.put(u.userid, u.password);
		userlist.put(u.userid, u.emailid);
		System.out.print(user.get(u.userid) + "Success");
	}

	@Override
	public void ResetPass(User_info u) {
		User_Repository fileRepositroy = new User_Repository(new FileRepository());
		User us = (User) fileRepositroy.load();
		Random rnd = new Random();
		int number = rnd.nextInt(999999);
		String OTP = String.format("%06d", number);
		if (u.userid.equals(us.userid)) {
			try {
				Notification_service.notify("someone", us.emailid, OTP, "Mail");
				String Input = JOptionPane.showInputDialog(null, "Enter OTP");
				if (Input.equals(OTP)) {
					String pass = JOptionPane.showInputDialog("Enter new Password");
					try {

						MessageDigest m = MessageDigest.getInstance("MD5");

						m.update(pass.getBytes());

						byte[] bytes = m.digest();

						StringBuilder s = new StringBuilder();
						for (int i = 0; i < bytes.length; i++) {
							s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
						}

						u.password = s.toString();
						user.put(u.userid,u.password);
						userlist.put(u.userid, u.emailid);
					    fileRepositroy.save(u);

					} catch (NoSuchAlgorithmException ex) {
						ex.printStackTrace();
					}
				}
			} catch (Exception e) {
				System.out.print(e);
			}
		}
	}
}