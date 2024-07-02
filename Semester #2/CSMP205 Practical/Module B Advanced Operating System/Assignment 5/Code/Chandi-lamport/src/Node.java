
/*ABOUT NODE CLASS
 * The Node class in Java models a system or process within a distributed environment,
 *  designed to communicate with neighboring nodes via a shared Channel.
 *  Each Node instance runs in its own thread (Runnable), implementing
 *  logic to send and receive messages, manage message types (TEXT or MARKER),
 *  and maintain message histories (sendMessages and receivedMessages). 
 * Key attributes include its name, neighbor nodes list, initiator status (isInitiator),
 *  and a random number generator for message scheduling. The class supports dynamic
 *  behavior where nodes can act as initiators, exchange messages, and react to incoming
 *  marker messages to record global states in distributed systems scenarios.
 */
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Node implements Runnable {
    private String name; // Name of the node
    private Channel channel; // Communication channel for the node
    private List<String> neighbor; // List of neighboring nodes
    private Boolean isInitiator = false; // Flag to check if the node is the initiator
    private Random random = new Random(); // Random number generator for message sending
    private MessageType messageType = MessageType.TEXT; // Type of message (TEXT or MARKER)
    private HashMap<String, List<String>> sendMessages = new HashMap<>(); // Messages sent by this node
    private HashMap<String, List<String>> receivedMessages = new HashMap<>(); // Messages received by this node
    private int markerSendCounter = 3; // Counter to control marker message sending

    // Constructor to initialize the node
    public Node(String name, Channel channel, List<String> neighbor) {
        this.name = name;
        this.channel = channel;
        this.neighbor = neighbor;
    }

    // Main logic of the node, to be executed in a separate thread
    @Override
    public synchronized void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                // If the node is the initiator, choose the type of message to send
                if (isInitiator)
                    messageTypeChooser();

                // Decide whether to send a message based on random chance
                int willSend = random.nextInt(10 + 1);
                if (messageType == MessageType.MARKER || willSend < 8)
                    sendMessage();

                // If the thread is not interrupted, wait for a random period and then receive a
                // message
                if (!Thread.currentThread().isInterrupted()) {
                    delay(random.nextInt(500) + 1);
                    receivedMessage();
                    delay(random.nextInt(500) + 1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Set the initiator status for the node
    public void setInitiatorStatus() {
        isInitiator = true;
    }

    // Get the messages sent by this node
    public HashMap<String, List<String>> getSendMessages() {
        return sendMessages;
    }

    // Get the messages received by this node
    public HashMap<String, List<String>> getReceivedMessages() {
        return receivedMessages;
    }

    // Get the name of the node
    public String getName() {
        return name;
    }

    // Method to send a message to a random neighbor
    private void sendMessage() {
        if (messageType == MessageType.MARKER) {
            // If the message type is MARKER, send it to all neighbors
            for (String receiverName : neighbor) {
                String message = messageGenerator(null, receiverName);
                System.out.println("Send Marker :: " + name + " is sending " + " marker " + " to " + receiverName);
                System.out.println("----------------------------------------------------------------");
                channel.deliver(message);
            }
            // Interrupt the thread after sending marker messages
            Thread.currentThread().interrupt();
        } else if (!neighbor.isEmpty()) {
            // If the message type is TEXT, send it to a random neighbor
            int randomIndex = random.nextInt(neighbor.size());
            String receiverName = neighbor.get(randomIndex);
            String messageContent = String.valueOf(random.nextInt(100));
            String message = messageGenerator(messageContent, receiverName);
            // Update the sent messages map
            if (sendMessages != null && sendMessages.keySet() != null) {
                boolean exists = sendMessages.keySet().stream()
                        .anyMatch(k -> k.equalsIgnoreCase(receiverName));
                if (exists) {
                    List<String> temp = sendMessages.get(receiverName);
                    if (temp != null) {
                        temp.add(messageContent);
                    } else {
                        temp = new LinkedList<>();
                        temp.add(messageContent);
                    }
                    sendMessages.put(receiverName, temp);
                } else {
                    List<String> temp = new LinkedList<>();
                    temp.add(messageContent);
                    sendMessages.put(receiverName, temp);
                }
            } else {
                List<String> temp = new LinkedList<>();
                temp.add(messageContent);
                sendMessages.put(receiverName, temp);
            }
            System.out.println("Send Message :: " + name + " is sending " + messageContent + " to " + receiverName);
            channel.deliver(message);
        }
    }

    // Method to receive a message from the channel
    private void receivedMessage() {
        String message = channel.receive(name);
        if (message != null) {
            String[] messageContent = message.trim().split(":");

            // If the received message is a MARKER, update the message type and send marker
            // messages
            if (messageContent[1].equals("MARKER")) {
                System.out.println(
                        "Received Marker :: " + name + " is received a marker from " + " from " + messageContent[0]);
                messageType = MessageType.MARKER;
                sendMessage();
            } else {
                // If the received message is a TEXT, update the received messages map
                if (receivedMessages != null && receivedMessages.keySet() != null) {
                    boolean exists = receivedMessages.keySet().stream()
                            .anyMatch(k -> k.equalsIgnoreCase(messageContent[0]));
                    System.out.println("Received Message :: " + name + " received a message " + messageContent[2]
                            + " from " + messageContent[0]);
                    if (exists) {
                        List<String> temp = receivedMessages.get(messageContent[0]);
                        if (temp != null) {
                            temp.add(messageContent[2]);
                        } else {
                            temp = new LinkedList<>();
                            temp.add(messageContent[2]);
                        }
                        receivedMessages.put(messageContent[0], temp);
                    } else {
                        List<String> temp = new LinkedList<>();
                        temp.add(messageContent[2]);
                        receivedMessages.put(messageContent[0], temp);
                    }
                } else {
                    List<String> temp = new LinkedList<>();
                    temp.add(messageContent[2]);
                    receivedMessages.put(messageContent[0], temp);
                }
            }
        }
    }

    // Method to choose the type of message to send (TEXT or MARKER)
    private void messageTypeChooser() {
        if (markerSendCounter <= 0) {
            int chooser = random.nextInt(10);
            if (chooser < 8)
                messageType = MessageType.TEXT;
            else
                messageType = MessageType.MARKER;
        } else {
            markerSendCounter--;
        }
    }

    // Method to generate a message string with the given content and receiver name
    private String messageGenerator(String messageContent, String receiverName) {
        String message;
        message = name + ":" + messageType + ":" + messageContent + ":" + receiverName;
        return message;
    }

    // Method to introduce a delay (in milliseconds) in the thread execution
    private void delay(int miliSec) throws InterruptedException {
        if (!Thread.currentThread().isInterrupted())
            Thread.sleep(miliSec);
    }
}