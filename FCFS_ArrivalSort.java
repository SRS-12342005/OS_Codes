import java.util.Scanner;

/*
 * Program: FCFS Scheduling with Arrival Time Sorting
 * Logic:
 * 1. Store processes in a list/array.
 * 2. SORT them based on Arrival Time (AT).
 * 3. Calculate Completion Time (CT), TAT, and WT.
 * 4. Print the table.
 */

public class FCFS_ArrivalSort {

    // Process class is now inside the main class
    static class Process {
        String id;  // Process ID (P1, P2...)
        int at;     // Arrival Time
        int bt;     // Burst Time
        int ct;     // Completion Time
        int tat;    // Turnaround Time
        int wt;     // Waiting Time
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter total number of processes: ");
        int n = sc.nextInt();

        // Create an array of Process objects
        Process[] p = new Process[n];

        // 1. INPUT
        for (int i = 0; i < n; i++) {
            p[i] = new Process();
            System.out.print("  Enter Arrival Time: ");
            p[i].at = sc.nextInt();
            System.out.print("  Enter Burst Time:   ");
            p[i].bt = sc.nextInt();
        }

        // 2. SORT BY ARRIVAL TIME (Simple Bubble Sort)
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (p[j].at > p[j + 1].at) {
                    // Swap the processes
                    Process temp = p[j];
                    p[j] = p[j + 1];
                    p[j + 1] = temp;
                }
            }
        }

        // 3. CALCULATIONS
        int currentTime = 0;

        for (int i = 0; i < n; i++) {
            // Logic: The process starts when it arrives OR when the previous one finishes.
            if (p[i].at > currentTime) {
                currentTime = p[i].at;
            }

            // Completion Time (CT) = Start Time + Burst Time
            p[i].ct = currentTime + p[i].bt;
            
            // Update current time for the next process
            currentTime = p[i].ct;

            // Turnaround Time (TAT) = CT - AT
            p[i].tat = p[i].ct - p[i].at;

            // Waiting Time (WT) = TAT - BT
            p[i].wt = p[i].tat - p[i].bt;
        }

        // 4. OUTPUT
        System.out.println("\n---------------------------------------------------------");
        System.out.printf("%-10s %-10s %-10s %-10s %-10s %-10s\n", 
                          "Process", "AT", "BT", "CT", "TAT", "WT");
        System.out.println("---------------------------------------------------------");

        for (int i = 0; i < n; i++) {
            System.out.printf("%-10s %-10d %-10d %-10d %-10d %-10d\n", 
                              p[i].id, p[i].at, p[i].bt, p[i].ct, p[i].tat, p[i].wt);
        }
        
        sc.close();
    }
}