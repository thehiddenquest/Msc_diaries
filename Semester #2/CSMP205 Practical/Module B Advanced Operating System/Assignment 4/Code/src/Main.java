/* About Project 
    This is Raymond’s Algorithm Diffusion-Computation approach using threads 
*/
/* About Main
        The Main class provided constructs a tree structure from a file based on hierarchical indentation, 
        using a stack to manage node relationships and a hashmap to store nodes by their IDs. 
        It includes functionality to prompt the user for a file using AWT's FileDialog, and 
        prints the tree recursively starting from the root node found in the hashmap. 
        The main method orchestrates these operations, also implementing threading for each node to simulate concurrent processes, 
        although the use of reverse order in the TreeMap for threading purposes is unusual unless specifically required by the test scenario.
        Overall, while the class showcases tree manipulation and concurrent execution, it appears geared towards testing or demonstration
        rather than implementing Raymond's inverted tree-based mutual exclusion algorithm directly.
*/

/* Author 
   Rahul Biswas, Arijit Mondal 
*/
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;
import java.util.TreeMap;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.awt.*;

public class Main {

    // Method to build a tree structure from a file
    public static HashMap<Integer, Node> treeBuilder(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        int id = 1;
        Stack<Node> precedenceStack = new Stack<>();
        HashMap<Integer, Node> treeNodes = new HashMap<>();

        // Read lines from the file
        while ((line = reader.readLine()) != null) {
            // Calculate the level of indentation (assuming 4 spaces per level)
            int level = countLeadingSpaces(line) / 4;
            String nodeName = line.trim();
            Node newNode = new Node(id, nodeName, level);

            if (level == 0) {
                // If it's a root node, push to stack
                precedenceStack.push(newNode);
                System.out.println("New Node (leaf) pushed: name: " + newNode.getName() + " id: " + newNode.getId());
            } else {
                // Connect child nodes to their parents
                while (!precedenceStack.isEmpty()) {
                    Node child = precedenceStack.peek();
                    if (child.getLevel() < level) {
                        child.addParent(newNode); // Set current node as child's parent
                        treeNodes.put(child.getId(), child); // Add child to treeNodes map
                        precedenceStack.pop(); // Remove processed node from stack
                        System.out.println("Child Popped: name: " + child.getName() + " id: " + child.getId()
                                + " parentid: " + child.getParentID());
                    } else {
                        break;
                    }
                }
                // Push current node to stack
                precedenceStack.push(newNode);
                System.out.println("New Node (internal) pushed: name: " + newNode.getName() + " id: " + newNode.getId());
            }
            id++;
        }

        int rootCounter = 0;
        // Pop remaining nodes from stack to finalize the tree
        while (!precedenceStack.isEmpty()) {
            Node node = precedenceStack.pop();
            node.setStatus(Status.PHOLD); // Set status of node
            node.addParent(null); // Set parent of root node to null
            treeNodes.put(node.getId(), node); // Add root node to treeNodes map
            System.out.println("Root Popped: name: " + node.getName() + " id: " + node.getId() + " parent Id: " + node.getParentID());
            System.out.println();
            System.out.println();
            System.out.println("System Log:");
            System.out.println("Information Message: Node " + node.getName() + " with ID " + node.getId() + " have been set to PHOLD initially.");
            rootCounter++;
        }

        reader.close();
        // Check if exactly one root node exists in the tree
        if (rootCounter != 1) {
            System.out.println("System Message: Multiple roots found.");
            return null;
        }
        return treeNodes; // Return the constructed treeNodes map
    }

    // Method to count leading spaces in a string
    public static int countLeadingSpaces(String line) {
        int count = 0;
        while (count < line.length() && line.charAt(count) == ' ') {
            count++;
        }
        return count;
    }

    // Method to open file dialog and return selected file path
    private static String openFileDialog() {
        FileDialog fd = new FileDialog((Frame) null, "Open", FileDialog.LOAD);
        fd.setVisible(true);
        String filename = fd.getFile();
        if (filename != null) {
            return fd.getDirectory() + filename; // Return full file path
        }
        return null;
    }

    // Method to print the tree structure starting from the root node
    public static void printTree(HashMap<Integer, Node> nodeMap) {
        Node root = null;
        // Find the root node (node with no parent)
        for (Node node : nodeMap.values()) {
            if (node.getParentID() == 0) {
                root = node;
                break;
            }
        }

        // Print the tree recursively starting from the root
        if (root != null) {
            printNode(root, 0, nodeMap); // Start printing from the root node
        } else {
            System.out.println("No root node found.");
        }
    }

    // Recursive method to print nodes and their children
    private static void printNode(Node node, int level, Map<Integer, Node> nodeMap) {
        for (int i = 0; i < level; i++) {
            System.out.print("  "); // Indentation based on level
        }
        System.out.println(node.getName()); // Print node's name

        // Recursively print children nodes
        for (Node child : nodeMap.values()) {
            if (child.getParentID() == node.getId()) {
                printNode(child, level + 1, nodeMap); // Print child nodes recursively
            }
        }
    }
    

    // Main method
    public static void main(String[] args) {
        try {
            String filePath = openFileDialog(); // Open file dialog to select input file
            if(filePath == null){
                System.out.println("\nNo Input File is selected!!");
                System.exit(0);
            }
            HashMap<Integer, Node> treeNodes = treeBuilder(filePath); // Build tree from selected file

            List<Thread> threads = new LinkedList<>();
            TreeMap<Integer, Node> sortedMap = new TreeMap<>(Comparator.reverseOrder());
            sortedMap.putAll(treeNodes); // Sort nodes in reverse order by ID

            if (sortedMap != null) {
                // Start threads for each node in reverse order
                for (Node node : sortedMap.values()) {
                    Thread thread = new Thread(node);
                    threads.add(thread);
                    thread.start(); // Start thread for each node
                }

                // Wait for all threads to complete
                for (Thread thread : threads) {
                    try {
                        thread.join(); // Wait for thread to finish
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // After threads finish, print the final inverted tree structure
                System.out.println("\nAll system processes have finished.");
                System.out.println("\nFinal Inverted tree is:");
                printTree(treeNodes);

                System.exit(0); // Exit the program
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}