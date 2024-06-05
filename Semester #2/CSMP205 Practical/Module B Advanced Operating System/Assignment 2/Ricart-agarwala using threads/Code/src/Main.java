// Here we implement Ricart–Agrawala Algorithm in Mutual Exclusion in Distributed System
// with Lamport's logical clock model using Thread

// Here Threads are shown as different system which are distributed and can request for critical state
//without any external interference.


// Input File Format
/* node1,node2,node3,node4 */
//or
/* node1,node2
    node3,node4
 */

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<String> nodeNames = new ArrayList<>();  // List to store node names from file
        int id = 1; // ID of the first node
        String filename = openFileDialog();  // Get file name from gui component
        if (filename == null) {
            System.out.println("No file selected.");
            return;
        }
        try {
            Scanner scanner = new Scanner(new File(filename));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] tokens = line.split(",");  // Split line by commas
                nodeNames.addAll(List.of(tokens)); // Store node name
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found - nodes.txt");
            // Handle the exception if the file is not found
        }
        Message messageSystem = new Message(nodeNames); // Message system which handel the message passing

        List<Node> nodes = new ArrayList<>(); // Store node as a Node component

        for (String nodeName : nodeNames) {
            nodes.add(new Node(String.valueOf(id), nodeName, messageSystem, nodes));
            id++;
        }

        for (Node node : nodes) {
            new Thread(node).start(); // start each node
        }
    }

    private static String openFileDialog() {
        FileDialog fd = new FileDialog((Frame) null, "Open", FileDialog.LOAD);
        fd.setVisible(true);
        String filename = fd.getFile();
        // Validate file extension
        if (filename != null) {
            return fd.getDirectory() + filename;
        }
        return null;
    }

}