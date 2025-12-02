import java.util.*;

public class LRU_PageReplacement {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("--- LRU Page Replacement Algorithm ---");
        
        System.out.print("Enter number of frames: ");
        int frames = sc.nextInt();

        System.out.print("Enter number of pages: ");
        int n = sc.nextInt();

        int[] pages = new int[n];
        System.out.println("Enter reference string (space separated): ");
        for (int i = 0; i < n; i++) {
            pages[i] = sc.nextInt();
        }

        ArrayList<Integer> memory = new ArrayList<>(frames);
        
        int pageFaults = 0;

        System.out.println("\nProcessing...");

        for (int i = 0; i < n; i++) {
            int currentPage = pages[i];

            if (memory.contains(currentPage)) {
                memory.remove(Integer.valueOf(currentPage)); 
                memory.add(currentPage);
                System.out.println("Page " + currentPage + ": HIT   -> Memory: " + memory);
            } 
            else {
                pageFaults++;

                if (memory.size() == frames) {
                    memory.remove(0);
                }

                memory.add(currentPage);
                System.out.println("Page " + currentPage + ": FAULT -> Memory: " + memory);
            }
        }

        System.out.println("\n----------------------------------");
        System.out.println("Total Page Faults: " + pageFaults);
        System.out.println("----------------------------------");
        
        sc.close();
    }
}
