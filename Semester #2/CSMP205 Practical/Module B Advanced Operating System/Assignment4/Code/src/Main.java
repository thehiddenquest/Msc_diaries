
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
    public static HashMap<Integer, Node> treeBuilder(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        int id = 1;
        Stack<Node> precedenceStack = new Stack<>();
        HashMap<Integer, Node> treeNodes = new HashMap<>();
        System.out.println("\nPrint Stack Updates");
        while ((line = reader.readLine()) != null) {
            int level = countLeadingSpaces(line) / 4;
            String nodeName = line.trim();
            Node newNode = new Node(id, nodeName, level);
            if (level == 0) {
                precedenceStack.push(newNode);
                System.out.println("New Node(leaf) pushed : name : " + newNode.getName() + " id: " + newNode.getId());
            } else {
                while (!precedenceStack.isEmpty()) {
                    Node child = precedenceStack.peek();
                    if (child.getLevel() < level) {
                        child.addParent(newNode);
                        // inverseTree.put(child.getId(), child);
                        treeNodes.put(child.getId(), child);
                        precedenceStack.pop();
                        System.out.println("child Popped: name : " + child.getName() + " id: " + child.getId()
                                + " parentid: " + child.getParentID());
                    } else {
                        break;
                    }
                }
                precedenceStack.push(newNode);
                System.out
                        .println("New Node(internal) pushed : name : " + newNode.getName() + " id: " + newNode.getId());
            }
            id++;
        }
        int rootCounter = 0;
        while (!precedenceStack.isEmpty()) {
            Node node = precedenceStack.pop();
            // inverseTree.put(node.getId(), node);
            node.setStatus(Status.PHOLD);
            node.addParent(null);
            treeNodes.put(node.getId(), node);
            System.out.println("root Popped: name :" + node.getName() + " id: " + node.getId() + " parent Id: "
                    + node.getParentID());
            System.out.println();
            System.out.println();
            System.out.println("System Log::");
            System.out.println("Information Message :: Node " + node.getName() + " with ID "
                    + node.getId() + " have been set to PHOLD Initially.");
            rootCounter++;
        }
        reader.close();
        if (rootCounter != 1) {
            System.out.println("System Message :: Multiple  root found.");
            return null;
        }
        return treeNodes;
    }

    public static int countLeadingSpaces(String line) {
        int count = 0;
        while (count < line.length() && line.charAt(count) == ' ') {
            count++;
        }
        return count;
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

    public static void printTree(HashMap<Integer, Node> nodeMap) {
        // 1. Create a map to store nodes by ID

        // 2. Find the root node (the node with no parent)
        Node root = null;
        for (Node node : nodeMap.values()) {
            if (node.getParentID() == 0) {
                root = node;
                break;
            }
        }

        // 3. Print the tree recursively (starting from the root)
        if (root != null) {
            printNode(root, 0, nodeMap); // Start printing from the root
        } else {
            System.out.println("No root node found.");
        }
    }

    // Recursive helper method to print nodes
    private static void printNode(Node node, int level, Map<Integer, Node> nodeMap) {
        // Print the node's name, indented based on its level
        for (int i = 0; i < level; i++) {
            System.out.print("  "); // Indentation
        }
        System.out.println(node.getName());

        // Recursively print the node's children
        for (Node child : nodeMap.values()) {
            if (child.getParentID() == node.getId()) {
                printNode(child, level + 1, nodeMap);
            }
        }
    }

    public static void main(String[] args) {
        try {
            String filePath = openFileDialog();
            HashMap<Integer, Node> treeNodes = treeBuilder(filePath);
            List<Thread> threads = new LinkedList<>();
            TreeMap<Integer, Node> sortedMap = new TreeMap<>(Comparator.reverseOrder());
            sortedMap.putAll(treeNodes);
            if (sortedMap != null) {
                // System.out.println("\n\n HashMap details: ");
                // for (Node node : sortedMap.values()) {
                // System.out.println(
                // "Name: " + node.getName() + " id: " + node.getId() + " parent ID: " +
                // node.getParentID());
                // }
                // System.out.println("\n\n Thread Details details: ");
                for (Node node : sortedMap.values()) {
                    // Create a new thread for each Node
                    Thread thread = new Thread(node);
                    threads.add(thread);
                    // Start the thread (this will execute the Node's run() method)
                    thread.start();
                }
                // Wait for all threads to finish
                for (Thread thread : threads) {
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("\nAll system processes have finished.");
                System.out.println("\nFinal Inverted tree is : ");
                printTree(treeNodes);
                System.exit(0);
            }
            // Set the status of the highest level node to PHOLD
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
