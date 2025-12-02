import java.util.Scanner;

public class BankersAlgorithm {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("--- Banker's Algorithm ---");
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

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                need[i][j] = max[i][j] - allocation[i][j];
            }
        }

        boolean[] finish = new boolean[n];
        int[] safeSequence = new int[n];
        int count = 0; 

        int[] work = new int[m];
        for (int j = 0; j < m; j++) work[j] = available[j];

        while (count < n) {
            boolean found = false;

            for (int i = 0; i < n; i++) {
                if (!finish[i]) {
                    boolean canProceed = true;
                    for (int j = 0; j < m; j++) {
                        if (need[i][j] > work[j]) {
                            canProceed = false;
                            break;
                        }
                    }
                    if (canProceed) {
                        for (int j = 0; j < m; j++) {
                            work[j] += allocation[i][j];
                        }
                        
                        safeSequence[count++] = i;
                        finish[i] = true;
                        found = true;
                    }
                }
            }

            if (!found) {
                break;
            }
        }

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
