
/*ABOUT CHANNEL CLASS
 * The Channel class in Java represents a communication channel between
 *  nodes in a distributed system. It uses a HashMap to manage communication
 *  queues (Queue<String>) for each receiver node. Messages can be delivered
 *  (deliver) to a specific receiver's queue based on the message content,
 *  and messages can be received (receive) from a particular receiver's queue.
 *  The class ensures that messages are queued appropriately and retrieved in
 *  a FIFO manner. Additionally, it provides a method (getCommunicationChannel)
 *  to access the entire communication channel map, useful for debugging or logging
 *  purposes in distributed system simulations or implementations. This class
 *  facilitates asynchronous communication between nodes, essential for exchanging
 *  messages and maintaining synchronization in distributed environments.
 */
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Channel {
    // HashMap to store communication channels for each receiver
    private HashMap<String, Queue<String>> communicationChannel = new HashMap<>();

    // Method to deliver a message to the appropriate receiver's queue
    public void deliver(String message) {
        int lastIndex = message.lastIndexOf(":"); // Find the last occurrence of ':' in the message
        String receiverName = message.substring(lastIndex + 1); // Extract receiver's name from the message

        // Check if the communication channel and its keys are not null
        if (communicationChannel != null && communicationChannel.keySet() != null) {
            // Check if the receiver's name exists in the communication channel
            boolean exists = communicationChannel.keySet().stream()
                    .anyMatch(k -> k.equalsIgnoreCase(receiverName));
            if (exists) {
                // If the receiver's queue exists, add the message to it
                Queue<String> temp = communicationChannel.get(receiverName);
                if (temp != null) {
                    temp.offer(message); // Add the message to the queue
                } else {
                    // If the queue is null, create a new queue and add the message to it
                    temp = new LinkedList<>();
                    temp.offer(message);
                }
                // Update the communication channel with the receiver's queue
                communicationChannel.put(receiverName, temp);
            } else {
                // If the receiver's queue does not exist, create a new queue and add the
                // message to it
                Queue<String> temp = new LinkedList<>();
                temp.offer(message);
                // Add the new queue to the communication channel for the receiver
                communicationChannel.put(receiverName, temp);
            }
        } else {
            // If the communication channel is null, create a new queue and add the message
            // to it
            Queue<String> temp = new LinkedList<>();
            temp.offer(message);
            // Add the new queue to the communication channel for the receiver
            communicationChannel.put(receiverName, temp);
        }
    }

    // Method to receive a message from the appropriate receiver's queue
    public String receive(String receiverName) {
        // Check if the receiver's name exists in the communication channel
        boolean exists = communicationChannel.keySet().stream()
                .anyMatch(k -> k.equalsIgnoreCase(receiverName));
        if (exists) {
            // Get the queue for the receiver
            Queue<String> temp = communicationChannel.get(receiverName);
            // If the queue is not null and not empty, poll a message from it
            if (temp != null && !temp.isEmpty()) {
                String message = temp.poll(); // Retrieve and remove the head of the queue
                return message; // Return the message
            }
        }
        return null; // Return null if no message is available
    }

    // Method to get the communication channel (for debugging or logging purposes)
    public HashMap<String, Queue<String>> getCommunicationChannel() {
        return communicationChannel;
    }
}