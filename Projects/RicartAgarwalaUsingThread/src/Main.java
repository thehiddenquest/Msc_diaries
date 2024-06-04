import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> nodeNames = List.of("Node1", "Node2", "Node3", "Node4");
        Message messageSystem = new Message(nodeNames);

        List<Node> nodes = new ArrayList<>();
        for (String nodeName : nodeNames) {
            nodes.add(new Node(nodeName.substring(nodeName.length() - 1), nodeName, messageSystem, nodes));
        }

        for (Node node : nodes) {
            new Thread(node).start();
        }
    }
}