/* About Project
    Token-Based Mutual Exclusion using Threads
    In this system, we implement mutual exclusion using tokens and threads.
    Initially, an arbitrary node holds the token.
    Nodes pass the token to each other based on requests,
    ensuring continuous execution of processes.
    Here, distributed systems are represented by nodes that can run independently.

*/

/* About Main Class
    In the Main Class, we retrieve a file from the disk containing the names of the nodes.
    The file format consists of either comma-separated node names (nodeName1, nodeName2) or
    each node name on a separate line (nodeName1 followed by nodeName2).
    We then create Node objects using these names and assign IDs to them before starting the Node threads.
 */

//Author Rahul Biswas, Arijit Mondal
//M.Sc. Computer Science, Semester-2 , University of Calcutta

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
            //Read node names from the file and set them in nodeNames list
            Scanner scanner = new Scanner(new File(filename));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] tokens = line.split(",");  // Split line by commas
                nodeNames.addAll(List.of(tokens)); // Store node name
            }
        } catch (FileNotFoundException e) { // Handle the exception if the file is not found
            System.out.println("Error: File not found ");
        }


        List<Node> nodes = new ArrayList<>(); // list of node objects
        for (String nodeName : nodeNames) {
            nodes.add(new Node(String.valueOf(id), nodeName));
            id++; // Set id according to file
        }
        Messenger messageSystem = new Messenger(nodes);

        Random random = new Random();
        int randomIndex = random.nextInt(nodes.size());
        nodes.get(randomIndex).setStatus(Status.HAVE_TOKEN); // Giving token to an arbitrary  node
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("Information Message :: [ "+ nodes.get(randomIndex).getName() +" ] Have Token Initially.");

        // Start all threads
        for (Node node : nodes) {
            node.setMessenger(messageSystem); // Set the messenger object
            new Thread(node).start();
        }
    }

    // Open File Dialog box
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