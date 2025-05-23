package chandi_lamport;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {

//	Store the vertices from the input file 
	private final List<String> vertices = new ArrayList<>();
//	Maps the input vertices with an index
	private final Map<String, Integer> vertexIndex = new HashMap<>();
	private int[][] adjacencyMatrix;
//	Store the edges from the input file
	private List<String> edges = new ArrayList<>();

	public Graph(String filename) {

		edges = readEdgesFromFile(filename);

		if (edges == null) {
			System.out.println("Error reading edges from file.");
			return;
		}

		// Identify unique vertices
		identifyVertices(edges);

		// Initialize adjacency matrix

		adjacencyMatrix = new int[vertices.size()][vertices.size()];
		// Populate adjacency matrix
		populateAdjacencyMatrix(edges, adjacencyMatrix);
	}

//  Read the edges from the file as input
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

//	Maps the input vertices name with an index 
	private void identifyVertices(List<String> edges) {
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

//Create adjacency matrix 
	private void populateAdjacencyMatrix(List<String> edges, int[][] adjacencyMatrix) {
		for (String edge : edges) {
			String[] verticesInEdge = edge.split(",");
			int i = vertexIndex.get(verticesInEdge[0]);
			int j = vertexIndex.get(verticesInEdge[1]);
			adjacencyMatrix[i][j] = 1;
		}
	}

//  Print the created adjacency matrix
	public void printAdjacencyMatrix() {
		System.out.println("Adjacency matrix of given graph is : ");
		System.out.print("  ");
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
		System.out.println();
	}

// 	Perform BFS on the given graph 
	private Boolean BFS(String ini) {
		if (!vertexIndex.containsKey(ini)) {
			System.out.println(ini + " is not a valid vertex.");
			return false;
		}
		// Number of vertices in the graph
		int noOfNode = vertices.size();
		boolean[] visited = new boolean[noOfNode];
		Arrays.fill(visited, false);
		List<Integer> q = new ArrayList<>();
		int iniIndex = vertexIndex.get(ini);
		q.add(iniIndex);

		// Set source as visited
		visited[iniIndex] = true;

		int vis;
		while (!q.isEmpty()) {
			vis = q.get(0);
			// Dequeue
			q.remove(0);

			// For every adjacent vertex to the current vertex
			for (int i = 0; i < noOfNode; i++) {
				if (adjacencyMatrix[vis][i] == 1 && !visited[i]) {
					// Enqueue the adjacent node to the queue
					q.add(i);
					visited[i] = true;
				}
			}
		}
		for (int i = 0; i < noOfNode; i++) {
			if (!visited[i]) {
				// If the given vertex is not an initiator return false
				return false;
			}
		}
		// If the given vertex is an initiator return true
		return true;
	}

// Check a given vertex is initiator or not  	
	public void initiator(String ini) {
		if (BFS(ini)) {
			System.out.println(ini + " is an initiator");
		} else
			System.out.println(ini + " is not an initiator");
	}

// List all initiator vertices
	public void initiator() {
		System.out.println("Initiators are: ");
		for (String ini : vertices) {
			if (BFS(ini)) {
				System.out.println(ini + " is an initiator");
			}
		}
	}
}
