import java.io.*;
import java.util.*;

import javax.swing.JOptionPane;

public class test {
    public static void main(String[] args) {
        Map<String, List<String>> adjacencyList = new HashMap<>();

        try {
            File file = new File("G1");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split("->");
                String src = parts[0].trim(); // Source node

                if (parts.length > 1) {
                    for (int i = 1; i < parts.length; i++) {
                        String dest = parts[i].trim(); // Destination node
                        if (dest.equalsIgnoreCase("null"))
                            addEdge(adjacencyList, src, null);
                        else
                            addEdge(adjacencyList, src, dest);

                    }
                } else {
                    addEdge(adjacencyList, src, null); // Handle single node case
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }

        // Printing the adjacency list
        System.out.println("Adjacency List:");
        for (Map.Entry<String, List<String>> entry : adjacencyList.entrySet()) {
            System.out.print(entry.getKey() + " -> ");
            List<String> neighbors = entry.getValue();
            if (!neighbors.isEmpty()) {
                for (int i = 0; i < neighbors.size(); i++) {
                    System.out.print(
                            neighbors.get(i) != null ? (neighbors.get(i).equals("null") ? "null" : neighbors.get(i))
                                    : "NULL");
                    if (i != neighbors.size() - 1) {
                        System.out.print(", ");
                    }
                }
            } else {
                System.out.print("null");
            }
            System.out.println();
        }

        // Prompt for initiator input
        String initiator = JOptionPane.showInputDialog("Enter initiator:");
        System.out.println("Initiator entered: " + initiator);
        boolean exists = adjacencyList.keySet().stream().anyMatch(k -> k.equalsIgnoreCase(initiator));
        System.out.println("Initiator exists in the adjacency list (case-insensitive): " + exists);
        if (exists) {
            if (BFS(initiator, adjacencyList))
                System.out.println("Can be an initiator.");
            else
                System.out.println("Cannot be an initiator.");
        } else
            System.out.println("Node is not exists in the adjacency list.Please check");
    }

    // Method to add an edge in the adjacency list representation
    private static void addEdge(Map<String, List<String>> adjacencyList, String src, String dest) {
        adjacencyList.putIfAbsent(src, new ArrayList<>());
        if (dest != null) {
            adjacencyList.get(src).add(dest);
        }
    }

    private static boolean BFS(String ini, Map<String, List<String>> adjacencyList) {
        // Number of vertices in the graph
        int noOfNodes = adjacencyList.size();
        boolean[] visited = new boolean[noOfNodes];
        Arrays.fill(visited, false);

        List<String> q = new ArrayList<>();
        q.add(ini);
        visited[getIndex(ini, adjacencyList)] = true; // Mark initial vertex as visited

        while (!q.isEmpty()) {
            String current = q.remove(0);
            List<String> neighbors = adjacencyList.get(current);

            if (neighbors != null) {
                for (String neighbor : neighbors) {
                    if (!visited[getIndex(neighbor, adjacencyList)]) {
                        q.add(neighbor);
                        visited[getIndex(neighbor, adjacencyList)] = true;
                    }
                }
            }
        }

        // Check if all nodes are visited
        for (int i = 0; i < noOfNodes; i++) {
            if (!visited[i]) {
                return false;
            }
        }
        return true;
    }

    // Helper function to get the index of a vertex in the adjacency list
    private static int getIndex(String vertex, Map<String, List<String>> adjacencyList) {
        // This function should return the index of the vertex in the adjacency list
        // You need to implement this based on your specific adjacency list and vertex
        // identifiers
        int index = 0;
        for (String key : adjacencyList.keySet()) {
            if (key.equals(vertex)) {
                return index;
            }
            index++;
        }
        return index;
    }
}