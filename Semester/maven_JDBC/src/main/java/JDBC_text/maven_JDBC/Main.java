package JDBC_text.maven_JDBC;

import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		DOA doa = new DOA();

		// Populate and display existing data
		
		doa.printdata();

		System.out.print("Do you want to insert data? (Enter 1): ");
		int flag = scanner.nextInt();
		scanner.nextLine(); // Consume newline character

		if (flag == 1) {
			while (flag == 1) {
				System.out.print("Enter name: ");
				String name = scanner.nextLine();
				System.out.print("Enter gender: ");
				String gender = scanner.nextLine();

				// Insert data into the database
				doa.InsertData(name, gender);

				System.out.print("Do you want to insert more data? (Enter 1 to continue): ");
				flag = scanner.nextInt();
				scanner.nextLine(); // Consume newline character
			}

			// Display updated data
			doa.printdata();
		} else {
			doa.closeConnection();
		}

		// Close scanner
		scanner.close();
	}
}