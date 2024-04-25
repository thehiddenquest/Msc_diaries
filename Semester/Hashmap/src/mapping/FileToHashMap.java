package mapping;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class FileToHashMap {
	public static void main(String[] args) {
		// Specify the path to your file
		String filePath = "temp.txt";

		// Create a HashMap to store data from the file, keyed by name
		Map<String, String[]> dataMapByName = new HashMap<>();

		// Create a second HashMap to store data from the file, keyed by IP address
		Map<String, String> dataMapByIP = new HashMap<>();
		Scanner scanner = new Scanner(System.in);



		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				// Split the line into columns based on a delimiter (e.g., comma)
				String[] columns = line.split(",");

				// Assuming three columns: name, IP, port
				if (columns.length == 3) {
					String name = columns[0].trim();
					String IP = columns[1].trim();
					String port = columns[2].trim();

					// Remove backslash from the beginning of the IP
					IP = IP.replace("\\", "");

					// Create an array to store IP and port
					String[] internetInfoArray = { IP, port };

					// Insert data into the HashMap keyed by name
					dataMapByName.put(name, internetInfoArray);

					// Insert data into the HashMap keyed by IP
					dataMapByIP.put(IP, name);
				} else {
					System.out.println("Invalid data format in the file: " + line);
				}
			}

			// Print the HashMap contents (keyed by name)
			for (Map.Entry<String, String[]> entry : dataMapByName.entrySet()) {
				String name = entry.getKey();
				String[] values = entry.getValue();
				System.out.println("Name: " + name + ", IP: " + values[0] + ", Port: " + values[1]);
			}

			// Test retrieval by IP
	         // Test retrieval by IP
   
	           // Test retrieval by name from the list

            List<String> nameList = new ArrayList<>();

            while (true) {
                System.out.print("Enter name (or 'exit' to stop): ");
                String name = scanner.nextLine();

                if ("exit".equalsIgnoreCase(name)) {
                    break;
                }

                nameList.add(name);
            }

            // Iterate through the list of names and find corresponding IP and port
            for (String name : nameList) {
                String[] values = dataMapByName.get(name);
                if (values == null) {
                    System.out.println("Name not present in the list for: " + name);
                } else {
                    System.out.println("Name: " + name + ", IP: " + values[0] + ", Port: " + values[1]);
                }
            }

			while (true) {
				System.out.print("Enter IP (or 'exit' to stop): ");
				String IP = scanner.nextLine();

				if ("exit".equalsIgnoreCase(IP)) {
					break;
				}
				IP = IP.replace("\\", "");
				String testIP = IP;
				String associatedName = dataMapByIP.get(testIP);
				if (associatedName == null) {
					System.out.println("Name not present in the list");
				} else {
					System.out.println("Name associated with IP " + testIP + ": " + associatedName);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
