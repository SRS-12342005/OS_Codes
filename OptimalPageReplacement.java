import java.util.*;

/*
 * Program: Optimal Page Replacement Algorithm
 * Logic:
 * 1. Look ahead to see which page currently in memory is used LAST (farthest in future).
 * 2. Replace that page.
 * 3. This guarantees the minimum possible page faults.
 */

public class OptimalPageReplacement {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("--- Optimal Page Replacement Algorithm ---");
        
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
        // Using an Array for memory to easily swap specific indices
        int[] memory = new int[frames];
        // Initialize memory with -1 (empty)
        Arrays.fill(memory, -1);
        
        int count = 0; // Pages currently in memory
        int pageFaults = 0;

        System.out.println("\nProcessing...");

        for (int i = 0; i < n; i++) {
            int currentPage = pages[i];
            boolean isHit = false;

            // CHECK: Is the page already in memory?
            for (int m : memory) {
                if (m == currentPage) {
                    isHit = true;
                    break;
                }
            }

            if (isHit) {
                System.out.println("Page " + currentPage + ": HIT   -> Memory: " + Arrays.toString(memory));
            } 
            else {
                // --- FAULT CASE ---
                pageFaults++;

                // A. If memory is NOT full, just add it
                if (count < frames) {
                    memory[count] = currentPage;
                    count++;
                } 
                // B. If memory IS FULL, finding the "Optimal" victim
                else {
                    int victimIndex = -1;
                    int farthestDistance = -1;

                    // Check every page currently in memory
                    for (int j = 0; j < frames; j++) {
                        int memPage = memory[j];
                        int nextUse = -1;

                        // Look AHEAD in the string to find when this page is used next
                        for (int k = i + 1; k < n; k++) {
                            if (pages[k] == memPage) {
                                nextUse = k;
                                break;
                            }
                        }

                        // If page is never used again, it's the perfect victim
                        if (nextUse == -1) {
                            victimIndex = j;
                            break; 
                        }

                        // Otherwise, find the one used farthest in the future
                        if (nextUse > farthestDistance) {
                            farthestDistance = nextUse;
                            victimIndex = j;
                        }
                    }

                    // Replace the victim
                    memory[victimIndex] = currentPage;
                }
                
                System.out.println("Page " + currentPage + ": FAULT -> Memory: " + Arrays.toString(memory));
            }
        }

        // 3. OUTPUT
        System.out.println("\n----------------------------------");
        System.out.println("Total Page Faults: " + pageFaults);
        System.out.println("----------------------------------");
        
        sc.close();
    }
}