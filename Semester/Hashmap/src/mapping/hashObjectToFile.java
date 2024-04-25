package mapping;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class hashObjectToFile {
	String name;
	String[] internetInfo;
	private final String filepath = "hashmap.ser";
	Map<String, String[]> myHashMap;

	hashObjectToFile() {
		myHashMap = new HashMap<>();

        // Display the HashMap before updating
        myHashMap.putAll(loadHashMapFromFile(filepath));
		System.out.println("HashMap before update:");
		displayHashMap();
		// Insert Values
		addFriend();
		// Display the HashMap after updating
		System.out.println("\nHashMap after update:");
		displayHashMap();
		// Write the updated HashMap to a file
		saveHashMapToFile("hashmap.ser");
	}

	// Load HashMap from file
	@SuppressWarnings("unchecked")
	private Map<String, String[]> loadHashMapFromFile(String filePath) {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
			return (Map<String, String[]>) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			// If the file doesn't exist or cannot be read, return a new HashMap
			return new HashMap<>();
		}
	}

	// Save HashMap to file
	private void saveHashMapToFile(String filePath) {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
			oos.writeObject(myHashMap);
			System.out.println("HashMap saved to file: " + filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Display HashMap contents
	private void displayHashMap() {
		for (Map.Entry<String, String[]> entry : myHashMap.entrySet()) {
			String name = entry.getKey();
			String[] values = entry.getValue();
			System.out.println("Name: " + name + ", IP: " + values[0] + ", Port: " + values[1]);
		}
	}

	@SuppressWarnings("resource")
	private void addFriend() {
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
			
			String info[]= {IP,port};
			myHashMap.put(name,info);
		}
	}
}