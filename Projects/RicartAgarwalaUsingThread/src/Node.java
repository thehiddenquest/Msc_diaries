import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class Node implements Runnable {
    private final Message messageSystem;
    private final Random random = new Random();
    private Status status = Status.None;
    private MessageType messageType;

    private String id;
    private String name;
    private String sMessage;
    private int logicalClock = 0;
    private int clockAfterCriticalState = 0;
    private int clockInCriticalState = 0;
    private boolean isRequested = false;
    private int requestLogicalClock;


    private List<Node> nodes;
    private Set<String> AcceptedCriticalState = new CopyOnWriteArraySet<>();
    private Set<String> DeferredWhileCriticalState = new CopyOnWriteArraySet<>();

    public Node(String id, String name, Message messageSystem, List<Node> nodes) {
        this.id = id;
        this.name = name;
        this.messageSystem = messageSystem;
        this.nodes = nodes;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            if (clockAfterCriticalState == 0) {
                if (status != Status.Requesting) {
                    status = Status.None;
                }
            }
            setStatus();
            checkClock();
            sendRequestMessage();
            String receivedMessage = messageSystem.getMessage(name);
            if (receivedMessage == null) {
                System.out.println("Received Message [ " + status + " ] :: Name: " + name + " Received Message: No message to receive");
                if(status == Status.AfterCriticalState){
                    if (!DeferredWhileCriticalState.isEmpty()) {
                        System.out.println(name + " exiting critical state.");
                        sendGO_AHEADMessage();
                        DeferredWhileCriticalState.clear();
                    }
                }
            } else {
                logicalClock++;
                String receivedMessageParts[] = receivedMessage.split(":");
                System.out.println("Received Message[ " + status + " ]:: Name: " + name + " Received Message: " + receivedMessage +
                        " Received from : " + receivedMessageParts[0]);
                System.out.println();
                if (receivedMessageParts[2].equalsIgnoreCase("GO_AHEAD")) {
                    AcceptedCriticalState.add(receivedMessageParts[0]);
                }

                if (AcceptedCriticalState.size() == nodes.size() - 1) {
                    System.out.println(name + " is in Critical State");
                    status = Status.InCriticalState;
                    clockInCriticalState = random.nextInt((5 - 1) + 1) + 1; // Stay in critical state for 1 tick to 5 tick
                    clockAfterCriticalState = clockInCriticalState + 4; // can't request to be in critical state for next 10 more ticks
                    AcceptedCriticalState.clear(); // Remove everyone who accepted request
                }

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
            this.status = Status.Requesting;
        }
    }

    private void checkClock() {
        if (clockAfterCriticalState != 0) {
            clockAfterCriticalState--;
        }
        if (clockInCriticalState != 0) {
            status = Status.InCriticalState;
            clockInCriticalState--;
        }
        if (clockInCriticalState == 0 && clockAfterCriticalState != 0) {
            status = Status.AfterCriticalState;
            isRequested = false;
        }
    }

    private void sendResponseMessage(String recipientNodeName) {
        sMessage = name + ":" + id + ":" + messageType + ":" + logicalClock;
        messageSystem.setMessage(sMessage, recipientNodeName);
        System.out.println("Send Message :: Name: " + name + " id: " + id + " status: " + status + " message sent: "
                + sMessage + " to: " + recipientNodeName + " at: " + logicalClock);
        logicalClock++;
    }


    private void sendRequestMessage() {
        if (status == Status.Requesting && !isRequested) {
            for (Node sendNode : nodes) {
                if (!sendNode.name.equalsIgnoreCase(name)) {
                    String rMessage = name + ":" + id + ":" + MessageType.REQUEST + ":" + logicalClock;
                    messageSystem.setMessage(rMessage, sendNode.name);
                    System.out.println("Request Message :: Name: " + name + " id: " + id + " status: " + status + " message sent: "
                            + rMessage + " to: " + sendNode.name + " at: " + logicalClock);
                }
            }
            requestLogicalClock = logicalClock;
            isRequested = true;
        }
    }

    private void sendGO_AHEADMessage() {
        for (String sendNode : DeferredWhileCriticalState) {
            String rMessage = name + ":" + id + ":" + MessageType.GO_AHEAD + ":" + logicalClock;
            System.out.println("Send Message :: Name: " + name + " id: " + id + " status: " + status + " message sent: "
                    + rMessage + " to: " + sendNode + " at: " + logicalClock);
            messageSystem.setMessage(rMessage, sendNode);
            logicalClock++;
        }
    }


}
