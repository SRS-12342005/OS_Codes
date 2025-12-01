import java.util.Scanner;

/*
 * Program: Preemptive Priority Scheduling (Higher # = Higher Priority)
 * Logic:
 * 1. Simulate time second by second.
 * 2. Every second, find the process with the HIGHEST Priority Number.
 * (e.g., Priority 40 runs before Priority 10).
 * 3. If a new process with a higher number arrives, switch to it (Preemption).
 */

public class PreemptivePriority {

    static class Process {
        String id;
        int at;             // Arrival Time
        int bt;             // Burst Time
        int priority;       // Priority (Higher # = Higher Priority)
        int remainingTime;  // Time left to finish
        int ct;             // Completion Time
        int tat;            // Turnaround Time
        int wt;             // Waiting Time
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
            System.out.print("  Enter Priority (Higher # = Higher Priority): "); 
            p[i].priority = sc.nextInt();
            
            // Initially, remaining time is the same as burst time
            p[i].remainingTime = p[i].bt;
            p[i].isCompleted = false;
        }

        // 2. LOGIC (Second-by-Second Simulation)
        int currentTime = 0;
        int completedCount = 0;

        while (completedCount < n) {
            
            // Step A: Find the highest priority process available RIGHT NOW
            int idx = -1;
            int bestPriority = -1; // Start with -1 so any positive priority is better

            for (int i = 0; i < n; i++) {
                // Check if process has arrived AND is not done
                if (p[i].at <= currentTime && !p[i].isCompleted) {
                    
                    // CHANGED LOGIC: Check for HIGHER number (Greater Than)
                    if (p[i].priority > bestPriority) {
                        bestPriority = p[i].priority;
                        idx = i;
                    }
                    // Tie-breaker: If priorities are same, pick the one who arrived first
                    else if (p[i].priority == bestPriority) {
                        if (p[i].at < p[idx].at) {
                            idx = i;
                        }
                    }
                }
            }

            // Step B: Run the chosen process for ONE second
            if (idx != -1) {
                p[idx].remainingTime--;
                currentTime++;

                // Step C: Check if it finished
                if (p[idx].remainingTime == 0) {
                    p[idx].ct = currentTime;
                    p[idx].tat = p[idx].ct - p[idx].at;
                    p[idx].wt = p[idx].tat - p[idx].bt;
                    
                    p[idx].isCompleted = true;
                    completedCount++;
                }
            } 
            else {
                // If no process has arrived yet, CPU is idle
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