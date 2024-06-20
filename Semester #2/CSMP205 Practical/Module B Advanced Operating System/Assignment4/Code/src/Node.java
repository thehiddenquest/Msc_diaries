/* About node.java
        The Node class models individual nodes within a distributed system, where each node communicates with its parent node 
        to coordinate access to critical sections using token-based synchronization. Key attributes include a unique identifier 
        (id), name (nodeName), hierarchical level (level), parent reference (parent), 
        current status (Status.NONE, Status.REQUESTING, Status.PHOLD, Status.ABORT), and various counters and queues for managing requests 
        and abort conditions. The class implements the Runnable interface to support concurrent execution, encapsulating logic for requesting and sending tokens,
        updating statuses based on probabilistic events, and managing time spent in critical states. Nodes interact exclusively 
        with their parent node to regulate access to shared resources, transitioning states dynamically based on internal conditions and external interactions.
        This setup enables nodes to operate autonomously yet collaboratively within the distributed system, demonstrating fundamental 
        concepts of mutual exclusion and distributed synchronization.
*/

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Node implements Runnable {
    private int id;
    private String nodeName;
    private int level;
    private Node parent;
    private Status status = Status.NONE; // Current status of the node
    private Random random = new Random(); // Random number generator for probabilistic behavior
    private int InCriticalStateClock = -1; // Clock for managing time in critical state
    private int abortCounter = 0; // Counter for abort conditions
    private int rootAbortCounter = 0; // Counter for root abort conditions
    private Queue<Node> requestQueue = new ConcurrentLinkedQueue<>(); // Queue for holding requests
    private Boolean isRequested = false; // Flag indicating if a request has been made

    // Constructor to initialize the node with an ID, name, and level
    public Node(int id, String nodeName, int level) {
        this.id = id;
        this.nodeName = nodeName;
        this.level = level;
    }

    // Getter for the node's level
    public int getLevel() {
        return level;
    }

    // Getter for the node's ID
    public int getId() {
        return id;
    }

    // Method to set the parent node
    public void addParent(Node parent) {
        this.parent = parent;
    }

    // Getter for the node's name
    public String getName() {
        return nodeName;
    }

    // Getter for the ID of the parent node
    public Integer getParentID() {
        return (parent != null ? parent.getId() : 0);
    }

    // Setter for the node's status
    public void setStatus(Status st) {
        this.status = st;
    }

    // Run method required by the Runnable interface
    @Override
    public synchronized void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                // Update the node's status and handle related logic
                setStatus();
                checkClock();

                // Node is requesting token from its parent
                if (status == Status.REQUESTING && !isRequested) {
                    requestQueue.add(this);
                    isRequested = true;
                    System.out.println("Request Message :: Node " + this.getName() + " [ " + status + " ] with ID "
                            + this.getId() + " is requesting token from " + this.parent.getName());
                    parent.requestToken(this); // Request token from parent
                }

                // Node is in a hold state and has requests in the queue
                if (status == Status.PHOLD && !requestQueue.isEmpty() && InCriticalStateClock <= 0) {
                    this.isRequested = false;
                    Node requestedNode = requestQueue.poll(); // Retrieve next request
                    if (requestedNode.getId() == this.id) {
                        // Node enters critical state
                        System.out.println("Information Message :: Node " + requestedNode.getName() + " with ID "
                                + requestedNode.getId() + " is in critical state");
                        InCriticalStateClock = random.nextInt(21) + 10; // Set clock for critical state
                        this.addParent(null); // Node has no parent in critical state
                    } else {
                        // Node sends token to requested node
                        System.out.println("Send Message :: Node " + this.getName() + " [ " + status + " ] with ID "
                                + this.getId() + " is sending token to " + requestedNode.getName());
                        requestedNode.sendToken(); // Send token to requested node
                        System.out.println("Information Message :: Node " + requestedNode.getName() + " with ID "
                                + requestedNode.getId() + " is now parent of " + getName());
                        this.addParent(requestedNode); // Set requested node as parent
                        if (!requestQueue.isEmpty()) {
                            // Node has more requests, continue requesting
                            this.setStatus(Status.REQUESTING);
                            isRequested = true;
                            System.out.println("Request Message :: Node " + this.getName() + " [ " + status
                                    + " ] with ID " + this.getId() + " is requesting token back from "
                                    + this.parent.getName() + " to send " + requestQueue.peek().getName());
                            parent.requestToken(this); // Request token from parent
                        } else {
                            this.setStatus(Status.NONE); // No more requests, set status to NONE
                        }
                    }
                }

                Thread.sleep(600); // Adjust delay time as needed
                // Node is terminated due to abort status
                if (status == Status.ABORT) {
                    System.out.println("Termination Message :: Node " + nodeName + " with ID: " + id
                            + " has been terminated due to an empty request queue.");
                    Thread.currentThread().interrupt(); // Interrupt current thread
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace for any exceptions
        }
    }

    // Method for requesting token from parent node
    public void requestToken(Node child) throws InterruptedException {
        Thread.sleep(500); // Adjust delay time as needed
        requestQueue.add(child); // Add child node to request queue
        if (status == Status.NONE && !isRequested) {
            isRequested = true;
            System.out.println("Request Message :: Node " + this.getName() + " [ " + status + " ] with ID "
                    + this.getId() + " is requesting token from " + this.parent.getName());
            parent.requestToken(this); // Request token from parent
        }
    }

    // Method for sending token to child node
    public void sendToken() throws InterruptedException {
        Thread.sleep(500); // Adjust delay time as needed
        this.setStatus(Status.PHOLD); // Set node's status to PHOLD (holding token)
    }

    // Method to update the node's status based on specific conditions
    private void setStatus() {
        // Update status if current status is NONE
        if (this.status == Status.NONE) {
            int randomValue = random.nextInt(1000); // Generate random value for probabilistic behavior
            if (randomValue < 20) { // 20 out of 1000 probability (2%) for requesting status
                this.setStatus(Status.REQUESTING);
            } else {
                if (requestQueue.isEmpty())
                    abortCounter++; // Increment abort counter if request queue is empty
                else
                    abortCounter = 0; // Reset abort counter if requests are pending
            }
            // Set node's status to ABORT if abort conditions are met
            if (abortCounter == 60) {
                this.setStatus(Status.ABORT);
            }
        }

        // Handle root node abort conditions when in PHOLD status and clock is 0
        if (status == Status.PHOLD && InCriticalStateClock == 0) {
            if (requestQueue.isEmpty())
                rootAbortCounter++; // Increment root abort counter if request queue is empty
            else
                rootAbortCounter = 0; // Reset root abort counter if requests are pending
            // Set node's status to ABORT if root abort conditions are met
            if (rootAbortCounter == 20) {
                this.setStatus(Status.ABORT);
            }
        }
        // Check if parent node is in abort status and update node's status accordingly
        if (parent != null) {
            if (this.status != Status.PHOLD && parent.status == Status.ABORT) {
                this.status = Status.ABORT;
            }
        }
    }

    // Method to decrement the critical state clock
    private void checkClock() {
        if (InCriticalStateClock != 0) {
            InCriticalStateClock--; // Decrement clock if node is in critical state
        }
    }
}