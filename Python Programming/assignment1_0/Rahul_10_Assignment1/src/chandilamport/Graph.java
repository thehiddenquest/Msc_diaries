package chandilamport;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Graph {
	private int noOfNode;
	private int[][] adjMat;

	public Graph(String fileName) {
		File fileN = new File(fileName);
		inputGraph(fileN);
	}

	private void inputGraph(File file) {
		try {
			int i = 0;
			Scanner scanner = new Scanner(file);
			noOfNode = scanner.nextInt();
			System.out.println(noOfNode);
			adjMat = new int[noOfNode][noOfNode];
			scanner.nextLine();
			while (scanner.hasNextLine()) {
				String[] lines = scanner.nextLine().split("[,|\\s]+");

				for (int j = 0; j < lines.length; j++) {
					adjMat[i][j] = Integer.parseInt(lines[j]);
				}
				i++;
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void printGraph() {
		for (int i = 0; i < noOfNode; i++) {
			for (int j = 0; j < noOfNode; j++) {
				System.out.print(adjMat[i][j] + " ");
			}
			System.out.println("");
		}
	}

	public void initiator(int ini) {
		boolean[] visited = new boolean[noOfNode];
		Arrays.fill(visited, false);
		List<Integer> q = new ArrayList<>();
		q.add(ini);

		// Set source as visited
		visited[ini] = true;

		int vis;
		while (!q.isEmpty()) {
			vis = q.get(0);
			q.remove(q.get(0));

			// For every adjacent vertex to
			// the current vertex
			for (int i = 0; i < noOfNode; i++) {
				if (adjMat[vis][i] == 1 && (!visited[i])) {

					// Push the adjacent node to
					q.add(i);
					visited[i] = true;
				}
			}
		}
		for (int i = 0; i < noOfNode; i++) {
			if (!visited[i]) {
				System.out.println(ini + " is not an initiator");
				return;
			}
		}
		System.out.println(ini + " is an initiator");
	}

	public void initiator() {
		for(int i = 0; i<noOfNode ; i++) {
			int ini = i;
		 boolean[] visited = new boolean[noOfNode];
	        Arrays.fill(visited, false);
	        List<Integer> q = new ArrayList<>();
	        q.add(ini);
	 
	        // Set source as visited
	        visited[ini] = true;
	 
	        int vis;
	        while (!q.isEmpty())
	        {
	            vis = q.get(0);
	            q.remove(q.get(0));
	 
	            // For every adjacent vertex to
	            // the current vertex
	            for(int k = 0; k < noOfNode; k++)
	            {
	                if (adjMat[vis][k] == 1 && (!visited[k]))
	                {
	                     
	                    // Push the adjacent node to
	                    q.add(k);
	                    visited[k] = true;
	                }
	            }
	        }
	        for(int j = 0;j<noOfNode;j++) {
	        	if(!visited[j]) {
	        		return ;
	        	}
	        }
	        System.out.println(ini+" is an initiator");
	}
			
	}
}
