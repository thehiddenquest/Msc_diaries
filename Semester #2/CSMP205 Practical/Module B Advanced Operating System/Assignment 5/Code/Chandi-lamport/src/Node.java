import java.util.HashMap;
import java.util.List;

public class Node implements Runnable {
    private String name;
    private Channel channel;
    private List<String> neighbor;
    private Boolean isInitiator = false;
    private HashMap<String, List<String>> sendMessages;
    private HashMap<String, List<String>> receivedMessages;

    public Node(String name, Channel channel, List<String> neighbor) {
        this.name = name;
        this.channel = channel;
        this.neighbor = neighbor;
    }

    @Override
    public synchronized void run() {
        while (!Thread.currentThread().isInterrupted()) {

        }
        System.out.println("Node " + name + " is running on channel having");
    }

    public void setInitiatorStatus() {
        isInitiator = true;
    }

    public HashMap<String, List<String>> getSendMessages() {
        return sendMessages;
    }

    public HashMap<String, List<String>> getReceivedMessages() {
        return receivedMessages;
    }

    public String getName() {
        return name;
    }

    private void sendMessage() {

    }

    private void receivedMessage() {

    }
}
