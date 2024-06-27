import java.awt.FileDialog;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        List<Thread> threads = new LinkedList<>();
        List<Node> nodes = new LinkedList<>();
        Channel channel = new Channel();
        String filePath = openFileDialog();
        if (filePath == null) {
            System.out.println("No file selected");
            System.exit(0);
        }
        Graph graph = new Graph(filePath);
        String initiator = JOptionPane.showInputDialog("Enter initiator:");
        Map<String, List<String>> nodeNames = graph.checkInitiator(initiator);
        if (nodeNames != null) {
            System.out.println();
            System.out.println("System Log ::::\n");
            // Create node with name initiator and search it from map
            Node node = new Node(initiator, channel, nodeNames.get(initiator));
            Thread thread = new Thread(node);
            nodes.add(node);
            threads.add(thread);
            thread.start();
            node.setInitiatorStatus();
            System.out.println("Information Message :: " + initiator + " is now initiator");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (String name : nodeNames.keySet()) {
                if (!name.equals(initiator)) {
                    Node n = new Node(name, channel, nodeNames.get(name));
                    Thread t = new Thread(n);
                    nodes.add(n);
                    threads.add(t);
                    t.start();
                }
            }
            for (Thread td : threads) {
                try {
                    td.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println();
            System.out.println("Global Snapshot ::::");
            for (Node n : nodes) {
                System.out.println("\nLocal State Recording of " + n.getName() + " ::");
                HashMap<String, List<String>> sendMessage = n.getSendMessages();
                System.out.println("-->Messages delivered: ");
                if (!sendMessage.isEmpty()) {
                    for (Map.Entry<String, List<String>> sendMessages : sendMessage.entrySet()) {
                        String key = sendMessages.getKey();
                        List<String> values = sendMessages.getValue();
                        System.out.print("To:  " + key + "  { ");
                        for (int i = 0; i < values.size(); i++) {
                            System.out.print(values.get(i));
                            if (i < values.size() - 1) {
                                System.out.print(" , ");
                            }
                        }
                        System.out.println(" }");
                    }
                } else {
                    System.out.println("null");
                }
                HashMap<String, List<String>> receivedMessage = n.getReceivedMessages();
                System.out.println("-->Messages received: ");
                if (!receivedMessage.isEmpty()) {
                    for (Map.Entry<String, List<String>> receivedMessages : receivedMessage.entrySet()) {
                        String key = receivedMessages.getKey();
                        List<String> values = receivedMessages.getValue();
                        System.out.print("From:  " + key + "  { ");
                        for (int i = 0; i < values.size(); i++) {
                            System.out.print(values.get(i));
                            if (i < values.size() - 1) {
                                System.out.print(" , ");
                            }
                        }
                        System.out.println(" }");
                    }
                } else {
                    System.out.println("null");
                }
            }
            System.out.println("\nCommunication Channel State ::");
            HashMap<String, Queue<String>> communicationChannel = channel.getCommunicationChannel();
            for (Map.Entry<String, Queue<String>> specificChannel : communicationChannel.entrySet()) {
                String receiverName = specificChannel.getKey();
                HashMap<String, List<String>> tempHashMap = new HashMap<>();
                Queue<String> tempQueue = specificChannel.getValue();
                for (String message : tempQueue) {
                    String[] splitMessage = message.split(":");
                    String senderName = splitMessage[0];
                    String messageContentType = splitMessage[1];
                    if (messageContentType.equalsIgnoreCase("TEXT")) {
                        if (tempHashMap.containsKey(senderName)) {
                            tempHashMap.get(senderName).add(splitMessage[2]);
                        } else {
                            List<String> tempList = new ArrayList<>();
                            tempList.add(splitMessage[2]);
                            tempHashMap.put(senderName, tempList);
                        }
                    }
                }
                for (Map.Entry<String, List<String>> tempNode : tempHashMap.entrySet()) {
                    String senderName = tempNode.getKey();
                    List<String> values = tempNode.getValue();
                    System.out.print("\n Channel :: " + senderName + " -- " + receiverName + " :  { ");
                    for (int i = 0; i < values.size(); i++) {
                        System.out.print(values.get(i));
                        if (i < values.size() - 1) {
                            System.out.print(" , ");
                        }
                    }
                    System.out.println(" }");
                }

            }

            System.exit(0);
        }
    }

    public static String openFileDialog() {
        FileDialog fd = new FileDialog((Frame) null, "Open", FileDialog.LOAD);
        fd.setVisible(true);
        String directory = fd.getDirectory();
        String file = fd.getFile();
        if (directory != null && file != null) {
            return directory + file;
        }
        return null;
    }
}
