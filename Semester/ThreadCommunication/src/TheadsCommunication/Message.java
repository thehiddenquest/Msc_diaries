package TheadsCommunication;

public class Message {
    private String message;
    private String recipientName;
    private static int numNodesWaiting = 0; // Track the number of nodes waiting
    private static boolean messageAvailable = false;

    public synchronized void setMessage(String message, String recipientName) {
        while (hasPendingMessage()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        this.message = message;
        this.recipientName = recipientName;
        messageAvailable = true;
        numNodesWaiting = 0; // Reset the count of waiting nodes when a message is set
        notifyAll(); // Notify waiting threads that a message is available
    }

    public synchronized String getMessage(String name) {
        if (name.equals(recipientName)) {
            messageAvailable = false;
            notifyAll(); // Notify waiting threads that the message has been received
            return message;
        }
        return null;
    }
    
    public synchronized boolean hasPendingMessage() {
        return messageAvailable;
    }
    
    public synchronized void nodeWaiting() {
        numNodesWaiting++;
    }
    
    public synchronized void nodeReady() {
        numNodesWaiting--;
    }
}