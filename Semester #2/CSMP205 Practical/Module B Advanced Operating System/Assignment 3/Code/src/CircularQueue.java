/* This is Circular Queue implementation using Queue */


import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class CircularQueue<T> implements Iterable<T> {
    private Queue<T> queue;
    private int maxSize;

    public CircularQueue(int maxSize) {
        this.maxSize = maxSize;
        this.queue = new LinkedList<>();
    }

    public void enqueue(T item) {
        if (queue.size() == maxSize) {
            queue.poll(); // Remove the oldest element if the queue is full
        }
        queue.offer(item);
    }

    @Override
    public Iterator<T> iterator() {
        return new CircularIterator();
    }
    private class CircularIterator implements Iterator<T> {
        private Iterator<T> iterator;

        public CircularIterator() {
            this.iterator = queue.iterator();
        }

        @Override
        public boolean hasNext() {
            return !queue.isEmpty();
        }

        @Override
        public T next() {
            if (!iterator.hasNext()) {
                iterator = queue.iterator(); // Reset iterator when reaching the end
            }
            return iterator.next();
        }

        @Override
        public void remove() {
            iterator.remove();
        }
    }
}