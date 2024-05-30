package TheadsCommunication;

import java.util.List;
import java.util.Random;

public class Node implements Runnable {
    private String id;
    private String name;
    private Message message;
    private List<Node> nodes;
    private String sendMessage;
    private int logicalClock = 0; // Logical clock value
    Random random = new Random();

    public Node(String id, String name, Message message, List<Node> nodes) {
        this.id = id;
        this.name = name;
        this.message = message;
        this.nodes = nodes;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (message) {
                String msg = message.getMessage(name);
                if (msg != null) {
                    // Extract logical clock value from received message
                    int colonIndex = msg.lastIndexOf(":");
                    if (colonIndex != -1) {
                        String logicalClockString = msg.substring(colonIndex + 1).trim();
                        int receivedLogicalClock = Integer.parseInt(logicalClockString);
                        logicalClock = Math.max(logicalClock, receivedLogicalClock) + 1;
                    } else {
                        System.out.println("Invalid message format: " + msg);
                    }
                    System.out.println(name + " received: " + msg + ", Logical Clock: " + logicalClock);
                } else {
                    // Message is null, node is waiting
                    message.nodeWaiting();
                    int canSend = random.nextInt(2); // 0 or 1
                    if (canSend == 1 || !message.hasPendingMessage()) {
                        // Create and send a message
                        createMessage();
                        Node recipient;
                        do {
                            recipient = nodes.get(random.nextInt(nodes.size()));
                        } while (recipient.name.equals(name));

                        // Increment logical clock before sending the message
                        logicalClock++;
                        message.setMessage(sendMessage + ": " + logicalClock, recipient.name);
                        System.out.println(name + " sent: " + sendMessage + " to " + recipient.name + ", Logical Clock: " + logicalClock);
                    } else {
                        // No message to send
                        System.out.println(name + ": No Message To Receive");
                    }
                }
            }

            try {
                Thread.sleep(1000); // Random delay between 0 and 999 milliseconds
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    private void createMessage() {
        sendMessage = name + " has sent you a message with id: " + id + ". This is my message";
    }
}