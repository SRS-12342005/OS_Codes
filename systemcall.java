import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class systemcall {

    public static void main(String[] args) {
        System.out.println("--- System Call Demonstration (Java) ---");
        
        String filename = "demo_java.txt";

        try {
            FileWriter writer = new FileWriter(filename);
            String msg = "Hello! This is a message from Java File IO.";
            writer.write(msg);
            writer.close();
            System.out.println("[Main] File '" + filename + "' written successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        Thread childProcess = new Thread(() -> {
            System.out.println("[Child] Process started. Reading file...");
            try {
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

        childProcess.start();

        try {
            System.out.println("[Parent] Waiting for child to finish...");
            childProcess.join(); 
            System.out.println("[Parent] Child has completed. Exiting.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
