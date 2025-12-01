import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

/*
 * Program: Demonstrate System Calls Logic in Java
 *
 * Mapping C to Java:
 * 1. open/write/close  -> FileWriter
 * 2. fork              -> Thread (start)
 * 3. read              -> Scanner (readFile)
 * 4. wait              -> Thread (join)
 */
public class systemcall {

    public static void main(String[] args) {
        System.out.println("--- System Call Demonstration (Java) ---");
        
        String filename = "demo_java.txt";

        // ==========================================
        // STEP 1: FILE OPERATIONS (Write to file)
        // Equivalent to: open(), write(), close()
        // ==========================================
        try {
            // 1. Create/Open the file
            FileWriter writer = new FileWriter(filename);
            
            // 2. Write the message
            String msg = "Hello! This is a message from Java File IO.";
            writer.write(msg);
            
            // 3. Close the file
            writer.close();
            System.out.println("[Main] File '" + filename + "' written successfully.");

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        // ==========================================
        // STEP 2: PROCESS LOGIC (Thread as Child)
        // Equivalent to: fork(), wait()
        // ==========================================

        // 4. Create the "Child" (Thread)
        // This simulates fork() by defining what the child will do.
        Thread childProcess = new Thread(() -> {
            System.out.println("[Child] Process started. Reading file...");
            
            try {
                // Equivalent to: open(), read(), close()
                File file = new File(filename);
                Scanner scanner = new Scanner(file);
                
                while (scanner.hasNextLine()) {
                    String data = scanner.nextLine();
                    System.out.println("[Child] Read Content: " + data);
                }
                
                scanner.close();
                System.out.println("[Child] Process finished.");
                
            } catch (Exception e) {
                System.out.println("[Child] Error reading file.");
            }
        });

        // Start the child (Equivalent to the actual split in fork)
        childProcess.start();

        // 5. PARENT WAITS
        // Equivalent to: wait()
        try {
            System.out.println("[Parent] Waiting for child to finish...");
            
            // join() forces the Main thread to wait until 'childProcess' is done
            childProcess.join(); 
            
            System.out.println("[Parent] Child has completed. Exiting.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}