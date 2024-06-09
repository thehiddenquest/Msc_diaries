/* About Messenger class
    In the Messenger class, we utilize it as a channel representing a logical ring.
     We implement this logical ring using a circular queue.
     Acting as a channel, it facilitates the delivery of request messages, tokens, and
     the request queue where nodes request tokens.
     The circular queue or logical ring channel only sends nodes from the sender to the recipient node.
 */

import java.util.List;
import java.util.Queue;

public class Messenger {

    private CircularQueue<Node> nodes; // Logical ring channel of nodes
    private String senderName; // Sender's name in the channel
    private String recipientName; // Recipient's name in the channel

    public Messenger(List<Node> nodeNames) {
        nodes = new CircularQueue<>(nodeNames.size() + 1);
        for (Node nodeName : nodeNames) {
            nodes.enqueue(nodeName); // Set nodes in the channel
        }
    }

    // Token request to the token holder
    public synchronized void send(String message) { // Synchronized for thread safety
        String[] messageContent = message.split(":");
        boolean nodeFound = false; // Requesting Node is found
        boolean requestSend = false; // Token requests are sent to the sender.
        senderName = null; // Reset senderName
        recipientName = null; // Reset recipientName

        while (!requestSend) { // While Token request is not sent
            for (Node node : nodes) {
                if (node.getUID().equals(messageContent[1]) && !nodeFound) {
                    // Token requesting node found
                    senderName = node.getName();
                    nodeFound = true;
                }
                if (nodeFound && node.getStatus() == Status.HAVE_TOKEN) { // Token Holder node found
                    recipientName = node.getName();
                    System.out.println("Request Message :: [ " + senderName + " ] Requesting Token From [ " + recipientName + " ].");
                    node.setRequest(messageContent[1]); // Set node name in the requesting queue of token holder
                    requestSend = true; // Token request is sent
                    break; // Exit the loop after sending the request
                }
            }
        }
    }

    // Sending token to the next token holder (first element in the requesting queue)
    public synchronized void releaseToken(String senderUID, Queue<String> requestQueue) { // Synchronized for thread safety
        boolean nodeFound = false;

        String priorityRecipientUID = requestQueue.poll(); // Getting the first node from the queue
        for (Node node : nodes) {
            if (node.getUID().equals(senderUID) && !nodeFound) {
                // Token Holder node found
                senderName = node.getName();
                nodeFound = true;
            } else if (nodeFound && node.getUID().equals(priorityRecipientUID)) {
                // Next Token holder node found
                recipientName = node.getName();
                System.out.println("Send Message :: [ " + senderName + " ] Sending Token To [ " + recipientName + " ].");
                node.receiveToken(requestQueue); // Releasing token to the next token holder
                break;
            }
        }
    }
}
