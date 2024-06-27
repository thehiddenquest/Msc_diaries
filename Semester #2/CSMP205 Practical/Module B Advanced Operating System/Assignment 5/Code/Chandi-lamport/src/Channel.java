import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Channel {
    private HashMap<String, Queue<String>> communicationChannel = new HashMap<>();

    public void deliver(String message) {
        int lastIndex = message.lastIndexOf(":");
        // Extract receiverName using substring
        String receiverName = message.substring(lastIndex + 1);
        if (communicationChannel != null && communicationChannel.keySet() != null) {
            boolean exists = communicationChannel.keySet().stream()
                    .anyMatch(k -> k.equalsIgnoreCase(receiverName));
            if (exists) {
                Queue<String> temp = communicationChannel.get(receiverName);
                if (temp != null) {
                    temp.offer(message);
                } else {
                    temp = new LinkedList<>();
                    temp.offer(message);
                }
                communicationChannel.put(receiverName, temp);
            } else {
                Queue<String> temp = new LinkedList<>();
                temp.offer(message);
                communicationChannel.put(receiverName, temp);
            }
        } else {
            Queue<String> temp = new LinkedList<>();
            temp.offer(message);
            communicationChannel.put(receiverName, temp);
        }
    }

    public String receive(String receiverName) {
        boolean exists = communicationChannel.keySet().stream()
                .anyMatch(k -> k.equalsIgnoreCase(receiverName));
        if (exists) {
            Queue<String> temp = communicationChannel.get(receiverName);
            if (temp != null && !temp.isEmpty()) {
                String message = temp.poll();
                return message;
            }
        }
        return null;
    }

    public HashMap<String, Queue<String>> getCommunicationChannel() {
        return communicationChannel;
    }
}
