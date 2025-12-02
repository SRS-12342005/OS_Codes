import java.util.concurrent.Semaphore;

public class ProducerConsumer {

    static int n = 5;
    static int[] buffer = new int[n];
    static int in = 0;
    static int out = 0;

    static Semaphore mutex = new Semaphore(1);
    static Semaphore empty = new Semaphore(n);
    static Semaphore full = new Semaphore(0);

    static class Producer implements Runnable {
        public void run() {
            try {
                int itemProduced = 0;
                while (true) {
                    itemProduced++;
                    empty.acquire();
                    mutex.acquire();

                    buffer[in] = itemProduced;
                    System.out.println("Producer added: " + itemProduced + " at index " + in);
                    in = (in + 1) % n;

                    mutex.release();
                    full.release();

                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Consumer implements Runnable {
        public void run() {
            try {
                while (true) {
                    full.acquire();
                    mutex.acquire();

                    int itemConsumed = buffer[out];
                    System.out.println("Consumer removed: " + itemConsumed + " from index " + out);
                    out = (out + 1) % n;

                    mutex.release();
                    empty.release();

                    Thread.sleep(1500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("--- Producer-Consumer Problem ---");
        System.out.println("Buffer Size: " + n);

        Thread prodThread = new Thread(new Producer());
        Thread consThread = new Thread(new Consumer());

        prodThread.start();
        consThread.start();
    }
}
