import java.util.concurrent.Semaphore;

public class threadandmutex {

    static int rc = 0;
    static Semaphore mutex = new Semaphore(1);
    static Semaphore db = new Semaphore(1);

    static class Reader implements Runnable {
        public void run() {
            try {
                while (true) {
                    mutex.acquire();
                    rc = rc + 1;
                    if (rc == 1) {
                        db.acquire();
                    }
                    mutex.release();

                    System.out.println("Reader is reading...");
                    Thread.sleep(1000);

                    mutex.acquire();
                    rc = rc - 1;
                    if (rc == 0) {
                        db.release();
                    }
                    mutex.release();

                    Thread.sleep(500); 
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Writer implements Runnable {
        public void run() {
            try {
                while (true) {
                    db.acquire();

                    System.out.println("Writer is writing...");
                    Thread.sleep(1000);

                    db.release();
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new Reader()).start();
        new Thread(new Writer()).start();
    }
}
