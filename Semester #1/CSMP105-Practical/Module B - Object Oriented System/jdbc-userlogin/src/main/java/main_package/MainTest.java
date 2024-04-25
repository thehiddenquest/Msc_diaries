package main_package;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.mail.MessagingException;

import DAO.MarksDAO;
import DAO.UserDAO;
import transfer_object.MarksTO;

public class MainTest {

	public static void main(String[] args) throws MessagingException, IOException {
		final encryptPassword epHashed = new encryptPassword();
		UserDAO user = new UserDAO();
		MarksDAO marks = new MarksDAO();
		PdfFile pf = new PdfFile();
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
			switch (ch) {
			case 1:
				System.out.println("Enter username: ");
				username = sc.nextLine();
				System.out.println("Enter password: ");
				password = sc.nextLine();
				System.out.println("Enter email address: ");
				emailID = sc.nextLine();
				hashedPasswordWithSalt = epHashed.hashPassword(password);
				hashedPassword = hashedPasswordWithSalt.getHashedPassword();
				salt = hashedPasswordWithSalt.getSalt();
				flag = user.createNewUser(username, hashedPassword, salt, emailID, ch);
				if (flag)
					System.out.println("Successfully Inserted");
				else
					System.out.println("Successfully failed!");
				break;

			case 2:
				System.out.println("Enter username: ");
				username = sc.nextLine();
				System.out.println("Enter password: ");
				password = sc.nextLine();
				String[] userDetails = user.authenticateUser(username, password, ch);
				if (userDetails != null) {
					flag = epHashed.authenticate(userDetails[0], password, userDetails[1]);
					if (flag) {
						System.out.println("Access Granted!");
						int choice = 0;
						do {
							System.out.println("1. Load data from excel.");
							System.out.println("2. Retrive data from database.");
							System.out.println("3. Run Queries.");
							System.out.println("4. Logout");
							System.out.println("Enter choice : ");
							choice = sc.nextInt();
							sc.nextLine();
							switch (choice) {
							case 1:
								ExcelReader excelReader = new ExcelReader();
								try {
									String[][] data = excelReader.readExcel("C:\\Users\\Rahul Biswas\\Desktop\\X\\BTECH 5TH SEMESTER 2023 CSCL501 30.xlsx");
									if (data != null) {
										flag = marks.insertDataBase(data);
										if(flag)
											System.out.println("Data inserted successfully");
										else
											System.out.println("Exception occur");
									} else {
										System.out.println("No data found.");
									}
								} catch (IOException e) {
									System.out.println("An error occurred while reading the Excel file: " + e.getMessage());
									e.printStackTrace();
								}
								break;
							case 2 : 
									ArrayList<MarksTO> retrieveMarks =  marks.retriveDataBase(String.valueOf(choice));
									if(retrieveMarks != null) {
										pf.wholePDF(retrieveMarks);
										System.out.println("PDF Generate");
									}
									else
										System.out.println("No data found");
								break;
							default : System.out.println("Choose a valid input"); break;
							
							}
						} while (choice != 4);
					} else
						System.out.println("Wrong Password!");

				} else
					System.out.println("No User Found!");
				break;

			case 3:
				System.out.println("Enter username: ");
				username = sc.nextLine();
				flag = user.deleteUser(username, ch);
				if (flag)
					System.out.println("User removed!");
				else
					System.out.println("User not removed or exception occur");
				break;

			case 4:
				System.out.println("Enter username");
				username = sc.nextLine();
				String[] emailIDS = user.authenticateUser(username, null, ch);
				emailID = emailIDS[0];
				System.out.println(emailID);
				Email mail = new Email(emailID);
				final String passcode = mail.sendMail();
				System.out.println("Enter OTP: ");
				String OTP = sc.nextLine();
				flag = epHashed.authenticate(passcode, OTP);
				if (flag) {
					System.out.println("Enter new password: ");
					password = sc.nextLine();
					hashedPasswordWithSalt = epHashed.hashPassword(password);
					hashedPassword = hashedPasswordWithSalt.getHashedPassword();
					salt = hashedPasswordWithSalt.getSalt();
					flag = user.createNewUser(username, hashedPassword, salt, emailID, ch);

				} else
					System.out.println("Incorrect OTP");

				break;
			}
		} while (ch != 5);
		sc.close();
	}
}