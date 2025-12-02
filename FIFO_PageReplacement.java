import java.util.*;

public class FIFO_PageReplacement {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("--- FIFO Page Replacement Algorithm ---");
        
        System.out.print("Enter number of frames (memory slots): ");
        int frames = sc.nextInt();

        System.out.print("Enter total number of pages in reference string: ");
        int n = sc.nextInt();

        int[] pages = new int[n];
        System.out.println("Enter the reference string (space separated): ");
        for (int i = 0; i < n; i++) {
            pages[i] = sc.nextInt();
        }

        HashSet<Integer> memorySet = new HashSet<>(frames);
        Queue<Integer> fifoQueue = new LinkedList<>();

        int pageFaults = 0;

        System.out.println("\nProcessing...");
        
        for (int i = 0; i < n; i++) {
            int currentPage = pages[i];

            if (!memorySet.contains(currentPage)) {
                if (memorySet.size() == frames) {
                    int oldestPage = fifoQueue.poll();
                    memorySet.remove(oldestPage);
                }

                memorySet.add(currentPage);
                fifoQueue.add(currentPage);
                pageFaults++;
                
                System.out.println("Page " + currentPage + ": FAULT -> Memory: " + fifoQueue);
            } 
            else {
                System.out.println("Page " + currentPage + ": HIT   -> Memory: " + fifoQueue);
            }
        }

        System.out.println("\n----------------------------------");
        System.out.println("Total Page Faults: " + pageFaults);
        System.out.println("----------------------------------");
        
        sc.close();
    }
}
