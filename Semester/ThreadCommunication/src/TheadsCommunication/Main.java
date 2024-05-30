package TheadsCommunication;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Message message = new Message();
        List<Node> nodes = new ArrayList<>();

        Node node1 = new Node("1", "Node1", message, nodes);
        Node node2 = new Node("2", "Node2", message, nodes);
        Node node3 = new Node("3", "Node3", message, nodes);

        nodes.add(node1);
        nodes.add(node2);
        nodes.add(node3);

        Thread nodeThread1 = new Thread(node1);
        Thread nodeThread2 = new Thread(node2);
        Thread nodeThread3 = new Thread(node3);

        nodeThread1.start();
        nodeThread2.start();
        nodeThread3.start();
    }
}
