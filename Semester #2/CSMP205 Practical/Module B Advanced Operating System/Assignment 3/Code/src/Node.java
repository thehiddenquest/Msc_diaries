/* About Node
    The Node class represents a real-life simulation of distributed processes.
    Each node operates independently and lacks knowledge about other processes.
    It communicates by sending and receiving tokens through the channel.
 */

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Node implements Runnable {
    // Used objects
    private Messenger messenger;
    private Status status = Status.NONE;
    private Random random = new Random();

    // Used variables
    private String UID; //ID of a node
    private String name; // Name of a node
    private int criticalStateClock = 0; // Clock while in critical section
    private int afterCriticalStateClock = 0; // Clock after critical section
    private Boolean isRequested = false; // Is request for token

    // Used data structures
    private Queue<String> requestQueue = new LinkedList<>(); // Requesting queue for nodes who are requested

    public Node(String UID, String name) {
        this.UID = UID;
        this.name = name;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            if (afterCriticalStateClock == 0) { // Node is come out of a critical state
                if (status != Status.REQUEST_TOKEN && status != Status.HAVE_TOKEN) {
                    status = Status.NONE;
                }
            }
            changeStatus(); // change status
            checkClock(); // set status according to clock
            requestToken(); // request for token
            if (status == Status.HAVE_TOKEN) {
                if (criticalStateClock == 0) { // If it can send token
                    if (!requestQueue.isEmpty()) { // request queue is not empty
                        sendToken(); // release token
                    } else {
                        criticalStateClock += 5; // If request queue is empty then hold the token
                    }
                }
            }
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                Thread.currentThread().interrupt();
            }

        }
    }

    // Get name
    public String getName() {
        return this.name;
    }

    // Get UID
    public String getUID() {
        return UID;
    }

    // Get Status
    public Status getStatus() {
        return this.status;
    }

    // Set Status
    public void setStatus(Status status) {
        this.status = status;
    }

    // Set channel object
    public void setMessenger(Messenger messenger) {
        this.messenger = messenger;
    }

    // Add requesting node to the queue
    public void setRequest(String recipientID) {
        requestQueue.add(recipientID);
    }

    // Set received queue in own queue
    public void setRequestQueue(Queue<String> requestQueue) {
        this.requestQueue = requestQueue;
    }

    // Send token
    public void sendToken() {
        this.status = Status.AFTER_CRITICAL_STATE;
        afterCriticalStateClock = 5;
        messenger.releaseToken(this.UID, requestQueue); // Release token to channel
    }

    // Request Token
    private void requestToken() {
        if (status == Status.REQUEST_TOKEN && !isRequested) {
            MessageType msg = MessageType.REQUESTING;
            String message = name + ":" + UID + ":" + msg;
            messenger.send(message); // sending token request to the channel
            isRequested = true;
        }
    }

    // Receive Token
    public void receiveToken(Queue<String> requestQueue) {
        setStatus(Status.HAVE_TOKEN); // Update the status to HAVE_TOKEN
        criticalStateClock = random.nextInt((5 - 1) + 1) + 1; // Stay in critical state for 1 tick to 5 ticks;
        System.out.println("Information Message :: [ " + name + " ] is in Critical State.");
        setRequestQueue(requestQueue);
    }

    // Changing status internally
    private void changeStatus() {
        if (status == Status.NONE) {
            int number = random.nextInt(100); // Generate a random number between 0 and 99
            if (number < 25) { // 25% probability
                this.status = Status.REQUEST_TOKEN;
            }
        }
    }

    // Set status according to the clock
    private void checkClock() {
        if (afterCriticalStateClock != 0) { // If node is in after Critical state
            afterCriticalStateClock--;
        }
        if (criticalStateClock != 0) { // If node is in critical state
            // If still in critical state
            status = Status.HAVE_TOKEN;
            criticalStateClock--;
        }
        if (criticalStateClock == 0 && afterCriticalStateClock != 0) {
            // If just came out from critical state but still cannot request
            status = Status.AFTER_CRITICAL_STATE;
            isRequested = false;
        }
    }
}
