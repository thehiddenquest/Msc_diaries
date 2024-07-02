
/* ABOUT PROJECT
 * This project demonstrates the Chandy-Lamport's global state recording algorithm
 * using threads to record the global state of distributed systems.
 */
/* ABOUT MAIN CLASS
 * The Main class serves as the core orchestrator for a simulation of distributed systems
 * using the Chandy-Lamport's global state recording algorithm in Java. 
 * It begins by importing necessary libraries for file handling, UI interactions, and data structures. 
 * The main method initializes essential components such as lists for managing threads and nodes,
 * and a Channel object for communication. Through a file dialog, users select a file containing 
 * the graph structure of the distributed system, which is then parsed by the Graph class. 
 * After prompting the user to specify an initiator node, the program validates this choice against 
 * the graph and proceeds to create and manage threads for each node. Threads are started for both
 * the initiator and other nodes, with synchronization ensuring proper message exchange and state recording.
 * Upon completion of thread execution, the program outputs a global snapshot, detailing the 
 * messages exchanged and current states of each node and the communication channels. 
 * Finally, the program gracefully exits, providing a comprehensive demonstration of the 
 * Chandy-Lamport's algorithm application in distributed system simulation.
 */
/* ABOUT AUTHOR
    Rahul Biswas, Arijit Mondal
 */
import java.awt.FileDialog;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        // Lists to keep track of threads and nodes
        List<Thread> threads = new LinkedList<>();
        List<Node> nodes = new LinkedList<>();
        Channel channel = new Channel(); // Shared communication channel

        // Open file dialog to select a file
        String filePath = openFileDialog();
        if (filePath == null) {
            System.out.println("No file selected");
            System.exit(0);
        }

        // Create graph from the file
        Graph graph = new Graph(filePath);

        // Get initiator name from the user
        String initiator = JOptionPane.showInputDialog("Enter initiator:");

        // Validate initiator and get node connections
        Map<String, List<String>> nodeNames = graph.checkInitiator(initiator);
        if (nodeNames != null) {
            System.out.println();
            System.out.println("System Log ::::\n");

            // Create the initiator node and start its thread
            Node node = new Node(initiator, channel, nodeNames.get(initiator));
            Thread thread = new Thread(node);
            nodes.add(node);
            threads.add(thread);
            thread.start(); // Start the initiator thread
            node.setInitiatorStatus(); // Set the node as the initiator
            System.out.println("Information Message :: " + initiator + " is now initiator");

            // Pause for a short duration to ensure initiator starts
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Create and start threads for all other nodes
            for (String name : nodeNames.keySet()) {
                if (!name.equals(initiator)) {
                    Node n = new Node(name, channel, nodeNames.get(name));
                    Thread t = new Thread(n);
                    nodes.add(n);
                    threads.add(t);
                    t.start(); // Start thread for each node
                }
            }

            // Wait for all threads to finish
            for (Thread td : threads) {
                try {
                    td.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Print global snapshot of the system
            System.out.println();
            System.out.println("Global Snapshot ::::");
            for (Node n : nodes) {
                System.out.println("\nLocal State Recording of " + n.getName() + " ::");
                System.out.println("-->Messages delivered: ");
                HashMap<String, List<String>> sendMessage = n.getSendMessages();
                if (!sendMessage.isEmpty()) {
                    for (Map.Entry<String, List<String>> sendMessages : sendMessage.entrySet()) {
                        String key = sendMessages.getKey();
                        List<String> values = sendMessages.getValue();
                        System.out.print("To:  " + key + "  { ");
                        for (int i = 0; i < values.size(); i++) {
                            System.out.print(values.get(i));
                            if (i < values.size() - 1) {
                                System.out.print(" , ");
                            }
                        }
                        System.out.println(" }");
                    }
                } else {
                    System.out.println("NONE");
                }
                System.out.println("-->Messages received: ");
                HashMap<String, List<String>> receivedMessage = n.getReceivedMessages();
                if (!receivedMessage.isEmpty()) {
                    for (Map.Entry<String, List<String>> receivedMessages : receivedMessage.entrySet()) {
                        String key = receivedMessages.getKey();
                        List<String> values = receivedMessages.getValue();
                        System.out.print("From:  " + key + "  { ");
                        for (int i = 0; i < values.size(); i++) {
                            System.out.print(values.get(i));
                            if (i < values.size() - 1) {
                                System.out.print(" , ");
                            }
                        }
                        System.out.println(" }");
                    }
                } else {
                    System.out.println("NONE");
                }
            }

            // Print communication channel state
            System.out.println("\nCommunication Channel State ::");
            HashMap<String, Queue<String>> communicationChannel = channel.getCommunicationChannel();
            for (Map.Entry<String, Queue<String>> specificChannel : communicationChannel.entrySet()) {
                String receiverName = specificChannel.getKey();
                HashMap<String, List<String>> tempHashMap = new HashMap<>();
                Queue<String> tempQueue = specificChannel.getValue();
                for (String message : tempQueue) {
                    String[] splitMessage = message.split(":");
                    String senderName = splitMessage[0];
                    String messageContentType = splitMessage[1];
                    if (messageContentType.equalsIgnoreCase("TEXT")) {
                        if (tempHashMap.containsKey(senderName)) {
                            tempHashMap.get(senderName).add(splitMessage[2]);
                        } else {
                            List<String> tempList = new ArrayList<>();
                            tempList.add(splitMessage[2]);
                            tempHashMap.put(senderName, tempList);
                        }
                    }
                }
                for (Map.Entry<String, List<String>> tempNode : tempHashMap.entrySet()) {
                    String senderName = tempNode.getKey();
                    List<String> values = tempNode.getValue();
                    System.out.print("\n Channel :: " + senderName + " -- " + receiverName + " :  { ");
                    for (int i = 0; i < values.size(); i++) {
                        System.out.print(values.get(i));
                        if (i < values.size() - 1) {
                            System.out.print(" , ");
                        }
                    }
                    System.out.println(" }");
                }
            }

            System.exit(0); // Exit program after printing snapshot
        } else {
            // If initiator is not valid
            System.out.println("Please choose another initiator!!");
            System.exit(0);
        }
    }

    // Method to open file dialog and get the selected file path
    public static String openFileDialog() {
        FileDialog fd = new FileDialog((Frame) null, "Open", FileDialog.LOAD);
        fd.setVisible(true); // Display file dialog
        String directory = fd.getDirectory();
        String file = fd.getFile();
        if (directory != null && file != null) {
            return directory + file; // Return full path of the selected file
        }
        return null; // Return null if no file was selected
    }
}
