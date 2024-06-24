import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Graph {
    private String filePath;
    private Map<String, List<String>> adjacencyList = new HashMap<>();

    public Graph(String filePath) {
        this.filePath = filePath;
        populateAdjacencyList();
    }

    private void populateAdjacencyList() {
        try {
            File file = new File(filePath);
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
                            addEdge(src, null);
                        else
                            addEdge(src, dest);

                    }
                } else {
                    addEdge(src, null); // Handle single node case
                }
            }

            scanner.close();
            printGraph();
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private void addEdge(String src, String dest) {
        adjacencyList.putIfAbsent(src, new ArrayList<>());
        if (dest != null) {
            adjacencyList.get(src).add(dest);
        }
    }

    private void printGraph() {
        System.out.println();
        System.out.println();
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
    }

    private Boolean bfsForAdjacencyList(String initiator) {
        // Number of vertices in the graph
        int noOfNodes = adjacencyList.size();
        boolean[] visited = new boolean[noOfNodes];
        Arrays.fill(visited, false);

        List<String> q = new ArrayList<>();
        q.add(initiator);
        visited[getIndex(initiator)] = true; // Mark initiator vertex as visited

        while (!q.isEmpty()) {
            String current = q.remove(0);
            List<String> neighbors = adjacencyList.get(current);

            if (neighbors != null) {
                for (String neighbor : neighbors) {
                    if (!visited[getIndex(neighbor)]) {
                        q.add(neighbor);
                        visited[getIndex(neighbor)] = true;
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

    private int getIndex(String vertex) {
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

    public Map<String, List<String>> checkInitiator(String initiator) {
        boolean exists = adjacencyList.keySet().stream().anyMatch(k -> k.equalsIgnoreCase(initiator));
        if (exists) {
            if (bfsForAdjacencyList(initiator)) {
                System.out.println(initiator + " Can be an initiator.");
                return adjacencyList;
            } else
                System.out.println(initiator + " Cannot be an initiator.");
            return null;
        } else
            System.out.println("Node is not exists in the adjacency list.Please check");
        return null;
    }
}
