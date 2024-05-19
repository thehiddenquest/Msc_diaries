import java.io.*;
import java.util.*;

public class Graph {
    private static final List<String> vertices = new ArrayList<>();
    private static final Map<String, Integer> vertexIndex = new HashMap<>();
    
    public static void main(String[] args) {
        String filename = "edges.txt";
        List<String> edges = readEdgesFromFile(filename);
        
        if (edges == null) {
            System.out.println("Error reading edges from file.");
            return;
        }

        // Step 1: Identify unique vertices
        identifyVertices(edges);
        
        // Step 2: Initialize adjacency matrix
        int[][] adjacencyMatrix = new int[vertices.size()][vertices.size()];
        
        // Step 3: Populate adjacency matrix
        populateAdjacencyMatrix(edges, adjacencyMatrix);
        
        // Step 4: Print adjacency matrix
        printAdjacencyMatrix(adjacencyMatrix);
    }
    
    private static List<String> readEdgesFromFile(String filename) {
        List<String> edges = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                edges.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return edges;
    }
    
    private static void identifyVertices(List<String> edges) {
        for (String edge : edges) {
            String[] verticesInEdge = edge.split(",");
            for (String vertex : verticesInEdge) {
                if (!vertexIndex.containsKey(vertex)) {
                    vertexIndex.put(vertex, vertices.size());
                    vertices.add(vertex);
                }
            }
        }
    }
    
    private static void populateAdjacencyMatrix(List<String> edges, int[][] adjacencyMatrix) {
        for (String edge : edges) {
            String[] verticesInEdge = edge.split(",");
            int i = vertexIndex.get(verticesInEdge[0]);
            int j = vertexIndex.get(verticesInEdge[1]);
            adjacencyMatrix[i][j] = 1;
        }
    }
    
    private static void printAdjacencyMatrix(int[][] adjacencyMatrix) {
        System.out.print("   ");
        for (String vertex : vertices) {
            System.out.print(vertex + " ");
        }
        System.out.println();
        
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            System.out.print(vertices.get(i) + " ");
            for (int j = 0; j < adjacencyMatrix[i].length; j++) {
                System.out.print(adjacencyMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}