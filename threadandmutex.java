import java.util.concurrent.Semaphore;

public class threadandmutex {

    // --- Variables from your image ---
    static int rc = 0;               // Read Count
    static Semaphore mutex = new Semaphore(1); // Protects 'rc'
    static Semaphore db = new Semaphore(1);    // Protects 'DB' (Database)

    // ------------------------------------------------------------------
    // READER (Matches the left side of your image)
    // ------------------------------------------------------------------
    static class Reader implements Runnable {
        public void run() {
            try {
                while (true) {
                    // --- Entry Section ---
                    mutex.acquire();          // down(mutex)
                    rc = rc + 1;              // rc = rc + 1
                    if (rc == 1) {
                        db.acquire();         // if (rc==1) then down(db)
                    }
                    mutex.release();          // up(mutex)

                    // --- CRITICAL SECTION (Reading DB) ---
                    System.out.println("Reader is reading...");
                    Thread.sleep(1000);

                    // --- Exit Section ---
                    mutex.acquire();          // down(mutex)
                    rc = rc - 1;              // rc = rc - 1
                    if (rc == 0) {
                        db.release();         // if (rc==0) then up(db)
                    }
                    mutex.release();          // up(mutex)
                    
                    // --- Process Data (Non-critical) ---
                    Thread.sleep(500); 
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // ------------------------------------------------------------------
    // WRITER (Matches the right side of your image)
    // ------------------------------------------------------------------
    static class Writer implements Runnable {
        public void run() {
            try {
                while (true) {
                    // --- Entry Section ---
                    db.acquire();             // down(db)

                    // --- CRITICAL SECTION (Writing DB) ---
                    System.out.println("Writer is writing...");
                    Thread.sleep(1000);

                    // --- Exit Section ---
                    db.release();             // up(db)
                    
                    Thread.sleep(1000); // Wait a bit before writing again
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        // Create and start threads
        new Thread(new Reader()).start();
        new Thread(new Writer()).start();
    }
}