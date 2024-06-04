

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class Message {
    private ConcurrentHashMap<String, BlockingQueue<String>> messageQueues = new ConcurrentHashMap<>();

    public Message(List<String> nodeNames) {
        for (String nodeName : nodeNames) {
            messageQueues.put(nodeName, new LinkedBlockingQueue<>());
        }
    }

    public void setMessage(String message, String recipientName) {
        BlockingQueue<String> recipientQueue = messageQueues.get(recipientName);
        if (recipientQueue != null) {
            try {
                recipientQueue.put(message);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public String getMessage(String name) {
        BlockingQueue<String> queue = messageQueues.get(name);
        if (queue != null) {
            try {
                return queue.poll();
            } catch (Exception e) {
                Thread.currentThread().interrupt();
            }
        }
        return null;
    }
}