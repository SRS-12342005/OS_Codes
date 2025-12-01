import java.util.Scanner;

/*
 * Program: SRTF (Shortest Remaining Time First) - Preemptive SJF
 * Logic:
 * 1. Instead of running a process fully, we run it for ONE second at a time.
 * 2. Every second, we check: "Is there a new process with less time remaining?"
 * 3. If yes, we switch to that new process.
 * 4. We keep track of 'remainingTime' for every process.
 */

public class SRTF_Scheduling {

    static class Process {
        String id;
        int at;             // Arrival Time
        int bt;             // Burst Time
        int remainingTime;  // Time left to finish (starts equal to bt)
        int ct;             // Completion Time
        int tat;            // Turnaround Time
        int wt;             // Waiting Time
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter total number of processes: ");
        int n = sc.nextInt();

        Process[] p = new Process[n];

        // 1. INPUT
        for (int i = 0; i < n; i++) {
            p[i] = new Process();
            System.out.println("\nProcess " + (i + 1) + ":");
            System.out.print("  Enter Process ID (e.g. P1): ");
            p[i].id = sc.next();
            System.out.print("  Enter Arrival Time: ");
            p[i].at = sc.nextInt();
            System.out.print("  Enter Burst Time:   ");
            p[i].bt = sc.nextInt();
            // Initially, remaining time is the same as burst time
            p[i].remainingTime = p[i].bt;
        }

        // 2. LOGIC (Second by Second simulation)
        int currentTime = 0;
        int completedCount = 0;

        // We assume a huge number for "shortest time" initially
        // so the first real process we find will definitely be smaller.
        
        while (completedCount < n) {
            int shortestIndex = -1;
            int minRemaining = 999999; 

            // STEP A: Find the process with the smallest remaining time
            // that has already arrived (at <= currentTime) and is not done (remaining > 0)
            for (int i = 0; i < n; i++) {
                if (p[i].at <= currentTime && p[i].remainingTime > 0) {
                    if (p[i].remainingTime < minRemaining) {
                        minRemaining = p[i].remainingTime;
                        shortestIndex = i;
                    }
                    // Tie-breaker: If remaining times are equal, pick the one who arrived first
                    else if (p[i].remainingTime == minRemaining) {
                        if (p[i].at < p[shortestIndex].at) {
                            shortestIndex = i;
                        }
                    }
                }
            }

            // STEP B: If we found a process to run
            if (shortestIndex != -1) {
                // Run the process for 1 unit of time
                p[shortestIndex].remainingTime--;
                currentTime++;

                // Check if it just finished
                if (p[shortestIndex].remainingTime == 0) {
                    completedCount++;
                    p[shortestIndex].ct = currentTime;
                    
                    // Formulas from your image
                    p[shortestIndex].tat = p[shortestIndex].ct - p[shortestIndex].at;
                    p[shortestIndex].wt = p[shortestIndex].tat - p[shortestIndex].bt;
                }
            } 
            else {
                // No process has arrived yet (CPU is idle)
                currentTime++;
            }
        }

        // 3. OUTPUT
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