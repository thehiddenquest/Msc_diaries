package chandilamport;

public class mainframe {
	public static void main(String args[]) {
		String filename = "C:\\Users\\SW205\\Documents\\G1.txt";
		Graph graph = new Graph(filename);
		graph.printGraph();
//		graph.initiator(6);
		graph.initiator();
	}
}
