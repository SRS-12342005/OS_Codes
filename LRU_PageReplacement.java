import java.util.*;

/*
 * Program: LRU (Least Recently Used) Page Replacement Algorithm
 * Logic:
 * 1. Maintain a list of pages in memory.
 * 2. Position rule: Index 0 is the Least Recent; Last Index is Most Recent.
 * 3. Hit: Remove the page and add it to the end (Refresh).
 * 4. Fault: If full, remove Index 0. Add new page to the end.
 */

public class LRU_PageReplacement {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("--- LRU Page Replacement Algorithm ---");
        
        // 1. INPUT
        System.out.print("Enter number of frames: ");
        int frames = sc.nextInt();

        System.out.print("Enter number of pages: ");
        int n = sc.nextInt();

        int[] pages = new int[n];
        System.out.println("Enter reference string (space separated): ");
        for (int i = 0; i < n; i++) {
            pages[i] = sc.nextInt();
        }

        // 2. LOGIC
        // We use ArrayList because we need to remove from the middle (for hits)
        ArrayList<Integer> memory = new ArrayList<>(frames);
        
        int pageFaults = 0;

        System.out.println("\nProcessing...");

        for (int i = 0; i < n; i++) {
            int currentPage = pages[i];

            // CHECK: Is the page already in memory?
            if (memory.contains(currentPage)) {
                // --- HIT CASE ---
                // Logic: It's a Hit, so this page is now "Most Recently Used".
                // We remove it from its current spot and add it to the end.
                memory.remove(Integer.valueOf(currentPage)); 
                memory.add(currentPage);
                
                System.out.println("Page " + currentPage + ": HIT   -> Memory: " + memory);
            } 
            else {
                // --- FAULT CASE ---
                pageFaults++;

                // If memory is full, remove the Least Recently Used (Index 0)
                if (memory.size() == frames) {
                    memory.remove(0);
                }

                // Add the new page to the end (Most Recently Used position)
                memory.add(currentPage);
                
                System.out.println("Page " + currentPage + ": FAULT -> Memory: " + memory);
            }
        }

        // 3. OUTPUT
        System.out.println("\n----------------------------------");
        System.out.println("Total Page Faults: " + pageFaults);
        System.out.println("----------------------------------");
        
        sc.close();
    }
}