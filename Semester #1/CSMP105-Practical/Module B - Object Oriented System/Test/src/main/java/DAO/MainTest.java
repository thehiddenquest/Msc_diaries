package DAO;

import java.util.Scanner;

public class MainTest {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		UserDAO doa = new UserDAO();
		String username, password, emailAddress;
		int option;
		do {
			System.out.println("Menu:");
			System.out.println("1. Add new user");
			System.out.println("2. Authenticate user");
			System.out.println("3. Delete user");
			System.out.println("4. Forget password");
			System.out.println("5. Exit");
			System.out.print("Enter your choice: ");
			option = sc.nextInt();
			sc.nextLine(); // Consume newline character

			switch (option) {
			case 1:
				System.out.print("Enter Username: ");
				username = sc.nextLine();
				System.out.print("Enter Password: ");
				password = sc.nextLine();
				System.out.print("Enter Email Address: ");
				emailAddress = sc.nextLine();
				doa.addNew(username, password, emailAddress);
				break;
			case 2:
				System.out.println("Enter Username: ");
				username = sc.nextLine();
				System.out.println("Enter Password: ");
				password = sc.nextLine();
				boolean auth = doa.authenticate(username, password);
				if (auth)
					System.out.println("Welcome");
				else
					System.out.println("Wrong password");
				break;
			case 3:
				System.out.println("Enter Username: ");
				username = sc.nextLine();
				doa.deleteUser(username);
				break;
			case 4:
				System.out.println("Enter Username: ");
				username = sc.nextLine();
				System.out.println(username);
				doa.resetPassword(username);
				break;
			default:
				System.out.println("Invalid option. Exiting...");
				break;

			}
		} while (option != 5);
		sc.close();

	}

}
