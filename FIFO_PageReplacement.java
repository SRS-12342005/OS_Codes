import java.util.*;

/*
 * Program: FIFO Page Replacement Algorithm
 * Logic:
 * 1. We have a set number of frames (memory slots).
 * 2. We traverse the "reference string" (sequence of page requests).
 * 3. If a page is NOT in memory (Miss):
 * - If memory is full, remove the oldest page (First In).
 * - Add the new page.
 * - Increment 'pageFaults'.
 * 4. If a page IS in memory (Hit): Do nothing.
 */

public class FIFO_PageReplacement {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("--- FIFO Page Replacement Algorithm ---");
        
        // 1. INPUT
        System.out.print("Enter number of frames (memory slots): ");
        int frames = sc.nextInt();

        System.out.print("Enter total number of pages in reference string: ");
        int n = sc.nextInt();

        int[] pages = new int[n];
        System.out.println("Enter the reference string (space separated): ");
        for (int i = 0; i < n; i++) {
            pages[i] = sc.nextInt();
        }

        // 2. LOGIC
        // HashSet helps us quickly check if a page exists in memory (O(1) lookup)
        HashSet<Integer> memorySet = new HashSet<>(frames);
        
        // Queue helps us determine which page to remove (First In, First Out)
        Queue<Integer> fifoQueue = new LinkedList<>();

        int pageFaults = 0;

        System.out.println("\nProcessing...");
        
        for (int i = 0; i < n; i++) {
            int currentPage = pages[i];

            // Case 1: Page is NOT in memory (Miss / Fault)
            if (!memorySet.contains(currentPage)) {
                
                // If memory is strictly full, remove the oldest one
                if (memorySet.size() == frames) {
                    int oldestPage = fifoQueue.poll(); // Remove from Queue
                    memorySet.remove(oldestPage);      // Remove from Set
                }

                // Add the new page
                memorySet.add(currentPage);
                fifoQueue.add(currentPage);
                pageFaults++;
                
                System.out.println("Page " + currentPage + ": FAULT -> Memory: " + fifoQueue);
            } 
            // Case 2: Page IS in memory (Hit)
            else {
                System.out.println("Page " + currentPage + ": HIT   -> Memory: " + fifoQueue);
            }
        }

        // 3. OUTPUT
        System.out.println("\n----------------------------------");
        System.out.println("Total Page Faults: " + pageFaults);
        System.out.println("----------------------------------");
        
        sc.close();
    }
}