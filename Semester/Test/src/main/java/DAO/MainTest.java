package DAO;

import java.util.Scanner;


public class MainTest {

	public static void main(String[] args) {
		 Scanner sc = new Scanner(System.in);
	        UserDAO doa = new UserDAO();
	        String username, password, emailAddress;
	        int option = 0;
	        System.out.print("Want to Add new user (press 1) or Authenticate user (press 2): ");
	        option = sc.nextInt();
	        sc.nextLine(); // Consume newline character
	        
	        if (option == 1) {
	            System.out.print("Enter Username: ");
	            username = sc.nextLine();
	            System.out.print("Enter Password: ");
	            password = sc.nextLine();
	            System.out.print("Enter Email Address: ");
	            emailAddress = sc.nextLine();
	            doa.addNew(username, password, emailAddress);
	        } else if (option == 2) {
	            System.out.println("Enter Username: ");
	            username = sc.nextLine();
	            System.out.println("Enter Password: ");
	            password = sc.nextLine();
	            boolean auth = doa.authenticate(username, password);
	            if (auth)
	                System.out.println("Welcome");
	            else
	                System.out.println("Wrong password");
	        } else {
	            System.out.println("Invalid option. Exiting...");
	        }

	        sc.close();

	}

}
