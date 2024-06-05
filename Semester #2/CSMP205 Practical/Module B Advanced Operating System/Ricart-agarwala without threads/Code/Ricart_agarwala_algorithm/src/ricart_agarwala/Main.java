package ricart_agarwala;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Main {
    public static void main(String[] args) {
        RicartAgarwala m = new RicartAgarwala();
        m.run();
    }
}

class RicartAgarwala {
    private HashMap<String, Node> nameToObject = new HashMap<>();
    private int clock = 1;

    public void run() {
        inputFromFile();
        nodesWantToEnterCS();
        requestingCS();
    }

    public void inputFromFile() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
        fileChooser.setFileFilter(filter);
        int returnVal = fileChooser.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                int nodeCount = 0;
                while ((line = br.readLine()) != null) {
                    if (nodeCount % 2 == 0) {
                        // Nodes
                        String[] nodes = line.split(",");
                        for (String nodeName : nodes) {
                            Node n = new Node();
                            n.setUID(++nodeCount);
                            n.setName(nodeName.trim());
                            nameToObject.put(nodeName.trim(), n);
                        }
                    } else {
                        // Nodes wanting to enter CS
                        String[] nodes = line.split(",");
                        for (String nodeName : nodes) {
                            Node n = nameToObject.get(nodeName.trim());
                            if (n != null) {
                                n.setTimestamp(clock);
                                clock++;
                                n.setStatus(1);
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No file selected.");
        }
    }

    public void nodesWantToEnterCS() {
        for (Entry<String, Node> entry : nameToObject.entrySet()) {
            Node n = entry.getValue();
            System.out.println("Name: " + entry.getKey() + ", UID: " + n.getUID() + ", Timestamp: " + n.getTimestamp()
                    + ", Status: " + n.getStatus());
        }
    }

    public void requestingCS() {
        for (Entry<String, Node> entry : nameToObject.entrySet()) {
            if (entry.getValue().getStatus() == 1) {
                for (Entry<String, Node> entry1 : nameToObject.entrySet()) {
                    if (!entry1.getValue().getName().equals(entry.getValue().getName())) {
                        entry.getValue().requestList.add(entry1.getValue().getName());
                    }
                }
            }
        }

        for (Entry<String, Node> entry : nameToObject.entrySet()) {
            if (entry.getValue().getStatus() == 1) {
                for (Entry<String, Node> entry1 : nameToObject.entrySet()) {
                    if (!entry1.getValue().getName().equals(entry.getValue().getName())) {
                        entry1.getValue().requestFrom(entry.getValue());
                    }
                }
            }
        }
    }
}