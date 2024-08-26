public class Helloworld {

    static class HelloWorldThread extends Thread {
        private final int id;

        public HelloWorldThread(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            System.out.println("Hello World from thread " + id);
        }
    }

    public static void main(String[] args) {
        int numThreads = Runtime.getRuntime().availableProcessors(); // Get number of available cores
        System.out.println(String.valueOf(numThreads));
        Thread[] threads = new Thread[numThreads];

        // Create and start threads
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new HelloWorldThread(i);
            threads[i].start();
        }

        // Wait for all threads to complete
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("All threads have finished executing.");
    }
}