import java.util.*;

public class OptimalPageReplacement {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("--- Optimal Page Replacement Algorithm ---");
        
        System.out.print("Enter number of frames: ");
        int frames = sc.nextInt();

        System.out.print("Enter number of pages: ");
        int n = sc.nextInt();

        int[] pages = new int[n];
        System.out.println("Enter reference string (space separated): ");
        for (int i = 0; i < n; i++) {
            pages[i] = sc.nextInt();
        }

        int[] memory = new int[frames];
        Arrays.fill(memory, -1);
        
        int count = 0;
        int pageFaults = 0;

        System.out.println("\nProcessing...");

        for (int i = 0; i < n; i++) {
            int currentPage = pages[i];
            boolean isHit = false;

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
                pageFaults++;

                if (count < frames) {
                    memory[count] = currentPage;
                    count++;
                } 
                else {
                    int victimIndex = -1;
                    int farthestDistance = -1;

                    for (int j = 0; j < frames; j++) {
                        int memPage = memory[j];
                        int nextUse = -1;

                        for (int k = i + 1; k < n; k++) {
                            if (pages[k] == memPage) {
                                nextUse = k;
                                break;
                            }
                        }

                        if (nextUse == -1) {
                            victimIndex = j;
                            break; 
                        }

                        if (nextUse > farthestDistance) {
                            farthestDistance = nextUse;
                            victimIndex = j;
                        }
                    }

                    memory[victimIndex] = currentPage;
                }
                
                System.out.println("Page " + currentPage + ": FAULT -> Memory: " + Arrays.toString(memory));
            }
        }

        System.out.println("\n----------------------------------");
        System.out.println("Total Page Faults: " + pageFaults);
        System.out.println("----------------------------------");
        
        sc.close();
    }
}
