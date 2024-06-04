package TheadsCommunication;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class Node implements Runnable {
	private String id;
	private String name;
	private Message messageSystem;
	private List<Node> nodes;
	private Set<String> messaged;
	private String sendMessage;
	private int logicalClock = 0;
	private Random random = new Random();
	private String status = "NON";
	private int ACS;
	private MessageType messageType;

	public Node(String id, String name, Message messageSystem, List<Node> nodes) {
		this.id = id;
		this.name = name;
		this.messageSystem = messageSystem;
		this.nodes = nodes;
		this.messaged = new CopyOnWriteArraySet<>(); // Thread-safe set
	}

	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			String msg = messageSystem.getMessage(name);
			if (msg != null) {
				processMessage(msg);
			}

			if (messaged.size() == nodes.size() - 1) {
				synchronized (messageSystem) {
					System.out.println(name + " is in critical state");
					try {
						status = "CS";
						Thread.sleep(random.nextInt(10000));
						messaged.clear();
						System.out.println(name + " is exiting critical state");
						ACS = logicalClock + 10;
						status = "NON";
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} 

			// 50% chance to send a message
			if (random.nextInt(2) == 1) {
				createAndSendMessage();
			}

			try {
				Thread.sleep(1000); // Sleep for 1 second
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}

	private void processMessage(String msg) {
		int colonIndex = msg.lastIndexOf(":");
		String[] parts = msg.split(":");

		if (parts.length >= 3) {
			String nodeNamePart = parts[0];
			String messageTypePart = parts[2];

			if (messageTypePart.equalsIgnoreCase("GO_AHEAD")) {
				messaged.add(nodeNamePart);
			}

			if (colonIndex != -1) {
				String logicalClockString = msg.substring(colonIndex + 1).trim();
				int receivedLogicalClock = Integer.parseInt(logicalClockString);
				logicalClock = Math.max(logicalClock, receivedLogicalClock) + 1;
				System.out.println(name + " received: " + msg + ", Logical Clock: " + logicalClock);
			} else {
				System.out.println("Invalid message format: " + msg);
			}
		}
	}

	private void createAndSendMessage() {
		createMessage();
		if (status.equalsIgnoreCase("CS")) {
			messageType = MessageType.DEFERRED;
		}
		
		Node recipient;
		do {
			recipient = nodes.get(random.nextInt(nodes.size()));
		} while (recipient.name.equals(name));

		logicalClock++;
		String fullMessage = sendMessage + ": " + logicalClock;
		messageSystem.setMessage(fullMessage, recipient.name);
		System.out.println(name + " sent: " + sendMessage + " to " + recipient.name + ", Logical Clock: " + logicalClock);
	}

	private void createMessage() {
		if (ACS == logicalClock) {
			messageType = MessageType.REQUESTED;
		} else {
			messageType = MessageType.GO_AHEAD;
		}
		sendMessage = name + ":" + id + ":" + messageType;
	}
}