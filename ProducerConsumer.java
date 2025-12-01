import java.util.concurrent.Semaphore;
import java.util.Scanner;

/*
 * Program: Producer-Consumer Problem using Semaphores
 * Logic:
 * 1. Producer waits for an 'empty' slot, locks the buffer, adds data, then signals 'full'.
 * 2. Consumer waits for a 'full' slot, locks the buffer, takes data, then signals 'empty'.
 * 3. Use 'mutex' to ensure they don't touch the buffer at the exact same time.
 */

public class ProducerConsumer {

    // --- Shared Resources ---
    static int n = 5;                  // Buffer Size
    static int[] buffer = new int[n];  // The Buffer
    static int in = 0;                 // Index for Producer to add
    static int out = 0;                // Index for Consumer to remove
    
    // --- Semaphores ---
    // mutex: Lock for critical section (Init 1 = unlocked)
    static Semaphore mutex = new Semaphore(1);
    
    // empty: Count of empty slots (Init n = all empty)
    static Semaphore empty = new Semaphore(n);
    
    // full: Count of full slots (Init 0 = all empty)
    static Semaphore full = new Semaphore(0);

    // ------------------------------------------------------------------
    // PRODUCER THREAD
    // ------------------------------------------------------------------
    static class Producer implements Runnable {
        public void run() {
            try {
                int itemProduced = 0;
                while (true) {
                    itemProduced++;
                    
                    // 1. Wait for an empty slot
                    empty.acquire(); 
                    
                    // 2. Lock the buffer (Entry Section)
                    mutex.acquire();

                    // --- CRITICAL SECTION ---
                    buffer[in] = itemProduced;
                    System.out.println("Producer added: " + itemProduced + " at index " + in);
                    in = (in + 1) % n; // Circular increment
                    // ------------------------

                    // 3. Unlock the buffer (Exit Section)
                    mutex.release();
                    
                    // 4. Signal that a new slot is full
                    full.release();

                    Thread.sleep(1000); // Simulate work
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // ------------------------------------------------------------------
    // CONSUMER THREAD
    // ------------------------------------------------------------------
    static class Consumer implements Runnable {
        public void run() {
            try {
                while (true) {
                    // 1. Wait for a full slot (Wait for data)
                    full.acquire(); 
                    
                    // 2. Lock the buffer (Entry Section)
                    mutex.acquire();

                    // --- CRITICAL SECTION ---
                    int itemConsumed = buffer[out];
                    System.out.println("Consumer removed: " + itemConsumed + " from index " + out);
                    out = (out + 1) % n; // Circular increment
                    // ------------------------

                    // 3. Unlock the buffer (Exit Section)
                    mutex.release();
                    
                    // 4. Signal that a slot is now empty
                    empty.release();

                    Thread.sleep(1500); // Simulate consuming time
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // ------------------------------------------------------------------
    // MAIN METHOD
    // ------------------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("--- Producer-Consumer Problem ---");
        System.out.println("Buffer Size: " + n);

        // Create threads
        Thread prodThread = new Thread(new Producer());
        Thread consThread = new Thread(new Consumer());

        // Start threads
        prodThread.start();
        consThread.start();
    }
}