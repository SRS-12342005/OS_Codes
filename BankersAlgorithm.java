import java.util.Scanner;

/*
 * Program: Banker's Algorithm for Deadlock Avoidance
 * Logic:
 * 1. Calculate 'Need' matrix (Max - Allocation).
 * 2. Try to find a process whose Need <= Available Resources.
 * 3. If found, execute it (simulate), then reclaim its resources.
 * 4. Repeat until all are done (Safe) or stuck (Unsafe).
 */

public class BankersAlgorithm {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("--- Banker's Algorithm ---");

        // 1. INPUT
        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        System.out.print("Enter number of resource types: ");
        int m = sc.nextInt();

        int[][] allocation = new int[n][m];
        int[][] max = new int[n][m];
        int[][] need = new int[n][m];
        int[] available = new int[m];

        System.out.println("\nEnter Allocation Matrix:");
        for(int i=0; i<n; i++) {
            System.out.print("Process " + i + ": ");
            for(int j=0; j<m; j++) allocation[i][j] = sc.nextInt();
        }

        System.out.println("\nEnter Max Matrix:");
        for(int i=0; i<n; i++) {
            System.out.print("Process " + i + ": ");
            for(int j=0; j<m; j++) max[i][j] = sc.nextInt();
        }

        System.out.println("\nEnter Available Resources:");
        for(int j=0; j<m; j++) available[j] = sc.nextInt();

        // 2. CALCULATE NEED MATRIX
        // Need = Max - Allocation
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                need[i][j] = max[i][j] - allocation[i][j];
            }
        }

        // 3. SAFETY ALGORITHM
        boolean[] finish = new boolean[n]; // Tracks who is done
        int[] safeSequence = new int[n];
        int count = 0; // How many processes finished

        // Make a copy of available resources so we don't mess up the original input
        int[] work = new int[m];
        for (int j = 0; j < m; j++) work[j] = available[j];

        while (count < n) {
            boolean found = false;

            // Try to find a process 'i' that is NOT finished 
            // and whose needs can be satisfied by 'work'
            for (int i = 0; i < n; i++) {
                if (!finish[i]) {
                    
                    // Check if Need[i] <= Work
                    boolean canProceed = true;
                    for (int j = 0; j < m; j++) {
                        if (need[i][j] > work[j]) {
                            canProceed = false;
                            break;
                        }
                    }

                    // If we can satisfy needs, "Execute" the process
                    if (canProceed) {
                        // Simulate execution: It finishes and returns allocated resources
                        for (int j = 0; j < m; j++) {
                            work[j] += allocation[i][j];
                        }
                        
                        safeSequence[count++] = i;
                        finish[i] = true;
                        found = true;
                    }
                }
            }

            // If we went through all processes and couldn't run ANY of them,
            // then we are stuck (Unsafe State).
            if (!found) {
                break;
            }
        }

        // 4. OUTPUT
        if (count == n) {
            System.out.println("\nSystem is in SAFE State.");
            System.out.print("Safe Sequence: ");
            for (int i = 0; i < n; i++) {
                System.out.print("P" + safeSequence[i]);
                if (i < n - 1) System.out.print(" -> ");
            }
            System.out.println();
        } else {
            System.out.println("\nSystem is in UNSAFE State (Deadlock Possible).");
        }
        
        sc.close();
    }
}