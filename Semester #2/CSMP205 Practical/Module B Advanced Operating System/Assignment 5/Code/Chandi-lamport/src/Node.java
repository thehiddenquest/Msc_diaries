
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Node implements Runnable {
    private String name;
    private Channel channel;
    private List<String> neighbor;
    private Boolean isInitiator = false;
    private Random random = new Random();
    private MessageType messageType = MessageType.TEXT;
    private HashMap<String, List<String>> sendMessages = new HashMap<>();
    private HashMap<String, List<String>> receivedMessages = new HashMap<>();
    private int markerSendCounter = 3;

    public Node(String name, Channel channel, List<String> neighbor) {
        this.name = name;
        this.channel = channel;
        this.neighbor = neighbor;
    }

    @Override
    public synchronized void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                if (isInitiator)
                    messageTypeChooser();
                int willSend = random.nextInt(10);
                if (messageType == MessageType.MARKER || willSend < 8)
                    sendMessage();
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
        if (messageType == MessageType.MARKER) {
            for (String receiverName : neighbor) {
                String message = messageGenerator(null, receiverName);
                System.out.println("Send Marker :: " + name + " is sending " + " marker " + " to " + receiverName);
                System.out.println("----------------------------------------------------------------");
                channel.deliver(message);
            }
            Thread.currentThread().interrupt();
        } else {
            int randomIndex = random.nextInt(neighbor.size());
            // Get the element at the random index
            String receiverName = neighbor.get(randomIndex);
            String messageContent = String.valueOf(random.nextInt(100));
            String message = messageGenerator(messageContent, receiverName);
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

    private void receivedMessage() {
        String message = channel.receive(name);
        if (message != null) {
            String[] messageContent = message.trim().split(":");

            if (messageContent[1].equals("MARKER")) {
                System.out.println(
                        "Received Marker :: " + name + " is received a marker from " + " from " + messageContent[0]);
                messageType = MessageType.MARKER;
                sendMessage();
            } else {
                if (receivedMessages != null && receivedMessages.keySet() != null) {
                    boolean exists = receivedMessages.keySet().stream()
                            .anyMatch(k -> k.equalsIgnoreCase(messageContent[0]));
                    System.out
                            .println("Received Message :: " + name + " received a message " + messageContent[2]
                                    + " from "
                                    + messageContent[0]);
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

    private String messageGenerator(String messageContent, String receiverName) {
        String message;
        message = name + ":" + messageType + ":" + messageContent + ":" + receiverName;
        return message;
    }

    private void delay(int miliSec) throws InterruptedException {
        if (!Thread.currentThread().isInterrupted())
            Thread.sleep(miliSec);
    }
}
