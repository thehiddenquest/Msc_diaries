package chandi_lamport;


import java.awt.FileDialog;
import java.awt.Frame;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Main_Frame {
	public static void main(String[] args) {
		String filename = openFileDialog();
		if (filename == null) {
			System.out.println("No file selected.");
			return;
		}
		if (!filename.endsWith(".graph")) {
			System.out.println("Error: The selected file is not a .graph file.");
			return;
		}
		Graph graph = new Graph(filename);
		graph.printAdjacencyMatrix();

		Scanner scanner = new Scanner(System.in);

		int option;

		do {
			System.out.println();
			System.out.println("Menu:");
			System.out.println("1. Find initiator with specified name");
			System.out.println("2. Find initiator without specifying name");
			System.out.println("0. Exit");
			System.out.print("Enter your choice: ");

			option = scanner.nextInt();
			scanner.nextLine(); // Consume newline character

			switch (option) {
			case 1:
				System.out.print("Enter initiator name: ");
				String initiatorName = scanner.nextLine();
				graph.initiator(initiatorName);
				break;
			case 2:
				graph.initiator();
				break;
			case 0:
				System.out.println("Exiting...");
				break;
			default:
				System.out.println("Invalid option. Please choose again.");
				break;
			}
		} while (option != 0);

		scanner.close();
		return;

	}

	private static String openFileDialog() {
//		JFileChooser fileChooser = new JFileChooser();
//		FileNameExtensionFilter filter = new FileNameExtensionFilter("Graph files", "graph");
//		fileChooser.setFileFilter(filter);
//		int returnValue = fileChooser.showOpenDialog(null);
//		if (returnValue == JFileChooser.APPROVE_OPTION) {
//			return fileChooser.getSelectedFile().getAbsolutePath();
//		}
//		return null;
        FileDialog fd = new FileDialog((Frame) null, "Open", FileDialog.LOAD);
        fd.setFilenameFilter((dir, name) -> name.endsWith(".graph"));
        fd.setVisible(true);
        String filename = fd.getFile();
		// Validate file extension
		if (!filename.endsWith(".graph")) {
			System.out.println("Error: The selected file is not a .graph file.");
			filename = null;
		}
		if (filename != null) {
            return fd.getDirectory() + filename;
        }
		return null;
	}
}
