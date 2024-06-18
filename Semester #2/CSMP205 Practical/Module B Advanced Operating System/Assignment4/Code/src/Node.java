
// Acts a process in distributed system

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Node implements Runnable {
    private int id;
    private String nodeName;
    private int level;
    private Node parent;
    private Status status = Status.NONE;
    private Random random = new Random();
    private int InCriticalStateClock = -1;
    private int abortCounter = 0;
    private int rootAbortCounter = 0;
    private Queue<Node> requestQueue = new ConcurrentLinkedQueue<>();
    private Boolean isRequested = false;

    public Node(int id, String nodeName, int level) {
        this.id = id;
        this.nodeName = nodeName;
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public int getId() {
        return id;
    }

    public void addParent(Node parent) {
        this.parent = parent;
    }

    public String getName() {
        return nodeName;
    }

    public Integer getParentID() {
        return (parent != null ? parent.getId() : 0);
    }

    public void setStatus(Status st) {
        this.status = st;
    }

    @Override
    public synchronized void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                // System.out.println("node " + nodeName + " : " + status + " is here " +
                // requestQueue.isEmpty());
                setStatus();
                checkClock();
                if (status == Status.REQUESTING && !isRequested) {
                    requestQueue.add(this);
                    isRequested = true;
                    System.out.println("Request Message :: Node " + this.getName() + " [ " + status + " ] with ID "
                            + this.getId() + " is requesting token from " + this.parent.getName());
                    parent.requestToken(this);

                }

                if (status == Status.PHOLD && !requestQueue.isEmpty() && InCriticalStateClock <= 0) {
                    this.isRequested = false;
                    Node requestedNode = requestQueue.poll();
                    if (requestedNode.getId() == this.id) {
                        System.out.println("Information Message :: Node " + requestedNode.getName() + " with ID "
                                + requestedNode.getId() + " is in critical state");
                        InCriticalStateClock = random.nextInt(21) + 10;
                        this.addParent(null);
                    } else {
                        System.out
                                .println("Send Message :: Node " + this.getName() + " [ " + status + " ] with ID "
                                        + this.getId() + " is sending token to " + requestedNode.getName());
                        requestedNode.sendToken();
                        System.out.println("Information Message :: Node " + requestedNode.getName() + " with ID "
                                + requestedNode.getId() + " is now parent of  " + getName());
                        this.addParent(requestedNode);
                        if (!requestQueue.isEmpty()) {
                            this.setStatus(Status.REQUESTING);
                            isRequested = true;
                            System.out
                                    .println("Request Message :: Node " + this.getName() + " [ " + status
                                            + " ] with ID "
                                            + this.getId() + " is requesting token back from " + this.parent.getName()
                                            + " to send " + requestQueue.peek().getName());

                            parent.requestToken(this);

                        } else {
                            this.setStatus(Status.NONE);
                        }
                    }
                }
                Thread.sleep(1000); // Adjust delay time as needed
                if (status == Status.ABORT) {
                    System.out.println("Termination Message :: Node " + nodeName + " with ID: " + id
                            + " has been terminated due to an empty request queue.");
                    Thread.currentThread().interrupt();
                }
            }
            // Implement the logic of your Node's task here
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void requestToken(Node child) throws InterruptedException {
        Thread.sleep(500); // Adjust delay time as needed
        requestQueue.add(child);
        if (status == Status.NONE && !isRequested) {
            isRequested = true;
            System.out.println("Request Message :: Node " + this.getName() + " [ " + status + " ] with ID "
                    + this.getId() + " is requesting token from " + this.parent.getName());
            parent.requestToken(this);

        }
    }

    public void sendToken() throws InterruptedException {
        Thread.sleep(500); // Adjust delay time as needed
        this.setStatus(Status.PHOLD);

    }

    private void setStatus() {

        if (this.status == Status.NONE) {
            int randomValue = random.nextInt(1000); // Generate one random value in a range that covers all
                                                    // probabilities
            if (randomValue < 20) { // 100 out of 1000 is equivalent to 10%
                this.setStatus(Status.REQUESTING);
            } else {
                if (requestQueue.isEmpty())
                    abortCounter++;
                else
                    abortCounter = 0;
            }
            if (abortCounter == 60) {
                this.setStatus(Status.ABORT);
            }
        }
        if (status == Status.PHOLD && InCriticalStateClock == 0) {
            if (requestQueue.isEmpty())
                rootAbortCounter++;
            else
                rootAbortCounter = 0;
            if (rootAbortCounter == 20) {
                this.setStatus(Status.ABORT);
            }
        }
    }

    private void checkClock() {
        if (InCriticalStateClock != 0) {
            InCriticalStateClock--;
        }
    }
}