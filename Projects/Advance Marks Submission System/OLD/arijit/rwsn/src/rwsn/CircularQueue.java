package rwsn;

public class CircularQueue {

	public CircularQueue() {
		front = -1;
	    rear = -1;
	}
	 
	  int front, rear;
	  Sensor items[] = new Sensor[1];

	  // Check if the queue is full
	  boolean isFull() {
	    if (front == 0 && rear == items.length) {
	      return true;
	    }
	    if (front == rear + 1) {
	      return true;
	    }
	    return false;
	  }

	  // Check if the queue is empty
	  boolean isEmpty() {
	    if (front == -1)
	      return true;
	    else
	      return false;
	  }

	  // Adding an element
	  void enQueue(Sensor element) {
	    if (isFull()) {
	      System.out.println("Queue is full");
	    } 
	    else {
	      if (front == -1)
	        front = 0;
	      rear = (rear + 1) % items.length;
	      items[rear] = element;
	      System.out.println("Inserted " + element.id);
	    }
	  }

	  // Removing an element
	  Sensor deQueue() {
	    Sensor element;
	    if (isEmpty()) {
	      System.out.println("Queue is empty");
	      return null;
	    } 
	    else {
	      element = items[front];
	      if (front == rear) {
	        front = -1;
	        rear = -1;
	      } /* Q has only one element, so we reset the queue after deleting it. */
	      else {
	        front = (front + 1) % items.length;
	      }
	      return (element);
	    }
	  }

	  void display() {
	    /* Function to display status of Circular Queue */
	    int i;
	    if (isEmpty()) {
	      System.out.println("Empty Queue");
	    } 
	    else {
	      System.out.println("Front -> " + front);
	      System.out.println("Items -> ");
	      for (i = front; i != rear; i = (i + 1) % items.length)
	        System.out.print(items[i] + " ");
	      System.out.println(items[i]);
	      System.out.println("Rear -> " + rear);
	    }
	  }
}
