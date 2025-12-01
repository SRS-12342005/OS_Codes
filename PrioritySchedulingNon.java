import java.util.Scanner;

/*
 * Program: Non-Preemptive Priority Scheduling
 * Logic:
 * 1. We track 'currentTime'.
 * 2. We loop until all processes are finished.
 * 3. In every step, we look for a process that:
 * - Has Arrived (AT <= Current Time)
 * - Is NOT completed
 * - Has the Highest Priority (Smallest number = High Priority)
 * 4. We execute that process completely (Non-preemptive).
 */

public class PrioritySchedulingNon {

    static class Process {
        String id;
        int at;         // Arrival Time
        int bt;         // Burst Time
        int priority;   // Priority (1 is high, 10 is low)
        int ct;         // Completion Time
        int tat;        // Turnaround Time
        int wt;         // Waiting Time
        boolean isCompleted;
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
            System.out.print("  Enter ID: "); p[i].id = sc.next();
            System.out.print("  Enter AT: "); p[i].at = sc.nextInt();
            System.out.print("  Enter BT: "); p[i].bt = sc.nextInt();
            System.out.print("  Enter Priority (Lower # = Higher Priority): "); 
            p[i].priority = sc.nextInt();
            p[i].isCompleted = false;
        }

        // 2. LOGIC
        int currentTime = 0;
        int completedCount = 0;

        while (completedCount < n) {
            
            // Step A: Find the highest priority process available right now
            int idx = -1;
            int bestPriority = 999999; // Start with a generic low priority

            for (int i = 0; i < n; i++) {
                // Check if process has arrived AND is not done
                if (p[i].at <= currentTime && !p[i].isCompleted) {
                    
                    // Check if this process has better (lower) priority than what we found so far
                    if (p[i].priority < bestPriority) {
                        bestPriority = p[i].priority;
                        idx = i;
                    }
                    // Tie-breaker: If priorities are same, pick the one who arrived first (FCFS)
                    else if (p[i].priority == bestPriority) {
                        if (p[i].at < p[idx].at) {
                            idx = i;
                        }
                    }
                }
            }

            // Step B: Execute the chosen process
            if (idx != -1) {
                // Since it's Non-Preemptive, we finish the WHOLE process
                p[idx].ct = currentTime + p[idx].bt;
                p[idx].tat = p[idx].ct - p[idx].at;
                p[idx].wt = p[idx].tat - p[idx].bt;
                
                // Mark as done
                p[idx].isCompleted = true;
                completedCount++;
                
                // Jump time forward
                currentTime = p[idx].ct;
            } else {
                // If no process has arrived yet, just wait
                currentTime++;
            }
        }

        // 3. OUTPUT
        System.out.println("\n----------------------------------------------------------------------");
        System.out.printf("%-10s %-10s %-10s %-10s %-10s %-10s %-10s\n", 
                          "Process", "AT", "BT", "Priority", "CT", "TAT", "WT");
        System.out.println("----------------------------------------------------------------------");

        for (int i = 0; i < n; i++) {
            System.out.printf("%-10s %-10d %-10d %-10d %-10d %-10d %-10d\n", 
                              p[i].id, p[i].at, p[i].bt, p[i].priority, p[i].ct, p[i].tat, p[i].wt);
        }
        
        sc.close();
    }
}