
/*ABOUT GRAPH CLASS
The Graph class in Java encapsulates functionality for managing a directed graph 
using an adjacency list data structure. It initializes with a specified file path
to populate its adjacency list, reading node connections and handling cases
where nodes have no neighbors or are connected to a null node. Methods include
adding edges between nodes, printing the graph's adjacency list representation,
and performing a Breadth-First Search (BFS) to check graph connectivity from a 
specified initiator node. It offers utility for applications needing to analyze
and manipulate graph structures, particularly in scenarios involving distributed 
systems or network topologies where connectivity and node relationships are crucial
for system behavior and analysis.
 */
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Graph {
    private String filePath; // Path to the file containing the graph data
    private Map<String, List<String>> adjacencyList = new HashMap<>(); // Adjacency list to store the graph

    // Constructor to initialize the graph with the file path
    public Graph(String filePath) {
        this.filePath = filePath;
        populateAdjacencyList(); // Populate the adjacency list with data from the file
    }

    // Method to read the file and populate the adjacency list
    private void populateAdjacencyList() {
        try {
            File file = new File(filePath); // Open the file
            Scanner scanner = new Scanner(file); // Scanner to read the file

            // Read the file line by line
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim(); // Read and trim each line
                if (line.isEmpty()) {
                    continue; // Skip empty lines
                }

                String[] parts = line.split("->"); // Split the line into source and destination nodes
                String src = parts[0].trim(); // Source node

                // If there are destination nodes, add them to the adjacency list
                if (parts.length > 1) {
                    for (int i = 1; i < parts.length; i++) {
                        String dest = parts[i].trim(); // Destination node
                        if (dest.equalsIgnoreCase("null"))
                            addEdge(src, null); // Add edge with null destination
                        else
                            addEdge(src, dest); // Add edge with valid destination
                    }
                } else {
                    addEdge(src, null); // Handle case with only source node
                }
            }

            scanner.close(); // Close the scanner
            printGraph(); // Print the adjacency list representation of the graph
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage()); // Handle file reading errors
        }
    }

    // Method to add an edge to the adjacency list
    private void addEdge(String src, String dest) {
        adjacencyList.putIfAbsent(src, new ArrayList<>()); // Add source node if not already present
        if (dest != null) {
            adjacencyList.get(src).add(dest); // Add destination node to the adjacency list of the source node
        }
    }

    // Method to print the adjacency list representation of the graph
    private void printGraph() {
        System.out.println();
        System.out.println("Graph Information :::: ");
        System.out.println();
        System.out.println("Inserted Graph (Adjacency List Representation) :");
        for (Map.Entry<String, List<String>> entry : adjacencyList.entrySet()) {
            System.out.print(entry.getKey() + " -> "); // Print source node
            List<String> neighbors = entry.getValue(); // Get list of neighbors
            if (!neighbors.isEmpty()) {
                for (int i = 0; i < neighbors.size(); i++) {
                    System.out.print(
                            neighbors.get(i) != null ? (neighbors.get(i).equals("null") ? "null" : neighbors.get(i))
                                    : "NULL"); // Print each neighbor
                    if (i != neighbors.size() - 1) {
                        System.out.print(", "); // Print comma if not the last neighbor
                    }
                }
            } else {
                System.out.print("null"); // Print null if no neighbors
            }
            System.out.println();
        }
    }

    // Method to perform BFS and check if the graph is connected from the initiator
    // node
    private Boolean bfsForAdjacencyList(String initiator) {
        int noOfNodes = adjacencyList.size(); // Number of nodes in the graph
        boolean[] visited = new boolean[noOfNodes]; // Array to keep track of visited nodes
        Arrays.fill(visited, false); // Initialize all nodes as not visited

        List<String> q = new ArrayList<>();
        q.add(initiator); // Add initiator to the queue
        visited[getIndex(initiator)] = true; // Mark initiator as visited

        // Perform BFS
        while (!q.isEmpty()) {
            String current = q.remove(0); // Dequeue a node
            List<String> neighbors = adjacencyList.get(current); // Get neighbors of the current node

            if (neighbors != null) {
                for (String neighbor : neighbors) {
                    if (!visited[getIndex(neighbor)]) {
                        q.add(neighbor); // Add unvisited neighbors to the queue
                        visited[getIndex(neighbor)] = true; // Mark neighbor as visited
                    }
                }
            }
        }

        // Check if all nodes were visited
        for (int i = 0; i < noOfNodes; i++) {
            if (!visited[i]) {
                return false; // If any node is not visited, return false
            }
        }
        return true; // Return true if all nodes were visited
    }

    // Method to get the index of a vertex in the adjacency list
    private int getIndex(String vertex) {
        int index = 0;
        for (String key : adjacencyList.keySet()) {
            if (key.equals(vertex)) {
                return index; // Return the index if vertex matches
            }
            index++;
        }
        return index; // Return the index
    }

    // Method to check if the initiator can be a valid starting node for BFS
    public Map<String, List<String>> checkInitiator(String initiator) {
        boolean exists = adjacencyList.keySet().stream().anyMatch(k -> k.equalsIgnoreCase(initiator)); // Check if
                                                                                                       // initiator
                                                                                                       // exists in the
                                                                                                       // graph
        if (exists) {
            if (bfsForAdjacencyList(initiator)) {
                System.out.println(initiator + " Can be an initiator."); // Print if initiator is valid
                return adjacencyList; // Return adjacency list
            } else {
                System.out.println(initiator + " Cannot be an initiator."); // Print if initiator is not valid
                return null;
            }
        } else {
            System.out.println("Node does not exist in the adjacency list. Please check"); // Print if initiator does
                                                                                           // not exist
            return null;
        }
    }
}