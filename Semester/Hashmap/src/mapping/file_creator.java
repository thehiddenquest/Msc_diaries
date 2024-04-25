package mapping;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class file_creator {
    public static void main(String[] args) {
        // Specify the path to the file
        String filePath = "temp.txt";

        try (FileWriter writer = new FileWriter(filePath)) {
            Scanner scanner = new Scanner(System.in);

            // Ask the user for input until they enter "exit"
            while (true) {
                System.out.print("Enter name (or 'exit' to stop): ");
                String name = scanner.nextLine();

                if ("exit".equalsIgnoreCase(name)) {
                    break;
                }

                System.out.print("Enter IP: ");
                String IP = scanner.nextLine();

                System.out.print("Enter port: ");
                String port = scanner.nextLine();

                // Write the data to the file
                writer.write(name + "," + IP + "," + port + "\n");

                System.out.println("Record added to the file.");
            }

            System.out.println("Data entry complete. Check the file at: " + filePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}