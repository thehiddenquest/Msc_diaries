import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class Node implements Runnable {
    // Objects from another class
    private final Message messageSystem;
    private final Random random = new Random();
    private Status status = Status.None;
    private MessageType messageType;

    // Used Variables
    private String id; //Store ID of a node
    private String name; // Store name of a node
    private String sMessage; // Message to be sent
    private int logicalClock = 0; // logical clock
    private int clockAfterCriticalState = 0; // Clock after critical state
    private int clockInCriticalState = 0; // clock in critical state
    private boolean isRequested = false;  // If a node  requested for critical state or not
    private int requestLogicalClock; // Timestamp while requesting for critical state

    //Used Structures
    private List<Node> nodes;       // Information of all the node in the system
    private Set<String> AcceptedCriticalState = new CopyOnWriteArraySet<>();  // To track who accepted our critical state request
    private Set<String> DeferredWhileCriticalState = new CopyOnWriteArraySet<>(); // To track requests while in critical state

    public Node(String id, String name, Message messageSystem, List<Node> nodes) {
        this.id = id;
        this.name = name;
        this.messageSystem = messageSystem;
        this.nodes = nodes;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            //After Node exited critical state and can request again for critical state
            if (clockAfterCriticalState == 0) {
                if (status != Status.Requesting) {
                    status = Status.None;
                }
            }
            setStatus(); // To set the status of node
            checkClock(); // To check the different clocks
            sendRequestMessage(); // Sending request critical state to everyone
            String receivedMessage = messageSystem.getMessage(name); // Receive message from other nodes
            if (receivedMessage == null) {
                System.out.println("Received Message [ " + status + " ] :: Name: " + name + " Received Message: No message to receive");
                // If no message is received but some already requested for critical state earlier
                if (status == Status.AfterCriticalState) {
                    if (!DeferredWhileCriticalState.isEmpty()) {
                        System.out.println(name + " exiting critical state.");
                        sendGO_AHEADMessage(); // Send those nodes GO_AHEAD
                        DeferredWhileCriticalState.clear(); // Clear the deferred List while is populated while in critical state
                    }
                }
            } else { // If some message is received
                logicalClock++; // Increments logical clock
                String receivedMessageParts[] = receivedMessage.split(":");
                System.out.println("Received Message[ " + status + " ]:: Name: " + name + " Received Message: " + receivedMessage +
                        " Received from : " + receivedMessageParts[0]);
                System.out.println();
                if (receivedMessageParts[2].equalsIgnoreCase("GO_AHEAD")) {
                    // If received message is GO_AHEAD them that node is permitting this node to go in critical state
                    AcceptedCriticalState.add(receivedMessageParts[0]);
                }

                if (AcceptedCriticalState.size() == nodes.size() - 1) {
                    //If all other nodes are agree for critical state
                    System.out.println(name + " is in Critical State");
                    status = Status.InCriticalState; // Changing state to critical state
                    clockInCriticalState = random.nextInt((5 - 1) + 1) + 1; // Stay in critical state for 1 tick to 5 tick
                    clockAfterCriticalState = clockInCriticalState + 4; // can't request to be in critical state for next 10 more ticks
                    AcceptedCriticalState.clear(); // Remove everyone who accepted request
                }
                // Behavior of a node according to it's status
                if (status == Status.None || status == Status.AfterCriticalState) {
                    messageType = MessageType.GO_AHEAD;
                }
                if (status == Status.Requesting) {
                    if (Integer.parseInt(receivedMessageParts[3]) < requestLogicalClock) {
                        messageType = MessageType.GO_AHEAD;

                    } else if (Integer.parseInt(receivedMessageParts[3]) == requestLogicalClock) {
                        if (Integer.parseInt(receivedMessageParts[1]) < Integer.parseInt(id)) {
                            messageType = MessageType.GO_AHEAD;
                        } else {
                            DeferredWhileCriticalState.add(receivedMessageParts[0]);
                            messageType = null;
                        }
                    } else {
                        logicalClock = Math.max(logicalClock, Integer.parseInt(receivedMessageParts[3])) + 1;
                        DeferredWhileCriticalState.add(receivedMessageParts[0]);
                        messageType = null;
                    }

                }
                if (status == Status.InCriticalState) {
                    DeferredWhileCriticalState.add(receivedMessageParts[0]);
                } else {
                    if (status == Status.AfterCriticalState) {
                        if (!DeferredWhileCriticalState.isEmpty()) {
                            System.out.println(name + " exiting critical state.");
                            sendGO_AHEADMessage();
                            DeferredWhileCriticalState.clear();
                        }
                    } else if (messageType != null) {
                        sendResponseMessage(receivedMessageParts[0]);
                    }
                }
            }

            try {
                Thread.sleep(1000); // Sleep for 1 second
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }


    private void setStatus() {
        int number = random.nextInt(22); // Generate a random number between 0 and 20
        if (number >= 13 && number < 21) {
            // A node has 38.10% chance to change its status to Requesting
            this.status = Status.Requesting;
        }
    }

    private void checkClock() {
        if (clockAfterCriticalState != 0) {
            clockAfterCriticalState--;
        }
        if (clockInCriticalState != 0) {
            //If still in critical state
            status = Status.InCriticalState;
            clockInCriticalState--;
        }
        if (clockInCriticalState == 0 && clockAfterCriticalState != 0) {
            //If just came out from critical state but still cannot request
            status = Status.AfterCriticalState;
            isRequested = false;
        }
    }

    private void sendResponseMessage(String recipientNodeName) {
        //Send any response message to specific recipient
        sMessage = name + ":" + id + ":" + messageType + ":" + logicalClock;
        messageSystem.setMessage(sMessage, recipientNodeName);
        System.out.println("Send Message :: Name: " + name + " id: " + id + " status: " + status + " message sent: "
                + sMessage + " to: " + recipientNodeName + " at: " + logicalClock);
        logicalClock++;
    }


    private void sendRequestMessage() {
        // To send request message for critical state
        if (status == Status.Requesting && !isRequested) {
            for (Node sendNode : nodes) {
                if (!sendNode.name.equalsIgnoreCase(name)) {
                    String rMessage = name + ":" + id + ":" + MessageType.REQUEST + ":" + logicalClock;
                    messageSystem.setMessage(rMessage, sendNode.name);
                    System.out.println("Request Message :: Name: " + name + " id: " + id + " status: " + status + " message sent: "
                            + rMessage + " to: " + sendNode.name + " at: " + logicalClock);
                }
            }
            requestLogicalClock = logicalClock; // Storing the actual timestamp of requesting
            isRequested = true;
        }
    }

    private void sendGO_AHEADMessage() {
        // To send agree to all the node who requested while this node is in critical state
        for (String sendNode : DeferredWhileCriticalState) {
            String rMessage = name + ":" + id + ":" + MessageType.GO_AHEAD + ":" + logicalClock;
            System.out.println("Send Message :: Name: " + name + " id: " + id + " status: " + status + " message sent: "
                    + rMessage + " to: " + sendNode + " at: " + logicalClock);
            messageSystem.setMessage(rMessage, sendNode);
            logicalClock++;
        }
    }
    
}
